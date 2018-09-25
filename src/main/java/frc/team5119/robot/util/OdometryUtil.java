package frc.team5119.robot.util;

import frc.team5119.robot.subsystems.DriveSubsystem;
import jaci.pathfinder.Pathfinder;

public class OdometryUtil {
    private DriveSubsystem subsystem;


    private volatile Pose2D pose;

    private Thread odometryThread = new Thread(() -> {
        int lastPos = (subsystem.encoders.left.get() + subsystem.encoders.right.get()) / 2;
        while (true) {
            int currentPos = (subsystem.encoders.left.get() + subsystem.encoders.right.get()) / 2;
            double dPos = Units.encoderCountsToFeet(currentPos - lastPos);
            synchronized (this) {
                pose.theta = Pathfinder.d2r(Pathfinder.boundHalfDegrees(subsystem.gyro.getAngle()));
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

    public OdometryUtil(DriveSubsystem driveSubsystem) {
        subsystem = driveSubsystem;
    }

    public void init() {
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

    public Pose2D getPose() {
        return pose.copy();
    }
}
