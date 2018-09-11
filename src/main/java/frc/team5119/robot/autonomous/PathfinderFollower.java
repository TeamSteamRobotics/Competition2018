package frc.team5119.robot.autonomous;

import frc.team5119.robot.Constants;
import frc.team5119.interfaces.subsystems.DriveSubsystem;
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
    }

    /**
     * this method calculates the correct wheel speeds and then drives the bot based on that
     */
    public void calcAndDrive() {
        subsystem.tankDrive(followerLeft.calculate(subsystem.getLeftEncoder()), followerRight.calculate(subsystem.getRightEncoder()));
    }
}
