/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.cargoIntakeCommand;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class cargoDriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static final WPI_TalonSRX cargoIntake = new WPI_TalonSRX(RobotMap.CARGO_INTAKE);
  public static final WPI_TalonSRX armDrive = new WPI_TalonSRX(RobotMap.ARM_DRIVE);
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new cargoIntakeCommand());
    //setDefaultCommand(new climbBackCommand());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setcargoDriveSpeed(double speed) {
    cargoIntake.set(speed);
  }

  public void setarmDriveSpeed(double speed) {
    armDrive.set(speed);
  }

}
