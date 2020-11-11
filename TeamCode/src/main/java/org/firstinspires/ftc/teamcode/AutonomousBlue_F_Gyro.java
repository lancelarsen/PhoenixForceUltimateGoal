package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(BLUE) F [Found->*P1(Wall)] GYRO", group="Blue")
public class AutonomousBlue_F_Gyro extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P4 - Face Foundation (side aligned with zone)
    //--- Turn foundation, pull 45 degree diagnal back and park in P1
    //----------------------------------------------------------------------
    static final double GYRO_ANGLE_BACKWALL = 90.0;
    static final double GYRO_ANGLE_FRONTWALL = -90.0;
    static final double GYRO_ANGLE_CENTER = 0.0;
    static final double GYRO_ANGLE_STARTWALL = 180.0;
    
    //----------------------------------------------------------------------
    //--- Autonomous Code
    //----------------------------------------------------------------------
    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        InitGyro();
        //InitSkystoneArms();
        
        ShowMessage("(BLUE) F [Found->*P1(Wall)] GYRO");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        sleep(1000);
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);

        //----------------------------------------------------------------------
        //--- Initialize before Start
        //----------------------------------------------------------------------
        SetMotorsToBrake();
        
        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();
        robot.TIMER_START_AUTO = System.currentTimeMillis();
        
        //----------------------------------------------------------------------
        // Set Drive Path
        //----------------------------------------------------------------------
        StrafeLeft(1,15,5);                     //--- *** Center on foundation 
        GyroTurn(GYRO_ANGLE_CENTER);            //--- Square up on wall
        
        LifterToPosition(500);                  //--- Raise lifter (releases arm)
        EncoderDriveAccel(1,26,26,3,10);        //--- Move forward towards foundation
        CaptureFoundation(1500);                //--- Move forward, using touch sensors to grab foundation

        EncoderDrive(1,-35,-35);                //--- Drag foundation backwards
        GyroTurn(1, GYRO_ANGLE_BACKWALL, 5000); //--- Turn foundation towards back wall
        EncoderDrive(1,15,15);                  //--- Push Foundation into wall

        SpacerUp();                             //--- Release the foundation
        OpenWhiskers();                         //--- Release the foundation
        SmartSleep(1000);                       //--- ... for seconds
        
        EncoderDrive(1,-5,-5);                  //--- Move back from foundation
        GyroTurn(GYRO_ANGLE_FRONTWALL);         //--- Face the parking
        StrafeRight(1,15,5);                    //--- *** Strafe against wall 
        EncoderDrive(1,-5,-5);                  //--- Backup towards foundation
        
        LifterToPosition(0);                    //--- Lower arm before parking
        WaitCountdown(6000);                    //--- Wait for 6 seconds to be left in Auto

        robot.tapeForwardMotor.setPower(1);            //--- Tapemeasure over parking
        PlayVictorySound();
        SmartSleep(1500);
        robot.tapeForwardMotor.setPower(0);
        
        SmartSleep(10000);
        StopMotors();
    }
}
