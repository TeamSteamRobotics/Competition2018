package frc.team5119.robot.autonomous;

import frc.team5119.robot.*;
import frc.team5119.robot.subsystems.*;
import frc.team5119.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * This class handles following of pathfinder-generated paths
 * TODO switch to ramsete
 */
public class PathfinderFollower {

    Trajectory trajectoryLeft;
    Trajectory trajectoryRight;
    EncoderFollower followerLeft;
    EncoderFollower followerRight;
    DriveSubsystem subsystem;

    /**
     * @param trajectories trajectories[0] is the path for the left wheels, trajectories[1] is the path for the right wheels
     * @param driveSubsystem the driveSubsystem of the bot
     */
    public PathfinderFollower(Trajectory[] trajectories, DriveSubsystem driveSubsystem) {
        trajectoryLeft = trajectories[0];
        trajectoryRight = trajectories[1];
        subsystem = driveSubsystem;
        followerLeft = new EncoderFollower(trajectoryLeft);
        followerRight = new EncoderFollower(trajectoryRight);
        followerLeft.configurePIDVA(Constants.kp, Constants.ki, Constants.kd, Constants.kv, Constants.ka);
        followerRight.configurePIDVA(Constants.kp, Constants.ki, Constants.kd, Constants.kv, Constants.ka);
        followerLeft.configureEncoder(subsystem.encoders.getLeft(), 2048, Constants.k_wheelRadius);
        followerRight.configureEncoder(subsystem.encoders.getRight(), 2048, Constants.k_wheelRadius);
    }

    /**
     * this method calculates the correct wheel speeds and then drives the bot based on that
     */
    public void calcAndDrive() {

        /*double l = followerLeft.calculate(subsystem.encoders.getLeft());
        double r = followerRight.calculate(subsystem.encoders.getRight());

        double gyro_heading = subsystem.gyro.getAngle();    // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(followerLeft.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;
        subsystem.drive.tank(l + turn, r - turn);*/
    }
}
