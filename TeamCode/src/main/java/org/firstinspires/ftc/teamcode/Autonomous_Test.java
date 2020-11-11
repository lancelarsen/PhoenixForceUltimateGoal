package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="Test", group="")
public class Autonomous_Test extends PhoenixBotSharedCode
{
    static final double GYRO_ANGLE_BACKWALL = 0.0;
    static final double GYRO_ANGLE_FRONTWALL = 180.0;
    static final double GYRO_ANGLE_CENTER = -90.0;
    static final double GYRO_ANGLE_STARTWALL = 90.0;
    
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
        //InitGyro();
        //InitSkystoneArms();
        ShowMessage("Test");

        //----------------------------------------------------------------------
        //--- Initialize before Start
        //----------------------------------------------------------------------
        SetMotorsToBrake();
        
        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();
        robot.TIMER_START_AUTO = System.currentTimeMillis();

        int timeBetween = 500;

        GripArmRightDropReady();
        sleep(1500);
        //GripArmRightDrop();

        //robot.gripperServo.setPosition(0.355);       //--- Close on stone

        //robot.rightSkystoneServo.setPosition(0);
        //robot.gripperServo.setPosition(0); //--- Open 

/*
        robot.gripperServo.setPosition(0); //--- Open 
        robot.rightSkystoneServo.setPosition(0);
        sleep(800);
        robot.gripperServo.setPosition(0.37); //--- Closed on Stone
        sleep(400);
        robot.rightSkystoneServo.setPosition(0.9);
        
        sleep(10000);
        
        robot.gripperServo.setPosition(0.37);
        robot.rightSkystoneServo.setPosition(0);
        sleep(800);
        robot.gripperServo.setPosition(0); //--- Open
        sleep(600);
        robot.rightSkystoneServo.setPosition(0.9);
        sleep(400);
        robot.gripperServo.setPosition(0.37);
        */

/*
        robot.gripperServo.setPosition(0); //--- Open 
        SmartSleep(3000);
        robot.gripperServo.setPosition(0.33); //--- Closed on Stone
*/
        //robot.rightSkystoneServo.setPosition(0.40);
        //sleep(750);
        //robot.leftSkystoneServo.setPosition(0.86);
        //sleep(1000);
        //robot.leftSkystoneServo.setPosition(0.8);
        //sleep(1000);
        /*
        robot.leftSkystoneServo.setPosition(0.75);
        sleep(timeBetween);
        robot.leftSkystoneServo.setPosition(0.7);
        sleep(timeBetween);
        robot.leftSkystoneServo.setPosition(0.65);
        sleep(timeBetween);
        robot.leftSkystoneServo.setPosition(0.6);
        sleep(timeBetween);
        robot.leftSkystoneServo.setPosition(0.55);
        sleep(timeBetween);
        robot.leftSkystoneServo.setPosition(0.5);
        sleep(timeBetween);
        robot.leftSkystoneServo.setPosition(0.45);
        sleep(timeBetween);
        */
        //robot.leftSkystoneServo.setPosition(0.0565);
        //sleep(500);
        
        //robot.leftSkystoneServo.setPosition(0.64); //--- Up

        //robot.leftSkystoneServo.setPosition(0.0565); //--- Down
        sleep(500);

        //WaitCountdown(20000);
        //ShowMessage("GO!");
        
        //----------------------------------------------------------------------
        // Set Drive Path
        //----------------------------------------------------------------------
        //CaptureFoundation(3000);
        //ReachArmExtendForward();
        //ReachArmExtendBack();
        
        //StrafeRightToShortDistance(0.5);
        //RSidearmUp();
        //SmartSleep(1000);
        //LSidearmDown();
        //SmartSleep(2000);
        //RSidearmDown();
        //SmartSleep(2000);
        //RSidearmRest();                         //--- Skystone arm down
        //SmartSleep(2000);
        //LSidearmCollapse();
        //SmartSleep(2000);
        
/*
 
        GyroDrive(1,90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,-90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,-90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,-90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,90,GYRO_ANGLE_BACKWALL);
        GyroDrive(1,-90,GYRO_ANGLE_BACKWALL);
*/

        //----------------------------------------------------------------------
        SmartSleep(20000);

        StopMotors();
    }
    
    void WaitCountdown(double millisecondsLeft)
    {
        robot.TIMER_REMAINING_AUTO = 30000; 
        while(opModeIsActive() && (robot.TIMER_REMAINING_AUTO > millisecondsLeft))
        {
            robot.TIMER_REMAINING_AUTO = 30000 - (System.currentTimeMillis() - robot.TIMER_START_AUTO); 
            ShowMessage("" + robot.TIMER_REMAINING_AUTO);
        }
    }
}
