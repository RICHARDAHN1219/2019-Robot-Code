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

public class pixyBlinkCommand extends Command {

    public pixyBlinkCommand() {
    // Use requires() here to declare subsystem dependencies
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 1, (byte) 1);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(0, 0, 0); 
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 0, (byte) 1); 
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 1, (byte) 0);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 0, (byte) 1);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
    Robot.pixy.setLED(255, 255, 255);
    Robot.pixy.setLamp((byte) 1, (byte) 0);
    Robot.pixy.setLED(0, 0, 0);
    Robot.pixy.setLamp((byte) 0, (byte) 0);
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
