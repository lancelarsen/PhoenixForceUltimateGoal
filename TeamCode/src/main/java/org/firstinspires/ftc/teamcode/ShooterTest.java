package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@TeleOp(name="Shooter Test", group="1")
public class ShooterTest extends LinearOpMode {
    CRServo raiserArm;
    Servo shooterArm;
    Servo shooterTilter;
    Servo leftGrabber;
    Servo rightGrabber;

    DcMotor shooterWheel;

    double shooterTilterPosition = 0.2;

    @Override
    public void runOpMode() {
        raiserArm = hardwareMap.get(CRServo.class, "raiserArm");
        shooterArm = hardwareMap.get(Servo.class, "shooterArm");
        shooterTilter = hardwareMap.get(Servo.class, "shooterTilter");
        leftGrabber = hardwareMap.get(Servo.class, "leftGrabberServo");
        rightGrabber = hardwareMap.get(Servo.class, "rightGrabberServo");

        shooterWheel = hardwareMap.get(DcMotor.class, "shooterWheel");
        shooterWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        waitForStart();
        while (opModeIsActive()) {
            //shooterWheel.setPower(0.5);

            if (gamepad1.a) {
                shooterArm.setPosition(0);
            } else {
                shooterArm.setPosition(1);
            }

            if (gamepad1.dpad_up) {
                raiserArm.setPower(1);
            } else if (gamepad1.dpad_down) {
                raiserArm.setPower(-1);
            } else {
                raiserArm.setPower(0);
            }

            if (gamepad1.dpad_left  && shooterTilterPosition > 0) {
                telemetry.addData("Status", "U");
                shooterTilterPosition -= 0.0001;
            } else if (gamepad1.dpad_right && shooterTilterPosition < 1) {
                telemetry.addData("Status", "D");
                shooterTilterPosition += 0.0001;
            }
            telemetry.addData("Pos", shooterTilterPosition);
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            //shooterTilter.setPosition(Double.parseDouble(df.format(shooterTilterPosition)));

            telemetry.addData("Trigger", gamepad1.left_trigger);
            if (gamepad1.left_trigger > 0.5) {
                leftGrabber.setPosition(1);
                rightGrabber.setPosition(0);
            } else {
                leftGrabber.setPosition(0);
                rightGrabber.setPosition(1);
            }

            telemetry.update();
        }
    }
}
