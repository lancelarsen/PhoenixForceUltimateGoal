package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="1")
public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        MecanumTeleOp mecanumTeleOp = new MecanumTeleOp(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            mecanumTeleOp.arcadeDrive();

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
