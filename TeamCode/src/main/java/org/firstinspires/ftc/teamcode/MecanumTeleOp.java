package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class MecanumTeleOp extends BotMecanumDrive {
    private LinearOpMode opMode;

    private double speedMultiplier = FAST_SPEED_MULTIPLIER;
    private boolean turningEnabled = false;

    public MecanumTeleOp(LinearOpMode opMode) {
        super(opMode.hardwareMap);
        this.opMode = opMode;

        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void enableTurning(boolean turningEnabled) {
        this.turningEnabled = turningEnabled;
    }

    public void arcadeDrive() {
        if (opMode.gamepad1.y) {
            speedMultiplier = FAST_SPEED_MULTIPLIER;
        } else if (opMode.gamepad1.b) {
            speedMultiplier = SLOW_SPEED_MULTIPLIER;
        }

        Pose2d drivePower = new Pose2d();
        if (dpadPressed()) {
            Gamepad gamepad = opMode.gamepad1;

            if (gamepad.dpad_up) {
                drivePower = new Pose2d(0, -speedMultiplier, 0);
            } else if (gamepad.dpad_down) {
                drivePower = new Pose2d(0, speedMultiplier, 0);
            }

            if (gamepad.dpad_left) {
                drivePower = new Pose2d(-speedMultiplier * 2, 0, 0);
            } else if (gamepad.dpad_right) {
                drivePower = new Pose2d(speedMultiplier * 2, 0, 0);
            }
        } else {
            drivePower = new Pose2d(
                    opMode.gamepad1.left_stick_x * speedMultiplier,
                    opMode.gamepad1.left_stick_y * speedMultiplier,
                    turningEnabled ? opMode.gamepad1.right_stick_x * speedMultiplier : 0
                    );
        }

        setMecanumDrivePower(drivePower);
        update();
    }

    private boolean dpadPressed()  {
        Gamepad gamepad = opMode.gamepad1;

        return gamepad.dpad_up || gamepad.dpad_down || gamepad.dpad_left || gamepad.dpad_right;
    }

    private void setMecanumDrivePower(Pose2d pose2d) {
        final double r = Math.hypot(pose2d.getX(), pose2d.getY());
        final double robotAngle = Math.atan2(-1 * pose2d.getY(), pose2d.getX()) - Math.PI / 4;
        final double rightX = pose2d.getHeading(); //--- Rotation
        final double leftFrontPower = r * Math.cos(robotAngle) + rightX;
        final double rightFrontPower = r * Math.sin(robotAngle) - rightX;
        final double leftRearPower = r * Math.sin(robotAngle) + rightX;
        final double rightRearPower = r * Math.cos(robotAngle) - rightX;

        setMotorPowers(leftFrontPower, leftRearPower, rightRearPower, rightFrontPower);
    }
}
