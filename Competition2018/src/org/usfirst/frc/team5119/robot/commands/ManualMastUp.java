/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5119.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class ManualMastUp extends Command {
	public ManualMastUp() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.mastSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (true) {//!Robot.mastSubsystem.isAtTop()) {
			Robot.mastSubsystem.move(-1);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;//Robot.mastSubsystem.isAtTop();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.mastSubsystem.move(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.mastSubsystem.move(0);
	}
}
