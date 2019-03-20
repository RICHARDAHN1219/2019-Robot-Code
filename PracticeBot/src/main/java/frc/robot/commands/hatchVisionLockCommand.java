/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class hatchVisionLockCommand extends Command {

    public boolean m_LimelightHasValidTarget = false;
    private double m_LimelightDriveCommand = 0.0;
    private double m_LimelightSteerCommand = 0.0;


  public hatchVisionLockCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_drive);
    SmartDashboard.putBoolean("HATCH_LOCK", m_LimelightHasValidTarget);  
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putBoolean("HATCH_LOCK", m_LimelightHasValidTarget);  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // These numbers must be tuned for Comp Robot!  Be careful!  
       final double STEER_K = 0.04;                    // how hard to turn toward the target
       final double DRIVE_K = 0.03;                    // how hard to drive fwd toward the target
       final double DESIRED_TARGET_AREA = 50.0;        // Area of the target when the robot reaches the wall
       final double MAX_DRIVE = 0.3;                   // Simple speed limit so we don't drive too fast

       double tv = NetworkTableInstance.getDefault().getTable("limelight-one").getEntry("tv").getDouble(0);
       double tx = NetworkTableInstance.getDefault().getTable("limelight-one").getEntry("tx").getDouble(0);
       double ta = NetworkTableInstance.getDefault().getTable("limelight-one").getEntry("ta").getDouble(0);

       if (tv < 1.0)
       {
         m_LimelightHasValidTarget = false;
         m_LimelightDriveCommand = 0.0;
         m_LimelightSteerCommand = 0.0;
         Robot.m_drive.arcadeDrive(0.0,0.0);
         return;
       }

       m_LimelightHasValidTarget = true;
       //OI.driveController.setRumble(RumbleType.kLeftRumble, 1);
       // Start with proportional steering
       double steer_cmd = tx * STEER_K;
       m_LimelightSteerCommand = steer_cmd;

       // try to drive forward until the target area reaches our desired area
       double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;

       // don't let the robot drive too fast into the goal
       if (drive_cmd > MAX_DRIVE)
       {
         drive_cmd = MAX_DRIVE;
       }
       m_LimelightDriveCommand = drive_cmd;
    
       Robot.m_drive.arcadeDrive(-OI.driveController.getY(GenericHID.Hand.kLeft), -m_LimelightSteerCommand);
     
 }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return isTimedOut();
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
