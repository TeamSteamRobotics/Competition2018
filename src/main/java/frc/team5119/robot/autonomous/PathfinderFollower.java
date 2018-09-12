package frc.team5119.robot.autonomous;

import frc.team5119.robot.Constants;
import frc.team5119.interfaces.subsystems.DriveSubsystem;
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
        followerLeft.configureEncoder(subsystem.getLeftEncoder(), 2048, Constants.k_wheelRadius);
        followerRight.configureEncoder(subsystem.getRightEncoder(), 2048, Constants.k_wheelRadius);
    }

    /**
     * this method calculates the correct wheel speeds and then drives the bot based on that
     */
    public void calcAndDrive() {
        double l = followerLeft.calculate(subsystem.getLeftEncoder());
        double r = followerRight.calculate(subsystem.getRightEncoder());

        double gyro_heading = subsystem.getGyroAngle();    // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(followerLeft.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;
        subsystem.tankDrive(l + turn, r - turn);
    }
}
