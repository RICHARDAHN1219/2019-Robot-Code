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

/**
 * Add your docs here.
 */
public class visionDriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public static final CANSparkMax vNeo1 = new CANSparkMax(RobotMap.NEO_1, MotorType.kBrushless);
  public static final CANSparkMax vNeo2 = new CANSparkMax(RobotMap.NEO_2, MotorType.kBrushless);
  public static final CANSparkMax vNeo3 = new CANSparkMax(RobotMap.NEO_3, MotorType.kBrushless);
  public static final CANSparkMax vNeo4 = new CANSparkMax(RobotMap.NEO_4, MotorType.kBrushless);

  public static SpeedController visionLeftSide = new SpeedControllerGroup(vNeo1, vNeo3);
  public static SpeedController visionRightSide = new SpeedControllerGroup(vNeo2, vNeo4);

  public static DifferentialDrive drive = new DifferentialDrive(visionLeftSide, visionRightSide);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void Drive(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }
}
