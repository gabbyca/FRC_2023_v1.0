package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class clawSubsystem extends SubsystemBase {

   private final TalonSRX m_claw; 

   public clawSubsystem(){
      TalonSRXConfiguration config = new TalonSRXConfiguration();
      config.peakCurrentLimit = 133;
      config.peakCurrentDuration = 1500;
      config.continuousCurrentLimit = 120;

      m_claw = new TalonSRX(5);
   }
   public void openClaw(){
    m_claw.set(TalonSRXControlMode.PercentOutput, 0.5);

   }

   public void closeClaw(){
    m_claw.set(TalonSRXControlMode.PercentOutput, -0.5);
   }

   public void noPowerClaw(){
      m_claw.set(TalonSRXControlMode.PercentOutput, 0);
     }

     @Override
     public void periodic(){
    
     }
   
  public static void wait(int ms)
  {
      try
      {
          Thread.sleep(ms); //core java delay command
      }
      catch(InterruptedException ex)
      {
          Thread.currentThread().interrupt(); //this exception is useful to remove the glitches and errors of the thread.sleep()
      }
  }
   

}
