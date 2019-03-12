/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2Line;
import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

public class pixyLineFollow extends Command {

  byte features;
  private boolean isFinished = false;
  public double theta;

  public pixyLineFollow() {
    // Use requires() here to declare subsystem dependencies
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // turn on the LED lights
    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 1, (byte) 1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    features = Robot.pixy.getLine().getMainFeatures();
    if ((features & Pixy2Line.LINE_VECTOR) == Pixy2Line.LINE_VECTOR) {
      Vector[] vectors = Robot.pixy.getLine().getVectors();
      if (vectors != null) {
        if (vectors.length > 0) {
          Vector vector = vectors[0];
          double leg1 = vector.getX0() - vector.getX1();
          double leg2 = vector.getY1() - vector.getY0();
          // angle of the line
          theta = Math.toDegrees(Math.atan2(leg2, leg1));
          // Where the line crosses the center of the image
          double center = (vector.getX0() + vector.getX1()) / 2.0;
          double off_center = center - 78;

          System.err.println("!!! Theta:" + theta + "  Off Center:"  + off_center );
          isFinished = true;
        } else {
          System.err.println("!!!Vector length is 0 or less. Unsuccessful!!!");
        
        } // (vectors.length > 0)
      } else {
        System.err.println("!!!Vector is Null. Unsuccessful!!!");
       
      } // (vectors != null)
    } else {
      System.err.println("!!!LINE_VECTOR Byte not on. Unsuccessful!!!");
      
    } // ((features & Pixy2Line.LINE_VECTOR) == Pixy2Line.LINE_VECTOR)
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // turn off the LEDs
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
