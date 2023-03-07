package frc.robot.commands;

import frc.robot.subsystems.StopperSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class RetractStopperCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final StopperSubsystem m_subsystem;

  public RetractStopperCommand(StopperSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_subsystem.retractStop();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}