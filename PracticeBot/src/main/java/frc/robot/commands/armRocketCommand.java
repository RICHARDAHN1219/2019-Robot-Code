/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

public class armRocketCommand extends armCommand {

  public armRocketCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    // targetPosition, kP, kI, kD, kF
    super(1000, 0.4, 0.0005, 0.75, 0.0);

    name = "Middle";
  }
}
