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
    
    void GripArmRightGrabReady()
    {
        robot.rightSkystoneServo.setPosition(0.12); //--- Position ready to grab
        robot.gripperServo.setPosition(0);          //--- Open gripper 
    }

    void GripArmRightGrab()
    {
        robot.rightSkystoneServo.setPosition(0);    //--- Lower to stone
        robot.gripperServo.setPosition(0.355);      //--- Close on stone
        sleep(400);                                 //--- ... allow time to close before pickup
        robot.rightSkystoneServo.setPosition(0.60); //--- Bring stone into robot
        sleep(250);                                 //--- ... allow time to pickup over stones
    }
    
    void GripArmRightDropReady()
    {
        robot.rightSkystoneServo.setPosition(0.15); //--- Position ready to drop
    }
    
    void GripArmRightDrop(int strafeDist)
    {
        StrafeRight(1,strafeDist,99);               //--- Strafe close to foundation
        robot.rightSkystoneServo.setPosition(0.08);
        robot.gripperServo.setPosition(0);
        sleep(500);
        robot.rightSkystoneServo.setPosition(0.60); //--- Bring stone into robot
        robot.gripperServo.setPosition(0.355);      //--- Open
        StrafeLeft(1,strafeDist,99);                //--- Strafe away foundation
    }
    
    void OpenWhiskers()
    {
        ShowMessage("Open Whiskers");
        robot.leftWhiskerServo.setPosition(0.35);
        robot.rightWhiskerServo.setPosition(0.65);        
    }
    
    void CloseWhiskers()
    {
        ShowMessage("Close Whiskers");        
        robot.leftWhiskerServo.setPosition(0.69);
        robot.rightWhiskerServo.setPosition(0.3);
    }
    
    void SpacerUp()
    {
        ShowMessage("Spacer Up");
        robot.leftSpacerServo.setPosition(1);       //-- Up
        robot.rightSpacerServo.setPosition(0.96);   //-- Up
    }
    
    void SpacerDown()
    {
        ShowMessage("Spacer Down");
        robot.leftSpacerServo.setPosition(0.51);    //-- Down
        robot.rightSpacerServo.setPosition(0.47);   //-- Down
    }
    
    void BrickIntakeInStart()
    {
        ShowMessage("Brick Intake");
        robot.intakeFrontServo.setPower(-1);
        robot.intakeBackServo.setPower(-1);
        //robot.intakeServo.setPower(-0.8);
    }
    
    void BrickIntakeOutStart()
    {
        ShowMessage("Brick Drop");
        robot.intakeFrontServo.setPower(1);
        robot.intakeBackServo.setPower(1);
        //robot.intakeServo.setPower(0.8);
    }
    
    void BrickIntakeStop()
    {
        robot.intakeFrontServo.setPower(0);
        robot.intakeBackServo.setPower(0);
    }
    
    void LSidearmDown(int sleepFor)
    {
        robot.leftSkystoneServo.setPosition(0.0565);
        sleep(sleepFor);
    }
    
    void LSidearmUp(int sleepFor)
    {
        robot.leftSkystoneServo.setPosition(0.64);
        sleep(sleepFor);
    }
    
    void LSidearmRest(int sleepFor)
    {
        robot.leftSkystoneServo.setPosition(0.86);
    }
    
    void RSidearmDown(int sleepFor)
    {
        robot.rightSkystoneServo.setPosition(0.03);
        sleep(sleepFor);
    }
    
    void RSidearmUp(int sleepFor)
    {
        robot.rightSkystoneServo.setPosition(0.64);
        sleep(sleepFor);
    }
    
    void RSidearmRest(int sleepFor)
    {
       robot.rightSkystoneServo.setPosition(0.8335);
       sleep(sleepFor);
    }
    
    void LSidearmCollapse(int sleepFor)
    {
        robot.leftSkystoneServo.setPosition(0.75);
        sleep(sleepFor);
    }
    
    void BlueInt()
    {
        robot.rightSkystoneServo.setPosition(0.40);
        sleep(750);
        robot.leftSkystoneServo.setPosition(0.5);
        sleep(750);
        LSidearmRest(1000);
        sleep(750);
        RSidearmUp(750);
    }
    
    void RedInt()
    {
        robot.leftSkystoneServo.setPosition(0.40);
        sleep(750);
        robot.rightSkystoneServo.setPosition(0.5);
        sleep(750);
        RSidearmRest(750);
        sleep(750);
        LSidearmUp(750);
    }
    
    void InitSkystoneArms()
    {
        robot.rightSkystoneServo.setPosition(0.40);
        sleep(750);
        robot.leftSkystoneServo.setPosition(0.5);
        sleep(750);
        RSidearmRest(750);
        sleep(750);
        LSidearmCollapse(750);
    }

    //----------------------------------------------------------------------
    //endregion

    //======================================================================
    //=== Controller Assignments
    //======================================================================

    //region --- Assign Controller Input ---
    //----------------------------------------------------------------------

    void TouchingFoundationSensors() 
    {
        if (robot.leftTouch.getState() != true & robot.rightTouch.getState() != true)
        {
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.FIRE_MEDIUM);
        }
    }

    void CapArm()
    {
        //--- gamepad2
        //--- x, b
        
        double capElbow;
        double capWrist;            
        if(gamepad2.x && !robot.G2X_CHANGED) 
        {
            robot.capElbowServo.setPosition(robot.G2X_ON ? 0.55 : 0.95);
            robot.G2X_ON = !robot.G2X_ON;
            robot.G2X_CHANGED = true;
        } 
        else if(!gamepad2.x) robot.G2X_CHANGED = false;
            
        if(gamepad2.b && !robot.G2B_CHANGED) 
        {
            if (robot.capElbowServo.getPosition() > 0.8)
            {
                //-- Set to parallel
                robot.capElbowServo.setPosition(0.890);
                SmartSleep(200);
                //-- Only move when down, so we don't knock off capstone
                robot.capWristServo.setPosition(robot.G2B_ON ? 0.7 : 1);
            }
            robot.G2B_ON = !robot.G2B_ON;
            robot.G2B_CHANGED = true;
        } 
        else if(!gamepad2.b) robot.G2B_CHANGED = false;
    }

    void WhiskerClamps()
    {
        //--- gamepad1
        //--- left_bumper, left_trigger
        
        if (gamepad1.left_bumper)
        {
            OpenWhiskers();
        } 
        else if (gamepad1.left_trigger > 0.5)
        {
            //SmartSleep(500); //-- Need to delay slightly to allow brick spacers to close ahead of whiskers
            CloseWhiskers();
        }
    }

    void BrickSpacer()
    {
        //--- gamepad1
        //--- right_bumper, right_trigger
        
        if (gamepad1.right_bumper)
        {
            SpacerUp();
        } 
        else if (gamepad1.right_trigger > 0.5)
        {
            SpacerDown();
        }
    }

    /*void MoveReachArm()
    {
        //--- gamepad2
        //--- right_trigger, right_bumper
        
        //-- Horizontal (Left/Right)
        double servoPostion = 0;
        if (gamepad2.left_bumper)
        { 
            robot.armMotor.setPower(0.1);
            
            servoPostion = robot.armServo.getPosition() + 0.06;
            if (servoPostion > 0.65) servoPostion = 0.65;
            robot.armServo.setPosition(servoPostion);
            
            if (!soundPlaying)
            {
                PlaySound(soundXFile); // ** Song Sound
            }
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
        } 
        else if (gamepad2.left_trigger > 0.5)
        {
            robot.armMotor.setPower(-0.1);
            
            servoPostion = robot.armServo.getPosition() - 0.03;
            robot.armServo.setPosition(servoPostion);
        }
        else
        {
            robot.armMotor.setPower(0);
        }
        
        /*
        if (gamepad2.left_trigger > 0.5)
        {
            robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.armMotor.setTargetPosition(10);
            robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.armMotor.setPower(0.2);
            while (opModeIsActive() && robot.armMotor.isBusy()) 
            { 
                ShowMessage("Arm " + robot.armMotor.getCurrentPosition());    
            }
            robot.armMotor.setPower(0);
        }
        else if (gamepad2.left_bumper)
        { 
            robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.armMotor.setTargetPosition(0);
            robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.armMotor.setPower(0.2);
            while (opModeIsActive() && robot.armMotor.isBusy()) { }
            robot.armMotor.setPower(0);
        }
        
    }
    */
    
    void MoveTape()
    {
        if (gamepad2.right_bumper)
        {
            robot.tapeForwardMotor.setPower(1);
            ShowMessage("Tape Out");
            
            if (!soundPlaying)
            {
                PlaySound(soundXFile); // ** Song Sound
            }
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
        }
        else if (gamepad2.right_trigger > 0.5) 
        {
            robot.tapeForwardMotor.setPower(-1);
            ShowMessage("Tape In");
        }
        else if (!gamepad2.right_bumper && gamepad2.right_trigger < 0.5)
        {
            robot.tapeForwardMotor.setPower(0);
        }
        
        if (gamepad2.left_bumper)
        {
            robot.tapeBackMotor.setPower(1);
            ShowMessage("Tape Out");
            
            if (!soundPlaying)
            {
                PlaySound(soundXFile); // ** Song Sound
            }
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
        }
        else if (gamepad2.left_trigger > 0.5) 
        {
            robot.tapeBackMotor.setPower(-1);
            ShowMessage("Tape In");
        }
        else if (!gamepad2.left_bumper && gamepad2.left_trigger < 0.5)
        {
            robot.tapeBackMotor.setPower(0);
        }
    }
    
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
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            SmartSleep(100);
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_LARSON_SCANNER);
        }
        else if (gamepad1.b) 
        {
            //--- Mecanum High Power!!!
            ShowMessage("High Power");
            robot.MECANUM_SPEED = PhoenixBotHardware.MECANUM_SPEED_HIGH;
            robot.MECANUM_SPEED_STRAFE = PhoenixBotHardware.MECANUM_SPEED_STRAFE_HIGH;
            robot.MECANUM_SPEED_DISPLAY = PhoenixBotHardware.MECANUM_SPEED_HIGH_DISPLAY;
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
            SmartSleep(100);
            //robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE);
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);
        }
        else if (gamepad1.a) 
        {
            //--- Mecanum Very Slow Power!!!
            ShowMessage("Very Slow Power");
            robot.MECANUM_SPEED = PhoenixBotHardware.MECANUM_SPEED_SLOW;
            robot.MECANUM_SPEED_STRAFE = PhoenixBotHardware.MECANUM_SPEED_STRAFE_SLOW;
            robot.MECANUM_SPEED_DISPLAY = PhoenixBotHardware.MECANUM_SPEED_SLOW_DISPLAY;
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
            SmartSleep(100);
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_FOREST_PALETTE);
            //robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        }
    }

    void Lifter()
    {
        //--- gamepad2
        //--- dpad_up, dpad_down, y, a, left_bumper, left_trigger, left_stick_button
        if (gamepad2.dpad_up)
        {
            robot.LIFTER_TARGET += 100;
            if (robot.LIFTER_TARGET > robot.LIFTERARM_TOP_COUNT) robot.LIFTER_TARGET = robot.LIFTERARM_TOP_COUNT;
            robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
            ShowMessage("Lifter Up");
        }
        else if (gamepad2.dpad_down)
        { 
            robot.LIFTER_TARGET -= 15;
            robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
            if (robot.LIFTER_TARGET < 0) robot.LIFTER_TARGET = 0;
            ShowMessage("Lifter Down");
        }
        else if (gamepad2.left_stick_y > 0.2)
        {
            robot.LIFTER_TARGET -= 100;
            robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
            if (robot.LIFTER_TARGET < 0) robot.LIFTER_TARGET = 0;
            ShowMessage("Lifter Down");
        }
        else if (gamepad2.left_stick_y < -0.2)
        {
            robot.LIFTER_TARGET += 100;
            if (robot.LIFTER_TARGET > robot.LIFTERARM_TOP_COUNT) robot.LIFTER_TARGET = robot.LIFTERARM_TOP_COUNT;
            robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
            ShowMessage("Lifter Up");
        }
        else if (gamepad2.a)
        {
            //--- Drop Brick
            BrickIntakeOutStart();
            robot.LIFTER_TARGET += 15;
            if (robot.LIFTER_TARGET > robot.LIFTERARM_TOP_COUNT) robot.LIFTER_TARGET = robot.LIFTERARM_TOP_COUNT;
            robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
        }
        else if (gamepad2.y)
        {
            //--- Pick Up Brick
            BrickIntakeInStart();
            robot.LIFTER_TARGET -= 30;
            if (robot.LIFTER_TARGET < 0) robot.LIFTER_TARGET = 0;
            robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
        }
        else
        {
            //--- Move Up a Level
            /*if(gamepad2.left_bumper && !robot.CHANGED_GP2_LEFT_BUMPER) 
            {
                robot.LIFTER_TARGET += 500;
                if (robot.LIFTER_TARGET > robot.LIFTERARM_TOP_COUNT) robot.LIFTER_TARGET = robot.LIFTERARM_TOP_COUNT;
                if (robot.LIFTER_TARGET == 2500) robot.LIFTER_TARGET = 2600;
                robot.ON_GP2_LEFT_BUMPER = !robot.ON_GP2_LEFT_BUMPER;
                robot.CHANGED_GP2_LEFT_BUMPER = true;
                ShowMessage("Lifter Up Level");
            } 
            else if (!gamepad2.left_bumper) 
            {
                robot.CHANGED_GP2_LEFT_BUMPER = false;
            }
            
            //--- Move Down a Level
            if(gamepad2.left_trigger > 0.5 && !robot.CHANGED_GP2_LEFT_TRIGGER) 
            {
                robot.LIFTER_TARGET -= 500;
                if (robot.LIFTER_TARGET < 0) robot.LIFTER_TARGET = 0;
                robot.ON_GP2_LEFT_TRIGGER = !robot.ON_GP2_LEFT_TRIGGER;
                robot.CHANGED_GP2_LEFT_TRIGGER = true;
                ShowMessage("Lifter Down Level");
            } 
            else if (!(gamepad2.left_trigger > 0.5)) 
            {
                robot.CHANGED_GP2_LEFT_TRIGGER = false;
            }
            */
            if (gamepad2.left_stick_button)
            {
                //--- In the off chance we start the robot when the lifter is
                //---   not fully down, we need a way to lower and reset the encoder.
                //---   This seems complex, but needed sleeps to reset encoder
                //---   as it takes time after calling STOP_AND_RESET_ENCODER
                robot.lifterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.lifterMotor.setPower(-0.3);
                ShowMessage("Reset Lifter Down 1");
                SmartSleep(1000);
                ShowMessage("Reset Lifter Down 2");
                SmartSleep(1000);
                robot.lifterMotor.setPower(0.1);
                SmartSleep(100);
                robot.lifterMotor.setPower(0);
                robot.lifterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                SmartSleep(1000);
                robot.lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ShowMessage("Reset Lifter");
            }
            else
            {
                //--- Move to the Level
                if (robot.LIFTER_TARGET != robot.lifterMotor.getCurrentPosition())
                {
                    robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
                    robot.lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.lifterMotor.setPower(1);
                }
            }

            //--- Stop Intake when not Y or A
            if (!gamepad2.y && !gamepad2.a)
            {
                BrickIntakeStop();
            }
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

    void DropStoneOnFoundation()
    {
        BrickIntakeOutStart();                  //--- Intake Servos On (Drop)
        LifterToPosition(150);                  //--- Raise Lifter, releasing stone without dislodging
        SmartSleep(250);
        LifterToPosition(200);
        SmartSleep(250);
        LifterToPosition(250);
        SmartSleep(250);
        LifterToPosition(300);
        SmartSleep(250);
        LifterToPosition(600);                  //--- Clear top of stone so we don't knock it off
        SmartSleep(250);
        BrickIntakeStop();                      //--- Intake Servos Stop
    }

    void LifterToPosition(int position)
    {
        robot.LIFTER_TARGET = position;
        
        if (robot.LIFTER_TARGET > robot.LIFTERARM_TOP_COUNT) robot.LIFTER_TARGET = robot.LIFTERARM_TOP_COUNT;
        if (robot.LIFTER_TARGET < 0) robot.LIFTER_TARGET = 0;

        robot.lifterMotor.setTargetPosition(robot.LIFTER_TARGET);
        robot.lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.lifterMotor.setPower(1);

        ShowMessage("Lifter to Postion");
    }

    void LifterUp(double speed, int milliseconds)
    {
        LifterMove(speed, milliseconds);
    }
    
    void LifterDown(double speed, int milliseconds)
    {
        LifterMove(-1 * speed, milliseconds);
    }
    
    void LifterMove(double speed, int milliseconds)
    {
        robot.lifterMotor.setPower(speed); //--- Positive Up, Negative Down
        SmartSleep(milliseconds);
        robot.lifterMotor.setPower(0);
    }

    void LifterDownWithIntake(double speed, int milliseconds)
    {
        BrickIntakeInStart();
        LifterDown(speed, milliseconds);
        BrickIntakeStop();
    }
    
    void LifterUpDropBrick(double speed, int milliseconds)
    {
        BrickIntakeOutStart();
        LifterUp(speed, milliseconds);
        BrickIntakeStop();
    }

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
    
   /* void ReachArmExtendForward()
    {
        robot.armServo.setPosition(0.3);
        robot.armMotor.setPower(0.1);
        SmartSleep(500);
        robot.armServo.setPosition(0.65);
        SmartSleep(1000);
        robot.armMotor.setPower(0);
    }
    
    void ReachArmExtendBack()
    {
        robot.armServo.setPosition(0.65);
    }
    */
    Boolean CaptureFoundation(double timeoutMilliseconds)
    {
        runtime.reset();
        boolean notPressed = true; 
        while (notPressed && opModeIsActive() && (runtime.milliseconds() < timeoutMilliseconds))
        {
            DriveForward(0.3);
            if (robot.leftTouch.getState() != true & robot.rightTouch.getState() != true)
            {
                notPressed = false;
            }
            
            ShowMessage("" + runtime.milliseconds());
        }
        
        EncoderDrive(0.2,1,1,99);
        StopMotors();
        SpacerDown();
        SmartSleep(500);
        CloseWhiskers();
        SmartSleep (500);
        
        return notPressed;
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

    public void EncoderDriveAccelToDistance
        (double speed, double leftInches, double rightInches, 
         double accelerationInches, double decelerationInches, double stopAtDistanceInches) 
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
            //--- Get the distance to the closest object
            double centerDistanceIN = robot.centerDistanceSensor.getDistance(DistanceUnit.INCH);

            if (centerDistanceIN <= stopAtDistanceInches)
            {
                running = false;
            }

            double leftCurrentPostion = Math.abs(robot.leftFrontMotor.getCurrentPosition());
            double rightCurrentPostion = Math.abs(robot.rightFrontMotor.getCurrentPosition());
            
            if (leftCurrentPostion < leftPositionStopAcceleration && rightCurrentPostion < rightPositionStopAcceleration)
            {
                //-- Accelerate
                powerLeft = Range.clip((leftCurrentPostion / leftAccelerateForRotations) + 0.15, 0.15, speed);
                powerRight = Range.clip((rightCurrentPostion / rightAccelerateForRotations) + 0.15, 0.15, speed);
                ShowMessage(String.format("Accel... (LPos = %.0f / %.0f, LSpeed = %.2f) [%.2f in]",
                    leftCurrentPostion, leftAccelerateForRotations, powerLeft, centerDistanceIN));
            }
            else if (leftCurrentPostion > leftPositionStartDeceleration)
            {
                //--- Decelerate
                powerLeft = Range.clip((leftAccelerateForRotations / leftCurrentPostion), 0.15, speed);
                powerRight = Range.clip((rightAccelerateForRotations / rightCurrentPostion), 0.15, speed);
                ShowMessage(String.format("Decel... (LPos = %.0f / %.0f, LSpeed = %.2f) [%.2f in]",
                    leftCurrentPostion, leftTotalRotations, powerLeft, centerDistanceIN));
            }
            else
            {
                //--- Full Speed!
                powerLeft = speed;
                powerRight = speed;
                ShowMessage(String.format("Full... (LPos = %.0f / %.0f, LSpeed = %.2f) [%.2f in]",
                    leftCurrentPostion, leftPositionStartDeceleration, powerLeft, centerDistanceIN));
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
    
    public void EncoderDriveAccelToDistanceShort
        (double speed, double leftInches, double rightInches, 
        double accelerationInches, double decelerationInches, double stopAtDistanceCM) 
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
            //--- Get the distance to the closest object
            double leftDistanceCM = robot.leftDistanceSensor.getDistance(DistanceUnit.CM);

            if (leftDistanceCM <= stopAtDistanceCM)
            {
                running = false;
            }

            double leftCurrentPostion = Math.abs(robot.leftFrontMotor.getCurrentPosition());
            double rightCurrentPostion = Math.abs(robot.rightFrontMotor.getCurrentPosition());
            
            if (leftCurrentPostion < leftPositionStopAcceleration && rightCurrentPostion < rightPositionStopAcceleration)
            {
                //-- Accelerate
                powerLeft = Range.clip((leftCurrentPostion / leftAccelerateForRotations) + 0.15, 0.15, speed);
                powerRight = Range.clip((rightCurrentPostion / rightAccelerateForRotations) + 0.15, 0.15, speed);
                ShowMessage(String.format("Accel... (LPos = %.0f / %.0f, LSpeed = %.2f) [%.2f cm]",
                    leftCurrentPostion, leftAccelerateForRotations, powerLeft, leftDistanceCM));
            }
            else if (leftCurrentPostion > leftPositionStartDeceleration)
            {
                //--- Decelerate
                powerLeft = Range.clip((leftAccelerateForRotations / leftCurrentPostion), 0.15, speed);
                powerRight = Range.clip((rightAccelerateForRotations / rightCurrentPostion), 0.15, speed);
                ShowMessage(String.format("Decel... (LPos = %.0f / %.0f, LSpeed = %.2f) [%.2f cm]",
                    leftCurrentPostion, leftTotalRotations, powerLeft, leftDistanceCM));
            }
            else
            {
                //--- Full Speed!
                powerLeft = speed;
                powerRight = speed;
                ShowMessage(String.format("Full... (LPos = %.0f / %.0f, LSpeed = %.2f) [%.2f cm]",
                    leftCurrentPostion, leftPositionStartDeceleration, powerLeft, leftDistanceCM));
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
    
    public void GyroDriveStoneSpecial(double speed, double distance, double angle, double readyArmDistance, boolean isRightArm, boolean isGrabStone) 
    {
        double timeoutMilliseconds = robot.AUTO_MAXTIMEOUT;

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
            double readyArmTarget = (int)(readyArmDistance * robot.COUNTS_PER_INCH);

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

                if (Math.abs(newLeftTarget - robot.leftFrontMotor.getCurrentPosition()) < readyArmTarget)
                {
                    if (isRightArm)
                    {
                        if (isGrabStone)
                        {
                            GripArmRightGrabReady();
                        }
                        else
                        {
                            GripArmRightDropReady();
                        }
                    }
                    else
                    {
                        
                    }
                }

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

    //region --- Drive by Distance ---
    
    public void StrafeRightToShortDistance(double speed, double timeoutMilliseconds) 
    {
        //----------------------------------------------------------------------
        // R3 distance sensors only read 3cm or lower
        //----------------------------------------------------------------------
        StrafeRight(speed);
        
        boolean running = true;
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < timeoutMilliseconds) && running)
        {
            //--- Move until under 3cm
            if (robot.rightBackDistanceSensor.getDistance(DistanceUnit.CM) <= 3.0)
            {
                running = false;
            }
        }
        
        StopMotors();
    }
    
    public void StrafeLeftToShortDistance(double speed, double timeoutMilliseconds) 
    {
        //----------------------------------------------------------------------
        // R3 distance sensors only read 3cm or lower
        //----------------------------------------------------------------------
        StrafeLeft(speed);
        
        boolean running = true;
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < timeoutMilliseconds) && running)
        {
            //--- Move until under 3cm
            if (robot.leftBackDistanceSensor.getDistance(DistanceUnit.CM) <= 3.0)
            {
                running = false;
            }
        }
        
        StopMotors();
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

    //region --- Mecanum Drive ---
    //----------------------------------------------------------------------

    void MecanumDrive()
    {
        //----------------------------------------------------------------------
        //--- Mecanum Tank drive
        //----------------------------------------------------------------------
        if (gamepad1.left_stick_y != 0 | gamepad1.right_stick_y != 0)
        {
            robot.leftFrontMotor.setPower(-1 * gamepad1.left_stick_y * robot.MECANUM_SPEED);
            robot.leftRearMotor.setPower(-1 * gamepad1.left_stick_y * robot.MECANUM_SPEED);
        
            robot.rightFrontMotor.setPower(-1 * gamepad1.right_stick_y * robot.MECANUM_SPEED);
            robot.rightRearMotor.setPower(-1 * gamepad1.right_stick_y * robot.MECANUM_SPEED);
        } 
        else if (gamepad1.left_stick_x < 0) 
        {
            StrafeLeft(robot.MECANUM_SPEED_STRAFE);
        } 
        else if (gamepad1.right_stick_x > 0) 
        {
            StrafeRight(robot.MECANUM_SPEED_STRAFE);
        }
        //--- Mecanum drive forward
        else if (gamepad1.dpad_up) 
        {
            DriveForward(robot.MECANUM_SPEED);
        }
        //--- Mecanum drive backwards
        else if (gamepad1.dpad_down) 
        {
            DriveBackwards(robot.MECANUM_SPEED);
        }
        //--- Mecanum strafe right
        else if (gamepad1.dpad_right) 
        {
            StrafeRight(robot.MECANUM_SPEED_STRAFE);
        }
        //--- Mecanum strafe left
        else if (gamepad1.dpad_left) 
        {
            StrafeLeft(robot.MECANUM_SPEED_STRAFE);
        } 
        else 
        {
            StopMotors();
        }
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
    
    //region --- Autonomous Skystone Scanning ---
    //----------------------------------------------------------------------
    
    Boolean ScanForSkystone(double speed, double sidewaysInches, double sidewaysBackToStoneCenterInches, double timeout)
    {
        //robot.leftHue[] = {0F, 0F, 0F};
        //robot.rightHue[] = {0F, 0F, 0F};
        robot.leftBrickExists = false;
        robot.rightBrickExists = false;
        robot.leftBrickColor = "";
        robot.rightBrickColor = "";

        boolean foundBlack = false;
        
        if (opModeIsActive())
        {
            ShowMessage("Skystone Scanning...");

            int distance = (int)(sidewaysInches * robot.COUNTS_PER_INCH);
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
            long timeStart = System.currentTimeMillis();
            while (opModeIsActive() &&
                    (foundBlack == false) &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftFrontMotor.isBusy() && robot.leftRearMotor.isBusy() && robot.rightFrontMotor.isBusy() && robot.rightRearMotor.isBusy()))
            {
                //----------------------------------------------------------------------
                //--- Convert RGB to HSV so we can get the Hue
                //----------------------------------------------------------------------
                Color.RGBToHSV((int) (robot.leftColorSensor.red() * robot.COLOR_SCALE_FACTOR),
                        (int) (robot.leftColorSensor.green() * robot.COLOR_SCALE_FACTOR),
                        (int) (robot.leftColorSensor.blue() * robot.COLOR_SCALE_FACTOR),
                        robot.leftHue);
                        
                Color.RGBToHSV((int) (robot.rightColorSensor.red() * robot.COLOR_SCALE_FACTOR),
                        (int) (robot.rightColorSensor.green() * robot.COLOR_SCALE_FACTOR),
                        (int) (robot.rightColorSensor.blue() * robot.COLOR_SCALE_FACTOR),
                        robot.rightHue);

                //----------------------------------------------------------------------
                //--- Use Distance sensor to detect if block exists in front of sensor
                //----------------------------------------------------------------------
                robot.leftBrickExists = robot.leftDistanceSensor.getDistance(DistanceUnit.CM) < 100;
                robot.rightBrickExists = robot.rightDistanceSensor.getDistance(DistanceUnit.CM) < 100;;

                //----------------------------------------------------------------------
                //--- Use Hue to differentiate between Yellow and Black
                //----------------------------------------------------------------------
                if (robot.leftBrickExists && robot.leftHue[0] < 75) robot.leftBrickColor = "Yellow";
                if (robot.leftBrickExists && robot.leftHue[0] > 75) robot.leftBrickColor = "Black";

                if (robot.rightBrickExists && robot.rightHue[0] < 75) robot.rightBrickColor = "Yellow";
                if (robot.rightBrickExists && robot.rightHue[0] > 75) robot.rightBrickColor = "Black";

                UpdateDisplay();

                if (robot.leftBrickColor == "Black" && robot.rightBrickColor == "Black")
                {
                    foundBlack = true;
                }
            }
            long timeFinish = System.currentTimeMillis();
            long timeElapsed = timeFinish - timeStart;
            robot.TIME_SKYSTONE_SEARCH = timeElapsed;

            //----------------------------------------------------------------------
            //--- We go past the Skystone by a couple inches, recenter before grabbing it!
            //----------------------------------------------------------------------
            if (sidewaysInches > 0) sidewaysBackToStoneCenterInches = sidewaysBackToStoneCenterInches * -1; //-- Reverse

            EncoderStrafe(speed, sidewaysBackToStoneCenterInches, 5);            

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            StopMotors();

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            //RunUsingEncoders();

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
        
        return foundBlack;
    }

    int ScanSkystoneRightSide()
    {        
        boolean frontStoneExists = false;
        boolean backStoneExists = false;
        String frontBrickColor = "";
        String backBrickColor = "";
        int skystonePosition = -1;
        
        if (opModeIsActive())
        {
            //----------------------------------------------------------------------
            //--- For R3 sensors, use ratio of green to blue to detect Yellow vs Black
            //--- Calibrate for EACH sensor as they are different
            //----------------------------------------------------------------------
            double greenBlueRatioFront = (double)robot.rightFrontColorSensor.green() / (double)robot.rightFrontColorSensor.blue();
            double greenBlueRatioBack = (double)robot.rightBackColorSensor.green() / (double)robot.rightBackColorSensor.blue();

            if (greenBlueRatioFront > 1.9) frontBrickColor = "Yellow";
            else frontBrickColor = "Black";
            
            if (greenBlueRatioBack > 2.1) backBrickColor = "Yellow";
            else backBrickColor = "Black";

            //----------------------------------------------------------------------
            //--- For R3 sensors, use ratio of green to blue to detect Yellow vs Black
            //----------------------------------------------------------------------
            if (backBrickColor == "Yellow" && frontBrickColor == "Yellow")
            {
                skystonePosition = 1;
            }
            else if (backBrickColor == "Yellow" && frontBrickColor == "Black")
            {
                skystonePosition = 2;
            }
            else if (backBrickColor == "Black" && frontBrickColor == "Yellow")
            {
                skystonePosition = 3;
            }
            else
            {
                skystonePosition = -1;
            }
            
            /*
            ShowMessage(String.format("F (G/B: %.2f G: %3d B: %3d R: %3d) B (G/B: %.2f G: %3d B: %3d R: %3d)", 
            greenBlueRatioFront, robot.rightFrontColorSensor.green(), robot.rightFrontColorSensor.blue(), robot.rightFrontColorSensor.red(), 
            greenBlueRatioBack, robot.rightBackColorSensor.green(), robot.rightBackColorSensor.blue(), robot.rightBackColorSensor.red()));
            
            ShowMessage(String.format("F (G/B: %.2f G: %3d B: %3d cm: %.2f) B (G/B: %.2f G: %3d B: %3d cm: %.2f)", 
            greenBlueRatioFront, robot.rightFrontColorSensor.green(), robot.rightFrontColorSensor.blue(), robot.rightFrontDistanceSensor.getDistance(DistanceUnit.CM), 
            greenBlueRatioBack, robot.rightBackColorSensor.green(), robot.rightBackColorSensor.blue(), robot.rightBackDistanceSensor.getDistance(DistanceUnit.CM)));
            */

            ShowMessage(String.format("F: %s (%.2f) B: %s (%.2f)", 
            frontBrickColor, greenBlueRatioFront, backBrickColor, greenBlueRatioBack));
        }

        return skystonePosition;
    }
    
    int ScanSkystoneLeftSide()
    {        
        boolean frontStoneExists = false;
        boolean backStoneExists = false;
        String frontBrickColor = "";
        String backBrickColor = "";
        int skystonePosition = -1;
        
        if (opModeIsActive())
        {
            //----------------------------------------------------------------------
            //--- For R3 sensors, use ratio of green to blue to detect Yellow vs Black
            //--- Calibrate for EACH sensor as they are different
            //----------------------------------------------------------------------
            double greenBlueRatioFront = (double)robot.leftFrontColorSensor.green() / (double)robot.leftFrontColorSensor.blue();
            double greenBlueRatioBack = (double)robot.leftBackColorSensor.green() / (double)robot.leftBackColorSensor.blue();

            if (greenBlueRatioFront > 2.1) frontBrickColor = "Yellow";
            else frontBrickColor = "Black";
            
            if (greenBlueRatioBack > 2.1) backBrickColor = "Yellow";
            else backBrickColor = "Black";

            //----------------------------------------------------------------------
            //--- For R3 sensors, use ratio of green to blue to detect Yellow vs Black
            //----------------------------------------------------------------------
            if (backBrickColor == "Yellow" && frontBrickColor == "Yellow")
            {
                skystonePosition = 1;
            }
            else if (backBrickColor == "Yellow" && frontBrickColor == "Black")
            {
                skystonePosition = 2;
            }
            else if (backBrickColor == "Black" && frontBrickColor == "Yellow")
            {
                skystonePosition = 3;
            }
            else
            {
                skystonePosition = -1;
            }
            
            /*
            ShowMessage(String.format("F (G/B: %.2f G: %3d B: %3d R: %3d) B (G/B: %.2f G: %3d B: %3d R: %3d)", 
            greenBlueRatioFront, robot.leftFrontColorSensor.green(), robot.leftFrontColorSensor.blue(), robot.leftFrontColorSensor.red(), 
            greenBlueRatioBack, robot.leftBackColorSensor.green(), robot.leftBackColorSensor.blue(), robot.leftBackColorSensor.red()));
            
            ShowMessage(String.format("F (G/B: %.2f G: %3d B: %3d cm: %.2f) B (G/B: %.2f G: %3d B: %3d cm: %.2f)", 
            greenBlueRatioFront, robot.leftFrontColorSensor.green(), robot.leftFrontColorSensor.blue(), robot.leftFrontDistanceSensor.getDistance(DistanceUnit.CM), 
            greenBlueRatioBack, robot.leftBackColorSensor.green(), robot.leftBackColorSensor.blue(), robot.leftBackDistanceSensor.getDistance(DistanceUnit.CM)));
            */

            ShowMessage(String.format("F: %s (%.2f) B: %s (%.2f)", 
            frontBrickColor, greenBlueRatioFront, backBrickColor, greenBlueRatioBack));
        }

        return skystonePosition;
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
        if (robot.lifterMotor != null)
        {
            telemetry.addData("Lifter",  "Position = %3d / Target = %3d", robot.lifterMotor.getCurrentPosition(), robot.LIFTER_TARGET);
        }
        if (robot.imu != null)
        {
            telemetry.addData("Gyro",  "Angle = %.3f, Global = %.2f, Correct = %.2f", 
                robot.angleCurrent.firstAngle, robot.angleGlobal, robot.angleCorrection);
        }
        if (robot.leftColorSensor != null)
        {
            telemetry.addData("Bricks", 
            "[L] " + robot.leftBrickColor + "(" + String.format(Locale.US, "%.02f", robot.leftDistanceSensor.getDistance(DistanceUnit.CM)) + " cm / " + robot.leftHue[0] + ") " +
            "[R] " + robot.rightBrickColor + "(" + String.format(Locale.US, "%.02f", robot.rightDistanceSensor.getDistance(DistanceUnit.CM)) + " cm / " + robot.rightHue[0] + ")");
        }

        telemetry.addData("Skystone",  "Search Time = %3d", robot.TIME_SKYSTONE_SEARCH);
        
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