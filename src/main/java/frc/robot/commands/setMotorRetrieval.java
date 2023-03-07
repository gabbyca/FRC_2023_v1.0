package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.shoulderpid;

public class setMotorRetrieval extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final shoulderpid m_subsystem;
  private final WristSubsystem m_wristSubsystem; 

  public setMotorRetrieval(shoulderpid subsystem, WristSubsystem wristSub) {
    m_subsystem = subsystem;
    m_wristSubsystem = wristSub;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_subsystem.moveArm(-708.0);
    m_wristSubsystem.moveWrist(63000.0); //downwards out
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}