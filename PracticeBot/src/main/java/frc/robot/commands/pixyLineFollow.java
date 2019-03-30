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

// Many thanks to FRC 3786 for blazing a trail and making this possible
// https://github.com/KentridgeRobotics/2019Robot/blob/master/src/main/java/org/usfirst/frc/team3786/robot/utils/RocketPortFinder.java

// This is just an example of what you can do with the pixy2 linefollow.

public class pixyLineFollow extends Command {

  byte features;
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
        
          // angle of the line  (-180,180) 
          // https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:line_quickstart
          theta = Math.toDegrees(Math.atan2(leg2, leg1));

          // how many pixels we're off center (-39,39)
          double off_center =  vector.getX1() - (78/2);
          System.err.println( off_center);
                    
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

    // turn LED's red for robot enabled but pixy disabled
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    // turn LED's red for robot enabled but pixy disabled
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 0, 0);
  }
}
