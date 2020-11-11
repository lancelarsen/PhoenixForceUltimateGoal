package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//--- IMU
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

//--- LEDs
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

//--- Sensors
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PhoenixBotHardware
{
    public PhoenixBotHardware(){}

    //----------------------------------------------------------------------
    //region --- Motor, Servo and Sensor Names
    //----------------------------------------------------------------------
    public DcMotor  leftFrontMotor      = null;
    public DcMotor  leftRearMotor       = null;
    public DcMotor  rightFrontMotor     = null;
    public DcMotor  rightRearMotor      = null;

//    public DcMotor  shooterOneMotor     = null;
//    public DcMotor  shooterTwoMotor     = null;

    public RevBlinkinLedDriver blinkinLedDriver1;

    public BNO055IMU imu;

    //endregion
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    //region --- Constants
    //----------------------------------------------------------------------
    static final double MECANUM_SPEED_HIGH          = 1.0;
    static final double MECANUM_SPEED_MED           = 0.75;
    static final double MECANUM_SPEED_LOW           = 0.5;
    static final double MECANUM_SPEED_SLOW          = 0.35;

    static final String MECANUM_SPEED_HIGH_DISPLAY  = "High";
    static final String MECANUM_SPEED_MED_DISPLAY   = "Medium";
    static final String MECANUM_SPEED_LOW_DISPLAY   = "Low";
    static final String MECANUM_SPEED_SLOW_DISPLAY  = "Slow";

    static final double MECANUM_SPEED_STRAFE_HIGH   = 1.0;
    static final double MECANUM_SPEED_STRAFE_MED    = 0.75;
    static final double MECANUM_SPEED_STRAFE_LOW    = 0.5;
    static final double MECANUM_SPEED_STRAFE_SLOW   = 0.35;

    public static final int LIFTERARM_TOP_COUNT     = 3300;
    public static final int LIFTERARM_INCREMENT     = 325;
    public static final int REACHARM_TOP_COUNT      = 200;

    //--- Distance
    private static final double COUNTS_PER_MOTOR_REV    = 1440;
    private static final double DRIVE_GEAR_REDUCTION    = 0.5382;  //--- Experimentally calculated
    private static final double WHEEL_DIAMETER_INCHES   = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    //--- (1440 * 0.5382) / (4 * 3.1415) = 775.008 / 12.566 = 61.674

    //--- Gyro
    static final double GYRO_TURN_SPEED         = 0.5;  //--- Half speed for accuracy
    static final double GYRO_HEADING_THRESHOLD  = 1;
    static final double GYRO_P_TURN_COEFF       = 0.1;  //--- Larger is more responsive, but also less stable
    static final double GYRO_P_DRIVE_COEFF      = 0.15; //--- Larger is more responsive, but also less stable

    //--- Autonomous
    static final double AUTO_MAXTIMEOUT         = 30000; //--- Max timeout in Auto is 30 seconds
    static final double AUTO_MAXTIMEOUT_S       = 30;    //--- Max timeout in Auto is 30 seconds

    //--- Testing
    static final boolean SLEEP_BETWEEN_MOVES = false;

    //endregion
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    //region --- Global Variables
    //----------------------------------------------------------------------
    double MECANUM_SPEED         = MECANUM_SPEED_HIGH;
    String MECANUM_SPEED_DISPLAY = MECANUM_SPEED_HIGH_DISPLAY;
    double MECANUM_SPEED_STRAFE  = MECANUM_SPEED_STRAFE_HIGH;
    String DISPLAY_MESSAGE       = "";

    //--- Gyro Angles
    Orientation angleCurrent = new Orientation();
    double angleGlobal;
    double angleCorrection;
    double GYRO_HEADING;

    //--- Timers
    long TIMER_START_AUTO = 0;
    long TIMER_REMAINING_AUTO = 0;

    //endregion
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    //--- Called when we Initialize our Robot's Hardware from OpModes
    //----------------------------------------------------------------------
    public void init(HardwareMap hwMap)
    {
        //----------------------------------------------------------------------
        //region --- Setup Motors
        //----------------------------------------------------------------------
        //--- Match motors to their configuration names
        leftFrontMotor  = hwMap.get(DcMotor.class, "lf");
        leftRearMotor   = hwMap.get(DcMotor.class, "lr");
        rightFrontMotor = hwMap.get(DcMotor.class, "rf");
        rightRearMotor  = hwMap.get(DcMotor.class, "rr");

//        shooterOneMotor = hwMap.get(DcMotor.class, "s1");
//        shooterTwoMotor = hwMap.get(DcMotor.class, "s2");

        //--- Set Motor Direction
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);

//        shooterOneMotor.setDirection(DcMotor.Direction.REVERSE);
//        shooterTwoMotor.setDirection(DcMotor.Direction.FORWARD);

        //--- Reset encoders.
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //--- Set all motors to zero power
        leftFrontMotor.setPower(0);
        leftRearMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightRearMotor.setPower(0);

//        shooterOneMotor.setPower(1);
//        shooterTwoMotor.setPower(1);

        //--- Set all motors to run using encoders.
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //endregion
        //----------------------------------------------------------------------

        //----------------------------------------------------------------------
        //--- Initialize IMU
        //----------------------------------------------------------------------
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                 = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit            = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled       = false;
        imu                             = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
}