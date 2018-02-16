package org.usfirst.frc.team5119.robot.commands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseHook extends Command {

    public ReleaseHook() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.gripperSubsystem.isHookReleased()) {
			Robot.gripperSubsystem.move(0);
		} else {
			Robot.gripperSubsystem.move(-1);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (Robot.gripperSubsystem.isHookReleased());
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
