package frc.team5119.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team5119.robot.Constants;
import frc.team5119.robot.RobotMap;
import frc.team5119.robot.util.Odometry;

/**
 *
 */
public class Drivetrain extends Subsystem {

    public Side left;
    public Side right;
    public Odometry odo;
    public AHRS ahrs;

    private boolean isRamping = false;
	
    public void initDefaultCommand() {}

    public void init() {
        left = new Side("left");
        right = new Side("right");
        odo = new Odometry(this);
        ahrs = new AHRS(SPI.Port.kMXP);
    }

    public void startRamping() {
        if (!isRamping) {
            left.startRamping();
            right.startRamping();
            isRamping = true;
        }
    }

    public void stopRamping() {
        if (isRamping) {
            left.stopRamping();
            right.stopRamping();
            isRamping = false;
        }
    }

    public void startPID() {
        left.startPID();
        right.startPID();
    }

    public void stopPID() {
        left.stopPID();
        right.stopPID();
    }

    public boolean isRamping() {
        return isRamping;
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

        if (xSpeed >= 0.0) {
            // First quadrant, else second quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            } else {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            }
        }

        left.set(leftMotorOutput);
        right.set(-rightMotorOutput);

    }

    public class Side {

        private final TalonSRX front, back;
        private final Encoder quadrature;
        public volatile double PIDSetpoint = 0;
        private volatile double lastRate = 0;

        private Notifier PIDLoop = new Notifier(() -> {
            double rate = getRate();
            double output = Constants.kp * (PIDSetpoint - rate);
            output -= Constants.kd * (rate - lastRate);
            set(output);
            lastRate = rate;
        });

        Side(String side) {
            if (side.equals("left")) {
                front = new TalonSRX(RobotMap.frontLeftTalon);
                back = new TalonSRX(RobotMap.backLeftTalon);
                quadrature = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false);
            } else {
                front = new TalonSRX(RobotMap.frontRightTalon);
                back = new TalonSRX(RobotMap.backRightTalon);
                quadrature = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);
            }

            quadrature.setDistancePerPulse(2 * Math.PI/2048.0); //should make getRate() return rad/s
        }

        public void set(double percentOutput) {
            percentOutput = Math.max(-1, Math.min(1, percentOutput));
            front.set(ControlMode.PercentOutput, percentOutput);
            back.set(ControlMode.PercentOutput, percentOutput);
        }

        public void startPID() {
            PIDLoop.startPeriodic(0.01);
        }

        public void stopPID() {
            PIDLoop.stop();
        }

        public void setSpeed(double setpoint) {
            PIDSetpoint = setpoint;
        }

        void startRamping() {
            front.configOpenloopRamp(0.5, 1000);
            back.configOpenloopRamp(0.5, 1000);
        }

        void stopRamping() {
                front.configOpenloopRamp(0, 1000);
                back.configOpenloopRamp(0, 1000);
        }

        public int get() {
            return quadrature.get();
        }

        public double getRate() {
            return quadrature.getRate();
        }
    }
}
