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
import frc.robot.RobotMap;

/**
 * armSubsystem controls the cargo collection arm's up and down movement.
 * 
 * Reference Code:
 *   https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/PositionClosedLoop/src/main/java/frc/robot/Robot.java
 *   https://github.com/Spartronics4915/developers_handbook/blob/master/actuators/talon/programming.md#setting-follower-mode
 */
public class armSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static final TalonSRX armDrive = new TalonSRX(RobotMap.ARM_DRIVE);
  StringBuilder _sb = new StringBuilder();
  private int startPosition = 0;
  private int targetPosition = 0;
  private int kPIDLoopIdx = 0;
  private int kTimeoutMs = 3;  // 30
  public double kP;  // 0.15
  public double kI;
  public double kD;  // 1.0
  public double kF;
  private int allowableError = 200;   // allowable error in encoder ticks

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

    armDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx,
        kTimeoutMs);
    /* Ensure sensor is positive when output is positive */
    armDrive.setSensorPhase(true);
    armDrive.setInverted(false);
    /* Config the peak and nominal outputs, 12V means full */
    armDrive.configNominalOutputForward(0, kTimeoutMs);
    armDrive.configNominalOutputReverse(0, kTimeoutMs);
    armDrive.configPeakOutputForward(1, kTimeoutMs);
    armDrive.configPeakOutputReverse(-1, kTimeoutMs);
    armDrive.configAllowableClosedloopError(allowableError, kPIDLoopIdx, kTimeoutMs);
    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    armDrive.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
    armDrive.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
    armDrive.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
    armDrive.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    /**
     * Grab the 360 degree position of the MagEncoder's absolute position, and intitally set the
     * relative sensor to match.
     */
    startPosition = armDrive.getSensorCollection().getPulseWidthPosition();

    _sb.append("ARM: start position ");
    _sb.append(startPosition);
    System.out.println(_sb);
    _sb.setLength(0);

    /* Mask out overflows, keep bottom 12 bits. Value will be 0-4096 */
    //startPosition &= 0xFFF;
    armDrive.setSelectedSensorPosition(startPosition, kPIDLoopIdx, kTimeoutMs);

    targetPosition = startPosition;

    printDebug();
  }

  /*
  * setPosition()
  * 
  * Tell the arm motor to move to the given targetPosition. Target position is relative
  * to the start postion when the robot turns on. Position is measured in encoder ticks.
  */
  public void setPosition(int desiredPosition) {

    if (desiredPosition != targetPosition) {
      targetPosition = desiredPosition;
      armDrive.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
      armDrive.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
      armDrive.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
      armDrive.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
      armDrive.set(ControlMode.Position, targetPosition);
    }
    printDebug();
  }

  public int getPosition() {
    return armDrive.getSensorCollection().getPulseWidthPosition();
  }

  public int getStartPosition() { return startPosition; }

  public int getTargetPosition() { return targetPosition; }

  public boolean isAtTargetPosition(int desiredPosition) {
    if (Math.abs(getPosition() - desiredPosition) <= allowableError) {
        return true;
    }
    else {
        return false;
    }
  }

  /*
  public void setPID(double _kP,double _kI, double _kD) {
    armDrive.config_kP(kPIDLoopIdx, _kP, kTimeoutMs);
    armDrive.config_kI(kPIDLoopIdx, _kI, kTimeoutMs);
    armDrive.config_kD(kPIDLoopIdx, _kD, kTimeoutMs);
  }
  */
  public void printDebug() {
    _sb.append("ARM out:");
    double motorOutput = armDrive.getMotorOutputPercent();
    _sb.append((int) (motorOutput * 100));
    _sb.append("%"); // Percent
    _sb.append("\tpos:");
    _sb.append(armDrive.getSelectedSensorPosition(0));
    _sb.append("u"); // Native units
    _sb.append("\ttarget:");
    _sb.append(targetPosition);
    _sb.append("u"); // Native Units
    _sb.append("\terr:");
    _sb.append(armDrive.getClosedLoopError(0));
    _sb.append("u");	// Native Units
    System.out.println(_sb);
    /* Reset built string for next loop */
    _sb.setLength(0);
  }
}
