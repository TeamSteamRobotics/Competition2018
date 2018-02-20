/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5119.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5119.robot.autonomous.AutonomousInit;
import org.usfirst.frc.team5119.robot.autonomous.Strategy;
import org.usfirst.frc.team5119.robot.commands.*;
import org.usfirst.frc.team5119.robot.subsystems.*;

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
	
	CameraServer server;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		server = CameraServer.getInstance();
		server.startAutomaticCapture();
		visionSubsystem = new VisionSubsystem();
		m_oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
		m_autonomousCommand = m_chooser.getSelected();
		mastSubsystem.resetEncoder();
		
		autonomousinit.init();
		/*if(autoSwitchSubsystem.getPosition() == -1) {
			m_autonomousCommand = null;//new LeftAutonomous();
		}else if (autoSwitchSubsystem.getPosition() == 1) {
			m_autonomousCommand = null;//new RightAutonomous();
		}else {
			m_autonomousCommand = new CenterAutonomous();
		}
		m_autonomousCommand = new CenterAutonomous();
*/
		 strategy.start();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			//m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
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
		SmartDashboard.putBoolean("gripperClosed", gripperSubsystem.isFullClosed());
		SmartDashboard.putBoolean("hook limit", gripperSubsystem.isHookReleased());
		SmartDashboard.putNumber("autoSwitch", autoSwitchSubsystem.getPosition());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
