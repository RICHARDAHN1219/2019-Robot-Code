/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;

public class frontStrutClimb extends frontClimbPIDCommand {

  public frontStrutClimb() {    
    // targetPosition, kP, kI, kD, kF
    // TODO: need to tune the position target value for front strut
    //super(17500, 0.2, 0.0, 0.0, 0.0);
    super(10000, 0.4, 0.0, 0.0, 0.0);
    requires(Robot.m_frontStilt);
    name = "frontClimb";
  }
}
