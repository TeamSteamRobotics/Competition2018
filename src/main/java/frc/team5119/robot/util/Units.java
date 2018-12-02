package frc.team5119.robot.util;

public class Units {
    public static double encoderCountsToFeet(int count) { return count * (1.60538 / 2048.0); }
}
