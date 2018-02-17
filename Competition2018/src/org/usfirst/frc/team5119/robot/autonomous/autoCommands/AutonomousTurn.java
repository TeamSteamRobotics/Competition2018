package org.usfirst.frc.team5119.robot.autonomous.autoCommands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousTurn extends Command {
	
	double goalDegree;
	boolean direction;
	
	double speed;
	
	boolean isDone;
	
    public AutonomousTurn(double degrees, boolean direction) {
        requires(Robot.driveSubsystem);
        requires(Robot.gyroSubsystem);
        speed = .25;
        goalDegree = degrees;
        direction = this.direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(direction) {
    		if (Robot.gyroSubsystem.gyroAngle() != goalDegree)
    			Robot.driveSubsystem.driveRobot(0, speed);
    		else {
    			Robot.driveSubsystem.driveRobot(0, 0);
    			isDone = true;
    		}
    	}
    	else {
    		if(Robot.gyroSubsystem.gyroAngle() != goalDegree) 
    			Robot.driveSubsystem.driveRobot(0, -speed);
    		else {
    			Robot.driveSubsystem.driveRobot(0, 0);
    			isDone = true;
    		}
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.driveRobot(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.driveRobot(0, 0);
    }
}
