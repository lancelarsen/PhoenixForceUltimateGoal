package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(RED) F [Found->P1(Wall)]", group="Red")
public class AutonomousRed_F extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Autonomous Code
    //----------------------------------------------------------------------
    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Strategy Details ---
        //--- Starting Postion: P4 - Face Foundation (side aligned with zone)
        //--- Turn foundation, pull 45 degree diagnal back and park in P1
        //----------------------------------------------------------------------
    
        //----------------------------------------------------------------------
        //--- Initialize
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        InitGyro();
        InitSkystoneArms();
        
        ShowMessage("(RED) F [Found->P1]");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        SmartSleep(1000);
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
        StrafeRight(1,15,5);                    //--- *** Center on Foundation 
        EncoderDrive(0.5,-3,-3,99);             //--- Square up on wall
        SmartSleep(10000);
        LifterToPosition(500);                  //--- Raise Lifter (Releases Arm)
        EncoderDriveAccel(1,26,26,3,10);        //--- Move Forward towards Foundation
        CaptureFoundation(1500);                //--- *** Move forward, using Touch sensors to grab Foundation

        EncoderDrive(1,-20,-40,99);             //--- *** Drag Foundation Backwards
        TurnRight90Gyro();                      //--- *** Turn Foundation towards wall
        EncoderDrive(1,15,15,99);               //--- Push Foundation into wall

        SpacerUp();                             //--- Release the Foundation
        OpenWhiskers();                         //--- Release the Foundation
        

        SetMotorsToFloat();                     //--- Turn off Motor Brakes as we just want to go back!

        //Strafe135(-1);                          //--- *** Strafe backwards 45 degrees
        //SmartSleep(500);                        //--- ...for 0.5 seconds
        //LifterToPosition(0);                    //--- Lower Arm before Parking
        //SmartSleep(850);                        //--- ...for 0.85 seconds
        //StopMotors();                           //--- Stop moving back

        StrafeRight(1,25,5);                    //--- *** Strafe against wall 
        SmartSleep(5000);
        //ReachArmExtendBack();                   //--- Reach out with the arm
        WaitCountdown(5000);                    //--- Wait for 5 seconds to be left in Auto

        EncoderDrive(1,-20,-20,99);             //--- Move Back to Park
        LifterToPosition(0);                    //--- Lower Arm before Parking

        SmartSleep(10000);
        StopMotors();
    }
}
