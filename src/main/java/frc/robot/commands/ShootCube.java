// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeSubsystem;

public class ShootCube extends CommandBase {
  CubeSubsystem cubeSubsystem;
  double RPM;
  boolean flag;
  double lastTimeStamp,timer=0;
  /** Creates a new ShootCube. */
  public ShootCube(CubeSubsystem cubeSubsystem, double RPM) {
    this.cubeSubsystem = cubeSubsystem;
    this.RPM = RPM;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(cubeSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    flag = false;
    timer = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // this.lastTimeStamp = (Timer.getFPGATimestamp()-this.timer);
    // cubeSubsystem.intakeCubeRPM(RPM);
    cubeSubsystem.intakeCubeRPM(RPM);
    lastTimeStamp = (Timer.getFPGATimestamp()-timer);
 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(this.lastTimeStamp>=2){
      cubeSubsystem.intakeCubeRPM(0);
      return true;
      // cubeSubsystem.intakeCubeRPM(0);
    }
    return false;
  }
  
}
