/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;


/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public static final Spark motorLeft =new Spark(RobotMap.LEFT_MOTOR);
  public static final Spark motorRight = new Spark(RobotMap.RIGHT_MOTOR);
  public static final CANSparkMax test = new CANSparkMax(RobotMap.CANSpark1, MotorType.kBrushless);

  public static SpeedController leftSide = new SpeedControllerGroup(motorLeft);
  public static SpeedController rightSide = new SpeedControllerGroup(motorRight);

  public static DifferentialDrive drive = new DifferentialDrive(leftSide, rightSide);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveCommand());
  }

  public void Drive(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation);
  }
}
