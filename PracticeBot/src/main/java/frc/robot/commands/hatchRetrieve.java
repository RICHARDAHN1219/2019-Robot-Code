/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class hatchRetrieve extends Command {
  public hatchRetrieve() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // System.out.println("Button is held down");
    // NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // NetworkTable table = inst.getTable("limelight-one");
    // NetworkTableEntry tvEntry = table.getEntry("tv");
    /// tvEntry.addListener(event -> {
    /// System.out.println("Vision Lock is currently " + event.value.getValue());
    /// }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);//
    Robot.beakSolenoid.set(DoubleSolenoid.Value.kReverse);
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
