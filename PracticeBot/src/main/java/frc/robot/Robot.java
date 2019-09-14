/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.armCargoShipCommand;
import frc.robot.commands.armHighCommand;
import frc.robot.commands.armLowCommand;
import frc.robot.commands.armRocketCommand;
import frc.robot.commands.autoClimbCommand;
import frc.robot.commands.climbCommand;
import frc.robot.subsystems.armSubsystem;
import frc.robot.subsystems.backStiltDriveSubsystem;
import frc.robot.subsystems.beakSubsystem;
import frc.robot.subsystems.cargoSubsystem;
import frc.robot.subsystems.driveSubsystem;
import frc.robot.subsystems.ejectorSubsystem;
import frc.robot.subsystems.limelightSubsystem;
import frc.robot.subsystems.shifterSubsystem;
import frc.robot.subsystems.backStiltSubsystem;
import frc.robot.subsystems.frontStiltSubsystem;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Ultrasonic;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.links.SPILink;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static boolean driveInvert = false;
  public static boolean IS_COMP_BOT = true;
  public static boolean climbSwap = false;


  public static cargoSubsystem m_intake;
  public static OI m_oi;
  public static driveSubsystem m_drive;
  public static shifterSubsystem m_shifter;
  public static limelightSubsystem m_pipeline;
  public static limelightSubsystem limelight_zero;
  public static limelightSubsystem limelight_one;
  public static armSubsystem m_arm;
  public static beakSubsystem m_beak;
  public static ejectorSubsystem m_ejector;
  public static backStiltSubsystem m_backStilt;
  public static backStiltDriveSubsystem m_backStiltDrive;
  public static frontStiltSubsystem m_frontStilt;
  public static VideoSink cameraServer;
  public static UsbCamera camera0;
  public static UsbCamera camera1;
  //private CANEncoder m_encoder1;
  //private CANEncoder m_encoder2;
  //private CANEncoder m_encoder3;
  //private CANEncoder m_encoder4;
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  //SendableChooser<Boolean> isCompBot = new SendableChooser<>();
  public static DoubleSolenoid beakSolenoid;
  public static DoubleSolenoid shifterSolenoid;
  public static DoubleSolenoid ejectorSolenoidLeft;
  public static DoubleSolenoid ejectorSolenoidRight;
  PowerDistributionPanel PowerDistributionPanel = new PowerDistributionPanel(0);
  //public Ultrasonic front_ultrasonic = new Ultrasonic(RobotMap.ULTRASONIC_FRONT_PING, RobotMap.ULTRASONIC_FRONT_PING);
  public static Pixy2 pixy = Pixy2.createInstance(new SPILink());
  public static AHRS ahrs;

  //private int loopcount = 0;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    //isCompBot.setDefaultOption("This is comp bot", true);
    //isCompBot.addOption("This is practice bot", false);
    //SmartDashboard.putData("Is this the comp bot?", isCompBot);

    //IS_COMP_BOT = isCompBot.getSelected();

    

    if (IS_COMP_BOT) {
      //beakSolenoid = new DoubleSolenoid(0, 1);
      //shifterSolenoid = new DoubleSolenoid(4, 5);
      //ejectorSolenoidLeft = new DoubleSolenoid(6, 7);
      //ejectorSolenoidRight = new DoubleSolenoid(2, 3);
      beakSolenoid = new DoubleSolenoid(6, 7);
      shifterSolenoid = new DoubleSolenoid(0, 1);
      ejectorSolenoidLeft = new DoubleSolenoid(4, 5);
      ejectorSolenoidRight = new DoubleSolenoid(2, 3);
    } else {
      beakSolenoid = new DoubleSolenoid(0, 1);
      shifterSolenoid = new DoubleSolenoid(4, 5);
      ejectorSolenoidLeft = new DoubleSolenoid(6, 7);
      ejectorSolenoidRight = new DoubleSolenoid(2, 3);
    }

    m_intake = new cargoSubsystem();
    m_drive = new driveSubsystem();
    m_shifter = new shifterSubsystem();
    //limelight_zero = new limelightSubsystem("limelight-zero");
    limelight_one = new limelightSubsystem("limelight-one");
    m_arm = new armSubsystem();
    m_beak = new beakSubsystem();
    m_ejector = new ejectorSubsystem();
    m_frontStilt = new frontStiltSubsystem();
    m_backStilt = new backStiltSubsystem();
    m_backStiltDrive = new backStiltDriveSubsystem();

    m_arm.init();
    m_backStilt.init();
    m_frontStilt.init();
    pixy.init();
    camera0 = CameraServer.getInstance().startAutomaticCapture(0);
    camera1 = CameraServer.getInstance().startAutomaticCapture(1);
    cameraServer = CameraServer.getInstance().getServer();
    //cameraServer.setSource(camera1);
    // OI needs to be last
    m_oi = new OI();
    ahrs = new AHRS(SerialPort.Port.kMXP);
    ahrs.zeroYaw();
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
    //IS_COMP_BOT = isCompBot.getSelected();
    double frontstilts1 = m_frontStilt.frontStrut1.getMotorOutputPercent();
    SmartDashboard.putNumber("front stilt 1", frontstilts1);
    double frontstilts2 = m_frontStilt.frontStrut2.getMotorOutputPercent();
    SmartDashboard.putNumber("front stilt 1", frontstilts2);
    double backstilt = m_backStilt.backStrut.getMotorOutputPercent();
    SmartDashboard.putNumber("back stilts", backstilt);
    double yaw = ahrs.getYaw();
    SmartDashboard.putNumber("yaw", yaw);
    
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
    //double armPos = m_arm.armDrive.getSelectedSensorPosition();
    //System.out.println(armPos);
    //Robot.m_arm.printDebug("debug");
    //double pos = m_arm.armDrive.getSelectedSensorPosition();
    //System.out.println(pos);
    Robot.pixy.setLED(255, 0, 0);
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
    Robot.pixy.setLED(0, 255, 0);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    int dPad = OI.operatorController.getPOV();
    if (dPad == 0) {
      new armHighCommand();
    }
    else if (dPad == 90) {
      new armCargoShipCommand();
    }
    else if (dPad == 180) {
      new armLowCommand();
    }
    else if (dPad == 270) {
      new armRocketCommand();
    }

    /*double yaw = ahrs.getYaw();
    double speed = -OI.climbController.getY(Hand.kLeft);
    if (yaw > 2) {
      m_backStilt.setBackClimberSpeed(speed * 0.7);
      m_frontStilt.setFrontClimberSpeed(speed);
    }
    else if (yaw < 0) {
      m_backStilt.setBackClimberSpeed(speed);
      m_frontStilt.setFrontClimberSpeed(speed * 0.9);
    }
    else {
      m_backStilt.setBackClimberSpeed(speed);
      m_frontStilt.setFrontClimberSpeed(speed);
    }*/
    
    
    //m_encoder1 = driveSubsystem.neo1.getEncoder();
    //m_encoder2 = driveSubsystem.neo2.getEncoder();
    //m_encoder3 = driveSubsystem.neo3.getEncoder();
    //m_encoder4 = driveSubsystem.neo4.getEncoder();
    //double averageDistance = m_encoder1.getPosition() + m_encoder2.getPosition() + m_encoder3.getPosition() +  m_encoder4.getPosition() / 4;
    //System.out.println();
    //Robot.m_frontStilt.printDebug("debug");
    //if (loopcount % 60 == 0) {
      // System.out.println("Front Distance: " + front_ultrasonic.getRangeInches() + " inches");
   // }
    //loopcount++;
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
