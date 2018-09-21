package frc.team5119.robot.util;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.team5119.robot.RobotMap;

public class EncoderUtil {
    public Encoder
            left = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false),
            right = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);

    public void init() {
        left.setDistancePerPulse(2 * Math.PI/2048.0); //2048 ticks per rot
        right.setDistancePerPulse(2 * Math.PI/2048.0);//2048 ticks per rot
        left.setPIDSourceType(PIDSourceType.kRate);
        right.setPIDSourceType(PIDSourceType.kRate);
        reset();
    }

    public int getLeft() {
        return left.get();
    }

    public int getRight() {
        return right.get();
    }

    public double getLeftFeet() {
        return left.getDistance();
    }

    public double getRightFeet() {
        return right.getDistance();
    }

    public void reset() {
        left.reset();
        right.reset();
    }

    public boolean isStopped() {
        return left.getStopped() && right.getStopped();
    }

    public double getLeftSpeed(){
        return left.getRate();
    }

    public double getRightSpeed(){
        return right.getRate();
    }


}
