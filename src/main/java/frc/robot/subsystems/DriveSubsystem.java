package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;

import com.revrobotics.RelativeEncoder;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  // The motors on drive system
  public static CANSparkMax m_frontLeftMotor = new CANSparkMax(DriveConstants.kFrontLeftWheelPort, MotorType.kBrushless);
  public static CANSparkMax m_frontRightMotor = new CANSparkMax(DriveConstants.kFrontRightWheelPort, MotorType.kBrushless);
  public static CANSparkMax m_backLeftMotor = new CANSparkMax(DriveConstants.kBackLeftWheelPort, MotorType.kBrushless);
  public static CANSparkMax m_backRightMotor = new CANSparkMax(DriveConstants.kBackRightWheelPort, MotorType.kBrushless);
  private static RelativeEncoder m_frontLeftEncoder = m_frontLeftMotor.getEncoder();
  private static RelativeEncoder m_frontRightEncoder = m_frontRightMotor.getEncoder();
  private static RelativeEncoder m_backLeftEncoder = m_backLeftMotor.getEncoder();
  private static RelativeEncoder m_backRightEncoder = m_backRightMotor.getEncoder();
 
  private AHRS navx = new AHRS(SerialPort.Port.kMXP);

  private final Field2d m_field = new Field2d();


  private final MecanumDriveKinematics m_kinematics = DriveConstants.kDriveKinematics;
   // Odometry class for tracking robot pose
  private final MecanumDriveOdometry m_odometry = new MecanumDriveOdometry(m_kinematics, navx.getRotation2d(), new MecanumDriveWheelPositions(
    m_frontLeftEncoder.getPosition(), m_frontRightEncoder.getPosition(),
    m_backLeftEncoder.getPosition(), m_backRightEncoder.getPosition()
  ));

  private MecanumDrive m_drive = new MecanumDrive(m_frontLeftMotor, m_backLeftMotor, m_frontRightMotor,
      m_backRightMotor);
  
  NetworkTableEntry m_xEntry = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("X");
  NetworkTableEntry m_yEntry = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("Y");
  NetworkTableEntry m_headingEntry = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("Heading");
 
  public DriveSubsystem() {

    m_drive.setSafetyEnabled(false);
    m_frontLeftMotor.setInverted(false);
    m_frontRightMotor.setInverted(true);
    m_backLeftMotor.setInverted(false);
    m_backRightMotor.setInverted(true);

    motorBrake();

    setEncoders();
    setEncoderVelo();
    SmartDashboard.putData("Field", m_field);
  }

  @Override
  public void periodic() {
    m_field.setRobotPose(getPose());

    var translation = getPose().getTranslation();
    m_xEntry.setNumber(translation.getX());
    m_yEntry.setNumber(translation.getY());
    m_headingEntry.setNumber(m_odometry.getPoseMeters().getRotation().getDegrees());
  }

  public void setEncoders() {
    m_frontLeftEncoder.setPositionConversionFactor(DriveConstants.encoderConversionFactor);
    m_frontRightEncoder.setPositionConversionFactor(DriveConstants.encoderConversionFactor);
    m_backLeftEncoder.setPositionConversionFactor(DriveConstants.encoderConversionFactor);
    m_backRightEncoder.setPositionConversionFactor(DriveConstants.encoderConversionFactor);
  }

  public void setEncoderVelo() {
    m_frontLeftEncoder.setVelocityConversionFactor(DriveConstants.encoderVeloConversionFactor);
    m_frontRightEncoder.setVelocityConversionFactor(DriveConstants.encoderVeloConversionFactor);
    m_backLeftEncoder.setVelocityConversionFactor(DriveConstants.encoderVeloConversionFactor);
    m_backRightEncoder.setVelocityConversionFactor(DriveConstants.encoderVeloConversionFactor);
  }

  public void motorBrake(){
    m_frontLeftMotor.setIdleMode(IdleMode.kBrake);
    m_frontRightMotor.setIdleMode(IdleMode.kBrake);
    m_backLeftMotor.setIdleMode(IdleMode.kBrake);
    m_backRightMotor.setIdleMode(IdleMode.kBrake);
  }

  public void motorCoast(){
    m_frontLeftMotor.setIdleMode(IdleMode.kCoast);
    m_frontRightMotor.setIdleMode(IdleMode.kCoast);
    m_backLeftMotor.setIdleMode(IdleMode.kCoast);
    m_backRightMotor.setIdleMode(IdleMode.kCoast);
  }
 
  public void mecanumDrive(double x, double y, double c) {
    m_drive.driveCartesian(x,-y,c);
  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  public MecanumDriveWheelSpeeds getCurrentWheelSpeeds() {
    return new MecanumDriveWheelSpeeds(m_frontLeftEncoder.getVelocity(),
      m_frontRightEncoder.getVelocity(),
      m_backLeftEncoder.getVelocity(),
      m_backRightEncoder.getVelocity());
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
  }

  public void setDriveMotorControllersVolts(MecanumDriveMotorVoltages volts){
    m_frontLeftMotor.setVoltage(volts.frontLeftVoltage);
    m_frontRightMotor.setVoltage(volts.frontRightVoltage);
    m_backLeftMotor.setVoltage(volts.rearLeftVoltage);
    m_backRightMotor.setVoltage(volts.rearRightVoltage);
}

  public void resetEncoders() {
    m_frontLeftEncoder.setPosition(0);
    m_frontRightEncoder.setPosition(0);
    m_backLeftEncoder.setPosition(0);
    m_backRightEncoder.setPosition(0);
  }

  public double getAverageEncoderDistance() {
    return (m_frontLeftEncoder.getPosition() + m_frontRightEncoder.getPosition()) / 2.0;
  }

  public RelativeEncoder getFrontLeftEncoder() {
    return m_frontLeftEncoder;
  }

  public RelativeEncoder getFrontRightEncoder() {
    return m_frontRightEncoder;
  }
  
  public RelativeEncoder getBackLeftEncoder() {
    return m_backLeftEncoder;
  }

  public RelativeEncoder getBackRightEncoder() {
    return m_backRightEncoder;
  }

  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  public void zeroHeading() {
    navx.reset();
  }

  public double getHeading() {
    return navx.getAngle();
  }

  public double getTurnRate() {
    return -navx.getRate();
  }

  public void speedLimit(double limit){
    DriveConstants.speed = limit;
  }

  public void resetNavx() {
    navx.reset();
  }

  //autonmous
  public void moveBackAuto(){
    m_frontLeftMotor.set(-0.05);
    m_frontRightMotor.set(-0.05);
    m_backRightMotor.set(-0.05);
    m_backLeftMotor.set(-0.05);
    
  }
  public void stop() {
    m_frontLeftMotor.set(0.0);
    m_frontRightMotor.set(0.0);
    m_backRightMotor.set(0.0);
    m_backLeftMotor.set(0.0);
  }

  public void taxiOut(){
    m_frontLeftMotor.set(-0.3);
    m_frontRightMotor.set(-0.3);
    m_backRightMotor.set(-0.3);
    m_backLeftMotor.set(-0.3);
    wait(3200);
    m_frontLeftMotor.set(0.0);
    m_frontRightMotor.set(0.0);
    m_backRightMotor.set(0.0);
    m_backLeftMotor.set(0.0);
  }
  
  public static void wait(int ms)
  {
      try
      {
          Thread.sleep(ms);
      }
      catch(InterruptedException ex)
      {
          Thread.currentThread().interrupt(); 
      }
  }
}
