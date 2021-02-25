package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//----------------------------------------------------------------------
// Gamepad 1
//  - Left Stick            - Arcade Drive Movement
//  - Right Stick           - Arcade Turning (Push: Realign Goal Lifter)
//  - Dpad Up               - Drive Straight Forward
//  - Dpad Down             - Drive Straight Backward
//  - Dpad Right            - Drive Strafe Right
//  - Dpad Left             - Drive Strafe Left
//  - Left Bumper           - Goal Grabber Open
//  - Left Trigger          - Goal Lifter Up
//  - Right Bumper          - Goal Grabber Closed
//  - Right Trigger         - Goal Lifter Down
//  - X                     -
//  - Y                     - Speed Fast
//  - B                     - Speed Slow
//  - A                     -
//----------------------------------------------------------------------
// Gamepad 2
//  - Left Stick            - Tilt Ring Shooter
//  - Right Stick           - (Push: Realign Ring Lifter)
//  - Dpad Up               - Ring Lifter Up
//  - Dpad Down             - Ring Lifter Down
//  - Dpad Right            -
//  - Dpad Left             -
//  - Left Bumper           -
//  - Left Trigger          -
//  - Right Bumper          -
//  - Right Trigger         -
//  - X                     - Shoot a Ring!
//  - B                     - Turn Ring Intake On/Off While Lifter Up
//  - Y                     - Turn Ring Shooter On/Off While Lifter Down
//  - A                     - Reverse Ring Intake Direction (While Pressed)
//----------------------------------------------------------------------

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="1")
public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumTeleOp mecanum = new MecanumTeleOp(this);
        AppendagesTeleOp appendages = new AppendagesTeleOp(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            appendages.commandIntake();
            appendages.commandWings();
            appendages.commandShooter();
            appendages.commandGoalGrabber();

            mecanum.enableTurning(!appendages.isAdjGoalLifterPosition());
            mecanum.arcadeDrive();

            telemetry.addData("Status", "Running");
            //telemetry.update();
        }
    }
}
