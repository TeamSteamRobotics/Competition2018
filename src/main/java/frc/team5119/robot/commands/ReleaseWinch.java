/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5119.robot.Robot;

/**
 * Release the winch. This should be called with button.whileHeld() because it won't stop.
 */
public class ReleaseWinch extends Command {
	public ReleaseWinch() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.winchSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.winchSubsystem.move(-1);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.winchSubsystem.move(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.winchSubsystem.move(0);
	}
}
