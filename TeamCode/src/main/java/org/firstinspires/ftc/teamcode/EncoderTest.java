package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.Encoder;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="EncoderTest", group="1")
public class EncoderTest extends LinearOpMode {
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private  Encoder frontEncoder;

    @Override
    public void runOpMode() {
        BotMecanumDrive drive = new BotMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "lr"));
        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rr"));
        rightEncoder.setDirection(Encoder.Direction.REVERSE);
        // Using encoder port from this motor as we arn't using an encoder on the roller
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "largeIntakeRoller"));
        frontEncoder.setDirection(Encoder.Direction.FORWARD);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("leftEncoder", leftEncoder.getCurrentPosition());
            telemetry.addData("rightEncoder", rightEncoder.getCurrentPosition());
            telemetry.addData("frontEncoder", frontEncoder.getCurrentPosition());
            telemetry.update();

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();
        }
    }
}
