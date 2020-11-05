/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.backClimbPIDCommand;
import frc.robot.commands.frontClimbPIDCommand;

public class climbLevel2 extends CommandGroup{

  public climbLevel2() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    addParallel(new frontClimbPIDCommand(12200, 0.8, 0.0, 0.0, 0.0));
    addSequential(new backClimbPIDCommand(5400, 0.6, 0.0, 0.0, 0.0));
  }
}
