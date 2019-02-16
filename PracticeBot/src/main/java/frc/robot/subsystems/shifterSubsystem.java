/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.shifterCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Shifter Subsystem: shift driving gears between high and low. High is faster.
 */
public class shifterSubsystem extends Subsystem {
  public Gears currentGear;

  public enum Gears {
    HIGH_GEAR, LOW_GEAR;
  }

  @Override
  public void initDefaultCommand() {
    // Default to high gear for speed
    setDefaultCommand( new shifterCommand(Gears.HIGH_GEAR));
  }
   
  public void highGear() {
    // TODO: need to check if this is the correct direction for high gear
    Robot.shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void lowGear() {
    Robot.shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
    currentGear = Gears.LOW_GEAR;
  }

  public Gears getGear() {
    return this.currentGear;
  }
}
