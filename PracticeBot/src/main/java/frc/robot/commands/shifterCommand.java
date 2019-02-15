/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class shifterCommand extends Command {
  public String gear;

  public shifterCommand(String _gear) {
    // valid gears are "HIGH_GEAR" and "LOW_GEAR"
    gear = _gear;
    requires(Robot.m_shifter);

    if (!gear.equals("HIGH_GEAR") && !gear.equals("LOW_GEAR")) {
      System.out.println("ERROR: bad gear selection: [" + gear + "]");
      gear = "HIGH_GEAR";
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (gear.equals("LOW_GEAR")) {
      Robot.m_shifter.lowGear();
    }
    else {
      Robot.m_shifter.highGear();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // nothing to do, it's all in initialize()
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // this is the default command, so we keep running until we're interupted
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
