package frc.team5119.robot.util;

import frc.team5119.robot.subsystems.DriveSubsystem;
import jaci.pathfinder.Pathfinder;

public class OdometryUtil {
    private DriveSubsystem subsystem;


    public volatile Pose2D pos;

    private Thread odometryThread = new Thread(() -> {
        int lastPos = (subsystem.encoders.getLeft() + subsystem.encoders.getRight()) / 2;
        while (true) {
            int currentPos = (subsystem.encoders.getLeft() + subsystem.encoders.getRight()) / 2;
            double dPos = Units.encoderCountsToFeet(currentPos - lastPos);
            synchronized (this) {
                pos.theta = Pathfinder.d2r(Pathfinder.boundHalfDegrees(subsystem.gyro.getAngle()));
                pos.x += Math.cos(pos.theta) * dPos;
                pos.y += Math.sin(pos.theta) * dPos;
            }
            lastPos = currentPos;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public OdometryUtil(DriveSubsystem driveSubsystem) {
        subsystem = driveSubsystem;
    }

    public void init() {
        synchronized (this) {
            pos = new Pose2D(0,0,0);
        }
        odometryThread.start();
    }

    public void setPosition(double x, double y, double theta) {
        synchronized (this) {
            pos = new Pose2D(x, y, theta);
        }
    }

    public double getX() {
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }

    public double getTheta() {
        return pos.theta;
    }
}
