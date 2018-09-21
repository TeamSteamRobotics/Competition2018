package frc.team5119.robot.util;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import frc.team5119.robot.subsystems.DriveSubsystem;
import jaci.pathfinder.Pathfinder;

public class GyroUtil {

    private DriveSubsystem subsystem;

    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    @Deprecated
    public double targetAngle = 0;

    public GyroUtil(DriveSubsystem driveSubsystem) {
        subsystem = driveSubsystem;
    }

    public void init() {
        reset();
    }

    /**
     * @return gyro angle <b>in degrees</b>
     */
    public double getAngle() {
        return gyro.getAngle() % 360;
    }

    public void reset() {
        gyro.reset();
    }

    @Deprecated
    public double relativeAngle(double reference) {
        double angle = reference - getAngle();
        return Pathfinder.boundHalfDegrees(angle);
    }
}
