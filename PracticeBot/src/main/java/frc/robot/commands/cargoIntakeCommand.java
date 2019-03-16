/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class cargoIntakeCommand extends Command {

  
  public cargoIntakeCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute(){
    /*
    if (Robot.IS_COMP_BOT) {
      Robot.m_intake.setCargoDriveSpeed(-OI.operatorController.getTriggerAxis(Hand.kRight) * 0.40 + OI.operatorController.getTriggerAxis(Hand.kLeft) * 0.50);
    }
    else {
      Robot.m_intake.setCargoDriveSpeed(OI.operatorController.getTriggerAxis(Hand.kRight) * 0.40 - OI.operatorController.getTriggerAxis(Hand.kLeft) * 0.50);
    }
    */
    
    if (Robot.IS_COMP_BOT) {
      if (Math.abs(OI.operatorController.getTriggerAxis(Hand.kRight) * 0.40 - OI.operatorController.getTriggerAxis(Hand.kLeft) * 0.50) > 0.05) {
        Robot.m_intake.setCargoDriveSpeed(-OI.operatorController.getTriggerAxis(Hand.kRight) + OI.operatorController.getTriggerAxis(Hand.kLeft));
      }
      else {
        Robot.m_intake.setCargoDriveSpeed(0.15);
      }
    }
    else{
      if (Math.abs(OI.operatorController.getTriggerAxis(Hand.kRight) * 0.40 - OI.operatorController.getTriggerAxis(Hand.kLeft) * 0.50) > 0.05) {
        Robot.m_intake.setCargoDriveSpeed(OI.operatorController.getTriggerAxis(Hand.kRight) - OI.operatorController.getTriggerAxis(Hand.kLeft));
      }
      else {
        Robot.m_intake.setCargoDriveSpeed(-0.15);
      }  
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
