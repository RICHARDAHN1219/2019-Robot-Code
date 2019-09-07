/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.OI;

public class autoClimbCommand extends Command {
  

  public autoClimbCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_frontStilt);
    requires (Robot.m_backStilt);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double yaw = Robot.ahrs.getYaw();
    double speed = -OI.operatorController.getY(Hand.kLeft);
    if (yaw > 2) {
      Robot.m_backStilt.setBackClimberSpeed(speed * 0.7);
      Robot.m_frontStilt.setFrontClimberSpeed(speed);
    }
    else if (yaw < 0) {
      Robot.m_backStilt.setBackClimberSpeed(speed);
      Robot.m_frontStilt.setFrontClimberSpeed(speed * 0.9);
    }
    else {
      Robot.m_backStilt.setBackClimberSpeed(speed);
      Robot.m_frontStilt.setFrontClimberSpeed(speed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
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
