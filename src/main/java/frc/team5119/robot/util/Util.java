package frc.team5119.robot.util;

public class Util {
    public static double boundHalfRadians(double radians) {
        while (radians < Math.PI) {
            radians += 2 * Math.PI;
        }

        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }

        return radians;
    }
}
