package frc.team5119.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5119.robot.Constants;
import frc.team5119.robot.Robot;
import jaci.pathfinder.Trajectory;
import frc.team5119.robot.util.Pose2D;
import jaci.pathfinder.Trajectory.Segment;

public class RamseteFollower extends Command {

    private Trajectory path;
    private int segment = 0;
    private double v, w, k1, k3, e_x, e_y, e_theta, v_d, w_d, w_L, w_R;
    private final double
            b = 0.7,
            zeta = 0.8,
            k2 = b;
    private Pose2D currentPose;

    public Telemetry telemetry;

    public RamseteFollower(Trajectory trajectory) {
        path = trajectory;

        requires(Robot.drivetrain);
    }

    public void initialize() {
        telemetry = new Telemetry();
        Robot.drivetrain.startPID();
        Robot.drivetrain.odo.setPose(path.get(0).x, path.get(0).y, path.get(0).heading);
    }

    public void execute() {
        currentPose = Robot.drivetrain.odo.getPose();

        v_d = path.get(segment).velocity;
        w_d = (path.get(segment+1).heading - path.get(segment).heading)/path.get(segment).dt;
        k1 = 2 * zeta * Math.sqrt(Math.pow(w_d, 2) + b * Math.pow(v_d, 2));
        k3 = k1;

        e_x = Math.cos(currentPose.theta) * (path.get(segment).x - currentPose.x) + Math.sin(currentPose.theta) * (path.get(segment).y - currentPose.y);
        e_y = Math.cos(currentPose.theta) * (path.get(segment).y - currentPose.y) - Math.sin(currentPose.theta) * (path.get(segment).x - currentPose.x);
        e_theta = path.get(segment).heading - currentPose.theta;

        v = v_d * Math.cos(e_theta) + k1 * e_x;
        w = w_d + k2 * sinE_thetaOverE_theta() * e_y + k3 * e_theta;

        w_L = (Constants.k_wheelbase * w - 2*v)/(-2 * Constants.k_wheelRadius);
        w_R = (Constants.k_wheelbase * w + 2*v)/(2 * Constants.k_wheelRadius);

        Robot.drivetrain.left.setSpeed(w_L);
        Robot.drivetrain.right.setSpeed(w_R);

        Robot.telemetry.send();
    }

    public void end() {
        Robot.drivetrain.stopPID();
    }

    public boolean isFinished() {
        return segment >= path.length();
    }

    private double sinE_thetaOverE_theta() {
        if (e_theta < 0.0000001) {
            return 1.0;
        } else {
            return Math.sin(e_theta)/e_theta;
        }
    }

    public class Telemetry {
        public double pathX, pathY, pathHeading;

        public void update() {
            pathX = path.get(segment).x;
            pathY = path.get(segment).y;
            pathHeading = path.get(segment).heading;
        }
    }
}
