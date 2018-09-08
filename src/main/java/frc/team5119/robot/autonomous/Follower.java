package frc.team5119.robot.autonomous;

import frc.team5119.robot.RobotMap;
import frc.team5119.robot.subsystems.DriveSubsystem;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * This class handles following of pathfinder-generated paths
 * TODO switch to ramsete
 */
public class Follower {

    Trajectory trajectoryLeft;
    Trajectory trajectoryRight;
    EncoderFollower followerLeft;
    EncoderFollower followerRight;
    DriveSubsystem subsystem;

    /**
     * @param trajectories trajectories[0] is the path for the left wheels, trajectories[1] is the path for the right wheels
     * @param driveSubsystem the driveSubsystem of the bot
     */
    public Follower(Trajectory[] trajectories, DriveSubsystem driveSubsystem) {
        trajectoryLeft = trajectories[0];
        trajectoryRight = trajectories[1];
        subsystem = driveSubsystem;
        followerLeft = new EncoderFollower(trajectoryLeft);
        followerRight = new EncoderFollower(trajectoryRight);
        followerLeft.configurePIDVA(RobotMap.kp, RobotMap.ki, RobotMap.kd, RobotMap.kv, RobotMap.ka);
        followerRight.configurePIDVA(RobotMap.kp, RobotMap.ki, RobotMap.kd, RobotMap.kv, RobotMap.ka);
    }

    /**
     * this method calculates the correct wheel speeds and then drives the bot based on that
     */
    public void calcAndDrive() {
        subsystem.tankDrive(followerLeft.calculate(subsystem.getLeftEncoder()), followerRight.calculate(subsystem.getRightEncoder()));
    }
}
