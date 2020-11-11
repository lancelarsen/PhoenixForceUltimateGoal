package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="Reset Arms", group="")
public class Autonomous_ResetArms extends PhoenixBotSharedCode
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

        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();

        int timeBetween = 500;

        robot.gripperServo.setPosition(0.37);       //--- Closed on Stone
        robot.rightSkystoneServo.setPosition(0);    //--- Level out and down
        robot.leftSkystoneServo.setPosition(0);     //--- Level out and down

        ShowMessage("Ready to Attach Arm");

        //----------------------------------------------------------------------
        SmartSleep(20000);

        StopMotors();
    }
}
