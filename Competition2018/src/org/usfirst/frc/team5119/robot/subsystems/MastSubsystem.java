/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class MastSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	Talon mastMotor = new Talon(3);
	DigitalInput originSwitch = new DigitalInput(5);
	DigitalInput topSwitch = new DigitalInput(4);
	Encoder encoder = new Encoder(1, 2, false);
	
	public MastSubsystem() {
		encoder.setDistancePerPulse(00000);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void move(double speed) {
		mastMotor.set(speed);
	}
	
	public boolean isAtOrigin() {
		return originSwitch.get();
	}
	
	public boolean isAtTop() {
		return topSwitch.get();
	}
	
	public double getPosition() {
		return encoder.getDistance();
	}

}
