package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="Strategy B (RED)", group="Red")
public class AutonomousRed_B extends PhoenixBotSharedCode
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
        
        ShowMessage("Strategy B (RED)");
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
        SpacerDown();                               //--- Brick spacer down 
        LifterToPosition(500);                      //--- Raise Lifter (Releases Arm)
        
        StrafeLeft(1,10,99);                       //--- Strafe to align towards middle of quary
        
        EncoderDriveAccel(1,26,26,3,10);            //--- Move Forward to Bricks
        
        EncoderDriveAccelToDistanceShort(1, 6, 6, 3, 3, 25);    //--- Stop at ~15cm from stones 
        Boolean foundStone = ScanForSkystone(0.2,-25,0,10);      //--- Color Sensor Scan for Skystone
        
        if (foundStone)
        {
            FoundStoneGrabIt();
            
            TurnRight90AbsoluteGyro();                   //--- Left turn, preparing to go under Bridge
            //EncoderDrive(1,1,-1,99);                    //--- Err on the side of being a little towards the center
            //StrafeRight(1,4,99);
            
            EncoderDriveAccel(1,70,70,5,10);            //--- Drive under the Bridge
            DropBrickOff();                             //--- Leave brick on other side of bridge
            //EncoderDrive(1,-1,1,99);                    //--- Err on the side of being a little towards the center
            EncoderDriveAccel(1,-35,-35,3,10);          //--- Drive back under the Bridge
            //StrafeLeft(1,4,99);
            
            LifterToPosition(500);                      //--- Raise Lifter (Releases Arm)
            TurnLeft90Gyro();                          //--- Turn towards quary
            
            EncoderDriveAccelToDistanceShort(1, 6, 6, 3, 3, 25);    //--- Stop at ~15cm from stones 
            foundStone = ScanForSkystone(0.2,-25,0,10);              //--- Color Sensor Scan for Skystone
            
            FoundStoneGrabIt();                         //--- Find it or not, grab something!
            
            //if (foundStone)
            //{
            //    FoundStoneGrabIt();
            //}
            //else
            //{
            //    LifterToPosition(0);                    //--- Pick up Brick
            //}
            
            TurnRight90AbsoluteGyro();                   //--- Left turn, preparing to go under Bridge
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
        StrafeLeft(0.2,3,99);                  //--- Recenter on brick
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

/*
SpacerDown();                           //--- Brick spacer down 
LifterToPosition(500);                  //--- Raise Lifter (Releases Arm)
EncoderDriveAccel(1,24,24,3,10);        //--- Move Forward to middle quary 
TurnRight90Gyro();                      //--- Turn toward the wall
EncoderDriveAccel(1,20,20,6,10);        //--- Move torward wall
TurnLeft90Gyro();                       //--- Turn toward the stones
EncoderDriveAccelToDistanceShort(0.3, 6, 6, 5, 0, 25);  //--- Stop at ~15cm from stones 
Boolean foundStone = ScanForSkystone(0.2,30,1,99);      //--- Color Sensor Scan for Skystone

if (foundStone)
{
    EncoderDrive(0.3, 4, 4, 99);            //--- Push into stones
    
    BrickIntakeInStart();                   //--- Intake Servos On
    LifterToPosition(0);                    //--- Pick up Brick
    SmartSleep(1500);                       //--- Wait while picking up Brick
    BrickIntakeStop();                      //--- Intake Servos Stop
    
    EncoderDriveAccel(1,-6,-6,3,3);     //--- Move Back from Stones
}
else
{
    //--- Capture with new arm?
    EncoderDriveAccel(1,-4,-4,3,3);     //--- Move Back from Stones
}

TurnLeft90AbsoluteGyro();               //--- Left turn, preparing to go under Bridge
*/

/*
EncoderDriveAccel(1,39,39,6,10);        //--- Move Forward wall
TurnLeft90Gyro();                       //--- Turn toward stones
LifterToPosition(500);                  //--- Raise Lifter (Releases Arm)
StrafeRight(0.4,5,3);
EncoderDriveAccelToDistanceShort(0.3, 6, 6, 5, 0, 25);  //--- Stop at ~15cm from stones 
Boolean foundStone = ScanForSkystone(0.2,-20,-1,99);    //--- Color Sensor Scan for Skystone

if (foundStone)
{   
    EncoderDriveAccel(0.2,1,1,1,0);     //--- Move Forward to Bricks

    BrickIntakeInStart();                   //--- Intake Servos On
    LifterToPosition(0);                    //--- Pick up Brick
    SmartSleep(2500);                       //--- Wait while picking up Brick (little longer than usual)
    BrickIntakeStop();                      //--- Intake Servos Stop
}
else
{
    EncoderDriveAccel(1,-6,-6,3,3);     //--- Move Back from Stones
    StrafeRight(0.4,25,5);              //--- Align back with wall
    
    EncoderDriveAccelToDistanceShort(0.3, 6, 6, 5, 0, 25);  //--- Stop at ~15cm from stones 
    EncoderDrive(0.3, 4, 4, 99);            //--- Push into stones
    EncoderDrive(0.3,10,-10,99);            //--- Turn towards wall
    EncoderDrive(0.3, 8, 8, 99);            //--- Push into stones turning ~45o so we can grab it!

    BrickIntakeInStart();                   //--- Intake Servos On
    LifterToPosition(0);                    //--- Pick up Brick
    SmartSleep(2500);                       //--- Wait while picking up Brick (little longer than usual)
    BrickIntakeStop();                      //--- Intake Servos Stop

    StrafeRight(1,15,3);                    //--- Strafe to get clear of Stones
}

EncoderDrive(1,-5,-5,99);               //--- Move back from Stones
TurnLeft90AbsoluteGyro();               //--- Left turn, preparing to go under Bridge
*/




//EncoderDriveAccelToDistanceShort(0.2, 1, 1, 5, 0, 15); //--- Stop at ~<5cm from stones 

//EncoderDriveAccel(0.5,3,3,3,0);         //--- Move Forward slightly to Bricks

/*
BrickIntakeInStart();                   //--- Intake Servos On
LifterToPosition(0);                    //--- Pick up Brick
SmartSleep(1500);                       //--- Wait while picking up Brick
BrickIntakeStop();                      //--- Intake Servos Stop

EncoderDriveAccel(0.8,-5,-5,3,10);      //--- Move back to give space for turn

TurnLeft90AbsoluteGyro();               //--- Left turn, preparing to go under Bridge

EncoderDriveAccel(1,56,56,3,10);        //--- Drive under the Bridge

LifterToPosition(500);                  //--- Raise Lifter
SmartSleep(500);
EncoderDriveAccelToDistance(1,25,25,3,3,28);    //--- Move until ~19inches (with overshoot)

SpacerUp();                         
TurnRight90Gyro();                      //--- Turn to face platform

CaptureFoundation();                    //--- Move forward, using Touch sensors to grab Foundation
EncoderDrive(1,-40,-20,99);             //--- Drag Foundation Backwards
TurnLeft90Gyro();                       //--- Turn Foundation towards wall
EncoderDrive(1,15,15,99);               //--- Push Foundation into wall

SpacerUp();                             //--- Release the Foundation
OpenWhiskers();                         //--- Release the Foundation

ReachArmExtendBack();                   //--- Extend Reach Back

LifterToPosition(200);                  //--- Lower Brick to Foundation
BrickIntakeOutStart();                  //--- Intake Servos On (Drop)
SmartSleep(500);                        //--- Wait while dropping Brick
LifterToPosition(600);                  //--- Raise Lifter
SmartSleep(1000);                       //--- Wait while dropping Brick
BrickIntakeStop();                      //--- Intake Servos Stop

SetMotorsToFloat();                     //--- Turn off Motor Brakes as we just want to go back!

EncoderDrive(1,-10,-10,99);             //--- Move Back away from Foundation
LifterToPosition(0);                    //--- Lower Arm before Parking
EncoderDrive(1,-25,-25,99);             //--- Move Back to Park
*/
