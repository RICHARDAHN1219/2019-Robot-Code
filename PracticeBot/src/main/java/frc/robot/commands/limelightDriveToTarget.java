/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.limelightSubsystem;

public class limelightDriveToTarget extends Command {
  public limelightSubsystem limelight;
  public boolean invertDrive;
  public boolean invertSteer;
  private NetworkTableEntry tvEntry;
  private NetworkTableEntry taEntry;
  private NetworkTableEntry txEntry;
  private NetworkTableEntry tyEntry;
  public limelightSubsystem.Pipeline pipeline;

  double STEER_K;             // how hard to turn toward the target
  double DRIVE_K;             // how hard to drive fwd toward the target
  double DESIRED_TARGET_AREA; // Area of the target when the robot reaches target
  double MAX_DRIVE;           // Max speed limit so we don't drive too fast


  /*
  * limelightDriveToTarget() constructor 
  *     ll     - limelight subsystem instance (Robot.limelightone_one)
  *     pipeline - pipeline number to use
  *     steer  - control how hard to steer towards target
  *     drive  - how hard/fast to drive to target
  *     ta     - desired target area as percent of field of view. range of 1-100. 
  *     md     - max drive/speed. cap speed to this limit.
  *     invd   - invert drive (drive backwards)
  *     invs   - invert steering (driving backwards)
  *
  *  Example:
  *     comboButton.whileHeld(new visionlockoncommand("limelight", 0.075, 0.1, 0.55, 0.5, false, false));
  */
  public limelightDriveToTarget(limelightSubsystem ll, limelightSubsystem.Pipeline p, double steer, double drive, double ta, double md, boolean invd, boolean invs) {
    // declare subsystem dependencies
    requires(Robot.m_drive);
    requires(ll);

    limelight = ll;
    pipeline = p;
  
    STEER_K = steer;
    DRIVE_K = drive;
    DESIRED_TARGET_AREA = ta;
    MAX_DRIVE = md;

    invertDrive = invd;
    invertSteer = invs;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
    tvEntry = limelight.getEntry("tv");
    taEntry = limelight.getEntry("ta");
    txEntry = limelight.getEntry("tx");
    tyEntry = limelight.getEntry("ty");
    limelight.getEntry("camMode").setNumber(0);
    limelight.setPipeline(pipeline);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // http://docs.limelightvision.io/en/latest/networktables_api.html
    // ta: Whether the limelight has any valid targets (0 or 1)
    // tx: Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    // ty: Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    // ta: Target Area (0% of image to 100% of image)
    double tv = tvEntry.getDouble(0.0);

    if (tv < 1.0) {
      // no target, stop driving
      Robot.m_drive.arcadeDrive(0.0, 0.0);
      return;
    }

    double tx = txEntry.getDouble(0.0);
    double ty = tyEntry.getDouble(0.0);
    double ta = taEntry.getDouble(0.0);
    
    // Start with proportional steering
    double steer_cmd = tx * STEER_K;

    // try to drive forward until the target area reaches our desired area
    double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;

    // don't let the robot drive too fast into the goal
    if (drive_cmd > MAX_DRIVE) {
      drive_cmd = MAX_DRIVE;
    }

    if (invertDrive) {
      drive_cmd = -drive_cmd;
    }

    if (invertSteer) {
      steer_cmd = -steer_cmd;
    }

    Robot.m_drive.arcadeDrive(drive_cmd, -steer_cmd);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("END limelightDriveToTarget for " + limelight.getLimelightName());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
