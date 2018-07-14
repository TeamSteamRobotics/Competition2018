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
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class GripperSubsystem extends Subsystem {
	//Motors
		Talon gripMotor = new Talon(RobotMap.gripperMotor);
	
	//Sensors
		DigitalInput closedLimit = new DigitalInput(RobotMap.gripperClosed);
		DigitalInput openLimit = new DigitalInput(RobotMap.gripperOpen);
		DigitalInput boxGrabbedLimit = new DigitalInput(RobotMap.gripperBox);
		DigitalInput hookLimit = new DigitalInput(RobotMap.gripperHook);
	
	//Safety cap: set to 1 when in normal driving
		double speedModifier = 1;
	
	public void initDefaultCommand() {
	}
	
	public void move(double speed) {
		gripMotor.set(speed*speedModifier);
	}
	
	public boolean isFullClosed() {
		return !closedLimit.get();
	}
	
	public boolean isFullOpen() {
		return !openLimit.get();
	}
	
	public boolean isBoxGrabbed() {
		return !boxGrabbedLimit.get();
	}
	public boolean isHookReleased() {
		return !hookLimit.get();
	}
}
