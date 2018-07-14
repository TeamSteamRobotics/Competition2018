package frc.team5119.robot.commands;

import frc.team5119.robot.Robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeCamera extends Command {
	
	UsbCamera cams[] = {Robot.cam0, Robot.cam1};

    public ChangeCamera() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	int currentCam = Integer.parseInt(Robot.server.getSource().getName());
    	if (currentCam < cams.length - 1) {
    		Robot.server.setSource(cams[currentCam+1]);
    	} else {
    		Robot.server.setSource(cams[0]);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
