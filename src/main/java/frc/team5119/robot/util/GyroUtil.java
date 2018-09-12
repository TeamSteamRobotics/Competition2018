package frc.team5119.robot.util;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import jaci.pathfinder.Pathfinder;

public class GyroUtil {
    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    @Deprecated
    public double targetAngle = 0;

    public void init() {
        reset();
    }

    /**
     * @return gyro angle <b>in degrees</b>
     */
    public double getAngle() {
        return gyro.getAngle() % 360;
    }

    /**
     * @return gyro angle <b>in radians</b>
     */
    public double getAngleRadians() {
        return ( gyro.getAngle() * 0.01745329251 ) % 6.28318530718;
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
