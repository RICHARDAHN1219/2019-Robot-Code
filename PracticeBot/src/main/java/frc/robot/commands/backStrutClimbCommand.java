/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;

public class backStrutClimbCommand extends backClimbPIDCommand {

  public backStrutClimbCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    
    // targetPosition, kP, kI, kD, kF
    //super(16500, 0.6, 0.0, 0.0, 0.0);
    super(3000, 0.6, 0.0, 0.0, 0.0);
    requires(Robot.m_backStilt);
    name = "backClimb";
  }
}
