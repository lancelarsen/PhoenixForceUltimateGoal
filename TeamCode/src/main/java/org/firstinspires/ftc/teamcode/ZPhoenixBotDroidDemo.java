package org.firstinspires.ftc.teamcode;

import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.File;

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
//  - Left Bumper           - Lifter Up Level
//  - Left Trigger          - Lifter Down Level
//  - Right Bumper          - Reach Arm Out
//  - Right Trigger         - Reach Arm In
//  - X                     - Cap Elbow In/Out
//  - B                     - Cap Wrist Up/Down
//  - Y                     - Drop Brick / Move Arm Up
//  - A                     - Pick Up Brick / Move Arm Down
//----------------------------------------------------------------------

@Disabled
@TeleOp(name="-- Droid Demo --", group="2")
public class ZPhoenixBotDroidDemo extends PhoenixBotSharedCode
{
    @Override
    public void runOpMode() {

        //----------------------------------------------------------------------
        //--- Initialize the hardware variables
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        //InitGyro();

        //----------------------------------------------------------------------
        //--- Send telemetry message to signify robot waiting
        //----------------------------------------------------------------------
        telemetry.addData("", "- DROID DEMO -");
        telemetry.update();

        //--- Wait for the game to start (driver presses PLAY)
        waitForStart();

        ShowMessage("Press a button!");

        //--- Run until the end of the match (driver presses STOP)
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
            Lifter();               //--- dpad_up, dpad_down, y, a, left_bumper, left_trigger, left_stick_button
            //MoveReachArm();         //--- right_trigger, right_bumper
            PlaySounds();           //--- right_stick
            CapArm();               //--- x, b

            //----------------------------------------------------------------------
            //--- Screen
            //----------------------------------------------------------------------
            UpdateDisplay();
        }
    }
}
