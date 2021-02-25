package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BotAppendages {
    public final static double TRIGGER_PRESSED_THRESH = 0.5;
    public final static double JOYSTICK_DEAD_ZONE = 0.05;

    public final static double MIN_SHOOTER_TILTER_ANGLE = 0.1;
    public final static double MAX_SHOOTER_TILTER_ANGLE = 0.0;
    public final static double EXTENDED_SHOOTER_ARM_ANGLE = 0.6;
    public final static double RETRACTED_SHOOTER_ARM_ANGLE = 0.75;
    public final static double RING_SHOOTER_WHEEL_SPEED = 1700;

    public final static double RING_LIFTER_SPEED = 1.0;
    public final static double DOWN_RING_LIFTER_POSITION = 0;
    public final static double UP_RING_LIFTER_POSITION = 7.25; // In inches

    public final static double EXTENDED_INTAKE_DEPLOYER_ANGLE = 0.0;
    public final static double RETRACTED_INTAKE_DEPLOYER_ANGLE = 0.6;
    public final static double LARGE_INTAKE_ROLLER_SPEED = 1.0;
    public final static double SMALL_INTAKE_ROLLER_SPEED = 1.0;

    public final static double OPEN_LEFT_WING_ANGLE = 0.5;
    public final static double OPEN_RIGHT_WING_ANGLE = 0.5;
    public final static double CLOSED_LEFT_WING_ANGLE = 0;
    public final static double CLOSED_RIGHT_WING_ANGLE = 0;
    public final static double EXTENDED_LEFT_WING_ANGLE = 0.25;
    public final static double EXTENDED_RIGHT_WING_ANGLE = 0.25;
    public final static double RETRACTED_LEFT_WING_ANGLE = 0;
    public final static double RETRACTED_RIGHT_WING_ANGLE = 0;
    public final static int OPEN_EXTEND_DELAY = 500;

    public final static double GOAL_LIFTER_SPEED = 1;
    public final static double DOWN_GOAL_LIFTER_POSITION = 0;
    public final static double UP_GOAL_LIFTER_POSITION = 40; // In inches
    public final static double OPEN_GOAL_GRABBER_ANGLE = 0.18;
    public final static double CLOSED_GOAL_GRABBER_ANGLE = 0.72;

    public final Servo shooterTilter;
    public final Servo shooterArm;
    public final DcMotorEx shooterWheel;

    public final DcMotorEx ringLifter;

    public final Servo intakeDeployerArm;
    public final DcMotor largeIntakeRoller;
    public final CRServo smallIntakeRoller;

    /*public final Servo leftWingOpener;
    public final Servo rightWingOpener;
    public final Servo leftWingExtender;
    public final Servo rightWingExtender;*/

    public final DcMotorEx goalLifter;
    public final Servo goalGrabber;

    public boolean intakeEnabled;
    public Direction intakeDirection;

    public enum Direction {
        FORWARD,
        REVERSE
    }

    public enum RingLifterPosition {
        UP,
        DOWN
    }

    public enum GoalLifterPosition {
        UP,
        DOWN
    }

    public BotAppendages(HardwareMap hardwareMap) {
        shooterTilter = hardwareMap.get(Servo.class, "shooterTilter");
        shooterArm = hardwareMap.get(Servo.class, "shooterArm");
        shooterWheel = hardwareMap.get(DcMotorEx.class, "shooterWheel");
        shooterWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ringLifter = hardwareMap.get(DcMotorEx.class, "ringRaiser");
        ringLifter.setDirection(DcMotorEx.Direction.REVERSE);
        ringLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ringLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ringLifter.setPositionPIDFCoefficients(1);

        intakeDeployerArm = hardwareMap.get(Servo.class, "intakeDeployerArm");
        largeIntakeRoller = hardwareMap.get(DcMotor.class, "largeIntakeRoller");
        smallIntakeRoller = hardwareMap.get(CRServo.class, "smallIntakeRoller");

        /*leftWingOpener = hardwareMap.get(Servo.class, "leftWingOpener");
        rightWingOpener = hardwareMap.get(Servo.class, "rightWingOpener");
        leftWingExtender = hardwareMap.get(Servo.class, "leftWingExtender");
        rightWingExtender = hardwareMap.get(Servo.class, "rightWingExtender");*/

        goalLifter = hardwareMap.get(DcMotorEx.class, "goalGrabberLifter");
        goalLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        goalLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        goalGrabber = hardwareMap.get(Servo.class, "leftGrabberServo");
    }

    public void setShooterTilterAngle(double angle) {
        double tilterAngle = MapUtil.map(angle, -1.0, 1.0, BotAppendages.MIN_SHOOTER_TILTER_ANGLE, BotAppendages.MAX_SHOOTER_TILTER_ANGLE);

        shooterTilter.setPosition(tilterAngle);
        //shooterTilter.setPosition(0); // For testing
    }

    public void enableShooterWheel(boolean enabled) {
        if (enabled) {
            shooterWheel.setVelocity(RING_SHOOTER_WHEEL_SPEED);
        } else {
            shooterWheel.setVelocity(0);
        }
    }

    public void extendShooterArm(boolean extended) {
        if (extended) {
            shooterArm.setPosition(EXTENDED_SHOOTER_ARM_ANGLE);
        } else {
            shooterArm.setPosition(RETRACTED_SHOOTER_ARM_ANGLE);
        }
    }

    public void setRingLifterPosition(RingLifterPosition position) {
        double lifterPosition;
        if (position == RingLifterPosition.UP) {
            lifterPosition = UP_RING_LIFTER_POSITION;
        } else {
            lifterPosition = DOWN_RING_LIFTER_POSITION;
        }

        ringLifter.setTargetPosition(EncoderUtil.inchesToTicks(EncoderUtil.Motor.REV_CORE_HEX, lifterPosition));
        ringLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ringLifter.setPower(RING_LIFTER_SPEED);
    }

    public void enableIntake(boolean enabled) {
        intakeEnabled = enabled;

        runIntake();
    }

    public void setIntakeDirection(Direction direction) {
        intakeDirection = direction;

        runIntake();
    }

    public /*Thread*/ void extendWings(boolean extend) {
        /*
        Runnable extendTask = () -> {
            try {
                if (extend) {
                    leftWingExtender.setPosition(EXTENDED_LEFT_WING_ANGLE);
                    rightWingExtender.setPosition(EXTENDED_RIGHT_WING_ANGLE);
                    Thread.sleep(OPEN_EXTEND_DELAY);
                    leftWingOpener.setPosition(OPEN_LEFT_WING_ANGLE);
                    rightWingOpener.setPosition(OPEN_RIGHT_WING_ANGLE);
                } else {
                    leftWingExtender.setPosition(RETRACTED_LEFT_WING_ANGLE);
                    rightWingExtender.setPosition(RETRACTED_RIGHT_WING_ANGLE);
                    Thread.sleep(OPEN_EXTEND_DELAY);
                    leftWingOpener.setPosition(CLOSED_LEFT_WING_ANGLE);
                    rightWingOpener.setPosition(CLOSED_RIGHT_WING_ANGLE);
                }
            } catch (Exception exception) { }
        };

        Thread extendThread = new Thread(extendTask);
        extendThread.start();

        return extendThread;
        */
    }

    public void extendIntakeDeployArm(boolean extend) {
        if (extend) {
            intakeDeployerArm.setPosition(EXTENDED_INTAKE_DEPLOYER_ANGLE);
        } else {
            intakeDeployerArm.setPosition(RETRACTED_INTAKE_DEPLOYER_ANGLE);
        }
    }

    public void setGoalLifterPosition(GoalLifterPosition position) {
        double lifterPosition;
        if (position == GoalLifterPosition.UP) {
            lifterPosition = UP_GOAL_LIFTER_POSITION;
        } else {
            lifterPosition = DOWN_GOAL_LIFTER_POSITION;
        }

        goalLifter.setTargetPosition(EncoderUtil.inchesToTicks(EncoderUtil.Motor.GOBILDA_5202, lifterPosition));
        goalLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        goalLifter.setPower(GOAL_LIFTER_SPEED);
    }

    public void openGoalGrabber(boolean open) {
        if (open) {
            goalGrabber.setPosition(OPEN_GOAL_GRABBER_ANGLE);
        } else {
            goalGrabber.setPosition(CLOSED_GOAL_GRABBER_ANGLE);
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

        largeIntakeRoller.setPower(largeIntakeSpeed);
        smallIntakeRoller.setPower(smallIntakeSpeed);
    }
}
