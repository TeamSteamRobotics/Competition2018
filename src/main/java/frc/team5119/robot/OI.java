/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot;

import frc.team5119.robot.commands.*;

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
    public Button winch_in = new JoystickButton(stick, 11);
    public Button winch_out = new JoystickButton(stick, 12);
    public Button grabCube = new JoystickButton(stick, 1);
    public Button releaseCube = new JoystickButton(stick, 2);
    public Button mast_down2 = new JoystickButton(stick, 7);
    public Button mast_up2 = new JoystickButton(stick, 8);
    public Button grabCube2 = new JoystickButton(stick, 9);
    public Button releaseCube2 = new JoystickButton(stick, 10);
    public Button releaseHook = new JoystickButton(stick, 4);
    public Button changeCamera = new JoystickButton(stick, 6);

    public OI() {

        mast_up.whileHeld(new ManualMastUp());
        mast_down.whileHeld(new ManualMastDown());
        winch_in.whileHeld(new PullWinch());
        winch_out.whileHeld(new ReleaseWinch());
        grabCube.whileHeld(new PickUpCube());
        releaseCube.whileHeld(new DropCube());
        mast_up2.whileHeld(new ManualMastUp());
        mast_down2.whileHeld(new ManualMastDown());
        grabCube2.whileHeld(new PickUpCube());
        releaseCube2.whileHeld(new DropCube());
        releaseHook.whileHeld(new ReleaseHook());
        mast_up.whenReleased(new MaintainMastLevel());
        mast_down.whenReleased(new MaintainMastLevel());
        mast_up2.whenReleased(new MaintainMastLevel());
        mast_down2.whenReleased(new MaintainMastLevel());
        changeCamera.whenPressed(new ChangeCamera());
    }
}
