package frc.team5119.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team5119.robot.RobotMap;

/**
 *
 */
public class AutoSwitchSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	protected AnalogInput rightPin = new AnalogInput(RobotMap.autoRightSwitch);
	protected AnalogInput leftPin = new AnalogInput(RobotMap.autoLeftSwitch);
	protected int lastPosition = 1;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public int getPosition() {
    	//return analogPin.getVoltage();
    	SmartDashboard.putNumber("leftSwitch", leftPin.getVoltage());
    	SmartDashboard.putNumber("rightSwitch", rightPin.getVoltage());
    	if (leftPin.getVoltage()>2) {
    	    lastPosition = 0;
    		return 0;
    	} else if (rightPin.getVoltage()>2) {
    	    lastPosition = 2;
    		return 2;
    	} else {
    	    lastPosition = 1;
    		return 1;
    	}
    }

    public boolean hasChanged() {
    	return getPosition() != lastPosition;
	}
}