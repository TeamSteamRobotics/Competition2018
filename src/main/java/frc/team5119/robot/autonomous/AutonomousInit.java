package frc.team5119.robot.autonomous;

import frc.team5119.robot.Robot;
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
		//Strategy.position = Robot.autoSwitchSubsystem.getPosition();
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		//closest switch to the alliance
		Switch.init(gameData.charAt(0), true);
		
		//the only scale
		//Scale.init(gameData.charAt(1), (Strategy.position == 0 && gameData.charAt(1) == 'L') || (Strategy.position == 2 && gameData.charAt(1) == 'R')); //sets scale to priority if it is on the same side as us

		//the movements based on if going for the switch or scale and position
		//Robot.strategy.init();
	}
}
