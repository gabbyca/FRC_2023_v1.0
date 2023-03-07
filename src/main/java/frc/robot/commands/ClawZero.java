package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.clawSubsystem;


public class ClawZero extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final clawSubsystem m_subsystem;

  public ClawZero(clawSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
    
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    
    m_subsystem.noPowerClaw();

  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return true;
  }
}