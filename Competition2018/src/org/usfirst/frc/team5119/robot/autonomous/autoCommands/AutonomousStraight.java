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
	
	int goalDifference;
	boolean forwards;
	int goalCount;
	
	double speed = 0.25;
	
	boolean isDone;
	
	//1 rotation 19 inches
	public AutonomousStraight(int rotations, boolean forwards) {
		this.forwards = forwards;
		goalDifference = rotations * 2048;
		requires(Robot.driveSubsystem);
	}

	@Override
	protected void initialize() {
		isDone = false;
		
		goalCount = goalDifference + Robot.driveSubsystem.getEncoderCount();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (forwards) {
			if (goalCount > Robot.driveSubsystem.getEncoderCount())
				Robot.driveSubsystem.driveRobot(speed, 0);
			else
				isDone = true;
		}
		else {
			if (goalCount < Robot.driveSubsystem.getEncoderCount())
				Robot.driveSubsystem.driveRobot(-speed, 0);
			else
				isDone = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isDone;
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
