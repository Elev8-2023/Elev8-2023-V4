package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeSubsystem;

public class IntakeAngle extends CommandBase {
  CubeSubsystem cubeSubsystem;
  double angle;
  boolean flag;

  public IntakeAngle(CubeSubsystem cubeSubsystem, double angle) {
    this.cubeSubsystem = cubeSubsystem;
    this.angle = angle;
    addRequirements(cubeSubsystem);
  }

  @Override
  public void initialize() {
    flag = false;
    if (angle >= 117) angle = 117;
    else if (angle <= 0) angle = 0;
  }

  @Override
  public void execute() {
    cubeSubsystem.setCubeIntakeAngle(angle);
    if (cubeSubsystem.getCubeUpDownAngle()>=angle){
      flag = true;
    }
  }

  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
