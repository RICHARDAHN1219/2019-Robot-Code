/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.RobotMap;

/**zm
 * Add your docs here.
 */
public class armSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static final WPI_TalonSRX armDrive = new WPI_TalonSRX(RobotMap.ARM_DRIVE);
  StringBuilder _sb = new StringBuilder();
  double targetPositionRotations;
  private static final int kPIDLoopIdx = 0;
  private static final int kTimeoutMs = 30;
  private final double kP = 0.15;
	private final double kI = 0.0;
	private final double kD = 1.0;
  private final double kF = 0.0;


  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    armDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs);
    /* Ensure sensor is positive when output is positive */
    armDrive.setSensorPhase(true);
    armDrive.setInverted(false);
    /* Config the peak and nominal outputs, 12V means full */
    armDrive.configNominalOutputForward(0, kTimeoutMs);
    armDrive.configNominalOutputReverse(0, kTimeoutMs);
    armDrive.configPeakOutputForward(1, kTimeoutMs);
    armDrive.configPeakOutputReverse(-1, kTimeoutMs);
    armDrive.configAllowableClosedloopError(0, kPIDLoopIdx, kTimeoutMs);
    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    armDrive.config_kF( kPIDLoopIdx,  kF,  kTimeoutMs);
		armDrive.config_kP( kPIDLoopIdx,  kP,  kTimeoutMs);
		armDrive.config_kI( kPIDLoopIdx,  kI,  kTimeoutMs);
    armDrive.config_kD( kPIDLoopIdx,  kD,  kTimeoutMs);
    /**
		 * Grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
    int absolutePosition = armDrive.getSensorCollection().getPulseWidthPosition();
    armDrive.setSelectedSensorPosition(absolutePosition,  kPIDLoopIdx,  kTimeoutMs);

  }
  public void setPosition(int targetPositionRotations) {
    armDrive.set(ControlMode.Position, targetPositionRotations);
  
  }
  public void printDebug() {
    _sb.append("\tout:");
    double motorOutput = armDrive.getMotorOutputPercent();
    _sb.append((int) (motorOutput * 100));
    _sb.append("%");	// Percent
    _sb.append("\tpos:");
		_sb.append(armDrive.getSelectedSensorPosition(0));
    _sb.append("u"); 	// Native units
    _sb.append("\ttrg:");
		_sb.append(targetPositionRotations);
    _sb.append("u");	/// Native Units
    System.out.println(_sb);
    /* Reset built string for next loop */
		_sb.setLength(0);
  }
}
