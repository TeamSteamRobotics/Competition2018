package frc.team5119.robot.autonomous;

import frc.team5119.robot.Constants;
import frc.team5119.robot.Robot;
import jaci.pathfinder.Trajectory;
import frc.team5119.robot.util.Pose2D;
import jaci.pathfinder.Trajectory.Segment;

public class RamseteFollower {
    private double v, w, k1, k3, e_x, e_y, e_theta, v_d, w_d;
    private final double
            b = 0.7,
            zeta = 0.7,
            k2 = 0.7;


    private void update(Segment seg, Segment nextSeg, Pose2D currentPose) {
        v_d = seg.velocity;
        w_d = (nextSeg.heading - seg.heading)/seg.dt;
        k1 = 2 * zeta * Math.sqrt(Math.pow(w_d, 2) + b * Math.pow(v_d, 2));
        k3 = k1;
        e_x = Math.cos(currentPose.theta) * (seg.x - currentPose.x) + Math.sin(currentPose.theta) * (seg.y - currentPose.y);
        e_y = Math.cos(currentPose.theta) * (seg.y - currentPose.y) - Math.sin(currentPose.theta) * (seg.x - currentPose.x);
        e_theta = seg.heading - currentPose.theta;
        v = v_d * Math.cos(e_theta) + k1 * e_x;
        w = w_d + k2 * sinE_thetaOverE_theta() * e_y + k3 * e_theta;
    }

    private double sinE_thetaOverE_theta() {
        if (e_theta < 0.0000001) {
            return 1.0;
        } else {
            return Math.sin(e_theta)/e_theta;
        }
    }
    public void setMotorSpeeds(Trajectory path, int currentSegment){
        update(path.get(currentSegment), path.get(currentSegment + 1), Robot.driveSubsystem.odo.pos.copy());

        double r = Constants.k_wheelRadius;
        double d = Constants.k_wheelbase;

        double w_L = (d * w - 2*v)/(-2 * r);
        double w_R = (d * w + 2*v)/(2 * r);

        Robot.driveSubsystem.drive.leftPID.setSetpoint(w_L);
        Robot.driveSubsystem.drive.rightPID.setSetpoint(w_R);
    }
}
