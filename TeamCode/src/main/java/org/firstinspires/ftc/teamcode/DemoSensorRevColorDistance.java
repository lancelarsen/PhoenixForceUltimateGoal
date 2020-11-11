package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Disabled
@TeleOp(name="Sensor: REVColorDistance", group="Demo")
public class DemoSensorRevColorDistance extends PhoenixBotSharedCode 
{
    ColorSensor leftColorSensor;
    ColorSensor rightColorSensor;
    DistanceSensor leftDistanceSensor;
    DistanceSensor rightDistanceSensor;

    @Override
    public void runOpMode() {

        //----------------------------------------------------------------------
        //--- Initialize
        //----------------------------------------------------------------------
        robot.init(hardwareMap);

        robot.leftSpacerServo.setPosition(0.5);             //-- Down
        robot.rightSpacerServo.setPosition(0.4);

        waitForStart();

        while (opModeIsActive()) 
        {
            //----------------------------------------------------------------------
            //--- Gamepad 1
            //----------------------------------------------------------------------
            SetDriveSpeeds();       //--- x, y, b, a 
            ArcadeDrivePlus();      //--- left_stick, right_stick, dpad_up, dpad_down, dpad_left, dpad_right
            

            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (robot.leftColorSensor.red() * robot.COLOR_SCALE_FACTOR),
                    (int) (robot.leftColorSensor.green() * robot.COLOR_SCALE_FACTOR),
                    (int) (robot.leftColorSensor.blue() * robot.COLOR_SCALE_FACTOR),
                    robot.leftHue);
                    
            Color.RGBToHSV((int) (robot.rightColorSensor.red() * robot.COLOR_SCALE_FACTOR),
                    (int) (robot.rightColorSensor.green() * robot.COLOR_SCALE_FACTOR),
                    (int) (robot.rightColorSensor.blue() * robot.COLOR_SCALE_FACTOR),
                    robot.rightHue);

            // send the info back to driver station using telemetry function.
            telemetry.addData("Distance (cm)", 
            "[L] " + String.format(Locale.US, "%.02f", robot.leftDistanceSensor.getDistance(DistanceUnit.CM)) +
            " [R] " + String.format(Locale.US, "%.02f", robot.rightDistanceSensor.getDistance(DistanceUnit.CM)));
            telemetry.addData("Alpha", "[L] " + robot.leftColorSensor.alpha() + " [R] " + robot.rightColorSensor.alpha());
            telemetry.addData("Red  ", "[L] " + robot.leftColorSensor.red() + " [R] " + robot.rightColorSensor.alpha());
            telemetry.addData("Green", "[L] " + robot.leftColorSensor.green() + " [R] " + robot.rightColorSensor.alpha());
            telemetry.addData("Blue ", "[L] " + robot.leftColorSensor.blue() + " [R] " + robot.rightColorSensor.alpha());
            telemetry.addData("Hue", "[L] " + robot.leftHue[0] + " [R] " + robot.rightHue[0]);

            robot.leftBrickExists = robot.leftDistanceSensor.getDistance(DistanceUnit.CM) < 100;
            robot.rightBrickExists = robot.rightDistanceSensor.getDistance(DistanceUnit.CM) < 100;;

            if (robot.leftBrickExists && robot.leftHue[0] < 75) robot.leftBrickColor = "Yellow";
            if (robot.leftBrickExists && robot.leftHue[0] > 75) robot.leftBrickColor = "Black";

            if (robot.rightBrickExists && robot.rightHue[0] < 75) robot.rightBrickColor = "Yellow";
            if (robot.rightBrickExists && robot.rightHue[0] > 75) robot.rightBrickColor = "Black";

            telemetry.addData("Brick?", "[L] " + robot.leftBrickExists + " [R] " + robot.rightBrickExists);
            telemetry.addData("Color?", "[L] " + robot.leftBrickColor + " [R] " + robot.rightBrickColor);

            if (gamepad1.right_bumper)
            {
                boolean foundBlack = false;
                while(!foundBlack)
                {
                    StrafeRight(0.5,1,99);
                    if (robot.leftBrickColor == "Black" && robot.rightBrickColor == "Black")
                    {
                        foundBlack = true;
                    }
                }
                StopMotors();
            }

            telemetry.update();
        }
    }
}
