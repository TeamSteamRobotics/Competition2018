package org.usfirst.frc.team5119.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VisionSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	NetworkTableInstance tables = NetworkTableInstance.getDefault();
	NetworkTable table;
	double[] sizeTable;
	double[] xTable;
	double[] yTable;
	
	
	double[] defaultValue = {80};

	public VisionSubsystem() {
		table = tables.getTable("GRIP/boxContours");
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    public double getBoxX() {
    	updateArrays();
    	if(xTable.length > 0) {
    		return xTable[0]-320;
    	}
    	return 0;
    }
    public double getBoxY() {
    	updateArrays();
    	return yTable[0];
    }
    public double getBoxArea() {
    	updateArrays();
    	return sizeTable[0];
    }
    public void updateArrays() {
    	sizeTable = table.getEntry("area").getDoubleArray(defaultValue);
    	xTable = table.getEntry("centerX").getDoubleArray(defaultValue);
    	yTable = table.getEntry("centerY").getDoubleArray(defaultValue);
    }
}

