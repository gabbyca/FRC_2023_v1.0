package frc.robot.commands.autoStuffHere;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shoulderpid;

public class MoveShoulder extends CommandBase{

    private final shoulderpid m_shoulder;
    private final double m_setPoint;
    private final double TOLERANCE = 100;

    public MoveShoulder(shoulderpid arm, double setpoint){
        this.m_shoulder = arm;
        this.m_setPoint = setpoint;
        addRequirements(arm);
    }

    @Override 
    public void initialize(){
       m_shoulder.moveArm(m_setPoint);
    }

    @Override 
    public boolean isFinished(){
       return m_shoulder.getDistance() < m_setPoint + TOLERANCE && m_shoulder.getDistance() > m_setPoint - TOLERANCE;
    }
    
}