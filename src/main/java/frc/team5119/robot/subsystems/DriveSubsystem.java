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
public class DriveSubsystem extends Subsystem {
	
	//Talons
		protected final WPI_TalonSRX frontRight = new WPI_TalonSRX(0);
		protected final WPI_TalonSRX frontLeft = new WPI_TalonSRX(1);
		protected final WPI_TalonSRX backRight = new WPI_TalonSRX(2);
		protected final WPI_TalonSRX backLeft = new WPI_TalonSRX(3);
	
	//Encoders
		protected Encoder leftEncoder = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false),
					  rightEncoder = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);
	
	//Speed limit: set to 1 when in normal driving
		protected double safetySpeedModifier = 1;
	
	//Drive Train
		protected SpeedControllerGroup rightMotors;
		protected SpeedControllerGroup leftMotors;
	
		protected static DifferentialDrive drive;
	
	public DriveSubsystem() {	
		frontRight.configOpenloopRamp(.5, 1000);
		frontLeft.configOpenloopRamp(.5, 1000);
		backRight.configOpenloopRamp(.5, 1000);
		backLeft.configOpenloopRamp(.5, 1000);
		
		leftEncoder.setDistancePerPulse(0.00048828125);// = 1/2048
		rightEncoder.setDistancePerPulse(0.00048828125);// = 1/2048
		
		rightMotors = new SpeedControllerGroup(frontRight, backRight);
		leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
		drive = new DifferentialDrive(leftMotors, rightMotors);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }
    
    public void driveRobot(double fwd, double turn) {
    	drive.arcadeDrive(fwd*safetySpeedModifier, turn*safetySpeedModifier, false);
    }

    public void tankDrive(double left, double right) {
	    drive.tankDrive(left, right);
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
