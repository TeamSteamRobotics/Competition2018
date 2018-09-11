package frc.team5119.robot.subsystems;

import frc.team5119.robot.RobotMap;
import frc.team5119.robot.commands.Drive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveSubsystem extends Subsystem implements frc.team5119.interfaces.subsystems.DriveSubsystem {
    //Talons
    protected final WPI_TalonSRX
            frontRight = new WPI_TalonSRX(0),
            frontLeft = new WPI_TalonSRX(1),
            backRight = new WPI_TalonSRX(2),
            backLeft = new WPI_TalonSRX(3);

    //Encoders
	protected Encoder
            leftEncoder = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false),
            rightEncoder = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);

	//Speed limit: set to 1 when in normal driving
	protected double safetySpeedModifier = 1;

	//Drive Train
	protected SpeedControllerGroup
            rightMotors = new SpeedControllerGroup(frontRight, backRight),
            leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
	
	protected DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
	
	public DriveSubsystem() {
		frontRight.configOpenloopRamp(.5, 1000);
		frontLeft.configOpenloopRamp(.5, 1000);
		backRight.configOpenloopRamp(.5, 1000);
		backLeft.configOpenloopRamp(.5, 1000);
		
		leftEncoder.setDistancePerPulse(17.0/2048.0); //17 inches per rot/2048 ticks per rot
		rightEncoder.setDistancePerPulse(17.0/2048.0);//17 inches per rot/2048 ticks per rot
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }
    
    public void arcadeDrive(double fwd, double turn) {
    	drive.arcadeDrive(fwd * safetySpeedModifier, turn * safetySpeedModifier, false);
    }

    public void tankDrive(double left, double right) {
	    drive.tankDrive(left, right, false);
    }
    
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
}
