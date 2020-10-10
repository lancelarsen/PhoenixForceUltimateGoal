package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(RED) D [Full->P2(Center)]", group="Red")
public class AutonomousRed_D extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Face Stones (back edge align on arena crack)
    //--- Get the close skystone, deliver to foundation, turn foundation, 
    //---  pull diag and then straight back and park in P2
    //----------------------------------------------------------------------

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
        InitSkystoneArms();
        
        ShowMessage("(RED) D [Full->P2(Center)]");
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
        SpacerDown();                           //--- Brick spacer down 
        LifterToPosition(500);                  //--- Raise Lifter (Releases Arm)
        EncoderDriveAccel(0.8,26,26,3,10);      //--- Move Forward to Bricks (???? Stones further away)
        ScanForSkystone(0.2,-24,0,10);          //--- *** Color Sensor Scan for Skystone
        
        //--- After scanning for Skystone, correct to center of stone
        if (robot.TIME_SKYSTONE_SEARCH > 3201)  //--- Found Skystone 3
        {
            StrafeLeft(1,2,5);                  //--- *** Strafe Left
        }
        else                                    //--- Found Skystone 1 or 2
        {
            StrafeRight(1,1,5);                 //--- *** Strafe Right
        }

        EncoderDriveAccel(0.5,3,3,3,0);         //--- Move Forward slightly to Bricks
        BrickIntakeInStart();                   //--- Intake Servos On
        LifterToPosition(0);                    //--- Pick up Brick
        SmartSleep(1500);                       //--- Wait while picking up Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        EncoderDriveAccel(0.8,-5,-5,3,10);      //--- Move back to give space for turn
        TurnRight90AbsoluteGyro();              //--- *** Right turn, preparing to go under Bridge

        //--- Drive under the Bridge, distance based on which Skystone we found
        if (robot.TIME_SKYSTONE_SEARCH < 2300)  //--- Skystone 1
        {
            EncoderDriveAccel(1,75,75,3,10);
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 2301 && robot.TIME_SKYSTONE_SEARCH < 3200) //--- Skystone 2
        {
            EncoderDriveAccel(1,83,83,3,10);
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 3201) //--- Skystone 3
        {
            EncoderDriveAccel(1,91,91,3,10);
        }

        LifterToPosition(500);                  //--- Raise Lifter
        SpacerUp();                             //--- Move spacers up
        TurnLeft90Gyro();                       //--- *** Turn to face platform
        
        CaptureFoundation(2300);                //--- Move forward, using Touch sensors to grab Foundation
        LifterToPosition(100);                  //--- Lower Brick to Foundation
        EncoderDrive(1,-20,-40,99);             //--- *** Drag Foundation Backwards
        TurnRight90Gyro();                      //--- *** Turn Foundation towards wall
        EncoderDrive(1,15,15,99);               //--- Push Foundation into wall

        SpacerUp();                             //--- Release the Foundation
        OpenWhiskers();                         //--- Release the Foundation
        //ReachArmExtendBack();                   //--- Reach out with the arm
        DropStoneOnFoundation();                //--- Raise Lifter, releasing stone without dislodging

        SetMotorsToFloat();                     //--- Turn off Motor Brakes as we just want to go back!
        Strafe45(-1);                           //--- *** Strafe backwards 135 degrees (aligning with P2)
        SmartSleep(500);                        //--- *** ...for 0.5 seconds
        LifterToPosition(0);                    //--- Lower Arm before Parking
        EncoderDrive(1,-20,-20,99);             //--- *** Move Back to Park
        LifterToPosition(0);                    //--- Lower Arm before Parking

        SmartSleep(10000);
        StopMotors();
    }
}
