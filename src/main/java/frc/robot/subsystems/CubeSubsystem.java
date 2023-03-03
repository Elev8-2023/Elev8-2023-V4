// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CubeSubsystem extends SubsystemBase {

  TalonFX cubeRight;
  TalonFX cubeLeft;
  TalonFX cubeUpDown;

  public static DigitalInput BeamBreakerCube = new DigitalInput(0);

  /** Creates a new CubeSubsystem. */
  public CubeSubsystem() {
    cubeLeft = new TalonFX(8);

    this.cubeLeft.configFactoryDefault();
    this.cubeLeft.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    this.cubeLeft.setSensorPhase(true);
    this.cubeLeft.setInverted(false);
    this.cubeLeft.configNominalOutputForward(0);
    this.cubeLeft.configNominalOutputReverse(0);
    this.cubeLeft.configPeakOutputForward(0.5);
    this.cubeLeft.configPeakOutputReverse(-0.5);
    this.cubeLeft.configAllowableClosedloopError(0, 0, 30);
    this.cubeLeft.config_kF(0, 0.0);
    this.cubeLeft.config_kP(0, 0.05);
    this.cubeLeft.config_kI(0, 0);
    this.cubeLeft.config_kD(0, 0);
    this.cubeLeft.setNeutralMode(NeutralMode.Coast);

    cubeRight = new TalonFX(9);

    this.cubeRight.configFactoryDefault();
    this.cubeRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    this.cubeRight.setSensorPhase(true);
    this.cubeRight.setInverted(false);
    this.cubeRight.configNominalOutputForward(0);
    this.cubeRight.configNominalOutputReverse(0);
    this.cubeRight.configPeakOutputForward(0.5);
    this.cubeRight.configPeakOutputReverse(-0.5);
    this.cubeRight.configAllowableClosedloopError(0, 0, 30);
    this.cubeRight.config_kF(0, 0.0);
    this.cubeRight.config_kP(0, 0.05);
    this.cubeRight.config_kI(0, 0);
    this.cubeRight.config_kD(0, 0);
    this.cubeRight.setNeutralMode(NeutralMode.Coast);

    cubeRight.setInverted(true);
    cubeRight.follow(cubeLeft);

    cubeUpDown = new TalonFX(10);
    this.cubeUpDown.configPeakOutputForward(0.7);
    this.cubeUpDown.configPeakOutputReverse(-0.7);
    this.cubeUpDown.selectProfileSlot(0, 0);
    this.cubeUpDown.configMotionCruiseVelocity(100000);
    this.cubeUpDown.configMotionAcceleration(100000);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("rpm", rpm);
    SmartDashboard.putNumber("Zero Position", this.cubeUpDown.getSelectedSensorPosition());
  }

  public void intakeCube(double percentOutput){
    cubeLeft.set(ControlMode.PercentOutput, percentOutput);
  }

  double rpm;

  public void intakeCubeRPM(double RPM){
    cubeLeft.set(ControlMode.Velocity, ((RPM*2048)/600));
  }

  public void outtakeCube(){
    cubeLeft.set(ControlMode.PercentOutput, -0.1);
  }

  public void setCubeIntakeAngle(double angle){
    cubeUpDown.set(ControlMode.Position, angle*((4*4*2.9*2.9*2048)/360));
  }

  public double getCubeUpDownAngle(){
    return ((cubeUpDown.getSelectedSensorPosition()*360)/(4*4*2.9*2.9*2048));
  }

//   public void resetCubeIntakeAngle(boolean resetIntake){
    
//     while(resetIntake == true) {
//       cubeLeft.set(ControlMode.PercentOutput, 0);
//       boolean state1 = getLimitSwitch();
      
//       if(state1==true) {
//         cubeUpDown.set(ControlMode.Velocity, 5000);
//         if (getLimitSwitch()==false) {
//           cubeUpDown.set(ControlMode.Velocity, -5000);
//           if(getLimitSwitch()==true) {
//             cubeUpDown.setSelectedSensorPosition(0);
//             resetIntake = false;
//           }
//       }
//     }
//       else if (state1==false) {
//         cubeUpDown.set(ControlMode.Velocity, -5000);
//         cubeLeft.set(ControlMode.PercentOutput, 0);
//         if (getLimitSwitch()==true) cubeUpDown.setSelectedSensorPosition(0); SmartDashboard.putNumber("Zero Position 2", this.cubeUpDown.getSelectedSensorPosition()); resetIntake = false;
//     }
//   }
// }


public  void ResetIntake(boolean resetflag, boolean pointflag){
  while(resetflag == true){
  if(this.cubeUpDown.isRevLimitSwitchClosed() == 1 && !homeSensorCheck()==false){
    this.cubeUpDown.set(TalonFXControlMode.Velocity,5000 );
   }
else if(this.cubeUpDown.isRevLimitSwitchClosed() == 0){    
this.cubeUpDown.set(TalonFXControlMode.Velocity, -5000);
     pointflag=true;
     SmartDashboard.putString("output", "4");
      while(this.cubeUpDown.isRevLimitSwitchClosed() == 1 && pointflag == true){
        this.cubeUpDown.set(TalonFXControlMode.Velocity, 0);
        this.cubeUpDown.setSelectedSensorPosition(0);
        SmartDashboard.putString("output", "5");
        return;
      }
  }
 }
 return;
}

public boolean homeSensorCheck(){
  if(this.cubeUpDown.isRevLimitSwitchClosed() == 1){
    // this.hanger.setSelectedSensorPosition(0);
    return true;
  }
  else{
    return false;
  }
 }

  public boolean getBeamBreakerCube(){
    return BeamBreakerCube.get();
  }

  public boolean getLimitSwitch(){
    if(cubeUpDown.isRevLimitSwitchClosed()== 1) return true;
    return false;
  }


}