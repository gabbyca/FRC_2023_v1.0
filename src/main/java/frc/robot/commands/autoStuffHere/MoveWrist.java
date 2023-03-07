package frc.robot.commands.autoStuffHere;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;


public class MoveWrist extends CommandBase{

    private final WristSubsystem m_wristSub;
    private final double m_setPoint;
    private final double TOLERANCE = 3000;
    
    public MoveWrist(WristSubsystem wrist, double setpoint){
        m_wristSub = wrist; 
        this.m_setPoint = setpoint;
        addRequirements(wrist);
    }

    @Override 
    public void initialize(){
       m_wristSub.moveWrist(m_setPoint);
    }

    @Override 
    public boolean isFinished(){
    return m_wristSub.getWristEncoderDistance() < m_setPoint + TOLERANCE && m_wristSub.getWristEncoderDistance() > m_setPoint - TOLERANCE;
    }
    
}