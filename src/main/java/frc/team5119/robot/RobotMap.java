/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot;

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
        mastOrigin = 11, mastBottom = 10, mastEncA = 4, mastEncB = 5,

        //gripper
        gripperBox = 9, gripperClosed = 7, gripperOpen = 8, gripperHook = 6,

        //winch
        winchlimit = 2,
        //auto switches
        autoLeftSwitch = 1, autoRightSwitch = 0,
        //motors
        //mast 10,11,18,19
        mastMotor = 3,
        //gripper
        gripperMotor = 2,
        //winch
        winchMotor = 4,

        //drivetrain
        //talon SRX CAN IDs
        frontRightTalon = 0, frontLeftTalon = 1, backRightTalon = 2, backLeftTalon = 3,
        //sensors
        leftDriveEncA = 0, leftDriveEncB = 1, rightDriveEncA = 2, rightDriveEncB = 3;
}
