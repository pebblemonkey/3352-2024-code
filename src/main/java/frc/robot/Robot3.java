// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Code for FRC team 3352 for the year of our lord two-thousand and twenty-four
// To be used only and explicitly for the intent of driving the robot made by the group of overacheiving high schoolers who have
// the identification in the FIRST robotics competition 3352 in the year stated in the line of the number of four




//this is just a backup of robot.java

package frc.robot;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot3 extends TimedRobot {
  private static final int kFrontLeftChannel = 1;
  private static final int kRearLeftChannel = 4;
  private static final int kFrontRightChannel = 5;
  private static final int kRearRightChannel = 6;

  private static final int kJoystickChannel = 1;

  private MecanumDrive m_robotDrive;
  private Joystick m_stick;
  XboxController xbox = new XboxController(0);
  WPI_TalonSRX Shooter1 = new WPI_TalonSRX(13); // shooter motor #1
  WPI_TalonSRX FrontLeftDrive = new WPI_TalonSRX(1);//Drive Motor \front left
  WPI_TalonSRX Shooter2 = new WPI_TalonSRX(3); //shooter motor #2
  WPI_TalonSRX RearLeftDrive = new WPI_TalonSRX(4);//Drive Motor \rear left
  WPI_TalonSRX FrontRightDrive = new WPI_TalonSRX(5);//Drive Motor \front right
  WPI_TalonSRX RearRightDrive = new WPI_TalonSRX(6);//Drive Motor \ rear right
  WPI_TalonSRX OuterBottom = new WPI_TalonSRX(7);// bottom outside conveyor motor
  WPI_TalonSRX OuterTop = new WPI_TalonSRX(8);// top outside shooter motor
  WPI_TalonSRX InnerTop = new WPI_TalonSRX(11);//top inside conveyor motor
  WPI_TalonSRX InnerBottom = new WPI_TalonSRX(9);//bottom inside conveyor motor
  WPI_TalonSRX LiftCIM = new WPI_TalonSRX(16);//lift motor
  Servo s_1 = new Servo(8);
  Servo s_2 = new Servo(9);
  Ultrasonic U_1 = new Ultrasonic(1,2);
  WPI_TalonSRX blinkin = new WPI_TalonSRX(10);
  int Chamber = 0;
  GenericEntry ultrasonic_thingy;
  GenericEntry camera_thingy;
  Timer autotimer= new Timer();
  Boolean chamber = false;

//Auto Choosing Program
     private static final String kDefaultAuto = "Auto1";
     private static final String kCustomAuto = "Auto2";
     private String m_autoSelected;
     private final SendableChooser<String> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {

    WPI_TalonSRX frontLeft = new WPI_TalonSRX(kFrontLeftChannel);
    WPI_TalonSRX rearLeft = new WPI_TalonSRX(kRearLeftChannel);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(kFrontRightChannel);
    WPI_TalonSRX rearRight = new WPI_TalonSRX(kRearRightChannel);

    CameraServer.startAutomaticCapture();
     CameraServer.startAutomaticCapture();

     U_1.setEnabled(true);
     Ultrasonic.setAutomaticMode(true);
     
     //More Auto selection programming
     m_chooser.setDefaultOption("Auto1", kDefaultAuto);
     m_chooser.addOption("Auto2", kCustomAuto);
     SmartDashboard.putData("Auto choices", m_chooser);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

    m_stick = new Joystick(kJoystickChannel);
    
    ultrasonic_thingy = Shuffleboard.getTab("ultra test")
   .add("chamber", chamber)
   .withWidget("Boolean Box")
   .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "maroon"))
   .getEntry();
  }

  @Override
  public void teleopPeriodic() {

    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-m_stick.getY(), -m_stick.getX(), -m_stick.getZ());
      
    //intake motor
    if(xbox.getRawButtonPressed(3)){
      Shooter1.set(-1);
      Shooter2.set(1);
    }


    if(xbox.getRawButtonReleased(3)){
      Shooter1.set(0);
      Shooter2.set(0);
    }
    
    //motor #1
    if(xbox.getRawButtonPressed(7)){
      LiftCIM.set(0.25);
    }

    if(xbox.getRawButtonReleased(7)){

      LiftCIM.set(0);
    }

     if (U_1.getRangeInches()<=17) {
       Chamber=1;
      SmartDashboard.putBoolean("chamber", chamber);
    } else{
      Chamber=0;
      SmartDashboard.putBoolean("chamber", chamber);}

    //motor #4 
    if(xbox.getRawButtonPressed(2)){
      InnerTop.set(1);
      OuterTop.set(-1);
    }

    if(xbox.getRawButtonReleased(2)){
      InnerTop.set(0);
      OuterTop.set(0);
    }

    if(xbox.getRawButtonPressed(1)){
      OuterBottom.set(1);
      InnerBottom.set(1);
    }

    if(xbox.getRawButtonReleased(1)){
      InnerBottom.set(0);
      OuterBottom.set(0);
    }
    
    if (xbox.getRawButtonPressed(5)){
      //Cannot be greater than +/- 180*, else it will not go to the correct angle.
      //This must be 40* minimum, else the note will push it out of the way.
      s_1.setAngle(0);
      s_2.setAngle(40);
    }

    if (xbox.getRawButtonPressed(4)){
      //See line 158
      //This must be 0*, because this is the "home" posistion.
      s_1.setAngle(40);
      s_2.setAngle(0);
    }
    
    double range = U_1.getRangeInches();

    SmartDashboard.putNumber("Range",range);

      if (range<10) {
        blinkin.set(1);
      } 
      else{
        blinkin.set(0);
      }
    }
  
  @Override
  public void autonomousInit() {
    m_autoSelected=m_chooser.getSelected();
    autotimer.reset();
    autotimer.start();
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        if(2>autotimer.get()){
          FrontLeftDrive.set(1);
          FrontRightDrive.set(1);
          RearLeftDrive.set(1);
          RearRightDrive.set(1);
        }
        else if(autotimer.get()<4){
          FrontLeftDrive.set(0);
          FrontRightDrive.set(0);
          RearLeftDrive.set(0);
          RearRightDrive.set(0);
        }else if(autotimer.get()<7){
          FrontLeftDrive.set(-1);
          FrontRightDrive.set(-1);
          RearLeftDrive.set(-1);
          RearRightDrive.set(-1);
        }
        break;
      case kDefaultAuto:
      default:
          FrontLeftDrive.set(0);
          FrontRightDrive.set(0);
          RearLeftDrive.set(0);
          RearRightDrive.set(0);

        break;
    }
  }

}

// Andre keeps being overdramatic and won't be quiet and stop yelling at us over everything that HE IS DOING