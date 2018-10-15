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
        right.set(rightMotorOutput);

    }

    public class Side {

        private final TalonSRX master, follower;
        private final Encoder quadrature;
        private volatile double setpoint = 0;
        private volatile double lastRate = 0;

        private Notifier PIDLoop = new Notifier(() -> {
            double rate = getRate();
            double output = Constants.kf * setpoint;
            output += Constants.kp * (setpoint - rate);
            output -= Constants.kd * (rate - lastRate);
            set(output);
            lastRate = rate;
        });

        Side(String side) {
            if (side.equals("left")) {
                master = new TalonSRX(RobotMap.frontLeftTalon);
                follower = new TalonSRX(RobotMap.backLeftTalon);
                quadrature = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false);
            } else {
                master = new TalonSRX(RobotMap.frontRightTalon);
                follower = new TalonSRX(RobotMap.backRightTalon);
                master.setInverted(true);
                follower.setInverted(true);
                quadrature = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);
            }

            follower.follow(master);

            quadrature.setDistancePerPulse(2 * Math.PI/2048.0); //should make getRate() return rad/s
        }

        public void set(double percentOutput) {
            percentOutput = Math.max(-1, Math.min(1, percentOutput));
            master.set(ControlMode.PercentOutput, percentOutput);
        }

        public void startPID() {
            PIDLoop.startPeriodic(0.01);
        }

        public void stopPID() {
            PIDLoop.stop();
        }

        public void setSpeed(double setpoint) {
            this.setpoint = setpoint;
        }

        void startRamping() {
            master.configOpenloopRamp(0.5, 1000);
        }

        void stopRamping() {
                master.configOpenloopRamp(0, 1000);
        }

        public int get() {
            return quadrature.get();
        }

        public double getRate() {
            return quadrature.getRate();
        }
    }
}
