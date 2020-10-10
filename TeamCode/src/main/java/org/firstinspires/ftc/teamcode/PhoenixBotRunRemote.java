package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

//----------------------------------------------------------------------
// Gamepad 1
//  - Left Stick            - Arcade Drive Movement
//  - Right Stick           - Arcade Turning
//  - Dpad Up               - Drive Straight Forward
//  - Dpad Down             - Drive Straight Backward
//  - Dpad Right            - Drive Strafe Right
//  - Dpad Left             - Drive Strafe Left
//  - Left Bumper           - Whiskers Up
//  - Left Trigger          - Whiskers Down
//  - Right Bumper          - Brick Spacer Up
//  - Right Trigger         - Brick Spacer Down
//  - X                     - Speed Slow
//  - Y                     - Speed Medium
//  - B                     - Speed Fast 
//  - A                     - Speed Very Slow
//----------------------------------------------------------------------
// Gamepad 2
//  - Left Stick            - Lifter Up/Down Quick
//  - Left Stick Button     - Lower and Reset Lifter Encoder
//  - Right Stick Button    - 
//  - Right Stick (Up)      - ** Angry Sound
//  - Right Stick (Down)    - ** Alert Sound
//  - Right Stick (Left)    - ** Song Sound
//  - Right Stick (Right)   - ** BT-BT Sound
//  - Right Stick Button    - 
//  - Dpad Up               - Lifter Up
//  - Dpad Down             - Lifter Down Slow
//  - Dpad Right            - 
//  - Dpad Left             - 
//  - Left Bumper           - Reach Arm Out
//  - Left Trigger          - Reach Arm In
//  - Right Bumper          - Lifter Up Level   Replace with tapemeasure
//  - Right Trigger         - Lifter Down Level  Replace with tapemeasure
//  - X                     - Cap Elbow In/Out
//  - B                     - Cap Wrist Up/Down
//  - Y                     - Drop Brick / Move Arm Up
//  - A                     - Pick Up Brick / Move Arm Down
//----------------------------------------------------------------------

@TeleOp(name="-- Remote Control --", group="1")
public class PhoenixBotRunRemote extends PhoenixBotSharedCode 
{
    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize the hardware variables
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        //InitGyro();
        //InitSkystoneArms();

        //----------------------------------------------------------------------
        //--- Send telemetry message to signify robot waiting
        //----------------------------------------------------------------------
        ShowMessage("Ready Player 1 - > Press START");

        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();
        ShowMessage("Running...");

        //----------------------------------------------------------------------
        //--- Run until the end of the match (driver presses STOP)
        //----------------------------------------------------------------------
        while (opModeIsActive()) 
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
            Lifter();               //--- dpad_up, dpad_down, y, a, left_bumper, left_trigger, left_stick, left_stick_button
            //MoveReachArm();         //--- right_trigger, right_bumper
            PlaySounds();           //--- right_stick
            CapArm();               //--- x, b
            MoveTape();

            //----------------------------------------------------------------------
            //--- Sensors
            //----------------------------------------------------------------------
            TouchingFoundationSensors();

            //----------------------------------------------------------------------
            //--- Screen
            //----------------------------------------------------------------------
            UpdateDisplay();
        }
    }
}
