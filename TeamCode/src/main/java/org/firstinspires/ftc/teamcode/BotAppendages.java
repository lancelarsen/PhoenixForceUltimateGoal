package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class BotAppendages {
    public final static double TRIGGER_PRESSED_THRESH = 0.5;
    public final static double JOYSTICK_DEAD_ZONE = 0.05;

    public final static double MIN_RING_TILTER_ANGLE = 0.12;
    public final static double MAX_RING_TILTER_ANGLE = 0;
    public final static double OPEN_RING_SHOOTER_ARM_ANGLE = 0.6;
    public final static double CLOSED_RING_SHOOTER_ARM_ANGLE = 0.75;
    public final static double RING_SHOOTER_WHEEL_SPEED = 36000;

    public final static double RING_RAISER_SPEED_MULTIPLIER = -0.5;

    public final static double OPEN_INTAKE_DEPLOYER_ANGLE = 0.0;
    public final static double CLOSED_INTAKE_DEPLOYER_ANGLE = 0.6;
    public final static double LARGE_INTAKE_ROLLER_SPEED = 1.0;
    public final static double SMALL_INTAKE_ROLLER_SPEED = 1.0;

    public final static double GOAL_GRABBER_LIFTER_SPEED = 0.5;
    public final static double OPEN_GOAL_GRABBER_ANGLE = 0.72;
    public final static double CLOSED_GOAL_GRABBER_ANGLE = 0.22;

    private Servo ringTilter;
    private Servo ringShooterArm;
    private DcMotorEx ringShooterWheel;

    private CRServo ringRaiser;

    private Servo intakeDeployerArm;
    private DcMotor largeIntakeRoller;
    private CRServo smallIntakeRoller;

    private DcMotor goalGrabberLifter;
    private Servo leftGoalGrabber;
    private Servo rightGoalGrabber;

    public BotAppendages(HardwareMap hardwareMap) {
        ringTilter = hardwareMap.get(Servo.class, "shooterTilter");
        ringShooterArm = hardwareMap.get(Servo.class, "shooterArm");
        ringShooterWheel = hardwareMap.get(DcMotorEx.class, "shooterWheel");
        ringShooterWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ringRaiser = hardwareMap.get(CRServo.class, "ringRaiser");
        ringRaiser.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeDeployerArm = hardwareMap.get(Servo.class, "intakeDeployerArm");
        largeIntakeRoller = hardwareMap.get(DcMotor.class, "largeIntakeRoller");
        smallIntakeRoller = hardwareMap.get(CRServo.class, "smallIntakeRoller");

        goalGrabberLifter = hardwareMap.get(DcMotor.class, "goalGrabberLifter");
        goalGrabberLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        goalGrabberLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftGoalGrabber = hardwareMap.get(Servo.class, "leftGrabberServo");
        rightGoalGrabber = hardwareMap.get(Servo.class, "rightGrabberServo");
    }

    public void setRingTilterAngle(double angle) {
        ringTilter.setPosition(angle);
    }

    public void setRingShooterArmAngle(double angle) {
        ringShooterArm.setPosition(angle);
    }

    public void setRingShooterWheelSpeed(double speed) {
        ringShooterWheel.setVelocity(speed, AngleUnit.DEGREES);
    }

    public void setRingRaiserSpeed(double speed) {
        ringRaiser.setPower(speed);
    }

    public void setLargeIntakeRollerSpeed(double speed) {
        largeIntakeRoller.setPower(speed);
    }

    public void setIntakeDeployerArmAngle(double angle) {
        intakeDeployerArm.setPosition(angle);
    }

    public void setSmallIntakeRollerSpeed(double speed) {
        smallIntakeRoller.setPower(speed);
    }

    public void setGoalGrabberLifterSpeed(double speed) {
        goalGrabberLifter.setPower(speed);
    }

    public void setGoalGrabberAngle(double angle) {
        leftGoalGrabber.setPosition(angle);
        rightGoalGrabber.setPosition(1 - angle);
    }
}
