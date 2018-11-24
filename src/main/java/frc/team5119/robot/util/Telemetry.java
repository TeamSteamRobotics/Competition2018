package frc.team5119.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team5119.robot.Robot;

public class Telemetry {
    private NetworkTable instance;

    public void sendTeleop() {
        if (instance == null) { instance = NetworkTableInstance.getDefault().getTable("Live Dashboard"); }

        Robot.drivetrain.telemetry.update();

        instance.getEntry("Drive Left Pct").setDouble(Robot.drivetrain.telemetry.leftPct);
        instance.getEntry("Drive Left Amps").setDouble(Robot.drivetrain.telemetry.leftAmps);
        instance.getEntry("Drive Left Encoder").setDouble(Robot.drivetrain.telemetry.leftEnc);

        instance.getEntry("Drive Right Pct").setDouble(Robot.drivetrain.telemetry.rightPct);
        instance.getEntry("Drive Right Amps").setDouble(Robot.drivetrain.telemetry.rightAmps);
        instance.getEntry("Drive Right Encoder").setDouble(Robot.drivetrain.telemetry.rightEnc);
    }

    public void sendAuto() {
        if (instance == null) { instance = NetworkTableInstance.getDefault().getTable("Live Dashboard"); }

        Robot.follower.telemetry.update();

        instance.getEntry("Path X").setDouble(Robot.follower.telemetry.pathX);
        instance.getEntry("Path Y").setDouble(Robot.follower.telemetry.pathY);
        instance.getEntry("Path Heading").setDouble(Robot.follower.telemetry.pathHeading);

        instance.getEntry("Robot X").setDouble(Robot.drivetrain.odo.getX());
        instance.getEntry("Robot Y").setDouble(Robot.drivetrain.odo.getY());
        instance.getEntry("Robot Heading").setDouble(Robot.drivetrain.odo.getTheta());

        instance.getEntry("Game Data").setString(Robot.gameData);
    }
}
