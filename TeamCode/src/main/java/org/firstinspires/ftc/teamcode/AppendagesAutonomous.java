package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AppendagesAutonomous {
    private LinearOpMode _opMode;
    private BotAppendages appendages;

    private boolean intakeEnabled;
    private Direction intakeDirection;

    public enum Direction {
        FORWARD,
        REVERSE
    }

    public enum RingRaiserPosition {
        UP,
        DOWN
    }

    public enum GoalLifterPosition {
        UP,
        DOWN
    }

    public AppendagesAutonomous(LinearOpMode opMode) {
        _opMode = opMode;
        appendages = new BotAppendages(_opMode.hardwareMap);
    }

    public void setShooterTilterAngle(double angle) {
        double tilterAngle = MapUtil.map(angle, -1.0, 1.0, BotAppendages.MIN_RING_TILTER_ANGLE, BotAppendages.MAX_RING_TILTER_ANGLE);

        appendages.setRingTilterAngle(tilterAngle);
    }

    public void enableShooterWheel(boolean enabled) {
        if (enabled) {
            appendages.setRingShooterWheelSpeed(BotAppendages.RING_SHOOTER_WHEEL_SPEED);
        } else {
            appendages.setRingShooterWheelSpeed(0);
        }
    }

    public void extendShooterArm(boolean extended) {
        if (extended) {
            appendages.setRingShooterArmAngle(BotAppendages.OPEN_RING_SHOOTER_ARM_ANGLE);
        } else {
            appendages.setRingShooterArmAngle(BotAppendages.CLOSED_RING_SHOOTER_ARM_ANGLE);
        }
    }

    public void setRingRaiserPosition(RingRaiserPosition position) {

    }

    public void enableIntake(boolean enabled) {
        intakeEnabled = enabled;

        runIntake();
    }

    public void setIntakeDirection(Direction direction) {
        intakeDirection = direction;

        runIntake();
    }

    public void setGoalLifterPosition(GoalLifterPosition position) {

    }

    public void openGoalGrabber(boolean open) {
        if (open) {
            appendages.setGoalGrabberAngle(BotAppendages.OPEN_GOAL_GRABBER_ANGLE);
        } else {
            appendages.setGoalGrabberAngle(BotAppendages.CLOSED_GOAL_GRABBER_ANGLE);
        }
    }

    private void runIntake() {
        double largeIntakeSpeed = 0;
        double smallIntakeSpeed = 0;

        if (intakeEnabled) {
            double direction = intakeDirection == Direction.FORWARD ? 1 : -1;

            largeIntakeSpeed = BotAppendages.LARGE_INTAKE_ROLLER_SPEED * direction;
            smallIntakeSpeed = BotAppendages.SMALL_INTAKE_ROLLER_SPEED * direction;
        }

        appendages.setLargeIntakeRollerSpeed(largeIntakeSpeed);
        appendages.setSmallIntakeRollerSpeed(smallIntakeSpeed);
    }
}
