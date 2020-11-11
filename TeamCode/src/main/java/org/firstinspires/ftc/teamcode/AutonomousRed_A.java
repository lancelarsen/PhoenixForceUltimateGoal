package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(RED) Auto", group="Red")
public class AutonomousRed_A extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Face Stones (back edge align on arena crack)
    //--- Get the close skystone, deliver to foundation, turn foundation, 
    //---  pull diag and then straight back and park in P2
    //----------------------------------------------------------------------
    static final double GYRO_ANGLE_BACKWALL = -90.0;
    static final double GYRO_ANGLE_FRONTWALL = 90.0;
    static final double GYRO_ANGLE_CENTER = 0.0;
    static final double GYRO_ANGLE_STARTWALL = 180.0;
    
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

//        ShowMessage("(BLUE) D [Full->*P2(Center)] GYRO");
//        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
//        sleep(1000);
//        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);

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
        SmartSleep(10000);
        StopMotors();
    }
}
