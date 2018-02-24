package org.usfirst.frc.team5119.robot.autonomous.autoCommands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonomousTurn extends TimedCommand {
double targetAngle;
	
    public AutonomousTurn(double timeout,double angle) {
    	super(timeout);
    	targetAngle = angle;
    	// Use requires() here to declare subsystem dependencies
    	requires(Robot.driveSubsystem);
    	requires(Robot.gyroSubsystem);
    	//requires(Robot.visionSubsystem);
    	// TODO Auto-generated constructor stub
    }

	

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.info("AutoTurn("+targetAngle+")");
    	Robot.gyroSubsystem.targetAngle = targetAngle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double correctionSpeed = Robot.gyroSubsystem.relativeAngle(targetAngle)/90;
    	if(correctionSpeed<-.25){
    		correctionSpeed=-.25;
    	}else if(correctionSpeed>.25){
    		correctionSpeed=.25;
    	}
    	SmartDashboard.putNumber("fwdAccel", Robot.gyroSubsystem.getForwardAcceleration());
    		
    	Robot.driveSubsystem.driveRobot(0, correctionSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut()||Math.abs(Robot.gyroSubsystem.relativeAngle(targetAngle)) < 3;
        //||(Robot.visionSubsystem.horizontalCenter()>300&&Robot.visionSubsystem.horizontalCenter()<340);
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
