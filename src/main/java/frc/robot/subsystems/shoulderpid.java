package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class shoulderpid extends SubsystemBase {
    
    private final TalonSRX m_leftFollow2; 
    private final TalonSRX m_leftFollow; 
    private final TalonSRX m_rightFollow; 
    private final TalonSRX m_rightLead;
    Encoder encoder;
    double currentShoulderDistance; 
    PIDController m_pidController; 
    double kP = 0.004; //0.004 
    double kI = 0.00; 
    double kD = 0.0001; 
    double setpoint = 0.0; 
    double maxPower = 0.4;

  public shoulderpid(){
      TalonSRXConfiguration config = new TalonSRXConfiguration();
      config.peakCurrentLimit = 133;
      config.peakCurrentDuration = 1500;
      config.continuousCurrentLimit = 120;
      
      m_rightLead = new TalonSRX(7);
      m_rightLead.configAllSettings(config);
      m_rightLead.setInverted(true);
     
      m_rightFollow =  new TalonSRX(8);
      m_leftFollow2 =  new TalonSRX(4); 
      m_leftFollow =  new TalonSRX(3);
      m_leftFollow.configAllSettings(config);
      m_rightFollow.configAllSettings(config);
      m_leftFollow2.configAllSettings(config);
      m_leftFollow.follow(m_rightLead);
      m_rightFollow.follow(m_rightLead);
      m_leftFollow2.follow(m_rightLead);
      m_rightFollow.setInverted(InvertType.FollowMaster);
      m_leftFollow.setInverted(false);
      m_leftFollow2.setInverted(false);

      m_rightLead.setNeutralMode(NeutralMode.Brake);
      m_rightFollow.setNeutralMode(NeutralMode.Brake);
      m_leftFollow2.setNeutralMode(NeutralMode.Brake);
      m_leftFollow.setNeutralMode(NeutralMode.Brake);

      encoder = new Encoder(0, 1, true, Encoder.EncodingType.k2X);
      encoder.setDistancePerPulse(1.0);
      encoder.reset();
      m_pidController = new PIDController(kP, kI, kD);
      m_pidController.setTolerance(5, 10);
      m_pidController.setSetpoint(0);
  }

  public double getDistance(){
    currentShoulderDistance = encoder.getDistance();
    SmartDashboard.putNumber("ShoulderDistance", currentShoulderDistance);
    return currentShoulderDistance;
  }
   
  public void resetEncoder(){
    encoder.reset();
  }

  public void motorZero(){
    m_rightLead.set(TalonSRXControlMode.PercentOutput, 0);
    m_pidController.setSetpoint(0);
  }

  public void moveArm(double pose) {
    m_pidController.setSetpoint(pose);
    setpoint = pose;
}
   public void calculate(){
    double output;
    
    if(setpoint < encoder.getDistance()) //less than?
        output = MathUtil.clamp(m_pidController.calculate(encoder.getDistance()), -maxPower, maxPower);
    else
        output = MathUtil.clamp(m_pidController.calculate(encoder.getDistance()), -maxPower, maxPower) * 0.2;
    
    m_rightLead.set(TalonSRXControlMode.PercentOutput, output);
    SmartDashboard.putNumber("SHOULDER Output", output);
    SmartDashboard.putNumber("SHOULDER Setpoint", setpoint);
  }


   @Override
   public void periodic(){
    calculate();
   }



}
