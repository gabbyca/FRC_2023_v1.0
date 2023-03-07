package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.clawSubsystem;


public class OpenClaw extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final clawSubsystem m_subsystem;
  private double startTime;
  private boolean isFinished = false;

  public OpenClaw(clawSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
    
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    if (Timer.getFPGATimestamp() - startTime < 1.2) {
      m_subsystem.openClaw();
      isFinished = false;
    } else {
      m_subsystem.noPowerClaw();
      isFinished = true;
    }

  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }
}