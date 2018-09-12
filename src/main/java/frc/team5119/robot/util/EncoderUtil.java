package frc.team5119.robot.util;

import edu.wpi.first.wpilibj.Encoder;
import frc.team5119.robot.RobotMap;

public class EncoderUtil {
    private Encoder
            leftEncoder = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false),
            rightEncoder = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);

    public void init() {
        leftEncoder.setDistancePerPulse(1.4167/2048.0); //17 inches (1.4167 ft) per rot/2048 ticks per rot
        rightEncoder.setDistancePerPulse(1.4167/2048.0);//17 inches (1.4167 ft) per rot/2048 ticks per rot
        reset();
    }

    public int getLeft() {
        return leftEncoder.get();
    }

    public int getRight() {
        return rightEncoder.get();
    }

    public double getLeftFeet() {
        return leftEncoder.getDistance();
    }

    public double getRightFeet() {
        return rightEncoder.getDistance();
    }

    public void reset() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public boolean isStopped() {
        return leftEncoder.getStopped() && rightEncoder.getStopped();
    }
}
