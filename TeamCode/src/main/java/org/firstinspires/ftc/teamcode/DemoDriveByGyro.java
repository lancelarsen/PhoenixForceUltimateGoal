package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Disabled
@Autonomous(name="Demo: Auto Drive By Gyro", group="Demo")
public class DemoDriveByGyro extends PhoenixBotSharedCode
{
    // These constants define the desired driving/control characteristics
    // The can/should be tweaked to suite the specific robot drive train.
    static final double DRIVE_SPEED         = 1;  // Nominal speed for better accuracy.

    static final double GYRO_ANGLE_BACKWALL = 0.0;
    static final double GYRO_ANGLE_FRONTWALL = 180.0;
    static final double GYRO_ANGLE_CENTER = -90.0;
    static final double GYRO_ANGLE_STARTWALL = 90.0;

    @Override
    public void runOpMode() 
    {
        robot.init(hardwareMap);
        InitGyro();

        SetMotorsToBrake();

        while (!isStarted()) 
        {
            GetAngle();
            ShowMessage("Gyro Heading = " + robot.angleCurrent.firstAngle);    
        }
/*
        EncoderDrive(1,100,100);
        EncoderDrive(1,-100,-100);
        EncoderDrive(1,100,100);
        EncoderDrive(1,-100,-100);
        EncoderDrive(1,100,100);
        EncoderDrive(1,-100,-100);
        EncoderDrive(1,100,100);
        EncoderDrive(1,-100,-100);
        */
        GyroDrive(DRIVE_SPEED, 100.0, 0);
        GyroDrive(DRIVE_SPEED, -100.0, 0);
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        SmartSleep(1000);
        GyroDrive(DRIVE_SPEED, 100.0, 0);
        GyroDrive(DRIVE_SPEED, -100.0, 0);
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        SmartSleep(1000);
        GyroDrive(DRIVE_SPEED, 100.0, 0);
        GyroDrive(DRIVE_SPEED, -100.0, 0);
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        SmartSleep(1000);
        GyroDrive(DRIVE_SPEED, 100.0, 0);
        GyroDrive(DRIVE_SPEED, -100.0, 0);
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        SmartSleep(1000);
        GyroDrive(DRIVE_SPEED, 100.0, 0);
        GyroDrive(DRIVE_SPEED, -100.0, 0);
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        SmartSleep(1000);
/*
        GyroDrive(DRIVE_SPEED, 48.0, -45);
        GyroDrive(DRIVE_SPEED, -40.0, -45);
        GyroTurn(0);
*/

     /*   GyroDrive(DRIVE_SPEED, 100.0, 0);
        robot.rightSkystoneServo.setPosition(0.03);
       SmartSleep(1000);
        robot.rightSkystoneServo.setPosition(0.35); 
        SmartSleep(1000);
        GyroTurn(GYRO_ANGLE_BACKWALL);
        GyroDrive(DRIVE_SPEED, -100.0, 0);
        robot.rightSkystoneServo.setPosition(0.03);
        SmartSleep(1000);
        robot.rightSkystoneServo.setPosition(0.35); 
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        GyroTurn(GYRO_ANGLE_BACKWALL);
        GyroDrive(DRIVE_SPEED, 70.0, 0);
        robot.rightSkystoneServo.setPosition(0.03);
        SmartSleep(1000);
        robot.rightSkystoneServo.setPosition(0.35); 
        SmartSleep(1000);
        GyroTurn(GYRO_ANGLE_BACKWALL);
        GyroDrive(DRIVE_SPEED, -70.0, 0);
        robot.rightSkystoneServo.setPosition(0.03);
       SmartSleep(1000);
        robot.rightSkystoneServo.setPosition(0.35); 
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        GyroTurn(GYRO_ANGLE_BACKWALL);
        GyroDrive(DRIVE_SPEED, 40.0, 0);
        robot.rightSkystoneServo.setPosition(0.03);
       SmartSleep(1000);
        robot.rightSkystoneServo.setPosition(0.35);
        SmartSleep(1000);
        SmartSleep(1000);
        GyroTurn(GYRO_ANGLE_BACKWALL);
        GyroDrive(DRIVE_SPEED, -40.0, 0);
        robot.rightSkystoneServo.setPosition(0.03);
        SmartSleep(1000);
        robot.rightSkystoneServo.setPosition(0.35);
        SmartSleep(1000);
        StrafeRight(1,10,99); 
        StrafeLeft(1,10,99);
        GyroTurn(GYRO_ANGLE_BACKWALL);
        SmartSleep(1000);
       */ 
      //  GyroTurn(0);

        /*
        GyroTurn(0);
        SmartSleep(2000);
        GyroTurn(45.0); //--- Left
        SmartSleep(2000);
        GyroTurn(90.0);
        SmartSleep(2000);
        GyroTurn(135.0);
        SmartSleep(2000);
        GyroTurn(180.0);
        SmartSleep(2000);

        GyroTurn(0);
        SmartSleep(2000);
        GyroTurn(-45.0); //--- Right
        SmartSleep(2000);
        GyroTurn(-90.0);
        SmartSleep(2000);
        GyroTurn(-135.0);
        SmartSleep(2000);
        GyroTurn(-180.0);
        SmartSleep(2000);
        
        GyroTurn(0);
        */
        
//        gyroTurn( TURN_SPEED, -45.0);         // Turn  CCW to -45 Degrees
//        gyroHold( TURN_SPEED, -45.0, 0.5);    // Hold -45 Deg heading for a 1/2 second
//        gyroDrive(DRIVE_SPEED, 12.0, -45.0);  // Drive FWD 12 inches at 45 degrees
//        gyroTurn( TURN_SPEED,  45.0);         // Turn  CW  to  45 Degrees
//        gyroHold( TURN_SPEED,  45.0, 0.5);    // Hold  45 Deg heading for a 1/2 second
//        gyroTurn( TURN_SPEED,   0.0);         // Turn  CW  to   0 Degrees
//        gyroHold( TURN_SPEED,   0.0, 1.0);    // Hold  0 Deg heading for a 1 second
//        gyroDrive(DRIVE_SPEED,-48.0, 0.0);    // Drive REV 48 inches

        ShowMessage("Complete");
    }
}
