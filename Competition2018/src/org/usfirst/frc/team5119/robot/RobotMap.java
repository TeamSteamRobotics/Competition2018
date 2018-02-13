/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static int
	//sensors
		//mast
			mastTop = 12,
			mastOrigin = 11,
			mastBottom = 10,
			mastEncA = 4,
			mastEncB = 5,
	
	
		//gripper
			gripperBox = 9,
			gripperClosed = 7,
			gripperOpen = 8,
	
		//winch
			winchlimit = 19,
	//motors
		//mast
			mastMotor = 3,
		//gripper
			gripperMotor = 2,
		//winch
			winchMotor = 4;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	public static int wheelEncoder1 = 0;
	public static int wheelEncoder2 = 1;
	public static int mastEncoder1 = 4;
	public static int mastEncoder2 = 5;
	public static int winchLimit = 8;
	public static int gripperClosedLimit = 11;
	public static int gripperOpenLimit = 12;
	public static int gripperBoxGrabbed = 13;
	public static int mastBottomLimit = 9;
	public static int mastTopLimit = 10;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
