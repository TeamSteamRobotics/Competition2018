/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot;

import org.usfirst.frc.team5119.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public Joystick stick = new Joystick(0);
	public Button mast_up = new JoystickButton(stick, 5);
	public Button mast_down = new JoystickButton(stick, 3);
	public Button winch_in = new JoystickButton(stick, 10);
	public Button winch_out = new JoystickButton(stick, 12);
	public Button faceCube = new JoystickButton(stick, 1);
	
	public OI() {
		mast_up.whileHeld(new ManualMastUp());
		mast_down.whileHeld(new ManualMastDown());
		winch_in.whileHeld(new PullWinch());
		winch_out.whileHeld(new ReleaseWinch());
		//faceCube.whileHeld(new FaceCube());
	} 
}
