package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(BLUE) E [2 Skystones->P2]", group = "Blue")
public class AutonomousBlue_E extends PhoenixBotSharedCode
{
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
        
        ShowMessage("(BLUE) E [2 Skystones->P2]");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        SmartSleep(500);
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);

        //----------------------------------------------------------------------
        //--- Initialize before Start
        //----------------------------------------------------------------------
        SetMotorsToBrake();
        
        int skystoneNumber = 0;
        
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
        EncoderDriveAccel(0.8,26,26,3,10);      //--- Move Forward to Bricks
        //EncoderDriveAccelToDistanceShort(1, 6, 6, 3, 3, 25);    //--- Stop at ~15cm from stones 
        Boolean foundStone = ScanForSkystone(0.2,24,0,10);      //--- *** Color Sensor Scan for Skystone
        
        //--- After scanning for Skystone, correct to center of stone
        if (robot.TIME_SKYSTONE_SEARCH > 3201)  //--- Found Skystone 3
        {
            StrafeRight(1,2,5);                 //--- *** Strafe Right
        }
        else                                    //--- Found Skystone 1 or 2
        {
            StrafeLeft(1,1,5);                  //--- *** Strafe Left
        }
        //SmartSleep(10000);
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
            skystoneNumber = 1;
            EncoderDriveAccel(1,75,75,3,15);
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 2301 && robot.TIME_SKYSTONE_SEARCH < 3200) //--- Skystone 2
        {
            skystoneNumber = 2;
            EncoderDriveAccel(1,83,83,3,15);
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 3201) //--- Skystone 3
        {
            skystoneNumber = 3;
            EncoderDriveAccel(1,91,91,3,15);
        }

        DropStoneOff();                         //--- Drop off Skystone 1
        EncoderDriveAccel(1,-80,-80,10,10);     //--- Drive back under the bridge
        LifterToPosition(500);                  //--- Raise Lifter
        TurnRight90Gyro();                      //--- *** Turn towards the stones

        EncoderDriveAccelToDistanceShort(1, 6, 6, 3, 3, 25);    //--- Stop at ~15cm from stones 
    
        if (skystoneNumber == 1)
        {
            StrafeLeft(1,8,5);                  //--- *** Align with Skystone 1
        }
        else if (skystoneNumber == 2)
        {
            StrafeLeft(1,2,5);                  //--- *** Align with Skystone 2
        }
        else if (skystoneNumber == 3)
        {
            StrafeRight(1,5,5);                 //--- *** Align with Skystone 3
        }

        EncoderDriveAccel(0.5,3,3,3,0);         //--- Move Forward slightly to Bricks
        BrickIntakeInStart();                   //--- Intake Servos On
        LifterToPosition(0);                    //--- Pick up Brick
        SmartSleep(1500);                       //--- Wait while picking up Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        EncoderDriveAccel(0.8,-5,-5,3,10);      //--- Move back to give space for turn
        TurnLeft90AbsoluteGyro();               //--- *** Left turn, preparing to go under Bridge

        

        SmartSleep(10000);
        StopMotors();

        /*
        
       SpacerDown();                               //--- Brick spacer down 
        LifterToPosition(500);                      //--- Raise Lifter (Releases Arm)
        
        StrafeRight(1,10,99);                       //--- Strafe to align towards middle of quary
        
        EncoderDriveAccel(1,26,26,3,10);            //--- Move Forward to Bricks
        
        EncoderDriveAccelToDistanceShort(1, 6, 6, 3, 3, 25);    //--- Stop at ~15cm from stones 
        Boolean foundStone = ScanForSkystone(0.2,25,0,10);      //--- Color Sensor Scan for Skystone
        
        if (foundStone)
        {
            FoundStoneGrabIt();
            
            TurnLeft90AbsoluteGyro();                   //--- Left turn, preparing to go under Bridge
            //EncoderDrive(1,1,-1,99);                    //--- Err on the side of being a little towards the center
            //StrafeRight(1,4,99);
            
            EncoderDriveAccel(1,70,70,5,10);            //--- Drive under the Bridge
            DropBrickOff();                             //--- Leave brick on other side of bridge
            //EncoderDrive(1,-1,1,99);                    //--- Err on the side of being a little towards the center
            EncoderDriveAccel(1,-35,-35,3,10);          //--- Drive back under the Bridge
            //StrafeLeft(1,4,99);
            
            LifterToPosition(500);                      //--- Raise Lifter (Releases Arm)
            TurnRight90Gyro();                          //--- Turn towards quary
            
            EncoderDriveAccelToDistanceShort(1, 6, 6, 3, 3, 25);    //--- Stop at ~15cm from stones 
            foundStone = ScanForSkystone(0.2,25,0,10);              //--- Color Sensor Scan for Skystone
            
            FoundStoneGrabIt();                         //--- Find it or not, grab something!
            
            //if (foundStone)
            //{
            //    FoundStoneGrabIt();
            //}
            //else
            //{
            //    LifterToPosition(0);                    //--- Pick up Brick
            //}
            
            TurnLeft90AbsoluteGyro();                   //--- Left turn, preparing to go under Bridge
            //EncoderDrive(1,1,-1,99);                  //--- Err on the side of being a little towards the center
            
            EncoderDriveAccel(1,45,45,3,10);            //--- Drive under the Bridge
            DropBrickOff();                             //--- Leave brick on other side of bridge
            EncoderDriveAccel(1,-25,-25,0,0);           //--- Drive back under the Bridge
        }
        else
        {
            //--- This is the far wall stone - can't easily get - new arm?
            //--- For now, get the skystone to robot's left
            foundStone = ScanForSkystone(0.2,-60,0,10);      //--- Color Sensor Scan for Skystone
            FoundStoneGrabItLeft();
            
            TurnLeft90AbsoluteGyro();                   //--- Left turn, preparing to go under Bridge
            EncoderDriveAccel(1,70,70,3,10);            //--- Drive under the Bridge to platform
            LifterToPosition(500);                      //--- Raise Lifter
            EncoderDriveAccel(1,30,30,3,10);            //--- Drive under the Bridge to platform
            DropBrickOff();                             //--- Leave brick on other side of bridge
            EncoderDriveAccel(1,-35,-35,0,0);           //--- Drive back under the Bridge
        }

        SmartSleep(10000);
        StopMotors();        
        
        */
    }
    
    
    void FoundStoneGrabIt()
    {
        StrafeLeft(0.2,2,99);                   //--- Recenter on brick
        EncoderDriveAccel(0.5,2,2,2,0);         //--- Move Forward slightly to Bricks
        
        BrickIntakeInStart();                   //--- Intake Servos On
        LifterToPosition(0);                    //--- Pick up Brick
        SmartSleep(1500);                       //--- Wait while picking up Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        
        EncoderDriveAccel(1,-5,-5,2,3);         //--- Move back to give space for turn 
    }
    
    void FoundStoneGrabItLeft()
    {
        StrafeLeft(0.2,3,99);                   //--- Recenter on brick
        EncoderDriveAccel(0.5,3,3,2,0);         //--- Move Forward slightly to Bricks
        
        BrickIntakeInStart();                   //--- Intake Servos On
        LifterToPosition(0);                    //--- Pick up Brick
        SmartSleep(1500);                       //--- Wait while picking up Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        
        EncoderDriveAccel(1,-5,-5,2,3);         //--- Move back to give space for turn 
    }
    
    void DropStoneOff()
    {
        BrickIntakeOutStart();                  //--- Intake Servos On (Drop)
        LifterToPosition(600);                  //--- Raise Lifter
        SmartSleep(1000);                       //--- Wait while dropping Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        EncoderDriveAccel(1,-6,-6,3,3);         //--- Back away from stone
        LifterToPosition(0);                    //--- Lower Lifter to ground
    }
}
