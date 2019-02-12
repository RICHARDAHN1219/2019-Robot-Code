/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.armSubsystem;
import frc.robot.subsystems.backStiltPIDSubsystem;
import frc.robot.subsystems.cargoSubsystem;
import frc.robot.subsystems.climbSubsystem;
import frc.robot.subsystems.driveSubsystem;
import frc.robot.subsystems.frontStiltsPIDSubsystem;
import frc.robot.subsystems.limelightSubsystem;
import frc.robot.subsystems.visionDriveSubsystem;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static cargoSubsystem m_intake;
  public static OI m_oi;
  public static driveSubsystem m_drive;
  public static climbSubsystem m_climb;
  public static visionDriveSubsystem m_vdrive;
  public static limelightSubsystem m_pipeline;
  public static limelightSubsystem limelight_zero;
  public static limelightSubsystem limelight_one;
  public static armSubsystem m_arm;
  public static frontStiltsPIDSubsystem m_fClimbPID;
  public static backStiltPIDSubsystem m_bClimbPID;
  private CANEncoder m_encoder1;
  private CANEncoder m_encoder2;
  private CANEncoder m_encoder3;
  private CANEncoder m_encoder4;
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  public static DoubleSolenoid beakSolenoid = new DoubleSolenoid(0, 1);
  public static DoubleSolenoid shifterSolenoid = new DoubleSolenoid(4, 5);
  public static DoubleSolenoid ejectorSolenoid = new DoubleSolenoid(3, 7);
  PowerDistributionPanel PowerDistributionPanel = new PowerDistributionPanel(0);


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_intake = new cargoSubsystem();
    m_drive = new driveSubsystem();
    m_climb = new climbSubsystem();
    m_vdrive = new visionDriveSubsystem();
    limelight_zero = new limelightSubsystem();
    limelight_one = new limelightSubsystem();
    m_arm = new armSubsystem();
    m_fClimbPID = new frontStiltsPIDSubsystem();
    m_bClimbPID = new backStiltPIDSubsystem();

    limelight_zero.setlimelightName("limelight-zero");
    limelight_one.setlimelightName("limelight-one");
  
    // OI needs to be last
    m_oi = new OI();

    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
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
    m_encoder1 = driveSubsystem.neo1.getEncoder();
    m_encoder2 = driveSubsystem.neo2.getEncoder();
    m_encoder3 = driveSubsystem.neo3.getEncoder();
    m_encoder4 = driveSubsystem.neo4.getEncoder();
    double averageDistance = m_encoder1.getPosition() + m_encoder2.getPosition() + m_encoder3.getPosition() +  m_encoder4.getPosition() / 4;
    double frontEncoder = frontStiltsPIDSubsystem.frontStrut1.getSelectedSensorPosition();
    System.out.println(frontEncoder);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
