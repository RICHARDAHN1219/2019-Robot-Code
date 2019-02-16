/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;

public class visionApproachCommand extends Command {
  
  public static final double KpDistance = -0.075; 
  public static final double desired_distance = 3 + 32;

  public visionApproachCommand() {
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
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-one");

    NetworkTableEntry ta = table.getEntry("ta");

    double TA = Math.sqrt(ta.getDouble(3.14));

    //double current_distance = (((42.357) * (TA * TA * TA * TA)) + ((-226.564) * (TA * TA * TA)) + ((426.004) * (TA * TA)) + ((-355.428) * TA) + 131.267);
    //double current_distance = (((-57.2018) * (TA * TA * TA)) + ((236.12) * (TA * TA)) - ((348.151) * (TA)) + 184.706);
    double current_distance = (((-14.4892) * (TA * TA * TA)) + ((102.636) * (TA * TA)) - ((253.741) * (TA)) + 255.752);

    //System.out.format("Area is : %f%n", TA);
    System.out.format("Distance is : %f%n", current_distance);

    double distance_error = desired_distance - current_distance;
    double driving_adjust = KpDistance * distance_error;

    Robot.m_drive.arcadeDrive(driving_adjust, 0);
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
