/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot.subsystems;

import org.usfirst.frc.team5119.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class MastSubsystem extends Subsystem {
	//Motors
		Talon mastMotor = new Talon(3);
	//Sensors
		DigitalInput bottomSwitch = new DigitalInput(RobotMap.mastBottom);
		DigitalInput originSwitch = new DigitalInput(RobotMap.mastOrigin);
		DigitalInput topSwitch = new DigitalInput(RobotMap.mastTop);
		Encoder encoder = new Encoder(RobotMap.mastEncA, RobotMap.mastEncB, false);
	//False positive protection: raise to stop mast from stopping prematurely
		protected int numTrues=0;
	//WIP
		public double mastLevel=0;
		
	public MastSubsystem() {
	}
	
	public void initDefaultCommand() {
	}
	
	public void move(double speed) {
		mastMotor.set(speed);
	}
	
	public boolean isAtBottom() {
		return !bottomSwitch.get();
	}
	
	public boolean isAtOrigin() {
		return originSwitch.get();
	}
	
	public boolean isAtTop() {
		if(topSwitch.get()) {
			numTrues++;
		}else {
			numTrues=0;
		}
		return numTrues >= 10;
	}
	
	public double getPosition() {
		return encoder.get();
	}
	
	public void setTargetLevel(double newTarget) {
		mastLevel = newTarget;
	}

}
