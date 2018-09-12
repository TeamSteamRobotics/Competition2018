package frc.team5119.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import frc.team5119.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team5119.robot.util.DriveUtil;
import frc.team5119.robot.util.EncoderUtil;
import frc.team5119.robot.util.GyroUtil;
import frc.team5119.robot.util.OdometryUtil;
import jaci.pathfinder.Pathfinder;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    public DriveUtil drive = new DriveUtil();
    public EncoderUtil encoders = new EncoderUtil();
    public GyroUtil gyro = new GyroUtil();
    public OdometryUtil odo = new OdometryUtil(this);
	
    public void initDefaultCommand() {}

    public void init() {
        drive.init();
        encoders.init();
        gyro.init();
        odo.init();
    }
}
