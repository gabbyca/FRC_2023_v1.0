// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.RetractStopperCommand;
import frc.robot.commands.CloseClaw;
import frc.robot.commands.CubeHighScore;
import frc.robot.commands.CubeMiddleScore;
import frc.robot.commands.ClawZero;
import frc.robot.commands.setMotorCompact;
import frc.robot.commands.setMotorHigh;
import frc.robot.commands.setMotorMiddle;
import frc.robot.commands.setMotorRetrieval;
import frc.robot.commands.speedButton;
import frc.robot.commands.autoStuffHere.AutoHolder;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.StopperSubsystem;
import frc.robot.subsystems.TrajectorySubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.clawSubsystem;
import frc.robot.subsystems.shoulderpid;

 
public class RobotContainer {
  
  CommandXboxController m_driverController= new CommandXboxController(0); 
  CommandXboxController m_coDriverController= new CommandXboxController(1); 

  Joystick m_joystick1 = new Joystick(0);
  Joystick m_joystick2 = new Joystick(0);
  
  //Subsystems
  final StopperSubsystem m_stopperSubsystem = new StopperSubsystem();
  final DriveSubsystem m_drive = new DriveSubsystem();
  final WristSubsystem m_wristSubsystem = new WristSubsystem();
  final shoulderpid m_shoulderpid = new shoulderpid();
  final clawSubsystem m_clawSubsystem = new clawSubsystem();
  final SendableChooser<Command> autoChooser = new SendableChooser<>();


  //Triggers
  Trigger aButton = m_coDriverController.a();
  Trigger bButton = m_coDriverController.b();
  Trigger xButton = m_coDriverController.x();
  Trigger yButton = m_coDriverController.y();
  Trigger rightBumperCo = m_coDriverController.rightBumper();
  Trigger leftBumperCo = m_coDriverController.leftBumper();
  Trigger dpadUpCo = m_coDriverController.povUp();
  Trigger dpadDownCo = m_coDriverController.povDown();
  Trigger leftShiftCo = m_coDriverController.leftTrigger();
  Trigger dpadLeftCo = m_coDriverController.povLeft();
  Trigger dPadDown = m_driverController.povDown(); 
  Trigger dPadUp = m_driverController.povUp();
  Trigger rightBumper = m_driverController.rightBumper();
  Trigger leftBumper = m_driverController.leftBumper();
  Command openClaw = new SequentialCommandGroup(new InstantCommand(m_clawSubsystem::openClaw), new WaitCommand(1.2), new InstantCommand(m_clawSubsystem::noPowerClaw));


  // //Commands
  private final ArcadeDrive m_ArcadeDrive = new ArcadeDrive(m_drive, () -> m_joystick2.leftY(),
   () -> m_joystick2.leftX(),
    () -> m_joystick2.rightX());

  final RetractStopperCommand m_retractStopperCommand = new RetractStopperCommand(m_stopperSubsystem);
  final setMotorMiddle m_SetMotorMiddle = new setMotorMiddle(m_shoulderpid, m_wristSubsystem); 
  final setMotorHigh m_SetMotorHigh = new setMotorHigh(m_shoulderpid, m_wristSubsystem); 
  final setMotorCompact m_motorCompact = new setMotorCompact(m_shoulderpid, m_wristSubsystem); 
  final setMotorRetrieval m_setMotoreRetrieval = new setMotorRetrieval(m_shoulderpid, m_wristSubsystem); 
  final CubeHighScore m_cubeHighScore = new CubeHighScore(m_shoulderpid, m_wristSubsystem);
  final CubeMiddleScore m_cubeMiddleScore = new CubeMiddleScore(m_shoulderpid, m_wristSubsystem);
  final speedButton m_SpeedButtonBoost = new speedButton(1.0); 
  final speedButton m_SpeedButtonNoBoost = new speedButton(0.3); 
  final CloseClaw m_closeClaw = new CloseClaw(m_clawSubsystem); 
  final ClawZero m_noPowerClaw = new ClawZero(m_clawSubsystem);
  
  MecanumControllerCommand mecanumControllerCommand = new MecanumControllerCommand(
    TrajectorySubsystem.exampleTrajectory,
    m_drive::getPose,
    DriveConstants.kFeedforward,
    DriveConstants.kDriveKinematics,
    DriveConstants.xController,
    DriveConstants.yController,
    DriveConstants.thetaController,
    DriveConstants.kMaxSpeedMetersPerSecond,
    DriveConstants.driveController,
    DriveConstants.driveController,
    DriveConstants.driveController,
    DriveConstants.driveController,
    m_drive::getCurrentWheelSpeeds,
    m_drive::setDriveMotorControllersVolts, 
    m_drive);
    
  private SendableChooser<Command> driveChooser = new SendableChooser<Command>();
 
  public RobotContainer() {
    driveChooser.setDefaultOption("Arcade Drive", m_ArcadeDrive);
    SmartDashboard.putData("Drive Mode", driveChooser);
    configureBindings();
    m_drive.setDefaultCommand(driveChooser.getSelected());
    SmartDashboard.putNumber("NAVX Angle", m_drive.getHeading());
    setAutoCommands();
    SmartDashboard.putData("auto", autoChooser);
  }

  private void configureBindings() { 
    aButton.onTrue(m_motorCompact);
    xButton.onTrue(m_setMotoreRetrieval);
    yButton.onTrue(m_SetMotorMiddle);
    bButton.onTrue(m_SetMotorHigh);
    leftBumperCo.onTrue(m_closeClaw);

    rightBumperCo.onTrue(openClaw);

    dpadUpCo.onTrue(m_cubeHighScore);
    dpadDownCo.onTrue(m_cubeMiddleScore);
    dpadLeftCo.onTrue(m_noPowerClaw);
    
    dPadDown.onTrue(m_retractStopperCommand);
    rightBumper.whileTrue(m_SpeedButtonBoost);
    leftBumper.whileTrue( m_SpeedButtonNoBoost);
  }

  public void setAutoCommands(){
    AutoHolder autos = new AutoHolder(m_drive, m_shoulderpid, m_clawSubsystem, m_wristSubsystem);
    autoChooser.addOption("auto1", autos.getAuto1());
    autoChooser.addOption("auto2", autos.getAuto2());
    autoChooser.addOption("auto3 - MiddleScore", autos.getAuto3());
    autoChooser.addOption("auto4", autos.getAuto4());
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

}
