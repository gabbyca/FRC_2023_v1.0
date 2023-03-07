package frc.robot.commands.autoStuffHere;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.clawSubsystem;
import frc.robot.subsystems.shoulderpid;

public class AutoHolder {

    private final DriveSubsystem drive;
    private final shoulderpid shoulder;
    private final clawSubsystem claw;
    private final WristSubsystem wrist; 

    public AutoHolder(DriveSubsystem drive, shoulderpid shoulder, clawSubsystem claw, WristSubsystem wrist){
        this.drive = drive;
        this.shoulder = shoulder;
        this.claw = claw;
        this.wrist = wrist; 
    }

    public SequentialCommandGroup getAuto1(){
        return new SequentialCommandGroup();
    }

    public SequentialCommandGroup getAuto2(){
        return new SequentialCommandGroup();
    } 

    public SequentialCommandGroup getAuto3(){
        return new SequentialCommandGroup(new DriveBack(drive), new MoveShoulder(shoulder, -3300.0), new MoveWrist(wrist, 80805.0),new MoveClaw(claw, true), new MoveWrist(wrist, 830.0), new MoveClaw(claw, false), new MoveShoulder(shoulder, 0));
    } 

    public SequentialCommandGroup getAuto4(){
        return new SequentialCommandGroup();
    } 

}
