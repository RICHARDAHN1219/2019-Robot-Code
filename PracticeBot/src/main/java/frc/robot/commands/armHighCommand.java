/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

public class armHighCommand extends armCommand {

  public armHighCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    // targetPosition, kP, kI, kP, kF
    super(0, 0.6, 0.0003, 0.0, 0.0);

    name = "High";
  }
}
