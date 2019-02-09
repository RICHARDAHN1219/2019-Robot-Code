/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class cargoArmPID extends PIDSubsystem {
  /**
   * Add your docs here.
   */


  public static final WPI_TalonSRX armDrive = new WPI_TalonSRX(RobotMap.ARM_DRIVE);


  public cargoArmPID() {
    // Intert a subsystem name and PID values here
    super("cargoPID", 1, 2, 3);
    double setpoint;
    // Use these to get going:
    Robot.m_cargoArms.setSetpoint(15); //- Sets where the PID controller should move the system
    setAbsoluteTolerance(0.05);
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
    return .7;
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    armDrive.pidWrite(output);
  }
}
