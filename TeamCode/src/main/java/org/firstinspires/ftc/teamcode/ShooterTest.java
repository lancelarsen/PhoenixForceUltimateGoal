package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Shooter Test", group="1")
public class ShooterTest extends LinearOpMode {
    Servo raiserArm;
    Servo shooterArm;
    Servo shooterTilter;

    DcMotor shooterWheel;

    @Override
    public void runOpMode() {
        raiserArm = hardwareMap.get(Servo.class, "raiserArm");
        shooterArm = hardwareMap.get(Servo.class, "shooterArm");
        shooterTilter = hardwareMap.get(Servo.class, "shooterTilter");

        shooterWheel = hardwareMap.get(DcMotor.class, "shooterWheel");
        //shooterWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        waitForStart();
        while (opModeIsActive()) {
            //telemetry.addData("Status", "Active");
            //telemetry.update();

            shooterWheel.setPower(.1);

            if (gamepad1.a) {
                shooterArm.setPosition(90);
            } else {
                shooterArm.setPosition(0);
            }

            if (gamepad1.dpad_up) {
                telemetry.addData("Status", "U");
                telemetry.update();
                raiserArm.setPosition(1);
            } else if (gamepad1.dpad_down) {
                raiserArm.setPosition(-1);
                telemetry.addData("Status", "D");
                telemetry.update();
            } else {
                raiserArm.setPosition(0);
                telemetry.addData("Status", "N");
                telemetry.update();
            }

            if (gamepad1.dpad_left) {
                shooterTilter.setPosition(5);
            } else if (gamepad1.dpad_right) {
                shooterTilter.setPosition(-5);
            } else {
                shooterTilter.setPosition(0);
            }
        }
    }
}
