/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.hatchRetrieveCommand;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Beak subsystem to operate the hatch holding beak.
 */
public class beakSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.

    // by default we want the beak to stay in the close, hatch holding position
    setDefaultCommand(new hatchRetrieveCommand());
  }

  public void hatchRelease() {
    Robot.beakSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void hatchRetrieve() {
    Robot.beakSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
}
