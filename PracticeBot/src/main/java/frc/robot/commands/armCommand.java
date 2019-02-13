/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class armCommand extends Command {

  public armCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // move arm to low position for cargo intake 
  public void setPositionLow() {
    // The arm down position is aprox 1/4 rotation. 
    // 4096 encoder ticks per revolution
    Robot.m_arm.setPosition(-250);
  }

  // move arm to high position for cargo intake 
  public void setPositionHigh() {
    // high position is the same as the start position
    Robot.m_arm.setPosition(0);
  }

  public void rotateUp() {
    // TODO rotate up a small amount and print debug info
    // small amount should be about 1 degree (multiply by 2 for 2:1 gear ratio)
    //  4096 * 2.0 / 360.0   
    Robot.m_arm.printDebug();
  }

  public void rotateDown() {
    // TODO rotate down a small amount and print debug infro
    Robot.m_arm.printDebug();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int currentPosition = Robot.m_arm.getPosition();
    Robot.m_arm.printDebug();
    if (currentPosition > -50) {
      setPositionLow();
    }
    else {
      setPositionHigh();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // maybe we need to stop moving if some other command needs to move the arm
  }
}
