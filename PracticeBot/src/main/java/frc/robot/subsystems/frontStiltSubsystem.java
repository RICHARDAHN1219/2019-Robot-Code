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
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.backStiltSpeedCommand;
import frc.robot.commands.climbCommand;
import frc.robot.commands.frontStiltSpeedCommand;
import frc.robot.commands.autoClimbCommand;

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
  public boolean l2HABFrontPos = false;
  public boolean l3HABFrontPos = false;
  StringBuilder _sb = new StringBuilder();
  public int startPosition1 = 0;
  public int startPosition2 = 0;
  private int targetPosition1 = 0;
  private int targetPosition2 = 0;
  private int kPIDLoopIdx = 0;
  private int kTimeoutMs = 3;  // 30
  // PID parameters below are set by the calling command, for example frontClimbPIDCommand.java
  public double kP;
  public double kI;
  public double kD;
  public double kF;
  private int allowableError = 50;
  private int habLevelRange = 50; 
  public int maxEncoderValue = 31100; //12200;
  public static final int level2EncoderValue = 11934 + 247;
  public static final int level3EncoderValue = 30193 + 247;   // allowable error in encoder ticks

  @Override
  public void initDefaultCommand() {
    // default to manual control
    setDefaultCommand(new autoClimbCommand());
    //setDefaultCommand(new autoClimbCommand());
  }

  public void init() {
    frontStrut1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
        kTimeoutMs);
    frontStrut2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
        kTimeoutMs);
        
    /* Ensure sensor is positive when output is positive */
    frontStrut1.setSensorPhase(false);
    frontStrut1.setInverted(true);
    frontStrut2.setSensorPhase(false);
    frontStrut2.setInverted(false);
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
    startPosition1 = frontStrut1.getSelectedSensorPosition();
    startPosition2 = frontStrut2.getSelectedSensorPosition();

    _sb.append("FRONTSTRUT: start position ");
    _sb.append(startPosition1);
    _sb.append(" start position2 ");
    _sb.append(startPosition2);
    System.out.println(_sb);
    _sb.setLength(0);

    /* Mask out overflows, keep bottom 12 bits. Value will be 0-4096 */
    //startPosition &= 0xFFF;
    frontStrut1.setSelectedSensorPosition(startPosition1, kPIDLoopIdx, kTimeoutMs);
    frontStrut2.setSelectedSensorPosition(startPosition2, kPIDLoopIdx, kTimeoutMs);

    targetPosition1 = startPosition1;
    targetPosition2 = startPosition2;

    printDebug("INIT");
  }

  /*
  * setPosition()
  * 
  * Tell the arm motor to move to the given targetPosition. Target position is relative
  * to the start position when the robot turns on. Position is measured in encoder ticks.
  */

  public void setPosition(int desiredPosition) {
    targetPosition1 = startPosition1 - desiredPosition;
    targetPosition2 = startPosition2 - desiredPosition;

    frontStrut1.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    frontStrut1.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    frontStrut1.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    frontStrut1.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    frontStrut1.configAllowableClosedloopError(allowableError, kPIDLoopIdx, kTimeoutMs);
    frontStrut1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
    kTimeoutMs);
    frontStrut1.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut1.set(ControlMode.Position, targetPosition1);

    frontStrut2.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    frontStrut2.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    frontStrut2.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    frontStrut2.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    frontStrut2.configAllowableClosedloopError(allowableError, kPIDLoopIdx, kTimeoutMs);
    frontStrut2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
    kTimeoutMs);
    frontStrut2.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut2.set(ControlMode.Position, targetPosition2);

    //System.out.println("TARGET SET");
    //System.out.println("TARGET SET");
    //System.out.println("TARGET SET");
    //System.out.println("TARGET 1: " + targetPosition1 + " TARGET 2: " + targetPosition2);

  }


  public void holdCurrentPosition() {
    targetPosition1 = frontStrut1.getSelectedSensorPosition();
    targetPosition2 = frontStrut2.getSelectedSensorPosition();

    //System.out.println("HOLDING");
    //System.out.println("HOLDING");
    //System.out.println("HOLDING");
    //System.out.println("HOLDING");
    //System.out.println("HOLDING");
    //System.out.println("HOLDING");
    //System.out.println("TARGET 1: " + targetPosition1 + " TARGET 2: " + targetPosition2);


    frontStrut1.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut1.set(ControlMode.Position, targetPosition1);

    frontStrut2.setIntegralAccumulator(0.0);  // zero out the kI error accumulator
    frontStrut2.set(ControlMode.Position, targetPosition2);
  }

  public boolean isAtTargetPosition(int desiredPosition) {
    if ((Math.abs(frontStrut1.getSelectedSensorPosition() - targetPosition1) <= allowableError) &&
        (Math.abs(frontStrut2.getSelectedSensorPosition() - targetPosition2) <= allowableError))
    {
        return true;
    }
    else {
        return false;
    }
  }
  public boolean l2HABFrontPos() {
    if ((Math.abs(frontStrut1.getSelectedSensorPosition() - level2EncoderValue) <= habLevelRange) &&
        (Math.abs(frontStrut2.getSelectedSensorPosition() - level2EncoderValue) <= habLevelRange))
    {
        return true;
    }
    else {
        return false;
    }
  }
   
  public boolean l3HABFrontPos() {
    if ((Math.abs(frontStrut1.getSelectedSensorPosition() - level3EncoderValue) <= habLevelRange) &&
        (Math.abs(frontStrut2.getSelectedSensorPosition() - level3EncoderValue) <= habLevelRange))
    {
        return true;
    }
    else {
        return false;
    }
  }
   
  // Set the back stilt climb motor speed, input from [-1,1]
  public void setFrontClimberSpeed(double speed) {

    int fs1_pos = frontStrut1.getSelectedSensorPosition();
    int fs2_pos = frontStrut2.getSelectedSensorPosition();

    int fs1_offset = fs1_pos - startPosition1;
    int fs2_offset = fs2_pos - startPosition2;

    int offset_diff = fs1_offset - fs2_offset;
    double fs1_speed_k = 1.0;
    double fs2_speed_k = 1.0;
    if (speed < 0 ) {
      if (offset_diff < 50) {
        fs1_speed_k = 0.90;
      }
      else if (offset_diff > 50) {
        fs2_speed_k = 0.90;
      }
    }
    else {
      if (offset_diff < 50) {
        fs2_speed_k = 0.90;
      }
      else if (offset_diff > 50) {
        fs1_speed_k = 0.90;
      }
    }

    if ((speed > 0 ) && (fs1_pos > startPosition1 - 800)) {
      frontStrut1.set(ControlMode.PercentOutput, 0);
    }
    else if ((speed < 0 ) && (fs1_pos < startPosition1 - maxEncoderValue)) {
      frontStrut1.set(ControlMode.PercentOutput, 0);
    }
    else {
      frontStrut1.set(ControlMode.PercentOutput, speed * fs1_speed_k);
    }
    if ((speed > 0 ) && (fs2_pos > startPosition2 - 800)) {
      frontStrut2.set(ControlMode.PercentOutput, 0);
    }
    else if ((speed < 0 ) && (fs2_pos < startPosition2 - maxEncoderValue)) {
      frontStrut2.set(ControlMode.PercentOutput, 0);
    }
    else {
      frontStrut2.set(ControlMode.PercentOutput, speed * fs2_speed_k);
    }
    
    //printDebug("speed");
    //System.out.println("SET SPEED: " + speed); 
  }

  // debug the encoder positions and motor output for PID
  public void printDebug(String name) {
    _sb.append("FRONTSTRUT 1 out:");
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
    _sb.append("\tspd:");
		_sb.append(frontStrut1.getSelectedSensorVelocity(0));
		_sb.append("u");
    _sb.append(" " + name);
    System.out.println(_sb);
    /* Reset built string for next loop */
    _sb.setLength(0);
    _sb.append("FRONTSTRUT 2 out:");
    motorOutput = frontStrut2.getMotorOutputPercent();
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
    _sb.append("\tspd:");
		_sb.append(frontStrut2.getSelectedSensorVelocity(0));
		_sb.append("u");
    _sb.append(" " + name);
    System.out.println(_sb);
    /* Reset built string for next loop */
    _sb.setLength(0);
  }
}
