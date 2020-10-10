package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Disabled
@TeleOp(name="Gyro Drive", group="ZDemo")
public class DemoDriveGyroTeleOp extends PhoenixBotSharedCode 
{
    double          power = .3;

    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize the hardware variables
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        InitGyro();
        
        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();

        //----------------------------------------------------------------------
        //--- Run until the end of the match (driver presses STOP)
        //----------------------------------------------------------------------
        while (opModeIsActive()) 
        {
            GyroUpdates();
            
            //----------------------------------------------------------------------
            //--- Gamepad 1
            //----------------------------------------------------------------------
            SetDriveSpeeds();       //--- x, y, b, a 
            ArcadeDrivePlus();      //--- left_stick, right_stick, dpad_up, dpad_down, dpad_left, dpad_right
            
            //----------------------------------------------------------------------
            // Gyro Driving Examples
            //----------------------------------------------------------------------
            if (gamepad1.left_bumper) Rotate(74, 1); //-- Turns Left 90
            if (gamepad1.right_bumper) Rotate(-74, 1); //-- Turns Right 90

/*
            if (gamepad1.right_trigger > 0.5) 
            {
                // reset angle tracking on new heading.
                resetAngle();
                
                while (opModeIsActive() && !gamepad1.right_stick_button)
                {
                    //robot.leftFrontMotor.setPower(power - correction);
                    //robot.leftRearMotor.setPower(power - correction);
                    //robot.rightFrontMotor.setPower(power + correction);
                    //robot.rightRearMotor.setPower(power + correction);                    
                }
                
                StopMotors();
            }
*/

            //----------------------------------------------------------------------
            //--- Screen
            //----------------------------------------------------------------------
            UpdateDisplay();
        }
    }
    
        
    //----------------------------------------------------------------------

}