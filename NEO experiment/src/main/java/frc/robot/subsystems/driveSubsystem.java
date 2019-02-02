/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


import frc.robot.RobotMap;
import frc.robot.commands.driveCommand;


/**
 * Add your docs here.
 */
public class driveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public static final CANSparkMax neo1 = new CANSparkMax(RobotMap.NEO_1, MotorType.kBrushless);
  public static final CANSparkMax neo2 = new CANSparkMax(RobotMap.NEO_2, MotorType.kBrushless);
  public static final CANSparkMax neo3 = new CANSparkMax(RobotMap.NEO_3, MotorType.kBrushless);
  public static final CANSparkMax neo4 = new CANSparkMax(RobotMap.NEO_4, MotorType.kBrushless);

  public static SpeedController leftSide = new SpeedControllerGroup(neo1);
  public static SpeedController rightSide = new SpeedControllerGroup(neo4);

  public static DifferentialDrive drive = new DifferentialDrive(leftSide, rightSide);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new driveCommand());
  }
  
  public void Drive(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation);
  }
}
