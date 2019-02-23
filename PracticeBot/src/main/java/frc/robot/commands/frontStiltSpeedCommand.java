/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class frontStiltSpeedCommand extends Command {
  private boolean holding = false;
  public frontStiltSpeedCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_frontStilt);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Robot.m_frontStilt.frontStrut2.follow(Robot.m_frontStilt.frontStrut1);
    // Robot.m_frontStilt.frontStrut2.setInverted(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.m_frontStilt.setFrontClimberSpeed(-OI.climbController.getY(Hand.kLeft));
    
    double climbSpeed = -OI.climbController.getY(Hand.kLeft);
    if (Math.abs(climbSpeed) > 0.1) {
      Robot.m_frontStilt.setFrontClimberSpeed(climbSpeed);
      holding = false;
    }
    else if (!holding) {
      Robot.m_frontStilt.holdCurrentPosition();
      holding = true;
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
