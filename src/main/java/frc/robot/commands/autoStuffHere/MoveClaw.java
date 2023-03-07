package frc.robot.commands.autoStuffHere;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.clawSubsystem;

public class MoveClaw extends CommandBase{

    private final clawSubsystem claw;
    private final boolean open; 
    private final double TIME = .8;
    private double startTime; 
    
    public MoveClaw(clawSubsystem claw, boolean open){
        this.claw = claw;
        this.open = open; 
        addRequirements(claw);
    }

    @Override 
    public void initialize(){
        startTime = Timer.getFPGATimestamp();
        if(open){
            claw.openClaw();
        }
        else{
            claw.closeClaw();
        }
    }

    @Override 
    public void end(boolean interrupted){
        claw.noPowerClaw();
    }

    @Override 
    public boolean isFinished(){
       return Timer.getFPGATimestamp() > startTime + TIME; 
    }
    
}
