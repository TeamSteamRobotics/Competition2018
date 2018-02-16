package org.usfirst.frc.team5119.robot.subsystems;

import org.usfirst.frc.team5119.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutoSwitchSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	protected AnalogInput rightPin = new AnalogInput(RobotMap.autoRightSwitch);
	protected AnalogInput leftPin = new AnalogInput(RobotMap.autoLeftSwitch);
	//protected AnalogInput analogPin = new AnalogInput(0);
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public int getPosition() {
    	//return analogPin.getVoltage();
    	if(leftPin.getVoltage()<.1) {
    		return -1;
    	}else if(rightPin.getVoltage()<.1) {
    		return 1;
    	}else {
    		return 0;
    	}
    }
}