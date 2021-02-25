package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AppendagesAutonomous extends BotAppendages {
    private LinearOpMode opMode;

    public AppendagesAutonomous(LinearOpMode opMode) {
        super(opMode.hardwareMap);
        this.opMode = opMode;
    }

    public Thread shootRing() {
        Runnable shootTask = () -> {
            try {
                extendShooterArm(true);
                Thread.sleep(500);
            } catch (Exception exception) {

            } finally {
                extendShooterArm(false);
            }
        };

        Thread shootThread = new Thread(shootTask);
        shootThread.start();

        return shootThread;
    }
}
