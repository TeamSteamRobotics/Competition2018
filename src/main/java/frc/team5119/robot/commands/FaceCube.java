package frc.team5119.robot.commands;

import frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FaceCube extends Command {

    public FaceCube() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubsystem);
        requires(Robot.visionSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double correctionConstant = .75;
    	double pegX;
    	double xCorrectionSpeed;
    	
    	SmartDashboard.putNumber("horizontalCenter",  pegX = Robot.visionSubsystem.getBoxX());
    	xCorrectionSpeed = correctionConstant*(pegX)/640;
    	
    	
    	Robot.driveSubsystem.drive.arcade(-Robot.m_oi.stick.getY(), xCorrectionSpeed);
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
