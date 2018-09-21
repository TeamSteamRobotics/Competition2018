package frc.team5119.robot.util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team5119.robot.Robot;

public class DriveUtil {
    public PIDController leftPID;
    public PIDController rightPID;
    private final WPI_TalonSRX
            frontRight = new WPI_TalonSRX(0),
            frontLeft = new WPI_TalonSRX(1),
            backRight = new WPI_TalonSRX(2),
            backLeft = new WPI_TalonSRX(3);

    private SpeedControllerGroup
            rightMotors = new SpeedControllerGroup(frontRight, backRight),
            leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
    private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    // arcadeDrive speed limit: set to 1 when in normal driving
    private double arcadeSafetySpeedModifier = 1;

    public void init() {
        // TALON RAMPING
        frontRight.configOpenloopRamp(.5, 1000);
        frontLeft.configOpenloopRamp(.5, 1000);
        backRight.configOpenloopRamp(.5, 1000);
        backLeft.configOpenloopRamp(.5, 1000);
        // END TALON RAMPING

        // PID
        leftPID = new PIDController(0.1, 0.001, 0.0, Robot.driveSubsystem.encoders.left, leftMotors);
        rightPID = new PIDController(0.1, 0.001, 0.0, Robot.driveSubsystem.encoders.right, rightMotors);
        // END PID
    }

    public void startPID() {
        leftPID.enable();
        rightPID.enable();
    }

    public void stopPID() {
        leftPID.disable();
        rightPID.disable();
    }

    public void arcade(double fwd, double turn) {
        drive.arcadeDrive(fwd * arcadeSafetySpeedModifier, turn * arcadeSafetySpeedModifier, false);
    }

    public void tank(double left, double right) {
        drive.tankDrive(left, right, false);
    }
}
