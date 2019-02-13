/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class armLowCommand extends Command {
  public int targetPosition = 350;

  public armLowCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  public void setPositionLow() {
    // 4096 encoder ticks per revolution
    Robot.m_arm.setPosition(targetPosition);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_arm.kP = 0.1;
    Robot.m_arm.kI = 0;
    Robot.m_arm.kD = 0;
    Robot.m_arm.kF = 0;
    setPositionLow();
    Robot.m_arm.printDebug();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.m_arm.isAtTargetPosition(targetPosition);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("ARM: Finshed moving to Low position");
    Robot.m_arm.printDebug();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // maybe we need to stop moving if some other command needs to move the arm
    System.out.println("ARM: INTERUPTED trying to get to Low position");
    Robot.m_arm.armDrive.set(ControlMode.Disabled, targetPosition);
  }
}
