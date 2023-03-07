package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class Constants {
    public static final class DriveConstants{

        //wheel ports
        public static final int kFrontLeftWheelPort = 2;
        public static final int kFrontRightWheelPort = 9;
        public static final int kBackLeftWheelPort = 1;
        public static final int kBackRightWheelPort = 10;

        
        public static final double metersPerRotation = 0.4787787204060999;

        public static final double gearRatio = 10.71;

        public static final double encoderConversionFactor = metersPerRotation / gearRatio;

        public static final double encoderVeloConversionFactor = metersPerRotation / gearRatio / 60;
        //drivetrain stuff
        public static double speed = 0.3; //speed control -- 30%

        public static double ks = 0.079775;
        public static double kv = 2.9146;
        public static double ka = 0.65184;
        public static final double kTrackWidth = 0.585; //23 in
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = 0.52; //20.5 in
        // Distance between centers of front and back wheels on robot
        
        // Example value only - as above, this must be tuned for your drive!
        public static final double kPDriveVel = 0.00028512;

        public static final double kMaxSpeedMetersPerSecond = 1;
        public static final double kMaxAccelerationMetersPerSecondSquared = 1;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 0.00028512;
        public static final double kPYController = 0.00028512;
        public static final double kPThetaController = 0.00028512;

        public static final MecanumDriveKinematics kDriveKinematics =
        new MecanumDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        public static final SimpleMotorFeedforward kFeedforward =
        new SimpleMotorFeedforward(ks, kv, ka);

        public static final PIDController driveController = new PIDController(kPDriveVel, 0, 0);
        
         // Constraint for the motion profilied robot angle controller
         public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
         new TrapezoidProfile.Constraints(
             kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

        public static final PIDController xController = new PIDController(kPXController, 0, 0);

        public static final PIDController yController = new PIDController(kPYController, 0, 0);

        public static final ProfiledPIDController thetaController
         = new ProfiledPIDController(kPThetaController, 0, 0, kThetaControllerConstraints);
    
    }

    public static final class LiftConstants{
        public static final int kLift1Port = 8;
        public static final int kLift2Port = 7;

        public static final double kIdealLiftSpeed = -0.25;

        public static final int bottomPose = 0; //bottom limit
        public static final int topPose = 150; //top limit

        public static final double liftSpeed = 100;

        public static final double kP = 0.06;
        public static final double kI = 0;
        public static final double kD = 0;

        public static final double kMaxLiftPower = 1;
    }

    public static final class JoystickConstants {
        public static final int kXStick1 = 0;
        public static final int kYStick1 = 1;
        public static final int kLeftTrigger = 2;
        public static final int kRightTrigger = 3;
        public static final int kXStick2 = 4;
        public static final int kYStick2 = 5;

        public static final int kJoystick1Port = 0;
        public static final int kJoystick2Port = 1;
    }

    public static final class BlinkinConstants{
        public static final int kBlinkinPort = 0;

        //colors
        public static final double kRed = 0.61;
        public static final double kOrange = 0.65;
        public static final double kYellow = 0.69;
        public static final double kGreen = 0.77;
        public static final double kBlue = 0.87;
        public static final double kViolet = 0.91;
        public static final double kWhite = 0.93;
        public static final double kBlack = 0.99;
    }
    public static final class AutoConstants {}

    
}
