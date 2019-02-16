/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

/**
 * Subsystem for Gyro.
 * 
 * API documentation:
 *   https://www.kauailabs.com/public_files/navx-mxp/apidocs/java/com/kauailabs/navx/frc/AHRS.html
 */

public class gyroSubsystem extends Subsystem {
  
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private AHRS gyro = new AHRS(SPI.Port.kMXP);
  // Roborio horizontal when laying flat, vertical when laying vertical
  private boolean RoborioHorizontal = false;
 
  // TODO:  get yaw error, return -180 to 180 error off of desired yaw

  public gyroSubsystem() {
    super("gyroSubsystem");   // call constructor to set name 
    gyro.reset();             // zero out the gyro
  }

  public void setRoborioVertical() {
    RoborioHorizontal = false;
  }

  public void setRoborioHorizontal() {
    RoborioHorizontal = true;
  }

  // return the angle of the robot in degrees -180 to 180
  public float getAngle() {

    // get angle command depends on the orientation of the RoboRio
    if (RoborioHorizontal) {
      return gyro.getYaw();
    }
    else {
      return gyro.getRoll();
    }
  } 
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }
}