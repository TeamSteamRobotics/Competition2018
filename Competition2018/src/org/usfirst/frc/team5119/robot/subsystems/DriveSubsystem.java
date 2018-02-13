package org.usfirst.frc.team5119.robot.subsystems;

import org.usfirst.frc.team5119.robot.RobotMap;
import org.usfirst.frc.team5119.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	protected static final WPI_TalonSRX frontRight = new WPI_TalonSRX(0);
	protected static final WPI_TalonSRX frontLeft = new WPI_TalonSRX(1);
	protected static final WPI_TalonSRX backRight = new WPI_TalonSRX(2);
	protected static final WPI_TalonSRX backLeft = new WPI_TalonSRX(3);
	public Encoder encoder = new Encoder(RobotMap.wheelEncoder1, RobotMap.wheelEncoder2, false);
	//set to 1 when in normal driving
	protected double safetySpeedModifier = 1;
	
	protected static SpeedControllerGroup rightMotors;
	protected static SpeedControllerGroup leftMotors;
	
	protected static DifferentialDrive drive;
	
	public DriveSubsystem() {
		/* quadrature */
		//frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); // PIDLoop=0, timeoutMs=0
		//frontRight.setInverted(true);
		//backRight.setInverted(true);
		
		frontRight.configOpenloopRamp(.5, 1000);
		frontLeft.configOpenloopRamp(.5, 1000);
		backRight.configOpenloopRamp(.5, 1000);
		backLeft.configOpenloopRamp(.5, 1000);
		
		rightMotors = new SpeedControllerGroup(frontRight, backRight);
		leftMotors = new SpeedControllerGroup(frontLeft, backLeft);
		drive = new DifferentialDrive(leftMotors, rightMotors);
		

	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Drive());
    }
    
    public void driveRobot(double fwd, double turn) {
    	drive.arcadeDrive(fwd*safetySpeedModifier, turn*safetySpeedModifier, false);
    	
    }
    public int getEncoderCount() {
    	
    	return encoder.get();
    	//return 5;
    	
    }
}
