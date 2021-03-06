package frc.team5119.robot.autonomous;

import java.util.Arrays;
import java.util.List;

import frc.team5119.robot.autonomous.autoCommands.AutoDropCube;
import frc.team5119.robot.commands.DummyCommand;
import frc.team5119.robot.commands.SwitchLevel;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Scale extends Subsystem {

	static boolean scalePriority = false;
	
	static int allianceSide;
	//left side for left side of scale TODO =================NEEDS TO BE WRITTEN=================
	static List<Double> position0TurnsLeft = Arrays.asList(0.0, 90.0, 90.0);
	static List<Double> position0ForwardsLeft = Arrays.asList(15.0, 1.0, -0.0);
	static List<Boolean> position0DirectionsLeft = Arrays.asList(true, true);
	static List<Boolean> position0TurnDirectionsLeft = Arrays.asList(true, true);
	static List<Command> position0CommandsLeft = Arrays.asList(new DummyCommand(), new DummyCommand(), new DummyCommand());//SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//left side for right side of scale TODO =================NEEDS TO BE WRITTEN=================
	static List<Double> position0TurnsRight = Arrays.asList(0.0, 90.0, 180.0, 180.0);
	static List<Double> position0ForwardsRight = Arrays.asList(12.0, 9.75, 2.0, 0.0);
	static List<Boolean> position0DirectionsRight = Arrays.asList(true, true);
	static List<Boolean> position0TurnDirectionsRight = Arrays.asList(true, true);
	static List<Command> position0CommandsRight = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//middle for right side of scale
	static List<Double> position1TurnsRight = Arrays.asList(0.0, 45.0, 0.0, 0.0);
	static List<Double> position1ForwardsRight = Arrays.asList(1.0, 4.0, 2.5, 0.0);
	static List<Boolean> position1DirectionsRight = Arrays.asList(true, true);
	static List<Boolean> position1TurnDirectionsRight = Arrays.asList(true, true);
	static List<Command> position1CommandsRight = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//middle for left side of scale
	static List<Double> position1TurnsLeft = Arrays.asList(0.0, -45.0, 0.0, 0.0);
	static List<Double> position1ForwardsLeft = Arrays.asList(1.0, 4.0, 2.5, 0.0);
	static List<Boolean> position1DirectionsLeft = Arrays.asList(true, true);
	static List<Boolean> position1TurnDirectionsLeft = Arrays.asList(true, true);
	static List<Command> position1CommandsLeft = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//right for left side of scale TODO =================NEEDS TO BE WRITTEN=================
	static List<Double> position2TurnsLeft = Arrays.asList(5.0, -90.0, 180.0, 180.0);
	static List<Double> position2ForwardsLeft = Arrays.asList(11.5, 8.5, 2.0, 0.0);
	static List<Boolean> position2DirectionsLeft = Arrays.asList(true, true);
	static List<Boolean> position2TurnDirectionsLeft = Arrays.asList(true, true);
	static List<Command> position2CommandsLeft = Arrays.asList(new DummyCommand(), new SwitchLevel(), new DummyCommand(), new AutoDropCube());
	
	//right for right side of scale TODO =================NEEDS TO BE WRITTEN=================
	static List<Double> position2TurnsRight = Arrays.asList(0.0, -90.0, -90.0);
	static List<Double> position2ForwardsRight = Arrays.asList(8.0, 3.0, 0.0);
	static List<Boolean> position2DirectionsRight = Arrays.asList(true, true);
	static List<Boolean> position2TurnDirectionsRight = Arrays.asList(true, true);
	static List<Command> position2CommandsRight = Arrays.asList(new SwitchLevel(), new DummyCommand(), new AutoDropCube());

	public static void init(char side, boolean priority) {
		scalePriority = priority;
		
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
