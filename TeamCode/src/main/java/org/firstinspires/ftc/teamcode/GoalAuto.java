package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(group = "drive")
public class GoalAuto extends LinearOpMode {
    private enum TargetZone {
        ZONE_A,
        ZONE_B,
        ZONE_C,
    }

    @Override
    public void runOpMode() throws InterruptedException {
        BotMecanumDrive drive = new BotMecanumDrive(hardwareMap);
        AppendagesAutonomous appendages = new AppendagesAutonomous(this);

        Pose2d initialPosition = new Pose2d(-62, -50, Math.toRadians(180));
        drive.setPoseEstimate(initialPosition);

        appendages.openGoalGrabber(true);
        appendages.setShooterTilterAngle(0);

        waitForStart();
        if (isStopRequested()) return;

        appendages.openGoalGrabber(false);
        sleep(1500);
        appendages.setGoalLifterPosition(BotAppendages.GoalLifterPosition.UP);

        // ToDo: Check how many rings there are
        TargetZone targetZone = TargetZone.ZONE_C;

        Trajectory driveToA = drive.trajectoryBuilder(initialPosition, true)
                .splineTo(new Vector2d(-2, -60), Math.toRadians(0))
                .build();

        Trajectory driveToB = drive.trajectoryBuilder(initialPosition, true)
                .splineTo(new Vector2d(-15, -50), Math.toRadians(0))
                .splineTo(new Vector2d(30, -37), Math.toRadians(0))
                .build();

        Trajectory driveToC = drive.trajectoryBuilder(initialPosition, true)
                .splineTo(new Vector2d(-15, -50), Math.toRadians(0))
                .splineTo(new Vector2d(50, -60), Math.toRadians(0))
                .build();

        Pose2d endPosition = new Pose2d();
        switch(targetZone) {
            case ZONE_A:
                drive.followTrajectory(driveToA);
                endPosition = driveToA.end();
            case ZONE_B:
                drive.followTrajectory(driveToB);
                endPosition = driveToB.end();
            case ZONE_C:
                drive.followTrajectory(driveToC);
                endPosition = driveToC.end();
        }

        appendages.setGoalLifterPosition(BotAppendages.GoalLifterPosition.DOWN);
        sleep(2000);
        appendages.openGoalGrabber(true);
        sleep(1000);

        appendages.setRingLifterPosition(BotAppendages.RingLifterPosition.UP);
        appendages.enableShooterWheel(true);

        Trajectory driveToShootingLocation = drive.trajectoryBuilder(endPosition, false)
                .splineToLinearHeading(new Pose2d(-15, -35, Math.toRadians(0)), Math.toRadians(180))
                .build();

        drive.followTrajectory(driveToShootingLocation);

        for(int i = 0; i < 3; i++) {
            appendages.extendShooterArm(true);
            sleep(350);
            appendages.extendShooterArm(false);
            sleep(700);
        }

        appendages.setRingLifterPosition(BotAppendages.RingLifterPosition.DOWN);
        appendages.enableShooterWheel(false);
        sleep(10000);
    }
}
