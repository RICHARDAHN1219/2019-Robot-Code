/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class driveShortDistance extends Command {
  public double xSpeed = 0.5;
  public double zRotation = 0.0;
  public double time = 0.2;

  /* 
   * drive in a given speed and direction for a time.
   */

  public void driveShortDistance(double _xSpeed, double _zRotation, double _time) {
    xSpeed = _xSpeed;
    zRotation = _zRotation;
    time = _time;
    requires(Robot.m_drive);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_drive.Drive(xSpeed, zRotation);
    setTimeout(time);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
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
