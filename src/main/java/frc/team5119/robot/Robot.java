/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5119.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team5119.robot.autonomous.PathfinderFollower;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import frc.team5119.robot.autonomous.AutonomousInit;
import frc.team5119.robot.autonomous.Strategy;
import frc.team5119.robot.subsystems.*;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

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
	public static final Strategy strategy = new Strategy();
	public static final AutonomousInit autonomousinit = new AutonomousInit();
	public static VisionSubsystem visionSubsystem;
	public static final GyroSubsystem gyroSubsystem = new GyroSubsystem();
	public static final AutoSwitchSubsystem autoSwitchSubsystem = new AutoSwitchSubsystem();
	public static OI m_oi;
	
	public static DriverStation driverStation = DriverStation.getInstance();
	public static String switchPositions = "LLR";//driverStation.getGameSpecificMessage();
	
    public static Logger logger = Logger.getLogger("RobotLog");  
    FileHandler fh;


    HashMap<String, Trajectory[]> trajectories;
    PathfinderFollower autoFollower;

	public static VideoSink server;
	public static UsbCamera cam0;
	public static UsbCamera cam1;
	public static UsbCamera cam2;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

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

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
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
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);

        //trajectoryGenerator = new TrajectoryGenerator(strategy.getSwitchLeft(autoSwitchSubsystem.getPosition()), strategy.getSwitchRight(autoSwitchSubsystem.getPosition()));

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	    DriverStation.reportWarning("disabledInit!", false);
        trajectories = getTrajectoriesfromDirectory(Constants.k_pathLocation);
        DriverStation.reportWarning("generation finished!", false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		gyroSubsystem.resetGyro();
		mastSubsystem.resetEncoder();
	//	SmartDashboard.putNumber("autoPosition", autoSwitchSubsystem.getPosition());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		mastSubsystem.resetEncoder();

		if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
            autoFollower = new PathfinderFollower(trajectories.get("test-1"), driveSubsystem);
        } else {
            autoFollower = new PathfinderFollower(trajectories.get("test-2"), driveSubsystem);
        }
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		autoFollower.calcAndDrive();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putBoolean("bottom", mastSubsystem.isAtBottom());
		SmartDashboard.putBoolean("origin", mastSubsystem.isAtOrigin());
		SmartDashboard.putBoolean("top", mastSubsystem.isAtTop());
		SmartDashboard.putNumber("encoder", mastSubsystem.getPosition());
		SmartDashboard.putBoolean("gripper full closed", gripperSubsystem.isFullClosed());
		SmartDashboard.putBoolean("gripper full open", gripperSubsystem.isFullOpen());
		SmartDashboard.putBoolean("hook limit", gripperSubsystem.isHookReleased());
		SmartDashboard.putNumber("autoSwitch", autoSwitchSubsystem.getPosition());
		SmartDashboard.putNumber("gyro", gyroSubsystem.gyroAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}


	// huge thanks to 3863 Pantherbotics for saying "just use our csv code"
    public HashMap<String, Trajectory[]> getTrajectoriesfromDirectory(String dir) {
	    HashMap<String, Trajectory[]> paths = new HashMap<>();
	    ArrayList<File> filesInFolder;

	    filesInFolder = listf(dir);
	    for ( int i = filesInFolder.size() - 1; i >= 0; i--) {
	        if (filesInFolder.get(i).getName().contains("_source_Jaci.csv")) {
	            filesInFolder.remove(i);
            }
        }

        for ( File traj : filesInFolder ) {
            System.out.println(traj.getName());
            paths.put(traj.getName().replace("_source_Jaci.csv", ""), new Trajectory[]{
                    Pathfinder.readFromCSV(new File(traj.getAbsolutePath().replace("_source_", "_left_"))),
                    Pathfinder.readFromCSV(new File(traj.getAbsolutePath().replace("_source_", "_right_"))),
                    Pathfinder.readFromCSV(traj)});
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
