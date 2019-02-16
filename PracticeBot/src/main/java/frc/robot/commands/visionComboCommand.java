/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class visionComboCommand extends Command {

  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ta;
  NetworkTableEntry tv;

  public static final double KpAim = -0.1;
  public static final double KpDistance = -0.025;
  public static final double min_aim_command = 0.025;
  public static final double desired_distance = 12 + 32;
  public static double steering_adjust = 0.0;

  public visionComboCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    table = NetworkTableInstance.getDefault().getTable("limelight-one");
    tv = table.getEntry("tv");
    tx = table.getEntry("tx");
    ta = table.getEntry("ta");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double TX;
    double TA;
    double heading_error;
    double current_distance;
    double distance_error;
    double TV = tv.getDouble(0.0);

    if (TV == 0.0) {
      System.out.println("Lost track of target.");
      return;
    }

    // Use default values outside of the acceptable ranges so we can check if we
    // got a valid response. 
    TX = tx.getDouble(180.0);   // return angle offset to target -27 to 27 degrees
    TA = ta.getDouble(-1.0);    // return area as percent of image 0-100

    if ((TX > 45.0) || ( TA < 0.0)) {
      System.out.println("Bad target values. tx: " + TX + " ta:" + TA);
      return;
    }

    heading_error = -TX;
    current_distance = (((-14.4892) * (TA * TA * TA)) + ((102.636) * (TA * TA)) - ((253.741) * (TA)) + 255.752);
    distance_error = -1 * (current_distance - desired_distance);

    System.out.format("Distance is : %f%n", current_distance);

    if (TX > 0) {
      steering_adjust = KpAim * heading_error - min_aim_command;
    } else if (TX < 0) {
      steering_adjust = KpAim * heading_error + min_aim_command;
    }

    double distance_adjust = KpDistance * distance_error;

    double left_command = 0.5 * (steering_adjust + distance_adjust);
    double right_command = 0.5 * (steering_adjust + distance_adjust);

    if (TX > 0) {
      left_command = left_command + 0.1;
    } else if (TX < 0) {
      right_command = right_command + 0.1;
    }

    Robot.m_vdrive.Drive(left_command, right_command);

/*
float KpAim = -0.1f;
float KpDistance = -0.1f;
float min_aim_command = 0.05f;

std::shared_ptr<NetworkTable> table = NetworkTable::GetTable("limelight");
float tx = table->GetNumber("tx");
float ty = table->GetNumber("ty");

if (joystick->GetRawButton(9))
{
    float heading_error = -tx;
    float distance_error = -ty;
    float steering_adjust = 0.0f;

    if (tx > 1.0)
    {
    steering_adjust = KpAim*heading_error - min_aim_command;
    }
    else if (tx < 1.0)
    {
    steering_adjust = KpAim*heading_error + min_aim_command;
    }

    float distance_adjust = KpDistance * distance_error;

    left_command += steering_adjust + distance_adjust;
    right_command -= steering_adjust + distance_adjust;
}
*/
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // TODO: we should realease control of the drive on a button press or driver input
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
