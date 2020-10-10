package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="Strategy B (BLUE)", group="Blue")
public class AutonomousBlue_B extends PhoenixBotSharedCode
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
        
        ShowMessage("Strategy B (BLUE)");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        SmartSleep(1000);
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);

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
    
    void DropBrickOff()
    {
        BrickIntakeOutStart();                  //--- Intake Servos On (Drop)
        LifterToPosition(600);                  //--- Raise Lifter
        SmartSleep(1000);                       //--- Wait while dropping Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        EncoderDriveAccel(1,-6,-6,3,3);         //--- Drive back under the Bridge
        LifterToPosition(0);                    //--- Lower Lifter to ground
    }
}
