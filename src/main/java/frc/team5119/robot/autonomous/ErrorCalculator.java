package frc.team5119.robot.autonomous;

import frc.team5119.robot.Robot;
import jaci.pathfinder.Trajectory;

public class ErrorCalculator{

    double w_d;
    double v_d;

    double k1= .5;
    double k2 = .5;

    public void setIdealVelocities(Trajectory traj, int index){
        w_d = (traj.segments[index].heading - traj.segments[index + 1].heading)/traj.segments[index].dt;
        v_d = traj.segments[index].velocity;
    }

    public double[] getErrors(double XD, double yd, double thetaD){
        double theta = Robot.driveSubsystem.odo.getTheta(); //absolute angle of robot
        double x = Robot.driveSubsystem.odo.getX(); //absolute x coord
        double y = Robot.driveSubsystem.odo.getY(); //absolute y coord

        double eX = Math.cos(theta) * (XD - x) + Math.sin(theta) * (yd - y);
        double eY = Math.cos(theta) * (yd - y) - Math.cos(theta) * (XD - x);
        double eT = thetaD - theta;

        return new double[] {eX, eY, eT};

    }

    public double[] getDesiredVelocities(double XD, double yd, double thetaD){
        double[] errors = getErrors(XD, yd, thetaD);

        double v = v_d * Math.cos(errors[2]) + k1 * Math.sqrt(Math.pow(v_d, 2) + Math.pow(w_d, 2)) * errors[0];

        return new double[] {1};
    }
}
