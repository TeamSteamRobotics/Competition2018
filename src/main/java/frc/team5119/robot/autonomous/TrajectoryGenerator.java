package frc.team5119.robot.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team5119.robot.RobotMap;
import jaci.pathfinder.*;
import jaci.pathfinder.modifiers.TankModifier;

import java.lang.reflect.Modifier;

public class TrajectoryGenerator {

    Trajectory.Config config;
    Waypoint[] waypointsLeft;
    Waypoint[] waypointsRight;
    Trajectory[] trajectoryLeft;
    Trajectory[] trajectoryRight;
    TankModifier modifier;

    public TrajectoryGenerator(Waypoint[] potentialLeft, Waypoint[] potentialRight) {
        DriverStation.reportWarning("1", false);
        // Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
        // Sample Count:        SAMPLES_HIGH (100 000)
        // Time Step:           0.05 Seconds
        // Max Velocity:        1.7 m/s
        // Max Acceleration:    2.0 m/s/s
        // Max Jerk:            60.0 m/s/s/s
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.25, 1.7, 2.0, 60.0);

        DriverStation.reportWarning("2", false);

        trajectoryLeft = new Trajectory[]{Pathfinder.generate(waypointsLeft, config)};
        DriverStation.reportWarning("2.5", false);
        trajectoryRight = new Trajectory[]{Pathfinder.generate(waypointsRight, config)};

        DriverStation.reportWarning("3", false);

        modifier = new TankModifier(trajectoryLeft[0]);
        modifier.modify(RobotMap.k_wheelbase);
        trajectoryLeft = new Trajectory[]{modifier.getLeftTrajectory(), modifier.getRightTrajectory(), modifier.getSourceTrajectory()};

        DriverStation.reportWarning("4", false);

        modifier = new TankModifier(trajectoryRight[0]);
        modifier.modify(RobotMap.k_wheelbase);
        trajectoryRight = new Trajectory[]{modifier.getLeftTrajectory(), modifier.getRightTrajectory(), modifier.getSourceTrajectory()};

        DriverStation.reportWarning("5", false);

    }

    public Trajectory[] getLeft() {
        return trajectoryLeft;
    }

    public Trajectory[] getRight() {
        return trajectoryRight;
    }
}
