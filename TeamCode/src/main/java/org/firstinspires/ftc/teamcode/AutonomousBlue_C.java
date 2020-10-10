package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="Strategy C (BLUE)", group="Blue")
public class AutonomousBlue_C extends PhoenixBotSharedCode
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
        
        ShowMessage("Strategy C (BLUE)");
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
        //SmartSleep(10000);                      //--- Wait for 10 seconds for
        SpacerDown();                           //--- Brick spacer down 
        LifterToPosition(500);                  //--- Raise Lifter (Releases Arm)
        StrafeRight(1,25,99);                   //--- Strafe Right to wall
        EncoderDriveAccel(0.8,26,26,3,10);      //--- Move Forward to Bricks

        ScanForSkystone(0.2, -60, 1, 10);        //--- Color Sensor Scan for Skystone
        EncoderDriveAccel(0.5,3,3,0,0);         //--- Move Forward slightly to Bricks
        
        BrickIntakeInStart();                   //--- Intake Servos On
        LifterToPosition(0);                    //--- Pick up Brick
        SmartSleep(1500);                       //--- Wait while picking up Brick
        BrickIntakeStop();                      //--- Intake Servos Stop
        
        EncoderDriveAccel(0.8,-5,-5,3,10);      //--- Move back to give space for turn

        TurnLeft90AbsoluteGyro();
        //TurnLeft90Gyro();                     //--- Left turn, preparing to go under Bridge
        
        EncoderDriveAccel(1,64,64,3,3);         //--- Drive under the Bridge

        LifterToPosition(500);                  //--- Raise Lifter
        SmartSleep(500);
        EncoderDriveAccelToDistance(1,30,30,3,5,20);    //--- Move until ~19inches (with overshoot)
        
        SpacerUp();                         
    
        /*CaptureFoundation();                    //--- Move forward, using Touch sensors to grab Foundation
        EncoderDriveAccel(1,-40,-20,3,3);       //--- Drag Foundation Backwards
        TurnLeft90Gyro();                       //--- Turn Foundation towards wall
        EncoderDriveAccel(1,20,20,0,0);         //--- Push Foundation into wall
        
        SpacerUp();                             //--- Release the Foundation
        OpenWhiskers();                         //--- Release the Foundation
        
        ReachArmExtendBack();                   //--- Extend Reach Back
        */
        
        LifterToPosition(200);                  //--- Lower Brick to Foundation
        BrickIntakeOutStart();                  //--- Intake Servos On (Drop)
        SmartSleep(500);                        //--- Wait while dropping Brick
        LifterToPosition(600);                  //--- Raise Lifter
        SmartSleep(1500);                       //--- Wait while dropping Brick
        BrickIntakeStop();                      //--- Intake Servos Stop

        EncoderDriveAccel(0.8,-10,-10,0,0);     //--- Move Back away from Foundation
        LifterToPosition(0);                    //--- Lower Arm before Parking
        EncoderDriveAccel(0.8,-25,-25,0,0);     //--- Move Back to Park
        
        SmartSleep(10000);

        StopMotors();
    }
}
