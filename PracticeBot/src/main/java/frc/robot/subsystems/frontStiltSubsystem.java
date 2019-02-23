/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.frontStiltSpeedCommand;

/**
 * armSubsystem controls the cargo collection arm's up and down movement.
 * 
 * Reference Code:
 *   https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/PositionClosedLoop/src/main/java/frc/robot/Robot.java
 *   https://github.com/Spartronics4915/developers_handbook/blob/master/actuators/talon/programming.md#setting-follower-mode
 */
public class frontStiltSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public TalonSRX frontStrut1 = new TalonSRX(RobotMap.FRONT_STRUT_1);
  public TalonSRX frontStrut2 = new TalonSRX(RobotMap.FRONT_STRUT_2);
  StringBuilder _sb = new StringBuilder();
  private int startPosition1 = 0;
  private int startPosition2 = 0;
  private int targetPosition1 = 0;
  private int targetPosition2 = 0;
  private int kPIDLoopIdx = 0;
  private int kTimeoutMs = 3;  // 30
  public double kP;  // 0.15
  public double kI;
  public double kD;  // 1.0
  public double kF;
  private int allowableError = 200;   // allowable error in encoder ticks

  @Override
  public void initDefaultCommand() {
    // default to manual control
    setDefaultCommand(new frontStiltSpeedCommand());
  }

  public void init() {
    frontStrut1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
        kTimeoutMs);
    frontStrut2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
        kTimeoutMs);
    /* Ensure sensor is positive when output is positive */
    if (Robot.IS_COMP_BOT) {
      frontStrut1.setSensorPhase(true);
      frontStrut1.setInverted(true);
      frontStrut2.setSensorPhase(false);
      frontStrut2.setInverted(false);
    }
    else {
      // TODO: check if setSensorPhase is correct on practicebot
      frontStrut1.setSensorPhase(false);
      frontStrut1.setInverted(true);
      frontStrut2.setSensorPhase(true);
      frontStrut2.setInverted(false);
    }
    /* Config the peak and nominal outputs, 12V means full */
    frontStrut1.configNominalOutputForward(0, kTimeoutMs);
    frontStrut1.configNominalOutputReverse(0, kTimeoutMs);
    frontStrut1.configPeakOutputForward(1, kTimeoutMs);
    frontStrut1.configPeakOutputReverse(-1, kTimeoutMs);
    frontStrut1.configAllowableClosedloopError(allowableError, kPIDLoopIdx, kTimeoutMs);
    /* Config Position Closed Loop gains in slot0, typically kF stays zero. */
    frontStrut1.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    frontStrut1.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    frontStrut1.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    frontStrut1.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    /* Config the peak and nominal outputs, 12V means full */
    frontStrut2.configNominalOutputForward(0, kTimeoutMs);
    frontStrut2.configNominalOutputReverse(0, kTimeoutMs);
    frontStrut2.configPeakOutputForward(1, kTimeoutMs);
    frontStrut2.configPeakOutputReverse(-1, kTimeoutMs);
    frontStrut2.configAllowableClosedloopError(allowableError, kPIDLoopIdx, kTimeoutMs);
    /* Config Position Closed Loop gains in slot0, typically kF stays zero. */
    frontStrut2.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    frontStrut2.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    frontStrut2.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    frontStrut2.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    /**
     * Grab the 360 degree position of the MagEncoder's absolute position, and initially set the
     * relative sensor to match.
     */
    startPosition1 = frontStrut1.getSensorCollection().getPulseWidthPosition();
    startPosition2 = frontStrut2.getSensorCollection().getPulseWidthPosition();

    _sb.append("FRONTSTRUT1: start position ");
    _sb.append(startPosition1);
    System.out.println(_sb);
    _sb.setLength(0);

    _sb.append("FRONTSTRUT2: start position ");
    _sb.append(startPosition2);
    System.out.println(_sb);
    _sb.setLength(0);

    /* Mask out overflows, keep bottom 12 bits. Value will be 0-4096 */
    //startPosition1 &= 0xFFF;
    frontStrut1.setSelectedSensorPosition(startPosition1, kPIDLoopIdx, kTimeoutMs);
    frontStrut2.setSelectedSensorPosition(startPosition2, kPIDLoopIdx, kTimeoutMs);

    targetPosition1 = startPosition1 - 0;
    targetPosition2 = startPosition2 - 0;

    printDebug1("INIT");
    printDebug2("INIT");
  }

  /*
  * setPosition()
  * 
  * Tell the arm motor to move to the given targetPosition1. Target position is relative
  * to the start position when the robot turns on. Position is measured in encoder ticks.
  */
  public void setPosition(int desiredPosition) {
    targetPosition1 = startPosition1 - desiredPosition;
    frontStrut1.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    frontStrut1.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    frontStrut1.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    frontStrut1.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    frontStrut1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
    kTimeoutMs);
    frontStrut1.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut1.set(ControlMode.Position, targetPosition1);
    
    targetPosition2 = startPosition2 - desiredPosition;
    frontStrut2.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    frontStrut2.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    frontStrut2.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    frontStrut2.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    frontStrut2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
    kTimeoutMs);
    frontStrut2.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut2.set(ControlMode.Position, targetPosition2);
  }

  public int getPosition1() {
    return frontStrut1.getSensorCollection().getPulseWidthPosition();
  }
  
  public int getPosition2() {
    return frontStrut2.getSensorCollection().getPulseWidthPosition();
  }

  public void holdCurrentPosition() {
    int currentPosition1 = frontStrut1.getSensorCollection().getPulseWidthPosition();
    frontStrut1.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut1.set(ControlMode.Position, currentPosition1);
    int currentPosition2 = frontStrut2.getSensorCollection().getPulseWidthPosition();
    frontStrut2.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut2.set(ControlMode.Position, currentPosition2);
  }
   
  // Set the back stilt climb motor speed
  public void setFrontClimberSpeed(double speed) {
    frontStrut1.set(ControlMode.PercentOutput, speed);
    frontStrut2.set(ControlMode.PercentOutput, speed);
  }

  // debug the encoder positions and motor output for PID
  public void printDebug1(String name) {
    _sb.append("FRONTSTRUT1 out:");
    double motorOutput = frontStrut1.getMotorOutputPercent();
    _sb.append((int) (motorOutput * 100));
    _sb.append("%"); // Percent
    _sb.append("\tpos:");
    _sb.append(frontStrut1.getSelectedSensorPosition(0));
    _sb.append("u"); // Native units
    _sb.append("\ttarget:");
    _sb.append(targetPosition1);
    _sb.append("u"); // Native Units
    _sb.append("\tstart:");
    _sb.append(startPosition1);
    _sb.append("u"); // Native Units
    _sb.append("\terr:");
    _sb.append(frontStrut1.getClosedLoopError(0));
    _sb.append("u");	// Native Units
    _sb.append(" " + name);
    System.out.println(_sb);
    /* Reset built string for next loop */
    _sb.setLength(0);
  }

  public void printDebug2(String name) {
    _sb.append("FRONTSTRUT1 out:");
    double motorOutput = frontStrut2.getMotorOutputPercent();
    _sb.append((int) (motorOutput * 100));
    _sb.append("%"); // Percent
    _sb.append("\tpos:");
    _sb.append(frontStrut2.getSelectedSensorPosition(0));
    _sb.append("u"); // Native units
    _sb.append("\ttarget:");
    _sb.append(targetPosition2);
    _sb.append("u"); // Native Units
    _sb.append("\tstart:");
    _sb.append(startPosition2);
    _sb.append("u"); // Native Units
    _sb.append("\terr:");
    _sb.append(frontStrut2.getClosedLoopError(0));
    _sb.append("u");	// Native Units
    _sb.append(" " + name);
    System.out.println(_sb);
    /* Reset built string for next loop */
    _sb.setLength(0);
  }
}
