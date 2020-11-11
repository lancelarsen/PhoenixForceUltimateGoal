package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Disabled
@TeleOp(name="Sensor: 2M Distance", group="Demo")
public class DemoSensor2mDistance extends PhoenixBotSharedCode
{
    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        
        //----------------------------------------------------------------------
        //--- Send telemetry message to signify robot waiting
        //----------------------------------------------------------------------
        telemetry.addData("Demo", "Press START");
        telemetry.update();

        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();

        //----------------------------------------------------------------------
        //--- Run until the end of the match (driver presses STOP)
        //----------------------------------------------------------------------
        while(opModeIsActive()) 
        {
            //----------------------------------------------------------------------
            //--- Gamepad 1
            //----------------------------------------------------------------------
            SetDriveSpeeds();       //--- x, y, b, a 
            ArcadeDrivePlus();      //--- left_stick, right_stick, dpad_up, dpad_down, dpad_left, dpad_right
            WhiskerClamps();        //--- left_bumper, left_trigger
            BrickSpacer();          //--- right_bumper, right_trigger
            
            //----------------------------------------------------------------------
            //--- Gamepad 2
            //----------------------------------------------------------------------
            Lifter();               //--- dpad_up, dpad_down, y, a, left_bumper, left_trigger
            
            
            double centerDistanceIN = robot.centerDistanceSensor.getDistance(DistanceUnit.INCH);

            ShowMessage(String.format("%.01f in", centerDistanceIN));

            telemetry.update();
        }
    }

}
