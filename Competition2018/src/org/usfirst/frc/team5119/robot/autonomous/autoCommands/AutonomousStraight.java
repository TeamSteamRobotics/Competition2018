
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot.autonomous.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5119.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class AutonomousStraight extends Command {
	
	double targetRotations;
	
	double speed;
	double turnCorrection;
	
	//1 rotation 19 inches
	public AutonomousStraight(double _rotations) {
		targetRotations = _rotations;
		requires(Robot.driveSubsystem);
	}

	@Override
	protected void initialize() {
		Robot.driveSubsystem.resetEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		speed = (targetRotations - (Robot.driveSubsystem.getLeftEncoderRotations() + Robot.driveSubsystem.getRightEncoderRotations())/2)/5;
		turnCorrection = (Robot.driveSubsystem.getRightEncoderCount() - Robot.driveSubsystem.getLeftEncoderCount())/200;
		Robot.driveSubsystem.driveRobot(speed, turnCorrection);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return speed < 0.1;
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

