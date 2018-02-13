package org.usfirst.frc.team5119.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;


/**
 *
 */
public class GyroSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//protected Gyro gyro = new ADXRS450_Gyro();
	protected AHRS gyro = new AHRS(SPI.Port.kMXP);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
    	/*double angle = (gyro.getAngle()-referenceAngle-180)%360;
    	if(angle<0){
    		return angle+360;
    	}else{
    		return angle;
    	}
    	*/
    	double angle = (referenceAngle-gyroAngle());
    	if(angle>180){
    		angle-=360;
    	}else if(angle<-180){
    		angle+=360;
    	}
    	return angle;
    }
}

