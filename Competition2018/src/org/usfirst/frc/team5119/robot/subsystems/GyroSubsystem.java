package org.usfirst.frc.team5119.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class GyroSubsystem extends Subsystem {
	//Gyro initialise
		protected AHRS gyro = new AHRS(SPI.Port.kMXP);
	
    public void initDefaultCommand() {
    	
    }
    
    public double gyroAngle(){
    	double angle = gyro.getAngle()%360;
    	if(angle<0){
    		return angle+360;
    	}else{
    		return angle;
    	}	
    }
    
    public void resetGyro(){
    	gyro.reset();
    }
    
    public double relativeAngle(double referenceAngle){
    	double angle = (referenceAngle-gyroAngle());
    	if(angle>180){
    		angle-=360;
    	}else if(angle<-180){
    		angle+=360;
    	}
    	return angle;
    }
}

