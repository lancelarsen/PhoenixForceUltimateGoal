package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    public DcMotor leftFrontMotor;
    public DcMotor leftRearMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightRearMotor;
    public DcMotorEx lifterMotor;
    //public DcMotor armMotor;
    public DcMotor tapeForwardMotor;
    public DcMotor tapeBackMotor;

    public Servo leftWhiskerServo;
    public Servo rightWhiskerServo;
    
    public Servo leftSpacerServo;
    public Servo rightSpacerServo;

    public Servo leftSkystoneServo;
    public Servo rightSkystoneServo;
    
    public Servo capElbowServo;
    public Servo capWristServo;

    public CRServo intakeFrontServo; 
    public CRServo intakeBackServo; 

    public Servo gripperServo;

    public RevBlinkinLedDriver blinkinLedDriver1;

    public BNO055IMU imu;
    
    public DigitalChannel leftTouch;
    public DigitalChannel rightTouch;
    
    public ColorSensor leftColorSensor;
    public ColorSensor rightColorSensor;
    public DistanceSensor leftDistanceSensor;
    public DistanceSensor rightDistanceSensor;
    
    public ColorSensor leftFrontColorSensor;
    public ColorSensor leftBackColorSensor;
    public DistanceSensor leftFrontDistanceSensor;
    public DistanceSensor leftBackDistanceSensor;
    
    public ColorSensor rightFrontColorSensor;
    public ColorSensor rightBackColorSensor;
    public DistanceSensor rightFrontDistanceSensor;
    public DistanceSensor rightBackDistanceSensor;
    
    public DistanceSensor centerDistanceSensor;
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
    
    public static final double MID_SERVO            = 0.5;
    public static final double OPEN_SERVO           = 1.0;
    public static final double CLOSE_SERVO          = 0.0;
    
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
    
    //--- Sensor Readings
    int BLOCK_COUNT             = 0;
    String BLOCK_LOCATION       = "left";
    
    //--- Lifter Position
    int LIFTER_TARGET           = 0;
    int ARM_TARGET              = 0;
    
    //--- Reach Arm
    int REACH_ARM_TARGET        = 0;
    
    //--- Gyro Angles
    Orientation angleCurrent    = new Orientation();
    double angleGlobal;
    double angleCorrection;
    
    double GYRO_HEADING;
    
    //--- Color Sensors
    // leftHue is an array that will hold the hue, saturation, and value information.
    float leftHue[] = {0F, 0F, 0F};
    float rightHue[] = {0F, 0F, 0F};
    final double COLOR_SCALE_FACTOR = 255;
    boolean leftBrickExists = false;
    boolean rightBrickExists = false;
    String leftBrickColor = "";
    String rightBrickColor = "";
    long TIME_SKYSTONE_SEARCH = 0;
    
    //--- Timers
    long TIMER_START_AUTO = 0;
    long TIMER_REMAINING_AUTO = 0;
    
    //endregion
    //----------------------------------------------------------------------
    
    //----------------------------------------------------------------------
    //--- Called when we Initialize our Robot's Hardware from OpModes
    //----------------------------------------------------------------------
    public void init(HardwareMap hardwareMap) 
    {
        //----------------------------------------------------------------------
        //region --- Setup Motors
        //----------------------------------------------------------------------
        //--- Match motors to their configuration names
        leftFrontMotor   = hardwareMap.get(DcMotor.class, "lf");
        leftRearMotor    = hardwareMap.get(DcMotor.class, "lr");
        rightFrontMotor  = hardwareMap.get(DcMotor.class, "rf");
        rightRearMotor   = hardwareMap.get(DcMotor.class, "rr");
        lifterMotor      = hardwareMap.get(DcMotorEx.class, "lift");
        tapeForwardMotor = hardwareMap.get(DcMotor.class, "tapef");
        tapeBackMotor    = hardwareMap.get(DcMotor.class, "tapeb");

        //--- Set Motor Direction
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
        lifterMotor.setDirection(DcMotor.Direction.REVERSE);
        tapeForwardMotor.setDirection(DcMotor.Direction.REVERSE);
        tapeBackMotor.setDirection(DcMotor.Direction.REVERSE);
        
        //--- Reset encoders.
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lifterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //--- Set motor mode
        lifterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        tapeForwardMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        tapeBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        //--- Set all motors to zero power
        leftFrontMotor.setPower(0);
        leftRearMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightRearMotor.setPower(0);
        lifterMotor.setPower(0);
        tapeForwardMotor.setPower(0);
        tapeBackMotor.setPower(0);
        
        //--- Set encoder defaults for motors
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

        //--- Set how quickly the motor goes to the position, too high and it will ossolate
        //--- https://ftctechnh.github.io/ftc_app/doc/javadoc/com/qualcomm/robotcore/hardware/DcMotorEx.html
        //--- https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit
        lifterMotor.setPositionPIDFCoefficients(10);
        //endregion
        //----------------------------------------------------------------------

        //----------------------------------------------------------------------
        //region --- Setup Servos
        //----------------------------------------------------------------------
        //--- Match servos to their configuration names
        rightSkystoneServo  = hardwareMap.get(Servo.class, "rsky");     //--- H1 / 0
        intakeBackServo     = hardwareMap.get(CRServo.class, "bint");   //--- H1 / 1
        leftSpacerServo     = hardwareMap.get(Servo.class, "lspacer");  //--- H1 / 2
        rightSpacerServo    = hardwareMap.get(Servo.class, "rspacer");  //--- H1 / 3
        leftWhiskerServo    = hardwareMap.get(Servo.class, "lclaw");    //--- H1 / 4
        rightWhiskerServo   = hardwareMap.get(Servo.class, "rclaw");    //--- H1 / 5

        capElbowServo       = hardwareMap.get(Servo.class, "capelbow"); //--- H10 / 0
        capWristServo       = hardwareMap.get(Servo.class, "capwrist"); //--- H10 / 1
        gripperServo        = hardwareMap.get(Servo.class, "grip");     //--- H10 / 2
        leftSkystoneServo   = hardwareMap.get(Servo.class, "lsky");     //--- H10 / 3
        blinkinLedDriver1   = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin1"); //--- H10 / 4
        intakeFrontServo    = hardwareMap.get(CRServo.class, "fint");    //--- H10 / 5
        
        //--- Set Servo Direction
        leftSpacerServo.setDirection(Servo.Direction.REVERSE);
        rightSkystoneServo.setDirection(Servo.Direction.REVERSE);

        //--- Set all servos to their starting positions
        leftWhiskerServo.setPosition(0.35);         //-- Up
        rightWhiskerServo.setPosition(0.65);        //-- Up
        
        //leftWhiskerServo.setPosition(1);         //-- Up
        //rightWhiskerServo.setPosition(1);        //-- Up
        
        //leftSpacerServo.setPosition(0.51);        //-- Down
        //rightSpacerServo.setPosition(0.47);       //-- Down
        leftSpacerServo.setPosition(1);             //-- Up
        rightSpacerServo.setPosition(0.96);         //-- Up
        
        //capElbowServo.setPosition(0.9);             //-- Down
        capWristServo.setPosition(0.7);            //-- Parallel to body
        capElbowServo.setPosition(0.55);            //-- Up (90 degrees)

        //gripperServo.setPosition(0.33); //--- Closed on Stone
        //gripperServo.setPosition(0); //--- Open 

        ////reachArmElbowServo.setPosition(0);        //-- Closed
        //reachArmShoulderServo.setPosition(0.62);    //-- Closed
        
        //intakeServo.setPower(-1);
        
        //Side Arms 
        //leftSkystoneServo.setPosition(0.03);        //--- Down
        //rightSkystoneServo.setPosition(0.03);       //--- Down
        //leftSkystoneServo.setPosition(0.5);        //--- Up
        //rightSkystoneServo.setPosition(0.65);       //--- Up
        //rightSkystoneServo.setPosition(0.65);
        //intakeFrontServo.setPower(1);
        //intakeBackServo.setPower(1);
        //endregion
        //----------------------------------------------------------------------
  
        //----------------------------------------------------------------------
        //region --- Setup Sensors
        //----------------------------------------------------------------------
        //--- Match sensors to their configuration names
        //--- REV Color / Distance Sensor (Close Distance)
        leftColorSensor     = hardwareMap.get(ColorSensor.class, "lcolor");
        rightColorSensor    = hardwareMap.get(ColorSensor.class, "rcolor");
        leftDistanceSensor  = hardwareMap.get(DistanceSensor.class, "lcolor");
        rightDistanceSensor = hardwareMap.get(DistanceSensor.class, "rcolor");

        leftFrontColorSensor     = hardwareMap.get(ColorSensor.class, "lfcolor");
        leftBackColorSensor      = hardwareMap.get(ColorSensor.class, "lrcolor");
        leftFrontDistanceSensor  = hardwareMap.get(DistanceSensor.class, "lfcolor");
        leftBackDistanceSensor   = hardwareMap.get(DistanceSensor.class, "lrcolor");
            
        rightFrontColorSensor    = hardwareMap.get(ColorSensor.class, "rfcolor");
        rightBackColorSensor     = hardwareMap.get(ColorSensor.class, "rrcolor");
        rightFrontDistanceSensor = hardwareMap.get(DistanceSensor.class, "rfcolor");
        rightBackDistanceSensor  = hardwareMap.get(DistanceSensor.class, "rrcolor");

        //--- REV 2M Distance Center
        centerDistanceSensor = hardwareMap.get(DistanceSensor.class, "dist");

        //--- REV Touch Sensors
        leftTouch  = hardwareMap.get(DigitalChannel.class, "ltouch");
        rightTouch = hardwareMap.get(DigitalChannel.class, "rtouch");

        leftTouch.setMode(DigitalChannel.Mode.INPUT);
        rightTouch.setMode(DigitalChannel.Mode.INPUT);

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
        imu                             = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
    
    //----------------------------------------------------------------------
    //region --- Controller State Variables
    //----------------------------------------------------------------------
    boolean CHANGED_GP2_LEFT_BUMPER = false, ON_GP2_LEFT_BUMPER = false;
    boolean CHANGED_GP2_LEFT_TRIGGER = false, ON_GP2_LEFT_TRIGGER = false;
    
    boolean G2X_CHANGED = false, G2X_ON = false;
    boolean G2B_CHANGED = false, G2B_ON = false;
    //endregion
    //----------------------------------------------------------------------
 }