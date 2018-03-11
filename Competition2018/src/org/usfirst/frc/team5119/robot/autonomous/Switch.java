package org.usfirst.frc.team5119.robot.autonomous;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team5119.robot.autonomous.autoCommands.*;
import org.usfirst.frc.team5119.robot.commands.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Switch extends Subsystem {

	static boolean switchPriority = false;
	
	static int allianceSide;
	//left side for left side of switch
	static List<Double> position0TurnsLeft = Arrays.asList(0.0, 90.0, 90.0);
	static List<Double> position0ForwardsLeft = Arrays.asList(8.5, 3.0, 0.0);
	static List<Boolean> position0DirectionsLeft = Arrays.asList(true, true);
	static List<Boolean> position0TurnDirectionsLeft = Arrays.asList(true, true);
	static List<Command> position0CommandsLeft = Arrays.asList(new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//left side for right side of switch
	static List<Double> position0TurnsRight = Arrays.asList(4.9, 90.0, 73.0, 180.0, 180.0);
	static List<Double> position0ForwardsRight = Arrays.asList(12.8, 12.75, -2.0, 2.0, 0.0);
	static List<Boolean> positio0DirectionsRight = Arrays.asList(true, true);
	static List<Boolean> position0TurnDirectionsRight = Arrays.asList(true, true);
	static List<Command> position0CommandsRight = Arrays.asList(new SwitchLevel(), new DummyCommand(), new DummyCommand(), new DummyCommand(), new AutoDropCube(), new DummyCommand(), new AutoDropCube());
	
	//middle for right side of switch
	static List<Double> position1TurnsRight = Arrays.asList(0.0, 45.0, 0.0, 0.0);
	static List<Double> position1ForwardsRight = Arrays.asList(1.0, 4.0, 3.0, 0.0);
	static List<Boolean> position1DirectionsRight = Arrays.asList(true, true);
	static List<Boolean> position1TurnDirectionsRight = Arrays.asList(true, true);
	static List<Command> position1CommandsRight = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//middle for left side of switch
	static List<Double> position1TurnsLeft = Arrays.asList(0.0, -45.0, 0.0, 0.0);
	static List<Double> position1ForwardsLeft = Arrays.asList(1.0, 5.0, 3.0, 0.0);
	static List<Boolean> position1DirectionsLeft = Arrays.asList(true, true);
	static List<Boolean> position1TurnDirectionsLeft = Arrays.asList(true, true);
	static List<Command> position1CommandsLeft = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//right for left side of switch
	static List<Double> position2TurnsLeft = Arrays.asList(4.9, -90.0, -82.0, -180.0, 180.0);
	static List<Double> position2ForwardsLeft = Arrays.asList(13.0, 12.375, -2.375, 2.0, 0.0);
	static List<Boolean> position2DirectionsLeft = Arrays.asList(true, true);
	static List<Boolean> position2TurnDirectionsLeft = Arrays.asList(true, true);
	static List<Command> position2CommandsLeft = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new DummyCommand(), new AutoDropCube(), new DummyCommand(), new AutoDropCube());
	
	//right for right side of switch
	
	static List<Double> position2TurnsRight = Arrays.asList(4.9, -90.0, -90.0);
	static List<Double> position2ForwardsRight = Arrays.asList(8.5, 3.0, 0.0);
	static List<Boolean> position2DirectionsRight = Arrays.asList(true, true);
	static List<Boolean> position2TurnDirectionsRight = Arrays.asList(true, true);
	static List<Command> position2CommandsRight = Arrays.asList(new SwitchLevel(), new DummyCommand(), new AutoDropCube());

	public static void init(char side, boolean priority) {
		switchPriority = priority;
		
		if (side == 'L') allianceSide = 1;
		else allianceSide = 0;
		
	}
	
	public static int getSide() {
		return allianceSide;
	}
	
	public static List<Double> getTurns(int position) {
		if (position == 0) if(getSide() == 0) return position0TurnsRight; else return position0TurnsLeft;
		if (position == 1) if(getSide() == 0) return position1TurnsRight; else return position1TurnsLeft;
		if (position == 2) if(getSide() == 0) return position2TurnsRight; else return position2TurnsLeft;
		else return Arrays.asList(0.0, 0.0);
	}
	
	public static List<Double> getDistances(int position) {
		if (position == 0) if(getSide() == 0) return position0ForwardsRight; else return position0ForwardsLeft;
		if (position == 1) if(getSide() == 0) return position1ForwardsRight; else return position1ForwardsLeft;
		if (position == 2) if(getSide() == 0) return position2ForwardsRight; else return position2ForwardsLeft;
		else return Arrays.asList(0.0, 0.0);
	}
	
	public static List<Boolean> getDirections(int position) {
		if (position == 0) return position0DirectionsLeft;
		if (position == 1) return position1DirectionsLeft;
		if (position == 2) return position2DirectionsLeft;
		else return Arrays.asList(true, true);
	}
	
	public static List<Boolean> getTurnDirection(int position) {
		if (position == 0) return position0TurnDirectionsLeft;
		if (position == 1) return position1TurnDirectionsLeft;
		if (position == 2) return position2TurnDirectionsLeft;
		else return Arrays.asList(true, true);
	}
	
	public static List<Command> getCommands(int position) {
		if (position == 0) if(getSide() == 0) return position0CommandsRight; else return position0CommandsLeft;
		if (position == 1) if(getSide() == 0) return position1CommandsRight; else return position1CommandsLeft;
		if (position == 2) if(getSide() == 0) return position2CommandsRight; else return position2CommandsLeft;
		else return Arrays.asList(new DummyCommand(), new DummyCommand());
	}
	
	@Override
	protected void initDefaultCommand() {
		// Auto-generated method stub
		
	}

	

	

}
