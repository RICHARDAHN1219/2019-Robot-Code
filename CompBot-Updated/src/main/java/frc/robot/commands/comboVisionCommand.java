/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import io.github.pseudoresonance.pixy2api.Pixy2Line;
import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

public class comboVisionCommand extends Command {

    byte features;
    public boolean m_LimelightHasValidTarget = false;
    public boolean m_LineFollowOnTarget = false;

    public double STEER_K = 0.03; // how hard to turn toward the target
    public double DRIVE_K = 0.06; // how hard to drive fwd toward the target
    public double LF_STEER_K = 0.025; // line follow kP
    public double DESIRED_TARGET_AREA = 45.0; // Area of the target when the robot reaches the wall
    public double MAX_DRIVE = 0.4; // Simple speed limit so we don't drive too fast
    public double LF_TARGET_AREA = 0; // Start looking for line follow at this distance
    public NetworkTable limelightTable;
    public boolean closeEnoughtForLineFollow = false;

    public comboVisionCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_drive);
    SmartDashboard.putBoolean("HATCH_LOCK", m_LimelightHasValidTarget);
    SmartDashboard.putBoolean("LF_ONTARGET", m_LineFollowOnTarget);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putBoolean("HATCH_LOCK", m_LimelightHasValidTarget);
    SmartDashboard.putBoolean("LF_ONTARGET", m_LineFollowOnTarget);

    // save the limelight table for later
    limelightTable = NetworkTableInstance.getDefault().getTable("limelight-one");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double tv = limelightTable.getEntry("tv").getDouble(0);
    double tx = limelightTable.getEntry("tx").getDouble(0);
    double ta = limelightTable.getEntry("ta").getDouble(0);

    double drive_cmd = 0.0;
    double steer_cmd = 0.0;

    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 1, (byte) 1);

    // are we doing line folow? so we an ignore the limelight
    boolean lineFollow = false;

    // how far off from center are we in pixels (-39,39)
    int lf_off_center = 0;

    if (ta >= LF_TARGET_AREA) {
      if (!closeEnoughtForLineFollow) {
        // Now that we're close enough, turn on the Pixy LED lights
        Robot.pixy.setLED(255, 255, 255);
        Robot.pixy.setLamp((byte) 1, (byte) 1);
      }

      // robot is close enough to target to start looking for a line to follow
      closeEnoughtForLineFollow = true;
    }

    if (closeEnoughtForLineFollow) {
      features = Robot.pixy.getLine().getMainFeatures();
      if ((features & Pixy2Line.LINE_VECTOR) == Pixy2Line.LINE_VECTOR) {
        // The pixy2 has picked up a line to follow
        Vector[] vectors = Robot.pixy.getLine().getVectorCache();
        if (vectors != null) {
          if (vectors.length > 0) {
            Vector vector = vectors[0];

            // distance off of center, the pixy image is 78 pixels wide.
            // X1 is the X coordinate of top of the line vector
            lf_off_center = vector.getX0() - 39 + 5;
            lineFollow = true;

            // TODO: maybe the sign of lf_off_center is reversed? need to test.
            steer_cmd = LF_STEER_K * -lf_off_center;

            drive_cmd = 0.1; // go slow

            if (Math.abs(lf_off_center) <= 8) {
              m_LineFollowOnTarget = true;
            }
            else {
              m_LineFollowOnTarget = false;
            }
          }
        }
      }
      else {
        m_LineFollowOnTarget = false;
      }
    }

    if ((tv < 1.0) && !lineFollow) {
      m_LimelightHasValidTarget = false;
      Robot.m_drive.arcadeDrive(0.0, 0.0);
      return;
    }

    if (!lineFollow) {
      m_LimelightHasValidTarget = true;

      // lock beak up out of the way, only for testing.
      // Robot.m_beak.hatchRetrieve();

      // Start with proportional steering
      steer_cmd = tx * STEER_K;

      // try to drive forward until the target area reaches our desired area
      drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
    }

    // don't let the robot drive too fast into the goal
    if (drive_cmd > MAX_DRIVE) {
      drive_cmd = MAX_DRIVE;
    }

    // drive_cmd is ignore, we use the driver's input
    Robot.m_drive.arcadeDrive(-OI.driveController.getY(GenericHID.Hand.kLeft) * 0.8, -steer_cmd);

    SmartDashboard.putBoolean("HATCH_LOCK", m_LimelightHasValidTarget);
    SmartDashboard.putBoolean("LF_ONTARGET", m_LineFollowOnTarget);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // This command runs while the button is pressed, so always return false.
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // turn off the LEDs
    Robot.pixy.setLED(0, 255, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    SmartDashboard.putBoolean("HATCH_LOCK", false);
    SmartDashboard.putBoolean("LF_ONTARGET", false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // turn off the LEDs
    Robot.pixy.setLED(0, 255, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    SmartDashboard.putBoolean("HATCH_LOCK", false);
    SmartDashboard.putBoolean("LF_ONTARGET", false);
  }
}