package org.usfirst.frc.team5119.robot.autonomous;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team5119.robot.commands.DummyCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Scale extends Subsystem {
	
	static boolean scalePriority = false;
	
	static int allianceSide;
	
	static List<Double> position0Turns = Arrays.asList(2.0, 3.0);
	static List<Double> position0Forwards = Arrays.asList(2.0, 3.0);
	static List<Boolean> position0Directions = Arrays.asList(true, true);
	static List<Boolean> position0TurnDirections = Arrays.asList(true, true);
	static List<Command> position0Commands = Arrays.asList(new DummyCommand(), new DummyCommand(), new DummyCommand());
	
	static List<Double> position1Turns = Arrays.asList(2.0, 3.0);
	static List<Double> position1Forwards = Arrays.asList(2.0, 3.0);
	static List<Boolean> position1Directions = Arrays.asList(true, true);
	static List<Boolean> position1TurnDirections = Arrays.asList(true, true);
	static List<Command> position1Commands = Arrays.asList(new DummyCommand(), new DummyCommand(), new DummyCommand());
	
	static List<Double> position2Turns = Arrays.asList(2.0, 3.0);
	static List<Double> position2Forwards = Arrays.asList(2.0, 3.0);
	static List<Boolean> position2Directions = Arrays.asList(true, true);
	static List<Boolean> position2TurnDirections = Arrays.asList(true, true);
	static List<Command> position2Commands = Arrays.asList(new DummyCommand(), new DummyCommand(), new DummyCommand());

	public static void init(char side, boolean priority) {
		scalePriority = priority;
		
		if (side == ('L')) allianceSide = 1;
		else allianceSide = 0;
	}
	
	public int getSide() {
		return allianceSide;
	}
	
	public static List<Double> getTurns(int position) {
		if (position == 0) return position0Turns;
		if (position == 1) return position1Turns;
		if (position == 2) return position2Turns;
		else return Arrays.asList(0.0, 0.0);
	}
	
	public static List<Double> getDistances(int position) {
		if (position == 0) return position0Forwards;
		if (position == 1) return position1Forwards;
		if (position == 2) return position2Forwards;
		else return Arrays.asList(0.0, 0.0);
	}
	
	public static List<Boolean> getDirections(int position) {
		if (position == 0) return position0Directions;
		if (position == 1) return position1Directions;
		if (position == 2) return position2Directions;
		else return Arrays.asList(true, true);
	}
	
	public static List<Boolean> getTurnDirection(int position) {
		if (position == 0) return position0TurnDirections;
		if (position == 1) return position1TurnDirections;
		if (position == 2) return position2TurnDirections;
		else return Arrays.asList(true, true);
	}
	
	public static List<Command> getCommands(int position) {
		if (position == 0) return position0Commands;
		if (position == 1) return position1Commands;
		if (position == 2) return position2Commands;
		else return Arrays.asList(new DummyCommand(), new DummyCommand());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	

}
