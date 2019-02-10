/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;
import frc.robot.subsystems.frontStiltsPIDSubsystem;;

/**
 * Add your docs here.
 */
public class backStiltPIDSubsystem extends PIDSubsystem {
  /**
   * Add your docs here.
   */

  public static final WPI_TalonSRX backStrut = new WPI_TalonSRX(RobotMap.BACK_STRUT);

  public backStiltPIDSubsystem() {
    // Intert a subsystem name and PID values here
    super("backStiltPIDSubsystem", 0.5, 0, 0);
    setAbsoluteTolerance(5);
    getPIDController().setContinuous(false);
    setSetpoint(frontStiltsPIDSubsystem.frontStrut1.getSelectedSensorPosition() + frontStiltsPIDSubsystem.frontStrut2.getSelectedSensorPosition() / 2);
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return backStrut.getSelectedSensorPosition() * 1.5;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    backStrut.set(output);
  }
}
