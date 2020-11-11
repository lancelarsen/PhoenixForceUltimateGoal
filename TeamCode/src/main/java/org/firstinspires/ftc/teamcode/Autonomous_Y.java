package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="Y [1 or 2->Tape]", group="Both")
public class Autonomous_Y extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P1 - Back towards Bridge (Align front of robot with Red tape)
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
        InitGyro();
        InitSkystoneArms();
        
        ShowMessage("Y [1 or 2->Tape]");
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
        WaitCountdown(20000);           //--- Wait for partner

        robot.tapeForwardMotor.setPower(1);    //--- Tapemeasure over parking
        PlayVictorySound();
        SmartSleep(1500);
        robot.tapeForwardMotor.setPower(0);
    }
}
