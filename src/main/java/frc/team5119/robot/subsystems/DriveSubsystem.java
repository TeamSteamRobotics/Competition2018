package frc.team5119.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SPI;
import frc.team5119.robot.RobotMap;
import frc.team5119.robot.commands.Drive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team5119.robot.util.Units;
import jaci.pathfinder.Pathfinder;

/**
 *
 */
public class DriveSubsystem extends Subsystem implements frc.team5119.interfaces.subsystems.DriveSubsystem {
    // DRIVETRAIN
    protected final WPI_TalonSRX
            frontRight = new WPI_TalonSRX(0),
            frontLeft = new WPI_TalonSRX(1),
            backRight = new WPI_TalonSRX(2),
            backLeft = new WPI_TalonSRX(3);

    protected SpeedControllerGroup
            rightMotors = new SpeedControllerGroup(frontRight, backRight),
            leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
    protected DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    // arcadeDrive speed limit: set to 1 when in normal driving
    protected double safetySpeedModifier = 1;
    // END DRIVETRAIN

    // ENCODERS
	protected Encoder
            leftEncoder = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false),
            rightEncoder = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);
	// END ENCODERS

    // GYRO
	protected AHRS gyro = new AHRS(SPI.Port.kMXP);

	@Deprecated
	public double targetAngle = 0;
	// END GYRO

    // ODOMETRY
    public volatile double x, y, theta;
    // END ODOMETRY
	
    public void initDefaultCommand() {}

    public void init() {
        // TALON RAMPING
        frontRight.configOpenloopRamp(.5, 1000);
        frontLeft.configOpenloopRamp(.5, 1000);
        backRight.configOpenloopRamp(.5, 1000);
        backLeft.configOpenloopRamp(.5, 1000);
        // END TALON RAMPING

        // ENCODER SETUP
        leftEncoder.setDistancePerPulse(1.4167/2048.0); //17 inches (1.4167 ft) per rot/2048 ticks per rot
        rightEncoder.setDistancePerPulse(1.4167/2048.0);//17 inches (1.4167 ft) per rot/2048 ticks per rot
        // END ENCODER SETUP

        // ODOMETRY
        new Thread(() -> {
            int lastPos = (getLeftEncoder() + getRightEncoder()) / 2;
            while (true) {
                int currentPos = (getLeftEncoder() + getRightEncoder()) / 2;
                double dPos = Units.encoderCountsToFeet(currentPos - lastPos);
                theta = Pathfinder.d2r(Pathfinder.boundHalfDegrees(gyro.getAngle()));
                x += Math.cos(theta) * dPos;
                y += Math.sin(theta) * dPos;
                lastPos = currentPos;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // END ODOMETRY
    }

    // DRIVING
    public void arcadeDrive(double fwd, double turn) {
    	drive.arcadeDrive(fwd * safetySpeedModifier, turn * safetySpeedModifier, false);
    }

    public void tankDrive(double left, double right) {
	    drive.tankDrive(left, right, false);
    }
    // END DRIVING

    // ENCODERS
    public int getLeftEncoder() {
    	return leftEncoder.get();
    }

    public int getRightEncoder() {
    	return rightEncoder.get();
    }
    
    public double getLeftEncoderRotations() {
    	return leftEncoder.getDistance();
    }
    
    public double getRightEncoderRotations() {
    	return rightEncoder.getDistance();
    }
    
    public void resetEncoders() {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }

    public boolean isStopped() {
    	return leftEncoder.getStopped() && rightEncoder.getStopped();
    }
    // END ENCODERS

    // GYRO
    /**
     * @return gyro angle <b>in degrees</b>
     */
    public double getGyroAngle() {
	    return gyro.getAngle() % 360;
    }

    /**
     * @return gyro angle <b>in radians</b>
     */
    public double getGyroAngleRadians() {
        return ( gyro.getAngle() * 0.01745329251 ) % 6.28318530718;
    }

    public void resetGyro() {
        gyro.reset();
    }

    @Deprecated
    public double relativeAngle(double reference) {
        double angle = reference - getGyroAngle();
        return Pathfinder.boundHalfDegrees(angle);
    }
    // END GYRO
}
