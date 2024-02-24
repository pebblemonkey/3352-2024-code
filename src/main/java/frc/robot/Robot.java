// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 1;
  private static final int kRearLeftChannel = 4;
  private static final int kFrontRightChannel = 5;
  private static final int kRearRightChannel = 6;


  private static final int kJoystickChannel = 1;

  private MecanumDrive m_robotDrive;
  private Joystick m_stick;
  WPI_TalonSRX intake = new WPI_TalonSRX(13);
  WPI_TalonSRX one = new WPI_TalonSRX(1);//Drive Motor
  WPI_TalonSRX two = new WPI_TalonSRX(3);
  WPI_TalonSRX three = new WPI_TalonSRX(4);//Drive Motor
  WPI_TalonSRX four = new WPI_TalonSRX(5);//Drive Motor
  WPI_TalonSRX five = new WPI_TalonSRX(6);//Drive Motor
  WPI_TalonSRX six = new WPI_TalonSRX(7);
  WPI_TalonSRX seven = new WPI_TalonSRX(8);
  WPI_TalonSRX eight = new WPI_TalonSRX(9);
  WPI_TalonSRX nine = new WPI_TalonSRX(11);
  WPI_TalonSRX ten = new WPI_TalonSRX(16);
  Servo s_1 = new Servo(8);
  Ultrasonic U_1 = new Ultrasonic(1, 2);
  Spark blinkin = new Spark(9);

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
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-m_stick.getY(), -m_stick.getX(), -m_stick.getZ());
      
    //intake motor
    if(m_stick.getRawButtonPressed(1))
{
      intake.set(0.4);
      System.out.println("Pressed");
    }

    if(m_stick.getRawButtonReleased(1)){
  
      intake.set(0);
    }
    
    //motor #1
    if(m_stick.getRawButtonPressed(2)){

      ten.set(0.5);
    }

    if(m_stick.getRawButtonReleased(2)){

      ten.set(0);
    }
    
    //motor #2
    if(m_stick.getRawButtonPressed(3)){

      two.set(0.5);
    }

    if(m_stick.getRawButtonReleased(3)){

      two.set(0);
    }
    
    //motor #3
    if(m_stick.getRawButtonPressed(4)){

      six.set(0.5);
    }

    if(m_stick.getRawButtonReleased(4)){

      six.set(0);
    }

    //motor #4 
    if(m_stick.getRawButtonPressed(5)){

      seven.set(0.5);
    }

    if(m_stick.getRawButtonReleased(5)){

      seven.set(0);
    }

        if(m_stick.getRawButtonPressed(8)){

      eight.set(0.5);
    }

    if(m_stick.getRawButtonReleased(8)){

      eight.set(0);
    }

        if(m_stick.getRawButtonPressed(9)){

      nine.set(0.5);
    }

    if(m_stick.getRawButtonReleased(9)){

      nine.set(0);
    }

    if (m_stick.getRawButtonPressed(11)) {
      
      //s_1.set(0.5);
      s_1.setAngle(30);
    }

    if (m_stick.getRawButtonReleased(11)) {
      
    

      //s_1.set(0.5);
      s_1.setAngle(0);
    }
    


    double range = U_1.getRangeInches();
    //System.out.println("Range" + range);


    if (U_1.getRangeInches()<46.5) {
      boolean chamber = true;
      SmartDashboard.putBoolean("chamber", chamber);
    }
    else{
      boolean chamber = false;
      SmartDashboard.putBoolean("chamber", chamber);
    }
    /*  if(U_1.getRangeInches()<5){
      System.out.println("True");
      
     }
     else {

      System.out.println("False");
     }*/
      SmartDashboard.putNumber("Range",range);

      blinkin.set(0.07);

  }

  @Override
  public void autonomousInit() {
    m_autoSelected=m_chooser.getSelected();
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        System.out.print("Hello Auto");
         one.set(1);
         three.set(1);
         four.set(1);
         five.set(1);
        break;
      case kDefaultAuto:
      default:

        break;
    }
  }

}
