package org.usfirst.frc.team5119.robot.commands;

import org.usfirst.frc.team5119.robot.Robot;
import java.lang.Math;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutonomousTurn extends TimedCommand {
	protected double speed;
	protected double degrees;
	protected boolean isTurningRight;
	protected double startingDegrees;
	protected double targetAngle;

    public AutonomousTurn( double _degrees, double timeout) {
    	super(timeout);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubsystem);
        requires(Robot.gyroSubsystem);
        speed = .25;
        degrees = _degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Math.abs(degrees) == degrees) {
    		isTurningRight = true;
    	}else {
    		isTurningRight = false;
    	}
    	startingDegrees = Robot.gyroSubsystem.gyroAngle();
    	targetAngle = (startingDegrees + degrees)%360;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    /*	if(isTurningRight) {
    		if(Robot.gyroSubsystem.gyroAngle() != targetAngle) {
    			Robot.driveSubsystem.driveRobot(0, speed);
    		}else {
    			Robot.driveSubsystem.driveRobot(0, 0);
    			isDone = true;
    		}
    	}else {
    		if(Robot.gyroSubsystem.gyroAngle() != targetAngle) {
    			Robot.driveSubsystem.driveRobot(0, -speed);
    		}else {
    			Robot.driveSubsystem.driveRobot(0, 0);
    			isDone = true;
    		}
    		
    	}*/

    	double correctionSpeed = Robot.gyroSubsystem.relativeAngle(targetAngle)/60;
		if(correctionSpeed<-.5){
			correctionSpeed=-.5;
		}else if(correctionSpeed>.5){
			correctionSpeed=.5;
		}
		/*if(correctionSpeed < .05) {
			isDone = true;
		}*/
			
		Robot.driveSubsystem.driveRobot(0, correctionSpeed*.59);
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
