/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.hatchRelease;
import frc.robot.commands.hatchRetrieve;
import frc.robot.commands.shifterCommand;
import frc.robot.commands.visionTargetingCommand;
import frc.robot.commands.visionApproachCommand;
import frc.robot.commands.visionComboCommand;

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

  // buttons on driveController
  Button hatchButton = new JoystickButton(driveController, RobotMap.AButton);
  Button targetingButton = new JoystickButton(driveController, RobotMap.Bbutton);
  Button approachButton = new JoystickButton(driveController, RobotMap.YButton);
  Button comboButton = new JoystickButton(driveController, RobotMap.XButton);
  Button shifterButton = new JoystickButton(driveController, RobotMap.LBumper);
  Button armButton = new JoystickButton(operatorController, RobotMap.AButton);

  public OI() {
    hatchButton.whileHeld(new hatchRetrieve());
    hatchButton.whenReleased(new hatchRelease());
    targetingButton.whileHeld(new visionTargetingCommand());
    approachButton.whileHeld(new visionApproachCommand());
    comboButton.whileHeld(new visionComboCommand());
    shifterButton.whileHeld(new shifterCommand());
    armButton.whileHeld(new armCommand());
  }
}
