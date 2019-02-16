/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Control a Limelight. 
 *   - Switch between pipelines
 */
public class limelightSubsystem extends Subsystem {
  public NetworkTable table;
  NetworkTableEntry pipeline;

  /*
   * limelightName is the networktable name. For example "limelight-zero"
   */
  public limelightSubsystem(String limelightName)
  {
    table = NetworkTableInstance.getDefault().getTable(limelightName);
    pipeline = table.getEntry("pipeline");
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(null);
  }

  public void setlimelightName(String limelightName) {
    table = NetworkTableInstance.getDefault().getTable(limelightName);
  }

  public void setVisionTargetPipeline() {
    pipeline.setNumber(0);
  }

  public void setCargoPipeline() {
    pipeline.setNumber(1);
  }

  public void setTargetPipeline() {
    pipeline.setNumber(2);
  }

  public void setDrivePipeline() {
    pipeline.setNumber(3);
  }
}

