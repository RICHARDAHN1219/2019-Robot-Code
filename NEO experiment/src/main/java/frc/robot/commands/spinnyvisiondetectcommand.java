/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;


public class spinnyvisiondetectcommand extends Command {
  
  public static final double Kp = -0.08;
  public static double visionLayout = -1.0;

  public spinnyvisiondetectcommand() {
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
    //System.out.println("Button is held down");
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-one");
    //SmartDashboard.putNumber("Kp Value", Kp);

    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry tx = table.getEntry("tx");

    double TX = tx.getDouble(3.14);
    double TV = tv.getDouble(3.14);

    if (TV != visionLayout) {
      visionLayout = TV;

      if (TV == 1.0) {
        System.out.println("Vision lock aquired");
      } else {
        System.out.println("Vision lock lost");
      }
    }

    System.out.format("steering is : %f%n", Kp * TX);

    double steering_adjust = Kp * TX;

    Robot.m_drive.Drive(0, steering_adjust);
    //zRotation += steering_adjust;
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
