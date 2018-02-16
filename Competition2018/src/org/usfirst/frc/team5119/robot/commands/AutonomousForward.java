package org.usfirst.frc.team5119.robot.commands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

import java.lang.Math;
/**
 *
 */
public class AutonomousForward extends TimedCommand {
	double speed;
	boolean forward;
	double encoderStart;
	boolean isDone;
	double r;
	int rotationMultiplier = 1;//2048;
	double goalRotations;
	double power;

    public AutonomousForward(double _speed, double rotations, double timeout) {
        // Use requires() here to declare subsystem dependencies
    	super(timeout);
        requires(Robot.driveSubsystem);
        speed = _speed;
        r = rotations*rotationMultiplier;  
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    	encoderStart = Robot.driveSubsystem.getEncoderCount();
    	if (Math.abs(r) == r) {
        	forward = true;
        	goalRotations = encoderStart + r;
        }else {
        	forward = false;
        	goalRotations = encoderStart - r;
        }
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*power = Math.min(.25, Math.max(-.25, (goalRotations - Robot.driveSubsystem.encoderOutput())/rotationMultiplier));
    	Robot.driveSubsystem.drive(power, 0);*/
    	/*if(forward) {
    		if (goalRotations >= Robot.driveSubsystem.getEncoderCount()) {
    			Robot.driveSubsystem.driveRobot(speed, 0);
    		}else {
    		//	Robot.driveSubsystem.drive(0, 0);
    			isDone = true;
    		}
    	}else {
    		if (goalRotations <= Robot.driveSubsystem.getEncoderCount()) {
    			Robot.driveSubsystem.driveRobot(speed, 0);
    		}else {
    		//	Robot.driveSubsystem.drive(0, 0);
    			isDone = true;
    		}
    	}*/
    	Robot.driveSubsystem.driveRobot(speed, r);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();//Done;
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
