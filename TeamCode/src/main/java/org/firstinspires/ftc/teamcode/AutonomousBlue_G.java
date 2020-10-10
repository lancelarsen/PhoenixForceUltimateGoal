package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(BLUE) E [2 Skystones->P2]", group="Blue")
public class AutonomousBlue_G extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Face Stones (back edge align on arena crack)
    //--- Get the close skystone, deliver to foundation, turn foundation, 
    //---  pull 45 degree diagnal back and park in P1
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
        BlueInt();
        
        ShowMessage("(BLUE) E [2 Skystones->P2]");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
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
        Strafe135(-1);                          //--- *** Strafe backwards 135 degrees
        SmartSleep(1500);                       //--- ...for seconds
        StopMotors();                           //--- Stop moving
        
        DriveBackwards(1);                      //--- Align back against wall
        SmartSleep(500);                        //--- ...for seconds
        StopMotors();                           //--- Stop moving

        StrafeRight(1,2,5);                     //--- *** Strafe Right
        
        //--- 1st Skystone
        
        EncoderDriveAccel(1,4,4,2,2);         //--- Move to first Skystone
        
        //---
        
        robot.rightSkystoneServo.setPosition(0.03); //--- Skystone arm down
        SmartSleep(500);                        //--- ...for seconds
        
        StrafeLeft(1,12,5);                     //--- *** Strafe away from stones
        
        //EncoderDriveAccel(1,83,83,6,15);        //--- Drive forward under bridge
        EncoderDrive(1,83,83,99);        //--- Drive forward under bridge
        
        robot.rightSkystoneServo.setPosition(0.35); //--- Skystone arm up
        
        //EncoderDriveAccel(1,-62,-62,6,15);        //--- Drive forward under bridge
        EncoderDrive(1,-62,-62,99);
        
        //---
        
        StrafeRight(1,12,5);                    //--- *** Strafe to the stones
        
        robot.rightSkystoneServo.setPosition(0.03); //--- Skystone arm down
        SmartSleep(500);                        //--- ...for seconds
        
        StrafeLeft(1,12,5);                     //--- *** Strafe away from stones
        
        //EncoderDriveAccel(1,62,62,6,15);        //--- Drive forward under bridge
        EncoderDrive(1,62,62,99);
        
        robot.rightSkystoneServo.setPosition(0.35); //--- Skystone arm down
        
        //EncoderDriveAccel(1,-52,-52,6,15);        //--- Drive forward under bridge
        EncoderDrive(1,-52,-52,99);
        
        //---
        
        StrafeRight(1,12,5);                    //--- *** Strafe to the stones
        
        robot.rightSkystoneServo.setPosition(0.03); //--- Skystone arm down
        SmartSleep(500);                        //--- ...for seconds
        
        StrafeLeft(1,12,5);                     //--- *** Strafe away from stones
        
        //EncoderDriveAccel(1,62,62,6,15);        //--- Drive forward under bridge
        EncoderDrive(1,62,62,99);
        
        robot.rightSkystoneServo.setPosition(0.35); //--- Skystone arm down
        
        //EncoderDriveAccel(1,-22,-22,6,15);        //--- Park
        EncoderDrive(1,-22,-22,99);

        
        /*
        SpacerDown();                           //--- Brick spacer down 
        LifterToPosition(500);                  //--- Raise Lifter (Releases Arm)
        EncoderDriveAccel(0.8,26,26,3,10);      //--- Move Forward to Bricks
        ScanForSkystone(0.2,24,0,10);           //--- *** Color Sensor Scan for Skystone
        
        //--- After scanning for Skystone, correct to center of stone
        if (robot.TIME_SKYSTONE_SEARCH > 3201)  //--- Found Skystone 3
        {
            StrafeRight(1,2,5);                 //--- *** Strafe Right
        }
        else                                    //--- Found Skystone 1 or 2
        {
            StrafeLeft(1,1,5);                  //--- *** Strafe Left
        }

        EncoderDriveAccel(0.5,3,3,3,0);         //--- Move Forward slightly to Bricks
        BrickIntakeInStart();                   //--- Intake Servos On
        LifterToPosition(0);                    //--- Pick up Brick
        SmartSleep(1500);                       //--- Wait while picking up Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        EncoderDriveAccel(0.8,-5,-5,3,10);      //--- Move back to give space for turn
        TurnLeft90AbsoluteGyro();               //--- *** Left turn, preparing to go under Bridge

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
        TurnRight90Gyro();                      //--- *** Turn to face platform
        
        CaptureFoundation(2300);                //--- Move forward, using Touch sensors to grab Foundation
        LifterToPosition(100);                  //--- Lower Brick to Foundation
        EncoderDrive(1,-40,-20,99);             //--- Drag Foundation Backwards
        TurnLeft90Gyro();                       //--- *** Turn Foundation towards wall
        EncoderDrive(1,15,15,99);               //--- Push Foundation into wall

        SpacerUp();                             //--- Release the Foundation
        OpenWhiskers();                         //--- Release the Foundation
        ReachArmExtendBack();                   //--- Reach out with the arm
        DropStoneOnFoundation();                //--- Raise Lifter, releasing stone without dislodging
        
        SetMotorsToFloat();                     //--- Turn off Motor Brakes as we just want to go back!
        Strafe45(-1);                           //--- *** Strafe backwards 45 degrees
        SmartSleep(500);                        //--- ...for 0.5 seconds
        LifterToPosition(0);                    //--- Lower Arm before Parking
        SmartSleep(850);                        //--- ...for 0.85 seconds
        StopMotors();                           //--- Stop moving back
        */

        SmartSleep(10000);
        StopMotors();
    }
}
