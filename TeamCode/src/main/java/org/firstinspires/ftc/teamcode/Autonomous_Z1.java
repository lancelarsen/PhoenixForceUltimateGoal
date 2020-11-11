package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="Z1 [2->P1(Wall)]", group="Both")
public class Autonomous_Z1 extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Back towards Bridge (Align front of robot with crack)
    //--- Sleep 10 seconds, drive backwards under bridge
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
        //InitGyro();
        //InitSkystoneArms();
        
        ShowMessage("Z1 [2->P1]");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
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
        //WaitCountdown(20000);                      //--- Wait for partner
        
        LifterToPosition(500);                  //--- Raise Lifter to release Arm
        SmartSleep(1000);
        LifterToPosition(0);                    //--- Lower Lifter
        
        EncoderDriveAccel(0.5,-10,-10,3,3);
        
        WaitCountdown(0);                      //--- Wait for end of match
        
        StopMotors();
    }
}
