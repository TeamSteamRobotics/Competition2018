
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot.autonomous.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5119.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class AutonomousStraight extends Command {
	
	double targetRotations;
	
	double speed;
	double turnCorrection;
	boolean isDone = false;
	double maxVelocity = 0;
	
	
	//1 rotation 19 inches
	public AutonomousStraight(double _rotations) {
		
		targetRotations = _rotations;
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
	}

	@Override
	protected void initialize() {
		Robot.driveSubsystem.resetEncoders();
		Robot.logger.info("AutoStraight("+targetRotations+")");
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		speed = Math.min(Math.max((targetRotations - (Robot.driveSubsystem.getLeftEncoderRotations() + Robot.driveSubsystem.getRightEncoderRotations())/2)/5, -.5), .5);
		if(targetRotations <= 1.0) {
			speed = Math.min(Math.max((targetRotations - (Robot.driveSubsystem.getLeftEncoderRotations() + Robot.driveSubsystem.getRightEncoderRotations())/2)/1, -.5), .5);
		}
		//turnCorrection = (Robot.driveSubsystem.getRightEncoderCount() - Robot.driveSubsystem.getLeftEncoderCount())/1000;
		SmartDashboard.putNumber("speed", speed);
		SmartDashboard.putNumber("fwdAccel", Robot.gyroSubsystem.getForwardAcceleration());
		
		turnCorrection = Robot.gyroSubsystem.relativeAngle(Robot.gyroSubsystem.targetAngle)/90;
		Robot.driveSubsystem.driveRobot(speed, turnCorrection);
		if(targetRotations > 0) {
			isDone = Robot.gyroSubsystem.getForwardAcceleration() > 1;
		}else {
			isDone = Robot.gyroSubsystem.getForwardAcceleration() < -1;
		}
		//Robot.logger.info("rightEncoder: "+Robot.driveSubsystem.getRightEncoderCount());
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(speed) < 0.05;// || isDone;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveSubsystem.driveRobot(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.driveSubsystem.driveRobot(0, 0);
	}
}

