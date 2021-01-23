package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="1")
public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumTeleOp mecanumTeleOp = new MecanumTeleOp(this);
        AppendagesTeleOp appendagesTeleOp = new AppendagesTeleOp(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            mecanumTeleOp.arcadeDrive();
            appendagesTeleOp.commandIntake();
            appendagesTeleOp.commandShooter();
            appendagesTeleOp.commandGoalGrabber();

            telemetry.addData("Status", "Running");
            //telemetry.update();
        }
    }
}
