package org.firstinspires.ftc.teamcode;

import java.util.Locale;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.annotation.Target;
import java.util.stream.Collector;
import java.io.File;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.ftccommon.SoundPlayer;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class PhoenixBotSharedCode extends LinearOpMode 
{
    PhoenixBotHardware robot = new PhoenixBotHardware();
    ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {}
    PhoenixBotSharedCode() {}

    //region --- Servo Positions ---
    //----------------------------------------------------------------------


    //----------------------------------------------------------------------
    //endregion

    //======================================================================
    //=== Controller Assignments
    //======================================================================

    //region --- Assign Controller Input ---
    //----------------------------------------------------------------------

    void ArcadeDrivePlus()
    {
        //--- gamepad1
        //--- left_stick, right_stick, dpad_up, dpad_down, dpad_left, dpad_right
        
        //--- Mecanum drive forward
        if (gamepad1.dpad_up) 
        {
            DriveForward(robot.MECANUM_SPEED);
            ShowMessage("Drive Forward!");
        }
        //--- Mecanum drive backwards
        else if (gamepad1.dpad_down) 
        {
            DriveBackwards(robot.MECANUM_SPEED);
            ShowMessage("Drive Back!");
        }
        //--- Mecanum strafe right
        else if (gamepad1.dpad_right) 
        {
            StrafeRight(robot.MECANUM_SPEED_STRAFE);
            ShowMessage("Drive Right!");
        }
        //--- Mecanum strafe left
        else if (gamepad1.dpad_left) 
        {
            StrafeLeft(robot.MECANUM_SPEED_STRAFE);
            ShowMessage("Drive Left!");
        } 
        else
        {
            ArcadeDrive();
        }
    }

    void SetDriveSpeeds()
    {
        if (gamepad1.x) 
        {
            //--- Mecanum Low Power!
            ShowMessage("Low Power");
            robot.MECANUM_SPEED = PhoenixBotHardware.MECANUM_SPEED_LOW;
            robot.MECANUM_SPEED_STRAFE = PhoenixBotHardware.MECANUM_SPEED_STRAFE_LOW;
            robot.MECANUM_SPEED_DISPLAY = PhoenixBotHardware.MECANUM_SPEED_LOW_DISPLAY;
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE);
            SmartSleep(100);
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_OCEAN_PALETTE);
        }
        else if (gamepad1.y) 
        {
            //--- Mecanum Medium Power!!
            ShowMessage("Medium Power");
            robot.MECANUM_SPEED = PhoenixBotHardware.MECANUM_SPEED_MED;
            robot.MECANUM_SPEED_STRAFE = PhoenixBotHardware.MECANUM_SPEED_STRAFE_MED;
            robot.MECANUM_SPEED_DISPLAY = PhoenixBotHardware.MECANUM_SPEED_MED_DISPLAY;
//            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
//            SmartSleep(100);
//            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_LARSON_SCANNER);
        }
        else if (gamepad1.b) 
        {
            //--- Mecanum High Power!!!
            ShowMessage("High Power");
            robot.MECANUM_SPEED = PhoenixBotHardware.MECANUM_SPEED_HIGH;
            robot.MECANUM_SPEED_STRAFE = PhoenixBotHardware.MECANUM_SPEED_STRAFE_HIGH;
            robot.MECANUM_SPEED_DISPLAY = PhoenixBotHardware.MECANUM_SPEED_HIGH_DISPLAY;
//            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
//            SmartSleep(100);
//            //robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE);
//            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);
        }
        else if (gamepad1.a) 
        {
            //--- Mecanum Very Slow Power!!!
            ShowMessage("Very Slow Power");
            robot.MECANUM_SPEED = PhoenixBotHardware.MECANUM_SPEED_SLOW;
            robot.MECANUM_SPEED_STRAFE = PhoenixBotHardware.MECANUM_SPEED_STRAFE_SLOW;
            robot.MECANUM_SPEED_DISPLAY = PhoenixBotHardware.MECANUM_SPEED_SLOW_DISPLAY;
//            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
//            SmartSleep(100);
//            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_FOREST_PALETTE);
        }
    }

    void TestMotors()
    {
        if (gamepad1.a)
        {
            robot.leftFrontMotor.setPower(0.2);
        }
        else if (gamepad1.b)
        {
            robot.rightFrontMotor.setPower(0.2);
        }
        else if (gamepad1.x)
        {
            robot.leftRearMotor.setPower(0.2);
        }
        else if (gamepad1.y)
        {
            robot.rightRearMotor.setPower(0.2);
        }
        else
        {
            StopMotors();
        }
    }
    
    //----------------------------------------------------------------------
    //endregion

    //======================================================================
    //=== Autonomous Code
    //======================================================================
    
    //region --- Autonomous ---
    //----------------------------------------------------------------------

    void StrafeRight(double speed, double sidewaysInches, double timeout)
    {
        EncoderStrafe(speed, sidewaysInches, timeout);
    }
    
    void StrafeLeft(double speed, double sidewaysInches, double timeout)
    {
        EncoderStrafe(speed, -1 * sidewaysInches, timeout);
    }

    void TurnLeftGyro(double speed, int angle)
    {
        Rotate(angle, speed);
    }
    
    void TurnRightGyro(double speed, int angle)
    {
        Rotate(-angle, speed);
    }

    void TurnLeft90Gyro()
    {
        Rotate(74, 1); //-- Turns Left 90 at full speed
    }
    
    void TurnRight90Gyro()
    {
        Rotate(-74, 1); //-- Turns Right 90 at full speed
    }
    
    void TurnLeft90AbsoluteGyro()
    {
        RotateAbsolute(74,1);
    }
    
    void TurnRight90AbsoluteGyro()
    {
        RotateAbsolute(-74,1);
    }

    //----------------------------------------------------------------------
    //endregion
    
    //region --- Drive by Encoder  ---

    void EncoderStrafe(double speed, double sidewaysInches, double timeout)
    {
        if (opModeIsActive())
        {
            //----------------------------------------------------------------------
            //--- Calculate Rotation Distance
            //----------------------------------------------------------------------
            int distance = (int)(sidewaysInches * robot.COUNTS_PER_INCH);

            EncoderStrafeByRotation(speed, distance, timeout);
        }
    }

    void EncoderStrafeByRotation(double speed, int distance, double timeout)
    {
        if (opModeIsActive())
        {
            //ShowMessage("--- Encoder Strafe: Start... ---");

            distance = -1 * distance;

            //----------------------------------------------------------------------
            //--- Reset Encoders
            //----------------------------------------------------------------------
            ResetEncoders();

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            SetTargetStrafePosition(distance);

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunToPosition();

            //----------------------------------------------------------------------
            //--- Start Motion
            //----------------------------------------------------------------------
            robot.leftFrontMotor.setPower(Math.abs(speed));
            robot.leftRearMotor.setPower(Math.abs(speed));
            robot.rightFrontMotor.setPower(Math.abs(speed));
            robot.rightRearMotor.setPower(Math.abs(speed));

            //----------------------------------------------------------------------
            //--- Loop While Active, Time is Left, and Motors are Running
            //----------------------------------------------------------------------
            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftFrontMotor.isBusy() && robot.leftRearMotor.isBusy() && robot.rightFrontMotor.isBusy() && robot.rightRearMotor.isBusy()))
            {
                //--- Display Progress
                //telemetry.addData("",  "--- Encoder Strafe ---");
                //telemetry.addData("",  "Running to %7d", distance);
                //telemetry.addData("",  "Running at %7d :%7d",
                //        robot.frontLeftMotor.getCurrentPosition(),
                //        robot.frontRightMotor.getCurrentPosition());
                //telemetry.update();
            }

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            StopMotors();

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunUsingEncoders();

            //ShowMessage("--- Encoder Strafe: Complete! ---");

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
    }

    void EncoderStrafe(double speed, double leftFrontInches, double leftRearInches, double rightFrontInches, double rightRearInches, double timeout)
    {
        if (opModeIsActive())
        {
            //----------------------------------------------------------------------
            //--- Calculate Rotation Distance
            //----------------------------------------------------------------------
            EncoderStrafeByRotation(speed,
            (int)(leftFrontInches * robot.COUNTS_PER_INCH),
            (int)(leftRearInches * robot.COUNTS_PER_INCH),
            (int)(rightFrontInches * robot.COUNTS_PER_INCH),
            (int)(rightRearInches * robot.COUNTS_PER_INCH), timeout);
        }
    }

    void EncoderStrafeByRotation(double speed, int leftFrontDistance, int leftRearDistance, int rightFrontDistance, int rightRearDistance, double timeout)
    {
        if (opModeIsActive())
        {
            //ShowMessage("--- Encoder Strafe: Start... ---");

            //----------------------------------------------------------------------
            //--- Reset Encoders
            //----------------------------------------------------------------------
            ResetEncoders();

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            robot.leftFrontMotor.setTargetPosition(leftFrontDistance);
            //robot.leftRearMotor.setTargetPosition(leftRearDistance);
            //robot.rightFrontMotor.setTargetPosition(rightFrontDistance);
            robot.rightRearMotor.setTargetPosition(rightRearDistance);

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunToPosition();

            //----------------------------------------------------------------------
            //--- Start Motion
            //----------------------------------------------------------------------
            robot.leftFrontMotor.setPower(Math.abs(speed));
            robot.leftRearMotor.setPower(Math.abs(speed));
            robot.rightFrontMotor.setPower(Math.abs(speed));
            robot.rightRearMotor.setPower(Math.abs(speed));

            //----------------------------------------------------------------------
            //--- Loop While Active, Time is Left, and Motors are Running
            //----------------------------------------------------------------------            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftFrontMotor.isBusy() && robot.leftRearMotor.isBusy() && robot.rightFrontMotor.isBusy() && robot.rightRearMotor.isBusy()))
            {
                //--- Display Progress
                //telemetry.addData("",  "--- Encoder Strafe ---");
                //telemetry.addData("",  "Running to %7d", distance);
                //telemetry.addData("",  "Running at %7d :%7d",
                //        robot.frontLeftMotor.getCurrentPosition(),
                //        robot.frontRightMotor.getCurrentPosition());
                //telemetry.update();
            }

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            StopMotors();

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunUsingEncoders();

            //ShowMessage("--- Encoder Strafe: Complete! ---");

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
    }

    void EncoderDrive(double speed, double leftInches, double rightInches, double timeout)
    {
        if (opModeIsActive())
        {
            //----------------------------------------------------------------------
            //--- Calculate Rotation Distance
            //----------------------------------------------------------------------
            int left = (int)(leftInches * robot.COUNTS_PER_INCH);
            int right = (int)(rightInches * robot.COUNTS_PER_INCH);

            EncoderDriveByRotation(speed, left, right, timeout);
        }
    }
    
    void EncoderDrive(double speed, double leftInches, double rightInches)
    {
        EncoderDrive(speed, leftInches, rightInches, robot.AUTO_MAXTIMEOUT_S);
    }

    void EncoderDriveByRotation(double speed, int encoderLeft, int encoderRight, double timeout)
    {
        if (opModeIsActive())
        {
            ShowMessage("--- Encoder Drive: Start... ---");

            //----------------------------------------------------------------------
            //--- Reset Encoders
            //----------------------------------------------------------------------
            ResetEncoders();

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            SetTargetPosition(encoderLeft, encoderRight);

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunToPosition();

            //----------------------------------------------------------------------
            //--- Start Motion
            //----------------------------------------------------------------------
            robot.leftFrontMotor.setPower(Math.abs(speed));
            robot.leftRearMotor.setPower(Math.abs(speed));
            robot.rightFrontMotor.setPower(Math.abs(speed));
            robot.rightRearMotor.setPower(Math.abs(speed));

            //----------------------------------------------------------------------
            //--- Loop While Active, Time is Left, and Motors are Running
            //----------------------------------------------------------------------
            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftFrontMotor.isBusy() && robot.leftRearMotor.isBusy() && robot.rightFrontMotor.isBusy() && robot.rightRearMotor.isBusy()))
            {
                //--- Display Progress
                //telemetry.addData("",  "--- Encoder Drive ---");
                //telemetry.addData("",  "Running to %7d :%7d", encoderLeft,  encoderRight);
                //telemetry.addData("",  "Running at %7d :%7d",
                //        robot.frontLeftMotor.getCurrentPosition(),
                //        robot.frontRightMotor.getCurrentPosition());
                //telemetry.update();
            }

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            StopMotors();

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunUsingEncoders();

            ShowMessage("--- Encoder Drive: Complete! ---");

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
    }

    public void EncoderDriveAccel(double speed, double leftInches, double rightInches, 
                                  double accelerationInches, double decelerationInches) 
    {
        //----------------------------------------------------------------------
        //--- Reset Encoders
        //----------------------------------------------------------------------
        ResetEncoders();
        while(robot.leftFrontMotor.getCurrentPosition() > 1 && robot.rightFrontMotor.getCurrentPosition()> 1)
        {
            sleep(25); //-- Wait until encoders reset
        }

        //----------------------------------------------------------------------
        // Convert inches to wheel rotations
        //----------------------------------------------------------------------
        double leftTotalRotations = leftInches * robot.COUNTS_PER_INCH;
        double rightTotalRotations = rightInches * robot.COUNTS_PER_INCH;

        double leftAccelerateForRotations = accelerationInches * robot.COUNTS_PER_INCH;
        double rightAccelerateForRotations = accelerationInches * robot.COUNTS_PER_INCH;
        
        double leftDecelerateForRotations = decelerationInches * robot.COUNTS_PER_INCH;
        double rightDecelerateforRotations = decelerationInches * robot.COUNTS_PER_INCH;

        double leftPositionStopAcceleration = Math.abs(leftAccelerateForRotations);
        double rightPositionStopAcceleration = Math.abs(rightAccelerateForRotations);
        
        double leftPositionStartDeceleration = Math.abs(leftTotalRotations) - Math.abs(leftDecelerateForRotations);
        double rightPositionStartDeceleration = Math.abs(rightTotalRotations) - Math.abs(rightDecelerateforRotations);

        //----------------------------------------------------------------------
        //--- Set the Target Position
        //----------------------------------------------------------------------
        SetTargetPosition((int)leftTotalRotations, (int)rightTotalRotations);

        //----------------------------------------------------------------------
        //--- Turn On RUN_TO_POSITION
        //----------------------------------------------------------------------
        RunToPosition();
        
        //----------------------------------------------------------------------
        // Gets the absolute value of the encoder positions at full speed
        //   the current speed, and while it's greater than 0, it will continues increasing the speed.
        // This allows the robot to accelerate over a set number of inches, which reduces wheel 
        //  slippage and increases overall reliability
        //----------------------------------------------------------------------
        double powerLeft = 0;
        double powerRight = 0;
        boolean running = true;
        while (opModeIsActive() && running &&
            (robot.leftFrontMotor.isBusy() && robot.leftRearMotor.isBusy() && robot.rightFrontMotor.isBusy() && robot.rightRearMotor.isBusy()))
        {
            double leftCurrentPostion = Math.abs(robot.leftFrontMotor.getCurrentPosition());
            double rightCurrentPostion = Math.abs(robot.rightFrontMotor.getCurrentPosition());
            
            //if (Math.abs(leftCurrentPostion) <= leftPositionStopAcceleration)
            if (leftCurrentPostion < leftPositionStopAcceleration && rightCurrentPostion < rightPositionStopAcceleration)
            {
                //-- Accelerate
                powerLeft = Range.clip((leftCurrentPostion / leftAccelerateForRotations) + 0.15, 0.15, speed);
                powerRight = Range.clip((rightCurrentPostion / rightAccelerateForRotations) + 0.15, 0.15, speed);
                ShowMessage(String.format("Accel... (LPos = %.0f / %.0f, LSpeed = %.2f)",leftCurrentPostion, leftAccelerateForRotations, powerLeft));
            }
            else if (leftCurrentPostion > leftPositionStartDeceleration)
            {
                //--- Decelerate
                powerLeft = Range.clip((leftAccelerateForRotations / leftCurrentPostion), 0.15, speed);
                powerRight = Range.clip((rightAccelerateForRotations / rightCurrentPostion), 0.15, speed);
                ShowMessage(String.format("Decel... (LPos = %.0f / %.0f, LSpeed = %.2f)",leftCurrentPostion, leftTotalRotations, powerLeft));
            }
            else
            {
                //--- Full Speed!
                powerLeft = speed;
                powerRight = speed;
                ShowMessage(String.format("Full... (LPos = %.0f / %.0f, LSpeed = %.2f)",leftCurrentPostion, leftPositionStartDeceleration, powerLeft));
            }
            
            robot.leftFrontMotor.setPower(powerLeft);
            robot.leftRearMotor.setPower(powerLeft);
            robot.rightFrontMotor.setPower(powerRight);
            robot.rightRearMotor.setPower(powerRight);
        }
        
        //----------------------------------------------------------------------
        //--- Stop all Motion
        //----------------------------------------------------------------------
        StopMotors();
        
        //----------------------------------------------------------------------
        // Turn off RUN_TO_POSITION
        //----------------------------------------------------------------------
        RunUsingEncoders();
        
        ShowMessage("--- Encoder Drive: Complete! ---");
        
        if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
    }

    //endregion

    //region --- Encoder Drive  ---

    void RunToPosition()
    {
        robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void SetTargetStrafePosition(int distance)
    {
        robot.leftFrontMotor.setTargetPosition(-distance);
        robot.leftRearMotor.setTargetPosition(distance);
        robot.rightFrontMotor.setTargetPosition(distance);
        robot.rightRearMotor.setTargetPosition(-distance);
    }

    void SetTargetPosition(int encoderLeft, int encoderRight)
    {
        robot.leftFrontMotor.setTargetPosition(encoderLeft);
        robot.leftRearMotor.setTargetPosition(encoderLeft);
        robot.rightFrontMotor.setTargetPosition(encoderRight);
        robot.rightRearMotor.setTargetPosition(encoderRight);
    }

    void RunUsingEncoders()
    {
        robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void ResetEncoders()
    {
        robot.leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
    }

    //endregion

    //region --- Drive by Gyro ---
    
       /**
    *  Method to drive on a fixed compass bearing (angle), based on encoder counts.
    *  Move will stop if either of these conditions occur:
    *  1) Move gets to the desired position
    *  2) Driver stops the opmode running.
    *
    * @param speed      Target speed for forward motion.  Should allow for _/- variance for adjusting heading
    * @param distance   Distance (in inches) to move from current position.  Negative distance means move backwards.
    * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
    *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
    *                   If a relative angle is required, add/subtract from current heading.
    */
    public void GyroDrive(double speed, double distance, double angle, double timeoutMilliseconds) 
    {
        int newLeftTarget;
        int newRightTarget;
        int moveCounts;
        double max;
        double error;
        double steer;
        double leftSpeed;
        double rightSpeed;

        ResetEncoders();

        if (opModeIsActive())
        {
            //--- Set distance
            moveCounts = (int)(distance * robot.COUNTS_PER_INCH);
            newLeftTarget = robot.leftFrontMotor.getCurrentPosition() + moveCounts;
            newRightTarget = robot.rightFrontMotor.getCurrentPosition() + moveCounts;

            //--- Start driving
            SetTargetPosition(newLeftTarget, newRightTarget);
            RunToPosition();
            DriveForward(speed);

            //--- Loop until motors have finished, or we timeout
            runtime.reset();
            while (opModeIsActive() 
                && (robot.leftFrontMotor.isBusy() && robot.rightFrontMotor.isBusy())
                && (runtime.milliseconds() < timeoutMilliseconds))
            {
                //--- Adjust relative speed based on heading error
                error = GetError(angle);
                steer = GetSteer(error, robot.GYRO_P_DRIVE_COEFF);

                //--- If driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                //--- Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                Drive(leftSpeed, rightSpeed);

                //--- Display drive status
                telemetry.addData("Err/St", "%5.1f/%5.1f", error, steer);
                telemetry.addData("Target", "%7d:%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Actual", "%7d:%7d", robot.leftFrontMotor.getCurrentPosition(), robot.rightFrontMotor.getCurrentPosition());
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            StopMotors();
            RunUsingEncoders();
        }
    }
    
    public void GyroDrive(double speed, double distance, double angle)
    {
        GyroDrive(speed, distance, angle, robot.AUTO_MAXTIMEOUT);
    }

    /**
     *  Method to spin on central axis to point in a new direction.
     *  Move will stop if either of these conditions occur:
     *  1) Move gets to the heading (angle)
     *  2) Driver stops the opmode running.
     *
     * @param speed Desired speed of turn.
     * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     */
    public void GyroTurn(double speed, double angle, double timeoutMilliseconds)
    {
        //--- Keep looping while not on heading, or we timeout
        runtime.reset();
        while (opModeIsActive() 
            && !OnHeading(speed, angle, robot.GYRO_P_TURN_COEFF) 
            && (runtime.milliseconds() < timeoutMilliseconds)) {}
    }
    
    public void GyroTurn(double angle, double timeoutMilliseconds)
    {
        //--- Best way to get accurate gyro turn is to call twice
        GyroTurn(robot.GYRO_TURN_SPEED, angle, timeoutMilliseconds);
        GyroTurn(robot.GYRO_TURN_SPEED, angle, 500);
    }
    
    public void GyroTurn(double angle)
    {
        //--- Best way to get accurate gyro turn is to call twice
        GyroTurn(robot.GYRO_TURN_SPEED, angle, 5000);
        GyroTurn(robot.GYRO_TURN_SPEED, angle, 500);
    }

    /**
     *  Method to obtain & hold a heading for a finite amount of time
     *  Move will stop once the requested time has elapsed
     *
     * @param speed      Desired speed of turn.
     * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     * @param holdTime   Length of time (in seconds) to hold the specified heading.
     */
    /*
    public void gyroHold( double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading(speed, angle, P_TURN_COEFF);
            telemetry.update();
        }

        // Stop all motion;
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
    }
    */

    /**
     * Perform one cycle of closed loop heading control.
     *
     * @param speed     Desired speed of turn.
     * @param angle     Absolute Angle (in Degrees) relative to last gyro reset.
     *                  0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                  If a relative angle is required, add/subtract from current heading.
     * @param PCoeff    Proportional Gain coefficient
     * @return
     */
    boolean OnHeading(double speed, double angle, double PCoeff) 
    {
        double error;
        double steer;
        boolean onTarget = false;
        double leftSpeed;
        double rightSpeed;

        //--- Determine turn power based on +/- error
        error = GetError(angle);

        if (Math.abs(error) <= robot.GYRO_HEADING_THRESHOLD) 
        {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else 
        {
            steer = GetSteer(error, PCoeff);
            rightSpeed = speed * steer;
            leftSpeed = -rightSpeed;
        }

        // Send desired speeds to motors.
        Drive(leftSpeed, rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double GetError(double targetAngle) 
    {
        GetAngle();
     
        double robotError;

        // calculate error in -179 to +180 range
        robotError = targetAngle - robot.angleCurrent.firstAngle;
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    public double GetSteer(double error, double PCoeff) 
    {
        return Range.clip(error * PCoeff, -1, 1);
    }

    //----------------------------------------------------------------------
    //endregion

    //======================================================================
    //=== Shared Code
    //======================================================================

    //region --- Arcade Drive ---
    //----------------------------------------------------------------------
    
    void ArcadeDrive()
    {
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(-1 * gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x; //--- Rotation
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        robot.leftFrontMotor.setPower(v1 * robot.MECANUM_SPEED * 1.5);
        robot.rightFrontMotor.setPower(v2 * robot.MECANUM_SPEED * 1.5);
        robot.leftRearMotor.setPower(v3 * robot.MECANUM_SPEED * 1.5);
        robot.rightRearMotor.setPower(v4 * robot.MECANUM_SPEED * 1.5);
    }

    //----------------------------------------------------------------------
    //endregion    
    
    //region --- Tank Drive ---
    //----------------------------------------------------------------------

    void DriveForward(double power)
    {
        robot.leftFrontMotor.setPower(power);
        robot.leftRearMotor.setPower(power);
        robot.rightFrontMotor.setPower(power);
        robot.rightRearMotor.setPower(power);
    }
    
    void Drive(double leftPower, double rightPower)
    {
        robot.leftFrontMotor.setPower(leftPower);
        robot.leftRearMotor.setPower(leftPower);
        robot.rightFrontMotor.setPower(rightPower);
        robot.rightRearMotor.setPower(rightPower);
    }

    void DriveBackwards(double power)
    {
        robot.leftFrontMotor.setPower(-power);
        robot.leftRearMotor.setPower(-power);
        robot.rightFrontMotor.setPower(-power);
        robot.rightRearMotor.setPower(-power);
    }
    
    void StrafeLeft(double power)
    {
        robot.leftFrontMotor.setPower(-power);
        robot.leftRearMotor.setPower(power);
        robot.rightFrontMotor.setPower(power);
        robot.rightRearMotor.setPower(-power);
    }

    void StrafeRight(double power)
    {
        robot.leftFrontMotor.setPower(power);
        robot.leftRearMotor.setPower(-power);
        robot.rightFrontMotor.setPower(-power);
        robot.rightRearMotor.setPower(power);
    }

    void Strafe45(double power)
    {
        robot.leftFrontMotor.setPower(power);
        robot.leftRearMotor.setPower(0);
        robot.rightFrontMotor.setPower(0);
        robot.rightRearMotor.setPower(power);
    }
    
    void Strafe135(double power)
    {
        robot.leftFrontMotor.setPower(0);
        robot.leftRearMotor.setPower(power);
        robot.rightFrontMotor.setPower(power);
        robot.rightRearMotor.setPower(0);
    }

    void StopMotors()
    {
        robot.leftFrontMotor.setPower(0);
        robot.leftRearMotor.setPower(0);
        robot.rightFrontMotor.setPower(0);
        robot.rightRearMotor.setPower(0);
    }
    
    void SetMotorsToBrake()
    {
        robot.leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);        
    }
    
    void SetMotorsToFloat()
    {
        robot.leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    //----------------------------------------------------------------------
    //endregion

    //region --- Gyro Driving ---
    //----------------------------------------------------------------------

    void InitGyro()
    {
        ShowMessage("Calibrating Gyro....");
        
        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }
        
        ShowMessage("Gyro Calibrated - " + robot.imu.getCalibrationStatus().toString());
    }
    
    void GyroUpdates()
    {
        // Set for when the gyro is used to drive in a straight line
        robot.angleCorrection = CheckDirection();
    }

    //----------------------------------------------------------------------
    // Resets the cumulative angle tracking to zero
    //----------------------------------------------------------------------
    public void ResetAngle()
    {
        robot.angleCurrent = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        robot.angleGlobal = 0;
    }
    
    //----------------------------------------------------------------------
    // Get current cumulative angle rotation from last reset
    // @return Angle in degrees. + = left, - = right.
    //----------------------------------------------------------------------
    public double GetAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - robot.angleCurrent.firstAngle;

        if (deltaAngle < -180) 
        {
            deltaAngle += 360;
        }
        else if (deltaAngle > 180) 
        {
            deltaAngle -= 360;
        }

        robot.angleGlobal += deltaAngle;
        robot.angleCurrent = angles;
        return robot.angleGlobal;
    }
    
    //----------------------------------------------------------------------
    // See if we are moving in a straight line and if not, return a power correction value
    // @return Power adjustment, + is adjust left - is adjust right.
    //----------------------------------------------------------------------
    public double CheckDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;
        
        angle = GetAngle();
        
        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.
        
        correction = correction * gain;
        
        return correction;
    }

    //----------------------------------------------------------------------
    // Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
    // @param degrees Degrees to turn, + is left - is right
    //----------------------------------------------------------------------
    public void Rotate(int degrees, double power)
    {
        double leftPower, rightPower;

        ResetAngle();   //-- Restart imu movement tracking

        //-- GetAngle() returns + when rotating counter clockwise (left) and - when rotating clockwise (right).

        if (degrees < 0)
        {   
            //-- Turn Right
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   
            //-- Turn Left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        SetMotorsToBrake();
        Drive(leftPower, rightPower);   //-- Rotate Robot

        //-- Rotate until turn is complete
        if (degrees < 0)
        {
            //-- On Right turn we have to get off zero first
            while (opModeIsActive() && GetAngle() == 0) {}
            while (opModeIsActive() && GetAngle() > degrees) {}
        }
        else
        {
            //-- Left turn
            while (opModeIsActive() && GetAngle() < degrees) {}
        }

        StopMotors();
        SmartSleep(1000);   //-- Wait for rotation to Stop
        ResetAngle();       //-- Reset angle tracking on new heading
        SetMotorsToFloat();
    }

    public void RotateAbsolute(int degrees, double power)
    {
        double  leftPower, rightPower;

        //ResetAngle();   //-- Restart imu movement tracking

        //-- GetAngle() returns + when rotating counter clockwise (left) and - when rotating clockwise (right).

        if (degrees < 0)
        {   
            //-- Turn Right
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   
            //-- Turn Left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        SetMotorsToBrake();
        Drive(leftPower, rightPower);   //-- Rotate Robot

        //-- Rotate until turn is complete
        if (degrees < 0)
        {
            //-- On Right turn we have to get off zero first
            while (opModeIsActive() && GetAngle() == 0) {}
            while (opModeIsActive() && GetAngle() > degrees) {}
        }
        else
        {
            //-- Left turn
            while (opModeIsActive() && GetAngle() < degrees) {}
        }

        StopMotors();
        SmartSleep(1000);   //-- Wait for rotation to Stop
        ResetAngle();       //-- Reset angle tracking on new heading
        SetMotorsToFloat();
    }

    //----------------------------------------------------------------------
    //endregion

    //region --- Sounds ---
    //----------------------------------------------------------------------

    //--- Point to sound files on the phone's drive
    private String soundPath = "/sdcard/FIRST/blocks/sounds";

    private File soundAFile = new File(soundPath + "/3-AngryLoud.WAV");
    private File soundBFile = new File(soundPath + "/2-Alert.WAV");
    private File soundXFile = new File(soundPath + "/5-SongShort2.mp3");
    private File soundYFile = new File(soundPath + "/1-BTBTLoud.WAV");

    //--- Flag that allows us to only play one sound at a time
    boolean soundPlaying = false;

    void PlaySounds()
    {
        //--- gamepad2
        //--- right_stick

        if (gamepad2.right_stick_y > 0.1 && !soundPlaying)
        {
            PlaySound(soundAFile); // ** Angry Sound
        }
        else if (gamepad2.right_stick_y < -0.1 && !soundPlaying)
        {
            PlaySound(soundBFile); // ** Alert Sound
        }
        else if (gamepad2.right_stick_x > 0.1 && !soundPlaying)
        {
            PlaySound(soundXFile); // ** Song Sound
        }
        else if (gamepad2.right_stick_x < -0.1 && !soundPlaying)
        {
            PlaySound(soundYFile); // ** BT-BT Sound
        }
    }
    
    void PlaySound(File soundFile)
    {
        //--- Configure our Sound Player
        SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
        params.loopControl = 0;
        params.waitForNonLoopingSoundsToFinish = true;
        
        // Start playing, when done update soundPlaying variable
        soundPlaying = true;
        ShowMessage("Playing Sound...");
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, soundFile, params, null,
            new Runnable() { public void run() 
            { 
                soundPlaying = false; 
                ShowMessage("Ready to Play!");
            }});
    }
    
    void PlayVictorySound()
    {
        if (!soundPlaying)
        {
            PlaySound(soundXFile); // ** Song Sound
        }
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);        
    }

    //----------------------------------------------------------------------
    //endregion
    
    //======================================================================
    //=== Utility Code
    //======================================================================
 
    //region --- Utility Code ---
    //----------------------------------------------------------------------
    
    void SmartSleep(double millisecondsToSleep)
    {
        runtime.reset(); //--- Reset the timer to 0
        while(opModeIsActive() && (runtime.milliseconds() < millisecondsToSleep))
        {
        }
    }
   
    void WaitCountdown(double millisecondsLeft)
    {
        robot.TIMER_REMAINING_AUTO = 30000; 
        while(opModeIsActive() && (robot.TIMER_REMAINING_AUTO > millisecondsLeft))
        {
            robot.TIMER_REMAINING_AUTO = 30000 - (System.currentTimeMillis() - robot.TIMER_START_AUTO); 
            ShowMessage("" + robot.TIMER_REMAINING_AUTO);
        }
    }
   
    void UpdateDisplay()
    {
        telemetry.addData("Speed",  robot.MECANUM_SPEED_DISPLAY);
        /*if (robot.blinkEyeServo != null)
        {
            telemetry.addData("Eye",  "Blink = %3f, Horz = %3f, Vert = %3f", 
                robot.blinkEyeServo.getPosition(),
                robot.horizontalEyeServo.getPosition(),
                robot.verticalEyeServo.getPosition());
        }*/
        /*
        if (robot.reachArmShoulderServo != null)
        {
            telemetry.addData("Reach Arm",  "Shoulder = %.3f, Elbow = %.3f", 
                robot.reachArmShoulderServo.getPosition(),
                robot.reachArmElbowServo.getPosition());
        }
        
        if (robot.armMotor != null)
        {
            telemetry.addData("Reach Arm",  "Motor = %3d, Servo = %.3f", 
                robot.armMotor.getCurrentPosition(),
                robot.armServo.getPosition());
        }
        if (robot.capElbowServo != null)
        {
            telemetry.addData("Cap Arm",  "Elbow = %.3f, Wrist = %.3f", 
                robot.capElbowServo.getPosition(),
                robot.capWristServo.getPosition());
        }
        if (robot.armMotor != null)
        {
            telemetry.addData("Arm",  "Position = %3d / Target = %3d", robot.armMotor.getCurrentPosition(), robot.LIFTER_TARGET);
        }
        */
//        if (robot.lifterMotor != null)
//        {
//            telemetry.addData("Lifter",  "Position = %3d / Target = %3d", robot.lifterMotor.getCurrentPosition(), robot.LIFTER_TARGET);
//        }
        if (robot.imu != null)
        {
            telemetry.addData("Gyro",  "Angle = %.3f, Global = %.2f, Correct = %.2f", 
                robot.angleCurrent.firstAngle, robot.angleGlobal, robot.angleCorrection);
        }
//        if (robot.leftColorSensor != null)
//        {
//            telemetry.addData("Bricks",
//            "[L] " + robot.leftBrickColor + "(" + String.format(Locale.US, "%.02f", robot.leftDistanceSensor.getDistance(DistanceUnit.CM)) + " cm / " + robot.leftHue[0] + ") " +
//            "[R] " + robot.rightBrickColor + "(" + String.format(Locale.US, "%.02f", robot.rightDistanceSensor.getDistance(DistanceUnit.CM)) + " cm / " + robot.rightHue[0] + ")");
//        }

        telemetry.addData("Message", robot.DISPLAY_MESSAGE);
        telemetry.update();
    }
   
    void ShowMessage(String message)
    {
        robot.DISPLAY_MESSAGE = message;
        UpdateDisplay();
    }
    
    //----------------------------------------------------------------------
    //endregion
}