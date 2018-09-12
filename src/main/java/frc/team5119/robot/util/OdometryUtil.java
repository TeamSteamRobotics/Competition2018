package frc.team5119.robot.util;

import frc.team5119.robot.subsystems.DriveSubsystem;
import jaci.pathfinder.Pathfinder;

public class OdometryUtil {
    private DriveSubsystem subsystem;

    public volatile double x, y, theta;

    private Thread odometryThread = new Thread(() -> {
        int lastPos = (subsystem.encoders.getLeft() + subsystem.encoders.getRight()) / 2;
        while (true) {
            int currentPos = (subsystem.encoders.getLeft() + subsystem.encoders.getRight()) / 2;
            double dPos = Units.encoderCountsToFeet(currentPos - lastPos);
            synchronized (this) {
                theta = Pathfinder.d2r(Pathfinder.boundHalfDegrees(subsystem.gyro.getAngle()));
                x += Math.cos(theta) * dPos;
                y += Math.sin(theta) * dPos;
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
            x = 0;
            y = 0;
            theta = 0;
        }
        odometryThread.start();
    }

    public void setPosition(double x, double y, double theta) {
        synchronized (this) {
            this.x = x;
            this.y = y;
            this.theta = theta;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return theta;
    }
}
