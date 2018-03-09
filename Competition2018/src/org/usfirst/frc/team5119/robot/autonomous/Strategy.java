package org.usfirst.frc.team5119.robot.autonomous;

import java.util.List;

import org.usfirst.frc.team5119.robot.Robot;
import org.usfirst.frc.team5119.robot.autonomous.autoCommands.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Strategy extends CommandGroup {
	
	public static int position;
	
	public void init() {
		if (((Scale.scalePriority == true) == true) == true) 
			scalePlan(Scale.getTurns(position), Scale.getDistances(position), Scale.getCommands(position));
		else if (Switch.switchPriority == true) 
			switchPlan(Switch.getTurns(position), Switch.getDistances(position), Switch.getCommands(position));
		else dummyPlan();
		
	}
	
	/** @param  turns
	 *  A list whose every index is a turn angle
	 *  
	 *  @param forwards
	 *  A list whose every index is a distance to travel
	 */
	public void switchPlan(List<Double> turns, List<Double> distance, List<Command> commands) {
		SmartDashboard.putString("plan", "switchPlan");
		for(int i= 0; i < turns.size(); i++) {
			addSequential(new AutonomousTurn(1.0, turns.get(i)));
			addParallel(commands.get(i));
			addSequential(new AutonomousStraight(distance.get(i)));
			
		}
		Robot.logger.info(turns+"");
		Robot.logger.info(distance+"");
		Robot.logger.info(commands+"");
	}
	
	public void scalePlan(List<Double> turns, List<Double> distance, List<Command> commands) {
		SmartDashboard.putString("plan", "scalePlan");
		for(int i= 0; i < turns.size(); i++) {
			addSequential(new AutonomousTurn(2.0, turns.get(i)) );
			addSequential(new AutonomousStraight(distance.get(i)));
			addParallel(commands.get(i));
		}
	}
	
	public void dummyPlan() {
		//driveforward
		SmartDashboard.putString("plan", "dummyPlan");
		addSequential(new AutonomousStraight(20));
	}
}
