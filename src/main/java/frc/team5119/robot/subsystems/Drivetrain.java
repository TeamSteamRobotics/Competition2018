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
import org.teamsteamrobotics.lib.wrappers.LazySRX;

/**
 *
 */
public class Drivetrain extends Subsystem {

    public Side left;
    public Side right;
    public Odometry odo;
    public AHRS ahrs;
    public Telemetry telemetry;

    private boolean isRamping = false;

    public void initDefaultCommand() {}

    public void init() {
        left = new Side("left");
        right = new Side("right");
        ahrs = new AHRS(SPI.Port.kMXP);
        odo = new Odometry(this);
        telemetry = new Telemetry();
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

    public boolean isRamping() { return isRamping; }

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

        final LazySRX master, follower;
        private final Encoder quadrature;
        private volatile double setpoint = 0;
        private volatile double lastError = 0;

        private Notifier PIDLoop = new Notifier(() -> {
            double error = (setpoint - getRate());
            double output = Constants.kf * setpoint;
            output += Constants.kp * error;
            output += Constants.kd * ((error - lastError) / 0.02);
            set(output);
            lastError = error;
        });

        Side(String side) {
            if (side.equals("left")) {
                master = new LazySRX(RobotMap.frontLeftTalon);
                follower = new LazySRX(RobotMap.backLeftTalon);
                quadrature = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false);
            } else {
                master = new LazySRX(RobotMap.frontRightTalon);
                follower = new LazySRX(RobotMap.backRightTalon);
                master.setInverted(true);
                follower.setInverted(true);
                quadrature = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);
            }

            follower.follow(master);

            quadrature.setDistancePerPulse(2 * Math.PI / 2048.0); //should make getRate() return rad/s
        }

        public void set(double percentOutput) {
            percentOutput = Math.max(-1, Math.min(1, percentOutput));
            master.set(ControlMode.PercentOutput, percentOutput);
        }

        public void startPID() { PIDLoop.startPeriodic(0.01); }

        public void stopPID() { PIDLoop.stop(); }

        public void setSpeed(double setpoint) { this.setpoint = setpoint; }

        void startRamping() { master.configOpenloopRamp(0.5, 1000); }

        void stopRamping() { master.configOpenloopRamp(0, 1000); }

        public int get() { return quadrature.get(); }

        public double getRate() { return quadrature.getRate(); }
    }

    public class Telemetry {
        public double leftPct, leftAmps, leftEnc, rightPct, rightAmps, rightEnc;

        public void update() {
            leftPct = left.master.getMotorOutputPercent();
            leftAmps = left.master.getOutputCurrent() + left.follower.getOutputCurrent();
            leftEnc = left.get();

            rightPct = right.master.getMotorOutputPercent();
            rightAmps = right.master.getOutputCurrent() + right.follower.getOutputCurrent();
            rightEnc = right.get();
        }
    }
}
