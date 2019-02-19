/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.shifterCommand;
import frc.robot.commands.visionlockoncommand;
import frc.robot.subsystems.shifterSubsystem;
import frc.robot.commandGroups.placeHatchLow;
import frc.robot.commandGroups.readyHatchPickup;
import frc.robot.commands.armHighCommand;
import frc.robot.commands.armLowCommand;
import frc.robot.commands.armMiddleCommand;
import frc.robot.commands.backStrutClimb;
import frc.robot.commands.backStrutStart;
import frc.robot.commands.frontStrutClimb;
import frc.robot.commands.frontStrutStart;
import frc.robot.commandGroups.climbLevel3;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  // controllers
  public static XboxController driveController = new XboxController(0);
  public static XboxController operatorController = new XboxController(1);
  public static XboxController climbController = new XboxController(2);

  // Driver
  Button shifterButton = new JoystickButton(driveController, RobotMap.LBumper);
  Button comboButton = new JoystickButton(driveController, RobotMap.Bbutton);
  
  // Operator
  Button hatchPlacementButton = new JoystickButton(operatorController, RobotMap.LBumper);
  Button hatchGrabButton = new JoystickButton(operatorController, RobotMap.RBumper);
  Button armLowButton = new JoystickButton(operatorController, RobotMap.AButton);
  Button armMiddleButton = new JoystickButton(operatorController, RobotMap.Bbutton);
  Button armHighButton = new JoystickButton(operatorController, RobotMap.YButton);

  // Climb
  Button backClimbPIDButton = new JoystickButton(climbController, RobotMap.YButton);
  Button backClimbStartPIDButton = new JoystickButton(climbController, RobotMap.Bbutton);
  Button frontClimbPIDButton = new JoystickButton(climbController, RobotMap.XButton);
  Button frontClimbStartPIDButton = new JoystickButton(climbController, RobotMap.AButton);
  Button level3Climb = new JoystickButton(climbController, RobotMap.StartButton);

  public OI() {
    
    // Driver
    shifterButton.whileHeld(new shifterCommand(shifterSubsystem.Gears.LOW_GEAR));
    comboButton.whileHeld(new visionlockoncommand());

    // Operator
    hatchPlacementButton.toggleWhenPressed(new placeHatchLow());
    hatchGrabButton.whileHeld(new readyHatchPickup());
    armLowButton.whenPressed(new armLowCommand());
    armMiddleButton.whenPressed(new armMiddleCommand());
    armHighButton.whenPressed(new armHighCommand());

    //Climb
    backClimbPIDButton.whenPressed(new backStrutClimb());
    backClimbStartPIDButton.whenPressed(new backStrutStart());
    frontClimbPIDButton.whenPressed(new frontStrutClimb());
    frontClimbStartPIDButton.whenPressed(new frontStrutStart());
    level3Climb.whenPressed(new climbLevel3());
  }
}
