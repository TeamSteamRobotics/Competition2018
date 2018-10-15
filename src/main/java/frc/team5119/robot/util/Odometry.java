package frc.team5119.robot.util;

import frc.team5119.robot.subsystems.Drivetrain;
import jaci.pathfinder.Pathfinder;

public class Odometry {
    private Drivetrain subsystem;


    private volatile Pose2D pose;

    private Thread odometryThread = new Thread(() -> {
        int lastPos = (subsystem.left.get() + subsystem.right.get()) / 2;
        while (true) {
            int currentPos = (subsystem.left.get() + subsystem.right.get()) / 2;
            double dPos = Units.encoderCountsToFeet(currentPos - lastPos);
            synchronized (this) {
                pose.theta = Pathfinder.d2r(subsystem.ahrs.getYaw());
                pose.x += Math.cos(pose.theta) * dPos;
                pose.y += Math.sin(pose.theta) * dPos;
            }
            lastPos = currentPos;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public Odometry(Drivetrain drivetrain) {
        subsystem = drivetrain;
        synchronized (this) {
            pose = new Pose2D(0,0,0);
        }
        odometryThread.start();
    }

    public void setPose(Pose2D newPose) {
        synchronized (this) {
            pose = newPose.copy();
        }
    }

    public void setPose(double x, double y, double theta) {
        synchronized (this) {
            pose = new Pose2D(x, y, theta);
        }
    }

    public Pose2D getPose() {
        return pose.copy();
    }
}
