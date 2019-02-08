/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.PipelineSwitchZero;
import frc.robot.commands.driveCommand;


/**
 * Add your docs here.
 */
public class limelightSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public NetworkTable table;
  
    //limelightName is the networktable name. For example "limelight-zero"
    @Override    
    public void initDefaultCommand() {
      // Set the default command for a subsystem here.
      // setDefaultCommand(new MySpecialCommand());
      setDefaultCommand(null);
    }
  public void setlimelightName(String limelightName){
    table = NetworkTableInstance.getDefault().getTable(limelightName);
  }
  public void setCargoPipeline() {
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  pipeline.setNumber(0);
  }
  public void setTargetPipeline() {
    NetworkTableEntry pipeline = table.getEntry("pipeline");
    pipeline.setNumber(1);
    }
  }

