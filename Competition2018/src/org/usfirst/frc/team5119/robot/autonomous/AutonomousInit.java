package org.usfirst.frc.team5119.robot.autonomous;

import org.usfirst.frc.team5119.robot.Robot;
import org.usfirst.frc.team5119.robot.subsystems.AutoSwitchSubsystem;

import edu.wpi.first.wpilibj.DriverStation;

public class AutonomousInit {

	/*String gameData;
	gameData = DriverStation.getInstance().getGameSpecificMessage();
	if(gameData.charAt(0) == 'L')
	*/
	
	//Position = robot starting point, Side = switch/scale side
	/*  _______________________________________________________________________________
	 *  |                |                                           |                |
	 *  |     pos0       |                  -------                  |       pos2     |
	 *  |                |__________       |scSide1|       __________|                |
	 * R|               _|        |_|      |_______|      |_|        |_               |B
	 * E|             _|_|swiSide1|_           |           _|swiSide0|_|_             |L
	 * D|     pos1   |_|_|        |_|          |          |_|        |_|_|   pos1     |U
	 *  |              |_|swiSide0|_       ____|____       _|swiSide1|_|              |E
	 *  |                |________|_|      |       |      |_|________|                |
	 *  |                |                 |scSide0|                 |                |
	 *  |     pos2       |                  -------                  |       pos0     |
	 *  |________________|___________________________________________|________________|
	 */
	
	public void init() {
		
		//position = autonomousSwitchPosition
		Strategy.position = Robot.autoSwitchSubsystem.getPosition();
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		//closest switch to the alliance
		Switch.init('R',true);//gameData.charAt(0), true);
		
		//the only scale
		Scale.init('L',false);//gameData.charAt(1), false);

		//the movements based on if going for the switch or scale and position
		Robot.strategy.init();
	}
}
