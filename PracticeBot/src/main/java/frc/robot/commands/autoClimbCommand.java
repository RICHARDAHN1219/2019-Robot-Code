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
  private boolean holding = false;

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
    
    double front_joy = -OI.operatorController.getY(Hand.kRight);
 
    double frontSpeed = speed;
    double backSpeed = speed * 0.8;

    if (yaw > 1.5) {
      backSpeed = backSpeed * 0.9;
      frontSpeed = speed;
      //Robot.m_backStilt.setBackClimberSpeed(speed * 0.7);
      //Robot.m_frontStilt.setFrontClimberSpeed(speed);
    }
    else if (yaw < 0.5) {
      backSpeed = speed;
      frontSpeed = speed * 0.9;
      // Robot.m_backStilt.setBackClimberSpeed(speed);
      // Robot.m_frontStilt.setFrontClimberSpeed(speed * 0.9);
    }
    else {
      backSpeed = speed;
      frontSpeed = speed;
      //Robot.m_backStilt.setBackClimberSpeed(speed);
      //Robot.m_frontStilt.setFrontClimberSpeed(speed);
    }

    if (Math.abs(front_joy) > 0.1) {
        frontSpeed = front_joy;
      }
  
    Robot.m_backStilt.setBackClimberSpeed(backSpeed);
    Robot.m_frontStilt.setFrontClimberSpeed(frontSpeed);
   

    
    
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
