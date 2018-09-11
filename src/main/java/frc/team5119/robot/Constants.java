package frc.team5119.robot;

public class Constants {
    public static double
    //pathfinder
        // chassis
            k_wheelbase = 0.7,
            k_wheelRadius = 0.15,
        //pathfinder PID tuning
            kp = 0.9,
            ki = 0.0,
            kd = 0.0,
            kv = 1.0/8.0, //this is 1 over max velocity in m/s
            ka = 0.0;
    public static String
            k_pathLocation = "/home/lvuser/paths/";
}
