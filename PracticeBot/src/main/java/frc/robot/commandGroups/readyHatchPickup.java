/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.ejectorIntake;
import frc.robot.commands.hatchRetrieve;

public class readyHatchPickup extends CommandGroup {
  /**
   * prepare to grab hatch. Open beak. retract pistons.
   */
  public readyHatchPickup() {
    addParallel(new hatchRetrieve());
    addSequential(new ejectorIntake());
  }
}
