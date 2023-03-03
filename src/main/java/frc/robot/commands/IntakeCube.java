package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeSubsystem;

public class IntakeCube extends CommandBase {
  CubeSubsystem cubeSubsystem;
  double angle;
  boolean flag;
  
  public IntakeCube(CubeSubsystem cubeSubsystem, double angle) {
    this.cubeSubsystem = cubeSubsystem;
    this.angle = angle;
    addRequirements(cubeSubsystem);
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {

      cubeSubsystem.setCubeIntakeAngle(100);
      cubeSubsystem.intakeCube(-0.2);

      if (cubeSubsystem.getBeamBreakerCube() == true) {
        cubeSubsystem.intakeCube(0);
        cubeSubsystem.setCubeIntakeAngle(angle);
        flag = true;
      }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
