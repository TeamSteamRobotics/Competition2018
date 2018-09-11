
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot.autonomous.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team5119.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class AutonomousStraight extends Command {
	
	double targetRotations;
	
	double speed;
	double turnCorrection;
	boolean hasMoved = false;
	double maxVelocity = 0;
	
	
	//1 rotation 19 inches
	public AutonomousStraight(double _rotations) {
		
		targetRotations = _rotations;
		requires(Robot.driveSubsystem);
	}

	@Override
	protected void initialize() {
		Robot.driveSubsystem.resetEncoders();
		Robot.logger.info("AutoStraight("+targetRotations+")");
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		speed = Math.min(Math.max((targetRotations - (Robot.driveSubsystem.getLeftEncoderRotations() + Robot.driveSubsystem.getRightEncoderRotations())/2)/7, -1), 1);
		if(targetRotations <= 1.0) {
			speed = Math.min(Math.max((targetRotations - (Robot.driveSubsystem.getLeftEncoderRotations() + Robot.driveSubsystem.getRightEncoderRotations())/2)/1, -.7), .7);
		}
		//turnCorrection = (Robot.driveSubsystem.getRightEncoderCount() - Robot.driveSubsystem.getLeftEncoderCount())/1000;
		SmartDashboard.putNumber("speed", speed);

		turnCorrection = Robot.driveSubsystem.relativeAngle(Robot.driveSubsystem.targetAngle)/67.5;
		Robot.driveSubsystem.arcadeDrive(speed, turnCorrection);
		if(!Robot.driveSubsystem.isStopped()) {
			hasMoved = true;
		}
		//Robot.logger.info("rightEncoder: "+Robot.driveSubsystem.getRightEncoderCount());
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(speed) < 0.1 || (hasMoved && Robot.driveSubsystem.isStopped());// || isDone;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}
}

