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

  private CANSparkMax neo1 = new CANSparkMax(RobotMap.NEO_1, MotorType.kBrushless);
  private CANSparkMax neo2 = new CANSparkMax(RobotMap.NEO_2, MotorType.kBrushless);
  private CANSparkMax neo3 = new CANSparkMax(RobotMap.NEO_3, MotorType.kBrushless);
  private CANSparkMax neo4 = new CANSparkMax(RobotMap.NEO_4, MotorType.kBrushless);
  private SpeedController leftSide;
  private SpeedController rightSide;
  private DifferentialDrive drive;

  public driveSubsystem(){
    super("driveSubsystem");

    // set all NEOs to factory defaults
    neo1.restoreFactoryDefaults();
    neo2.restoreFactoryDefaults();
    neo3.restoreFactoryDefaults();
    neo4.restoreFactoryDefaults();

    if (false) {
      // Instead of SpeedControllerGroup use NEO controls to lead follow
      // TODO: test this again now that drive is working.
      neo2.follow(neo1);
      neo4.follow(neo3);
      drive = new DifferentialDrive(neo1, neo3);
    }
    else {
      // the old-school way using SpeedControllerGroup
      leftSide = new SpeedControllerGroup(neo1, neo2);
      rightSide = new SpeedControllerGroup(neo3, neo4);
      drive = new DifferentialDrive(leftSide, rightSide);
    }

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new driveCommand());
  }
  
  public void arcadeDrive(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }

}
