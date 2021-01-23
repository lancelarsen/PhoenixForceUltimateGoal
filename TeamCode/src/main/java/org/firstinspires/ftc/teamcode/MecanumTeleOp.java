package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class MecanumTeleOp {
    private LinearOpMode _opMode;
    private BotMecanumDrive mecanumDrive;

    public MecanumTeleOp(LinearOpMode opMode) {
        _opMode = opMode;
        mecanumDrive = new BotMecanumDrive(_opMode.hardwareMap);
    }

    public void arcadeDrive() {
        final double r = Math.hypot(_opMode.gamepad1.left_stick_x, _opMode.gamepad1.left_stick_y);
        final double robotAngle = Math.atan2(-1 * _opMode.gamepad1.left_stick_y, _opMode.gamepad1.left_stick_x) - Math.PI / 4;
        final double rightX = _opMode.gamepad1.right_stick_x; //--- Rotation
        final double leftFrontPower = r * Math.cos(robotAngle) + rightX;
        final double rightFrontPower = r * Math.sin(robotAngle) - rightX;
        final double leftRearPower = r * Math.sin(robotAngle) + rightX;
        final double rightRearPower = r * Math.cos(robotAngle) - rightX;

        mecanumDrive.setMotorPowers(leftFrontPower, rightFrontPower, leftRearPower, rightRearPower);
    }
}
