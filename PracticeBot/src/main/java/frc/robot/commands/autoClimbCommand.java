/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import frc.robot.Robot;
import frc.robot.subsystems.backStiltSubsystem;
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
    double backSpeed = speed * 0.80;

    if (speed < 0) {
      if (yaw > 1.2) {
        backSpeed = backSpeed * 0.9;
        frontSpeed = frontSpeed;
        //Robot.m_backStilt.setBackClimberSpeed(speed * 0.7);
        //Robot.m_frontStilt.setFrontClimberSpeed(speed);
      }
      else if (yaw < 0.8) {
        backSpeed = backSpeed;
        frontSpeed = frontSpeed * 0.8;
        // Robot.m_backStilt.setBackClimberSpeed(speed);
        // Robot.m_frontStilt.setFrontClimberSpeed(speed * 0.9);
      }
  
      if (Math.abs(front_joy) > 0.1) {
          frontSpeed = front_joy;
        }
    }
    else {
      if (yaw > 1.25) {
        backSpeed = backSpeed;
        frontSpeed = frontSpeed * 0.1;
        //Robot.m_backStilt.setBackClimberSpeed(speed * 0.7);
        //Robot.m_frontStilt.setFrontClimberSpeed(speed);
      }
      else if (yaw < 0.75) {
        backSpeed = backSpeed * 0.1;
        frontSpeed = frontSpeed;
        // Robot.m_backStilt.setBackClimberSpeed(speed);
        // Robot.m_frontStilt.setFrontClimberSpeed(speed * 0.9);
      }
  
      if (Math.abs(front_joy) > 0.1) {
          frontSpeed = front_joy;
        }
    }
  
    Robot.m_backStilt.setBackClimberSpeed(backSpeed);
    Robot.m_frontStilt.setFrontClimberSpeed(frontSpeed);
    SmartDashboard.putBoolean("Back Level 2?", Robot.m_backStilt.l2HABBackPos());
    SmartDashboard.putBoolean("Back Level 3?", Robot.m_backStilt.l3HABBackPos());
    SmartDashboard.putBoolean("Front Level 2?", Robot.m_frontStilt.l2HABFrontPos());
    SmartDashboard.putBoolean("Front Level 3?", Robot.m_frontStilt.l3HABFrontPos());

    
    
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
