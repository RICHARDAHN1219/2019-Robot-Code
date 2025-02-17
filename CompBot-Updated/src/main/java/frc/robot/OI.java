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
import frc.robot.commandGroups.placeHatchLow;
import frc.robot.commandGroups.readyHatchPickup;
import frc.robot.commands.armCargoShipCommand;
import frc.robot.commands.armHighCommand;
import frc.robot.commands.armLowCommand;
import frc.robot.commands.armRocketCommand;
import frc.robot.commands.comboVisionCommand;
import frc.robot.commands.driveInvertCommand;
import frc.robot.commands.limelightVisionCommand;
import frc.robot.commands.pixyVisionCommand;
import frc.robot.commands.shifterCommand;
import frc.robot.subsystems.shifterSubsystem;

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
  // by Button you can create custom triggers and bind those to
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
  Button driveInvert = new JoystickButton(driveController, RobotMap.RBumper);
  Button driverArmDown = new JoystickButton(driveController, RobotMap.YButton);
  Button limelightButton = new JoystickButton(driveController, RobotMap.XButton);
  Button pixyButton = new JoystickButton(driveController, RobotMap.Bbutton);
  Button comboButton = new JoystickButton(driveController, RobotMap.AButton);
  
  // Operator
  Button hatchPlacementButton = new JoystickButton(operatorController, RobotMap.LBumper);
  Button hatchGrabButton = new JoystickButton(operatorController, RobotMap.RBumper);
  Button armLowButton = new JoystickButton(operatorController, RobotMap.AButton);
  Button armCargoShipButton = new JoystickButton(operatorController, RobotMap.Bbutton);
  Button armHighButton = new JoystickButton(operatorController, RobotMap.YButton);
  Button armRocketButton = new JoystickButton(operatorController, RobotMap.XButton);
  Button level2PIDClimb = new JoystickButton(operatorController, RobotMap.SelectButton);
  //Button level3PIDClimb = new JoystickButton(operatorController, RobotMap.StartButton);
  Button climbChangeButton = new JoystickButton(operatorController, RobotMap.StartButton);

  // Climb
  Button backClimbPIDButton = new JoystickButton(climbController, RobotMap.YButton);
  Button backClimbStartPIDButton = new JoystickButton(climbController, RobotMap.Bbutton);
  Button frontClimbPIDButton = new JoystickButton(climbController, RobotMap.XButton);
  Button frontClimbStartPIDButton = new JoystickButton(climbController, RobotMap.AButton);
  Button level3Climb = new JoystickButton(climbController, RobotMap.StartButton);
  Button climbArmUp = new JoystickButton(climbController, RobotMap.RBumper);
  Button climbArmDown = new JoystickButton(climbController, RobotMap.LBumper);
  Button frontUpArmDownButton = new JoystickButton(climbController, RobotMap.AButton);
  

  public OI() {
    
    // Driver
    shifterButton.whileHeld(new shifterCommand(shifterSubsystem.Gears.HIGH_GEAR));
    driveInvert.whenPressed(new driveInvertCommand());
    driverArmDown.whenPressed(new armLowCommand());
    limelightButton.whileHeld(new limelightVisionCommand());
    pixyButton.whileHeld(new pixyVisionCommand());
    comboButton.whileHeld(new comboVisionCommand());

    // Operator
    hatchPlacementButton.toggleWhenPressed(new placeHatchLow());
    hatchGrabButton.whileHeld(new readyHatchPickup());
    armLowButton.whenPressed(new armLowCommand());
    armCargoShipButton.whenPressed(new armCargoShipCommand());
    armHighButton.whenPressed(new armHighCommand());
    armRocketButton.whenPressed(new armRocketCommand());
    //level2PIDClimb.whenPressed(new climbLevel2());
    //climbChangeButton.whenPressed(new climbChangeCommand());

    //Climb
    /*backClimbPIDButton.whenPressed(new backStrutClimbCommand());
    backClimbStartPIDButton.whenPressed(new backStrutStartCommand());
    frontClimbPIDButton.whenPressed(new frontStrutClimb());
    frontClimbStartPIDButton.whenPressed(new frontStrutStartCommand());
    frontUpArmDownButton.whenPressed(new frontUpArmDown());
    level3Climb.whenPressed(new climbLevel3());
    climbArmDown.whenPressed(new armLowCommand());
    climbArmUp.whenPressed(new armHighCommand());*/
  }
}
