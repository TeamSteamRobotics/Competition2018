package frc.team5119.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team5119.robot.util.DriveUtil;
import frc.team5119.robot.util.EncoderUtil;
import frc.team5119.robot.util.GyroUtil;
import frc.team5119.robot.util.OdometryUtil;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    public DriveUtil drive = new DriveUtil(this);
    public EncoderUtil encoders = new EncoderUtil(this);
    public GyroUtil gyro = new GyroUtil(this);
    public OdometryUtil odo = new OdometryUtil(this);
	
    public void initDefaultCommand() {}

    public void init() {
        drive.init();
        encoders.init();
        gyro.init();
        odo.init();
    }
}
