package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class StopperSubsystem extends SubsystemBase{

  DoubleSolenoid stopper;
 
  public StopperSubsystem(){
    stopper = new DoubleSolenoid(20, PneumaticsModuleType.CTREPCM, 0,1);
  }

  public void deployStop(){
    stopper.set(Value.kForward);
  }

  public void retractStop(){
    stopper.set(Value.kReverse);
  }
}
