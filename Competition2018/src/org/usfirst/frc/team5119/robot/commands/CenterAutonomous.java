package org.usfirst.frc.team5119.robot.commands;

import org.usfirst.frc.team5119.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 
public class CenterAutonomous extends CommandGroup {

    public CenterAutonomous() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
    	
    	requires(Robot.driveSubsystem);
    	boolean shouldFlip = Robot.switchPositions.charAt(0)+"" == "R";
    	addSequential(new AutonomousForward(.5,0, .5));
    	addParallel(new SwitchLevel());
    	
    	if(shouldFlip) {
    		addSequential(new AutonomousTurn(90,3));
    	}else {
    		addSequential(new AutonomousTurn(-90,3));
    	}
    
    	addSequential(new AutonomousForward(.5,0, .375));
    	
    	if(shouldFlip) {
    		addSequential(new AutonomousTurn(-90,3));
    	}else {
    		addSequential(new AutonomousTurn(90,3));
    	}
    	
    	addSequential(new AutonomousForward(.5,0, 1));
    	addSequential(new AutoDropCube());

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
*/
