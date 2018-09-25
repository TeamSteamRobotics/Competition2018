/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team5119.robot.autonomous.PositionCommandHandler;
import frc.team5119.robot.autonomous.RamseteFollower;
import frc.team5119.robot.commands.Drive;
import frc.team5119.robot.subsystems.*;
import frc.team5119.robot.util.Pose2D;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
	public static final MastSubsystem mastSubsystem = new MastSubsystem();
	public static final WinchSubsystem winchSubsystem = new WinchSubsystem();
	public static final GripperSubsystem gripperSubsystem = new GripperSubsystem();
	public static VisionSubsystem visionSubsystem;
	public static final AutoSwitchSubsystem autoSwitchSubsystem = new AutoSwitchSubsystem();
	public static RamseteFollower follower = new RamseteFollower();
	public static OI m_oi;
	
	public static DriverStation driverStation = DriverStation.getInstance();
	public static String switchPositions = "LLR";//driverStation.getGameSpecificMessage();
	
    public static Logger logger = Logger.getLogger("RobotLog");  
    FileHandler fh;


    HashMap<String, Trajectory> trajectories;

	public static VideoSink server;
	public static UsbCamera cam0;
	public static UsbCamera cam1;
	public static UsbCamera cam2;

	private int currentIndex;
	private Trajectory autoTraj;

	Command m_teleopCommand = new Drive();
	SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		try {  

	        // This block configure the logger with handler and formatter  
			fh = new FileHandler("/home/lvuser/robotLog-"+LocalDateTime.now()+".log"); // THIS COULD BREAK. IF SO, GET RID OF LocalDateTime.now()
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        //logger.info("My first log");  

	    } catch (Exception e) {
	        e.printStackTrace();  
	    }

	    logger.info("Hi How r u?");
		cam0 = CameraServer.getInstance().startAutomaticCapture("0",0);
		cam1 = CameraServer.getInstance().startAutomaticCapture("1",1);
//		cam2 = CameraServer.getInstance().startAutomaticCapture("2", 2);
		
		server = CameraServer.getInstance().getServer();
		server.setSource(cam0);
		visionSubsystem = new VisionSubsystem();
        m_oi = new OI();

        driveSubsystem.init();

        // AUTO STUFF
        trajectories = getTrajectoriesfromDirectory(Constants.k_pathLocation);
        m_chooser.addDefault("none", null);
        for (String key : trajectories.keySet()) {
            m_chooser.addObject(key, key);
        }
		SmartDashboard.putData("Auto mode", m_chooser);
        // END AUTO STUFF

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveSubsystem.drive.stopPID();
	    if (m_teleopCommand != null) {
	        m_teleopCommand.cancel();
        }
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		driveSubsystem.gyro.reset();
		mastSubsystem.resetEncoder();
	//	SmartDashboard.putNumber("autoPosition", autoSwitchSubsystem.getPosition());
	}

	/**
	 * resets everything
	 */
	@Override
	public void autonomousInit() {
		mastSubsystem.resetEncoder();
		currentIndex = 0;
		autoTraj = trajectories.get(m_chooser.getSelected() == null ? "easy" : m_chooser.getSelected());
		driveSubsystem.odo.setPose(new Pose2D(autoTraj.get(0).x, autoTraj.get(0).y, autoTraj.get(0).heading));
		driveSubsystem.drive.startPID();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		follower.setMotorSpeeds(autoTraj, currentIndex);
        PositionCommandHandler.getInstance().run(driveSubsystem.odo.getPose());
		currentIndex++;
	}

	@Override
	public void teleopInit() {
		driveSubsystem.drive.stopPID();
		// this starts the teleop command if it isn't null
		if (m_teleopCommand != null) {
			m_teleopCommand.start();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		if (!m_teleopCommand.isRunning()) {
		    m_teleopCommand.start();
        }
		SmartDashboard.putBoolean("bottom", mastSubsystem.isAtBottom());
		SmartDashboard.putBoolean("origin", mastSubsystem.isAtOrigin());
		SmartDashboard.putBoolean("top", mastSubsystem.isAtTop());
		SmartDashboard.putNumber("encoder", mastSubsystem.getPosition());
		SmartDashboard.putBoolean("gripper full closed", gripperSubsystem.isFullClosed());
		SmartDashboard.putBoolean("gripper full open", gripperSubsystem.isFullOpen());
		SmartDashboard.putBoolean("hook limit", gripperSubsystem.isHookReleased());
		SmartDashboard.putNumber("autoSwitch", autoSwitchSubsystem.getPosition());
		SmartDashboard.putNumber("gyro", driveSubsystem.gyro.getAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}


	// huge thanks to 3863 Pantherbotics for saying "just use our csv code"
    public HashMap<String, Trajectory> getTrajectoriesfromDirectory(String dir) {
	    HashMap<String, Trajectory> paths = new HashMap<>();
	    ArrayList<File> filesInFolder;

	    filesInFolder = listf(dir);
	    for ( int i = filesInFolder.size() - 1; i >= 0; i--) {
	        if (filesInFolder.get(i).getName().contains("_source_Jaci.csv")) {
	            filesInFolder.remove(i);
            }
        }

        for ( File traj : filesInFolder ) {
            System.out.println(traj.getName());
            paths.put(traj.getName().replace("_source_Jaci.csv", ""), Pathfinder.readFromCSV(traj));
        }

        return paths;
    }

    public ArrayList<File> listf(String directoryName) {
        File directory = new File(directoryName);

        ArrayList<File> resultList = new ArrayList<>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        return resultList;
    }
}
