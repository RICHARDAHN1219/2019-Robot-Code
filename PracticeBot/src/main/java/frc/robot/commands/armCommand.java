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

public class armCommand extends Command {
  public int targetPosition = 0;
  public String name = "armCommand";
  private double kP = 0.0;
  private double kI = 0.0;
  private double kD = 0.0;
  private double kF = 0.0;

  public armCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_arm);
  }

  public armCommand(int tpos, double _kP, double _kI, double _kD, double _kF) {
    targetPosition = tpos;
    kP = _kP;
    kI = _kI;
    kD = _kD;
    kF = _kF;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_arm.kP = kP;
    Robot.m_arm.kI = kI;
    Robot.m_arm.kD = kD;
    Robot.m_arm.kF = kF;

    setPosition();
    Robot.m_arm.printDebug(name);
  }

  public void setPosition() { 
    // 4096 encoder ticks per revolution
    Robot.m_arm.setPosition(targetPosition);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_arm.printDebug(name);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("ARM: Finished moving to " + name + "position");
    Robot.m_arm.printDebug(name);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // maybe we need to stop moving if some other command needs to move the arm
    System.out.println("ARM: INTERRUPTED trying to get to " + name + "position");
    // Robot.m_arm.armDrive.set(ControlMode.Disabled, targetPosition);
  }
}
