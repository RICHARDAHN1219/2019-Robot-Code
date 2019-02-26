/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/


package frc.robot.commandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.backClimbPIDCommand;
import frc.robot.commands.frontClimbPIDCommand;

public class climbLevel3 extends CommandGroup {

  public climbLevel3() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    
    int n = 7; //number of increments to use
    int start_front = 0;
    int end_front = 18200;
    int start_back = 0;
    int end_back = 7300;
    int delta_front = end_front - start_front;
    int delta_back = end_back - start_back;
    int inc_front = delta_front / n;
    int inc_back = delta_back / n;
    int i = 1;

    while(i <= 2) {
      addParallel(new frontClimbPIDCommand(start_front + i * inc_front, 0.3, 0.0, 0.0, 0.0));
      addSequential(new backClimbPIDCommand(start_back + i * inc_back, 0.8, 0.0, 0.0, 0.0));
      addSequential(new WaitCommand(3));
      i ++;
    }

    //addSequential(new YourCommandClassHere, TIMEOUT_IN_SECONDS);
  }
}