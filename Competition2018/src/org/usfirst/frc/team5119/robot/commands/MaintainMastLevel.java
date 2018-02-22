package org.usfirst.frc.team5119.robot.commands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MaintainMastLevel extends Command {
	protected double mastLevel;
	protected double difference;

    public MaintainMastLevel() {
        requires(Robot.mastSubsystem);//scale: -1500
        //top: -2200
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mastLevel = Robot.mastSubsystem.getPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	difference = mastLevel - Robot.mastSubsystem.getPosition();
    	Robot.mastSubsystem.move(difference/500);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
