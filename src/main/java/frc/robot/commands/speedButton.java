package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;

public class speedButton extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final double m_speed; 

  public speedButton(double speed) {
    m_speed = speed; 
    addRequirements();
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
   DriveConstants.speed = m_speed; 
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}