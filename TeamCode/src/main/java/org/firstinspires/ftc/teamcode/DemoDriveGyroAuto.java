package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Disabled
@Autonomous(name="Auto (Drive Gyro)", group="Test")
public class DemoDriveGyroAuto extends PhoenixBotSharedCode
{
    BNO055IMU               imu;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, power = .3, correction;
    boolean                 aButton, bButton;
    
    //----------------------------------------------------------------------
    //--- Autonomous Code
    //----------------------------------------------------------------------
    @Override
    public void runOpMode() 
    {
        //----------------------------------------------------------------------
        //--- Initialize
        //----------------------------------------------------------------------
        robot.init(hardwareMap);
        ShowMessage("Gyro Drive");

        //----------------------------------------------------------------------
        //--- Initialize before Start
        //----------------------------------------------------------------------
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        //----------------------------------------------------------------------
        //--- Send telemetry message to signify robot waiting
        //----------------------------------------------------------------------
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.addData("Demo", "Press START");
        telemetry.update();
        
        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();

        while (opModeIsActive())
        {
            //----------------------------------------------------------------------
            // Set Drive Path
            //----------------------------------------------------------------------
            // Use gyro to drive in a straight line.
            correction = checkDirection();
    
            telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);
            telemetry.update();
    
            //----------------------------------------------------------------------
            //--- Motors
            //----------------------------------------------------------------------
            robot.leftFrontMotor.setPower(power - correction);
            robot.leftRearMotor.setPower(power - correction);
            robot.rightFrontMotor.setPower(power + correction);
            robot.rightRearMotor.setPower(power + correction);
    
            // We record the sensor values because we will test them in more than
            // one place with time passing between those places. See the lesson on
            // Timing Considerations to know why.
    
            aButton = gamepad1.a;
            bButton = gamepad1.b;
    
            if (aButton || bButton)
            {
                //----------------------------------------------------------------------
                //--- Motors
                //----------------------------------------------------------------------
                robot.leftFrontMotor.setPower(power);
                robot.leftRearMotor.setPower(power);
                robot.rightFrontMotor.setPower(power);
                robot.rightRearMotor.setPower(power);
    
                sleep(500);
    
                StopMotors();
                
                // turn 90 degrees right.
                if (aButton) rotate(-90, power);
    
                // turn 90 degrees left.
                if (bButton) rotate(90, power);
            }
        }

        StopMotors();
    }
    
    /**
    * Resets the cumulative angle tracking to zero.
    */
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
    
    /**
    * Get current cumulative angle rotation from last reset.
    * @return Angle in degrees. + = left, - = right.
    */
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) 
        {
            deltaAngle += 360;
        }
        else if (deltaAngle > 180) 
        {
            deltaAngle -= 360;
        }

        globalAngle += deltaAngle;
        lastAngles = angles;
        return globalAngle;
    }
    
    /**
    * See if we are moving in a straight line and if not return a power correction value.
    * @return Power adjustment, + is adjust left - is adjust right.
    */
    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;
        
        angle = getAngle();
        
        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.
        
        correction = correction * gain;
        
        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        // set power to rotate.
        robot.leftFrontMotor.setPower(leftPower);
        robot.leftRearMotor.setPower(leftPower);
        robot.rightFrontMotor.setPower(rightPower);
        robot.rightRearMotor.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}
            while (opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {}

        StopMotors();

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
}



/*
@Autonomous(name="Drive Gyro", group="Exercises")

public class DemoDriveGyroAuto extends PhoenixBotSharedCode
{
    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    {
            //----------------------------------------------------------------------
    //--- Initialize
    //----------------------------------------------------------------------
    robot.init(hardwareMap);
        
//        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

    //----------------------------------------------------------------------
    //--- Send telemetry message to signify robot waiting
    //----------------------------------------------------------------------
    telemetry.addData("Demo", "Press START");
    telemetry.update();
    
    //----------------------------------------------------------------------
    //--- Wait for the game to start (driver presses PLAY)
    //----------------------------------------------------------------------
    waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        sleep(1000);

        // drive until end of period.

        while (opModeIsActive())
        {

        }

        // turn the motors off.
            robot.leftFrontMotor.setPower(0);
            robot.leftRearMotor.setPower(0);
            robot.rightFrontMotor.setPower(0);
            robot.rightRearMotor.setPower(0);
    }

    
}
*/
