package org.usfirst.frc.team5119.robot.autonomous;

import java.util.List;

import org.usfirst.frc.team5119.robot.autonomous.autoCommands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Strategy extends CommandGroup {
	
	public static int position;
	
	public void init() {
		if (Scale.scalePriority == true) 
			scalePlan(Scale.getTurns(position), Scale.getDistances(position), Switch.getDirections(position), Switch.getTurnDirection(position));
		else if (Switch.switchPriority == true) 
			switchPlan(Switch.getTurns(position), Switch.getDistances(position), Switch.getDirections(position), Scale.getTurnDirection(position));
		else dummyPlan();
	}
	
	/** @param  turns
	 *  A list whose every index is a turn angle
	 *  
	 *  @param forwards
	 *  A list whose every index is a distance to travel
	 */
	public void switchPlan(List<Integer> turns, List<Integer> distance, List<Boolean> straightDirection, List<Boolean> turnDirection) {
		for(int i= 0; i < turns.size(); i++) {
			addSequential(new AutonomousTurn(turns.get(i), turnDirection.get(i)) );
			addSequential(new AutonomousStraight(distance.get(i), straightDirection.get(i)));
		}
	}
	
	public void scalePlan(List<Integer> turns, List<Integer> distance, List<Boolean> straightDirection, List<Boolean> turnDirection) {
		for(int i= 0; i < turns.size(); i++) {
			addSequential(new AutonomousTurn(turns.get(i), turnDirection.get(i)) );
			addSequential(new AutonomousStraight(distance.get(i), straightDirection.get(i)));
		}
	}
	
	public static void dummyPlan() {
		//driveforward
		new AutonomousStraight(20, false);
	}

	public void initDefaultCommand() {
	
	}
}
