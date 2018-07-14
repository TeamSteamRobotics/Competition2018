/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot.subsystems;

import frc.team5119.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem that controls the winch.
 */
public class WinchSubsystem extends Subsystem {
	//Motors
		Talon winchMotor = new Talon(RobotMap.winchMotor);
		
	//Speed Modifier: set to 1 for no speed cap
		double speedModifier = 1;

	public void initDefaultCommand() {
		
	}
	
	public void move(double speed) {
		winchMotor.set(speed*speedModifier);
		
	}
}
