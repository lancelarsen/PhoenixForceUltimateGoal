package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AppendagesTeleOp {
    private LinearOpMode _opMode;
    private BotAppendages appendages;

    private boolean shooterTogglePrePressed = false;
    private boolean shooterEnabled = false;

    private boolean intakeTogglePrePressed = false;
    private boolean intakeEnabled = false;

    private boolean goalGrabberOpen = false;

    public AppendagesTeleOp(LinearOpMode opMode) {
        _opMode = opMode;
        appendages = new BotAppendages(_opMode.hardwareMap);

        appendages.setIntakeDeployerArmAngle(BotAppendages.CLOSED_INTAKE_DEPLOYER_ANGLE);
    }

    public void commandShooter() {
        double stickY = _opMode.gamepad2.left_stick_y;
        if (Math.abs(stickY) < BotAppendages.JOYSTICK_DEAD_ZONE) {
            stickY = 0;
        }

        double tilterAngle = MapUtil.map(stickY, -1.0, 1.0, BotAppendages.MIN_RING_TILTER_ANGLE, BotAppendages.MAX_RING_TILTER_ANGLE);

        appendages.setRingTilterAngle(tilterAngle);

        if (_opMode.gamepad2.x) {
            appendages.setRingShooterArmAngle(BotAppendages.OPEN_RING_SHOOTER_ARM_ANGLE);
        } else {
            appendages.setRingShooterArmAngle(BotAppendages.CLOSED_RING_SHOOTER_ARM_ANGLE);
        }

        if (_opMode.gamepad2.y && !shooterTogglePrePressed) {
            shooterEnabled = !shooterEnabled;
            shooterTogglePrePressed = true;
        }
        if (!_opMode.gamepad2.y) {
            shooterTogglePrePressed = false;
        }

        if (shooterEnabled) {
            appendages.setRingShooterWheelSpeed(BotAppendages.RING_SHOOTER_WHEEL_SPEED);
        } else {
            appendages.setRingShooterWheelSpeed(0);
        }

        double ringRaiserSpeed = 0;

        if (Math.abs(_opMode.gamepad2.right_stick_y) > BotAppendages.JOYSTICK_DEAD_ZONE) {
            ringRaiserSpeed = _opMode.gamepad2.right_stick_y * BotAppendages.RING_RAISER_SPEED_MULTIPLIER;
        }

        appendages.setRingRaiserSpeed(ringRaiserSpeed);
    }

    public void commandIntake() {
        appendages.setIntakeDeployerArmAngle(BotAppendages.OPEN_INTAKE_DEPLOYER_ANGLE);

        double largeRollerSpeed = BotAppendages.LARGE_INTAKE_ROLLER_SPEED;
        double smallRollerSpeed = BotAppendages.SMALL_INTAKE_ROLLER_SPEED;

        if (_opMode.gamepad2.a) {
            largeRollerSpeed *= -1;
            smallRollerSpeed *= -1;
        }

        if (_opMode.gamepad2.b && !intakeTogglePrePressed) {
            intakeEnabled = !intakeEnabled;
            intakeTogglePrePressed = true;
        }
        if (!_opMode.gamepad2.b) {
            intakeTogglePrePressed = false;
        }
        if (!intakeEnabled) {
            largeRollerSpeed = 0;
            smallRollerSpeed = 0;
        }

        appendages.setLargeIntakeRollerSpeed(largeRollerSpeed);
        appendages.setSmallIntakeRollerSpeed(smallRollerSpeed);
    }

    public void commandGoalGrabber() {
        double goalGrabberLifterSpeed = BotAppendages.GOAL_GRABBER_LIFTER_SPEED;

        if (_opMode.gamepad2.dpad_down) {
            goalGrabberLifterSpeed *= -1;
        } else if (!_opMode.gamepad2.dpad_up) {
            goalGrabberLifterSpeed = 0;
        }

        appendages.setGoalGrabberLifterSpeed(goalGrabberLifterSpeed);

        if (_opMode.gamepad2.left_trigger > BotAppendages.TRIGGER_PRESSED_THRESH) {
            goalGrabberOpen = true;
        } else if (_opMode.gamepad2.right_trigger > BotAppendages.TRIGGER_PRESSED_THRESH) {
            goalGrabberOpen = false;
        }

        if (goalGrabberOpen) {
            appendages.setGoalGrabberAngle(BotAppendages.OPEN_GOAL_GRABBER_ANGLE);
        } else {
            appendages.setGoalGrabberAngle(BotAppendages.CLOSED_GOAL_GRABBER_ANGLE);
        }
    }
}
