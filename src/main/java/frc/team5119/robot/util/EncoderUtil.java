package frc.team5119.robot.util;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.team5119.robot.RobotMap;
import frc.team5119.robot.subsystems.DriveSubsystem;

public class EncoderUtil {

    public DriveSubsystem subsystem;

    public Encoder
            left = new Encoder(RobotMap.leftDriveEncA, RobotMap.leftDriveEncB, false),
            right = new Encoder(RobotMap.rightDriveEncA, RobotMap.rightDriveEncB, false);

    public EncoderUtil(DriveSubsystem driveSubsystem) {
        subsystem = driveSubsystem;
    }

    public void init() {
        left.setDistancePerPulse(2 * Math.PI/2048.0); //2pi/2048 ticks per rad
        right.setDistancePerPulse(2 * Math.PI/2048.0);//2pi/2048 ticks per rad
        left.setPIDSourceType(PIDSourceType.kRate);
        right.setPIDSourceType(PIDSourceType.kRate);
        reset();
    }

    public void reset() {
        left.reset();
        right.reset();
    }

    public boolean isStopped() {
        return left.getStopped() && right.getStopped();
    }


}
