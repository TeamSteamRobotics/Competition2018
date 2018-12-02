package frc.team5119.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.teamsteamrobotics.lib.utils.Pose2D;

import java.util.HashMap;
import java.util.Map;

public class PositionCommandHandler {
    static private PositionCommandHandler instance = null;

    private HashMap<double[], Command> commandList = new HashMap<>();

    /**
     * @param bounds [xmin, xmax, ymin, ymax]
     * @param command command to run when in those bounds
     */
    public void add(double[] bounds, Command command) { commandList.put(bounds, command); }

    /**
     * @param otherMap HashMap to add to the list
     */
    public void add(Map<double[], Command> otherMap) { commandList.putAll(otherMap); }

    /**
     * @param location current robot location
     */
    public void run(Pose2D location) {
        for (Map.Entry<double[], Command> entry : commandList.entrySet()) {
            if (isIn(entry.getKey(), location)) {
                if (!entry.getValue().isRunning()) entry.getValue().start();
            } else {
                if (entry.getValue().isRunning()) entry.getValue().cancel();
            }
        }
    }

    private boolean isIn(double[] bounds, Pose2D location) {
        return location.x > bounds[0] && location.x < bounds[1] && location.y > bounds[2] && location.y < bounds[3];
    }

    static public PositionCommandHandler getInstance() {
        if (instance == null) { instance = new PositionCommandHandler(); }

        return instance;
    }
}