package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class WristSubsystem extends SubsystemBase {
    
  private final TalonSRX talon; 
  double wristEncoderDistance; 
  PIDController m_pidController; 

  double setpoint = 0.0; 
  double maxPower = 0.4;

  double kP = 0.00001; 
  double kI = 0.00; 
  double kD = 0.0;  

  public WristSubsystem(){
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    config.peakCurrentLimit = 133;
    config.peakCurrentDuration = 1500;
    config.continuousCurrentLimit = 120;
     
    talon =  new TalonSRX(12);
    talon.setSelectedSensorPosition(0.0);
    talon.set(TalonSRXControlMode.Position, 0);

    m_pidController = new PIDController(kP, kI, kD);
    m_pidController.setTolerance(5, 10);
    m_pidController.setSetpoint(0);
    
  }

  public double getWristEncoderDistance(){
    wristEncoderDistance = talon.getSelectedSensorPosition();
    SmartDashboard.putNumber("WristDistance", wristEncoderDistance);
    return wristEncoderDistance; 
  }

  public void moveWrist(double pose){
    m_pidController.setSetpoint(pose);
    setpoint = pose;
  }

   public void calculateWrist(){
    double output;

    if(setpoint > talon.getSelectedSensorPosition()) //less than?
        output = MathUtil.clamp(m_pidController.calculate(talon.getSelectedSensorPosition()), -maxPower, maxPower);
    else
        output = MathUtil.clamp(m_pidController.calculate(talon.getSelectedSensorPosition()), -maxPower, maxPower) * 0.6;
    
    talon.set(TalonSRXControlMode.PercentOutput, output);
    SmartDashboard.putNumber("WRIST Output", output);
    SmartDashboard.putNumber("WRIST Setpoint", setpoint);
  }


  public void resetEncoder(){
    talon.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic(){
   calculateWrist();
  }

 

}
