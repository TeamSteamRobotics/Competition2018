package frc.team5119.robot.autonomous;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Strategy {
    boolean scalePriority;

    public void setPriority(String priority) {
        if (priority.equals("scale")) {
            scalePriority = true;
        } else {
            scalePriority = false;
        }
    }

    public Waypoint[] getSwitchLeft( int position ) {
        //TODO replace this placeholder code with actual paths
        return new Waypoint[]{
                new Waypoint(0, 1, 0),
                new Waypoint(0, 2, Pathfinder.d2r(45)),
        };
    }

    public Waypoint[] getSwitchRight( int position ) {
        //TODO replace this placeholder code with actual paths
        return new Waypoint[]{
                new Waypoint(0, 1, 0),
                new Waypoint(0, 2, Pathfinder.d2r(-45)),
        };
    }
}
