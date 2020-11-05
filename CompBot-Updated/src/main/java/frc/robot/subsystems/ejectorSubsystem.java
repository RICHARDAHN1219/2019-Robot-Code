/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.ejectorIntakeCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Add your docs here.
 */
public class ejectorSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new ejectorIntakeCommand());
  }

  public void extend() {
    if (Robot.IS_COMP_BOT) {
      Robot.ejectorSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
      Robot.ejectorSolenoidRight.set(DoubleSolenoid.Value.kForward);
    }
    else {
      Robot.ejectorSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
      Robot.ejectorSolenoidRight.set(DoubleSolenoid.Value.kForward);
    }
  }

  public void retract() {
    if (Robot.IS_COMP_BOT) {
      Robot.ejectorSolenoidLeft.set(DoubleSolenoid.Value.kForward);
      Robot.ejectorSolenoidRight.set(DoubleSolenoid.Value.kReverse);
    }
    else {
      //Robot.ejectorSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }
}

