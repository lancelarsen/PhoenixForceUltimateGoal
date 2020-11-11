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
@TeleOp(name="Remote Testing", group="1")
public class PhoenixBotRunRemoteTest extends PhoenixBotSharedCode 
{
    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize the hardware variables
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        InitGyro();

        //----------------------------------------------------------------------
        //--- Send telemetry message to signify robot waiting
        //----------------------------------------------------------------------
        ShowMessage("Ready Player 1 - > Press START");

        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();
        ShowMessage("Running...");

        SpacerDown();

        //----------------------------------------------------------------------
        //--- Run until the end of the match (driver presses STOP)
        //----------------------------------------------------------------------
        //double reachArmShoulderServo = 0.30;
        while (opModeIsActive()) 
        {
            int skystonePosition = ScanSkystoneRightSide();
            //int skystonePosition = ScanSkystoneLeftSide();
            
            //StrafeRightToShortDistance(0.5);
            
            /*
            GyroUpdates();
            
            if (gamepad1.y)
            {
                RotateTo(1, 0.3);
            }
            else if (gamepad1.b)
            {
                RotateTo(-90, 0.3);
            }
            else if (gamepad1.a)
            {
                RotateTo(135, 0.3);
            }
            else if (gamepad1.x)
            {
                RotateTo(90, 0.3);
            }
            */

            /*
            if (gamepad1.x)
            {
                reachArmShoulderServo = reachArmShoulderServo + 0.01;
                //robot.reachArmShoulderServo.setPosition(0.3);
                //robot.reachArmElbowServo.setPosition(0.3);
                //robot.reachArmShoulderServo.setPosition(0.15);
                
                //robot.reachArmShoulderServo.setPosition(reachArmShoulderServo);
                

                //--- Joint 2                
                robot.reachArmElbowServo.setPosition(0); //--- Closed
                
                //--- Joint 3
                robot.reachArmShoulderServo.setPosition(0.02); //--- Closed
            }
            else if (gamepad1.y)
            {
                reachArmShoulderServo = reachArmShoulderServo - 0.01;

                //--- Joint 2                
                robot.reachArmElbowServo.setPosition(0.65); //--- 90 degrees

                //--- Joint 3
                robot.reachArmShoulderServo.setPosition(0.72); //--- 90 degrees
                
                
                
                //robot.reachArmShoulderServo.setPosition(reachArmShoulderServo);

                //robot.reachArmElbowServo.setPosition(0); //--- Closed
            }
            ShowMessage("Shoulder " + reachArmShoulderServo);
            
            if (gamepad1.a)
            {
                //ArmToPosition(10);
                
                robot.armMotor.setPower(-1);
            }
            else if (gamepad1.b)
            {
                //ArmToPosition(60);
                robot.armMotor.setPower(1);
            }
            else
            {
                robot.armMotor.setPower(0);
            }
            */
            
            //----------------------------------------------------------------------
            //--- Gamepad 1
            //----------------------------------------------------------------------
            //SetDriveSpeeds();       //--- x, y, b, a 
            ArcadeDrivePlus();      //--- left_stick, right_stick, dpad_up, dpad_down, dpad_left, dpad_right
            //WhiskerClamps();        //--- left_bumper, left_trigger
            //BrickSpacer();          //--- right_bumper, right_trigger
            
            //----------------------------------------------------------------------
            //--- Gamepad 2
            //----------------------------------------------------------------------
            //Lifter();               //--- dpad_up, dpad_down, y, a, left_bumper, left_trigger, left_stick, left_stick_button
            //MoveReachArm();         //--- right_trigger, right_bumper
            //PlaySounds();           //--- right_stick
            //CapArm();               //--- x, b

            //----------------------------------------------------------------------
            //--- Sensors
            //----------------------------------------------------------------------
            //TouchingFoundationSensors();

            //----------------------------------------------------------------------
            //--- Screen
            //----------------------------------------------------------------------
            UpdateDisplay();
        }
    }
    
    //----------------------------------------------------------------------
    // Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
    // @param degrees Degrees to turn, + is left - is right
    //----------------------------------------------------------------------
    public void RotateTo(float degrees, double power)
    {
        double leftPower, rightPower;

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

        Drive(leftPower, rightPower);   //-- Rotate Robot

        if (degrees < 0)
        {
            //-- On Right turn we have to get off zero first
            //while (opModeIsActive() && GetAngle() == 0) {}
            while (opModeIsActive() && GetAngle() > degrees) 
            {
                ShowMessage("GetAngle() > degrees | " + GetAngle() + " > " + degrees);
            }
            StopMotors();
            SmartSleep(100);
            ShowMessage("GetAngle() > degrees | " + GetAngle() + " > " + degrees);
        }
        else
        {
            //-- Left turn
            while (opModeIsActive() && GetAngle() < degrees) 
            {
                ShowMessage("GetAngle() > degrees | " + GetAngle() + " < " + degrees);
            }
            StopMotors();
            SmartSleep(100);
            ShowMessage("GetAngle() > degrees | " + GetAngle() + " < " + degrees);
        }

        StopMotors();        
    }
    
    /*
    void ArmToPosition(int position)
    {
        robot.ARM_TARGET = position;
        
        if (robot.ARM_TARGET > 1000) robot.ARM_TARGET = 1000;
        if (robot.ARM_TARGET < 0) robot.ARM_TARGET = 0;

        robot.armMotor.setTargetPosition(robot.ARM_TARGET);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor.setPower(1);

        ShowMessage("Arm to Postion");
    }
    */
}
