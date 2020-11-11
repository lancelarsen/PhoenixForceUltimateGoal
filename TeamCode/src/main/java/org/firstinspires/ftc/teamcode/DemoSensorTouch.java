package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Disabled
@TeleOp(name = "Sensor: Touch", group = "Demo")
public class DemoSensorTouch extends PhoenixBotSharedCode
{
    DigitalChannel leftTouch;
    DigitalChannel rightTouch;

    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        
        leftTouch  = hardwareMap.get(DigitalChannel.class, "ltouch");
        rightTouch = hardwareMap.get(DigitalChannel.class, "rtouch");

        leftTouch.setMode(DigitalChannel.Mode.INPUT);
        rightTouch.setMode(DigitalChannel.Mode.INPUT);

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
            ArcadeDrivePlus();      //--- left_stick, right_stick, dpad_up, dpad_down, dpad_left, dpad_right
            
            if (leftTouch.getState() == true) 
            {
                telemetry.addData("Left Touch", "NOT Pressed");
            } 
            else 
            {
                telemetry.addData("Left Touch", "Pressed");
            }
            
            if (rightTouch.getState() == true) 
            {
                telemetry.addData("Right Touch", "NOT Pressed");
            } 
            else 
            {
                telemetry.addData("Right Touch", "Pressed");
            }
            
            boolean notPressed = true; 
            while (notPressed)
            {
                DriveForward(0.3);
                if (leftTouch.getState() != true & rightTouch.getState() != true)
                {
                    notPressed = false;
                    StopMotors();
                    //SpacerDown();
                    //CloseWhiskers();
                }  
            }


/*
            if (leftTouch.getState() != true & rightTouch.getState() != true)
            {
                SpacerDown();
                CloseWhiskers();
            }
*/

            telemetry.update();
        }
    }
}
