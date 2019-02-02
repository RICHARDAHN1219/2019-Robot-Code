/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

/**
 * Subsystem for Gyro.
 */

public class gyroSubsystem extends Subsystem {
  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private AHRS gyro = new AHRS(SPI.Port.kMXP);
  private boolean RoborioHorizontal = false;
  /* 
  Roborio horizontal when laying flat, vertical when laying vertical
  */
  private boolean RoborioVertical = true;
  
//Returns in degrees https://www.kauailabs.com/public_files/navx-mxp/apidocs/java/com/kauailabs/navx/frc/AHRS.html   class AHRS
 
  // TODO:
  // init function to rest Gyro
  // getRoll function
  // get yaw error, return -180 to 180 error off of desired yaw
 public void setRoborioVertical() {
   RoborioVertical = true;
 }
  public void setRoborioHorizontal() {
    RoborioHorizontal = true;
  }
  public float getAngle() {
    if RoborioHorizontal {
      return gyro.getYaw();
    }
    else {
      return getRoll();
    }
  } 
  

  @Override
  public void initDefaultCommand() {
    
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

    gyro.reset();
  }