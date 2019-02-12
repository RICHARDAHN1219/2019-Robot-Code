/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  // motor controlers
  public static final int NEO_1 = 1;
  public static final int NEO_2 = 2;
  public static final int NEO_3 = 3;
  public static final int NEO_4 = 0;
  public static final int FRONT_STRUT_1 = 11;
  public static final int FRONT_STRUT_2 = 10;
  public static final int BACK_STRUT = 13;
  public static final int CLIMBER_DRIVE = 9;
  public static final int CARGO_INTAKE = 4;
  public static final int ARM_DRIVE = 8;

  // buttons
  public static final int AButton = 1;
  public static final int Bbutton = 2;
  public static final int XButton = 3;
  public static final int YButton = 4;
  public static final int LBumper = 5;
  public static final int RBumper = 6;
  public static final int SelectButton = 7;
  public static final int StartButton = 8;
  public static final int LeftJoystickClick = 9;
  public static final int RightJoystickClick = 10;
}
