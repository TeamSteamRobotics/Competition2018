package org.usfirst.frc.team5119.robot.commands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoDropCube extends TimedCommand {
	public AutoDropCube() {
		super(1);
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gripperSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.gripperSubsystem.isFullOpen()) {
			Robot.gripperSubsystem.move(0);
		} else {
			Robot.gripperSubsystem.move(1);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.gripperSubsystem.isFullOpen() || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.gripperSubsystem.move(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.gripperSubsystem.move(0);
	}
}
