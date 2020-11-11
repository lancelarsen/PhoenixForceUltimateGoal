package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(RED) G [2+ Skystones->P2] GYRO", group="Red")
public class AutonomousRed_G_Gyro extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Face Stones (back edge align on arena crack)
    //--- Get the close skystone, deliver to foundation, turn foundation, 
    //---  pull 45 degree diagnal back and park in P1
    //----------------------------------------------------------------------
    static final double GYRO_ANGLE_BACKWALL = 0.0;
    static final double GYRO_ANGLE_FRONTWALL = 180.0;
    static final double GYRO_ANGLE_CENTER = 90.0;
    static final double GYRO_ANGLE_STARTWALL = -90.0;
    static final double STRAFE_DIST = 12;
    static final double SHORTDIST_TIMEOUT = 750;
    int    SKYSTONE = 1;
    
    int parkDistance                = -13;
    
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
        InitGyro();
        RedInt();
       
        ShowMessage("(RED) G [2+ Skystones->P2] GYRO");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        sleep(1000);
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);

        //----------------------------------------------------------------------
        //--- Initialize before Start
        //----------------------------------------------------------------------
        SetMotorsToBrake();
        
        //----------------------------------------------------------------------
        //--- Wait for the game to start (driver presses PLAY)
        //----------------------------------------------------------------------
        waitForStart();
        robot.TIMER_START_AUTO = System.currentTimeMillis();
        
        //----------------------------------------------------------------------
        // Set Drive Path
        //----------------------------------------------------------------------
        LifterToPosition(500);                  //--- Raise lifter (releases arm)
        Strafe45(-1);                           //--- *** Strafe backwards 45 degrees
        SmartSleep(1200);                       //--- ...for seconds
        LifterToPosition(0);                    //--- Lower lifter
        StopMotors();                           //--- Stop moving
        
        DriveBackwards(0.7);                    //--- Align back against wall
        SmartSleep(200);                        //--- ...for seconds
        StopMotors();                           //--- Stop moving

        StrafeLeftToShortDistance(0.3, 5000);   //--- Get a set distance from the stones
        SKYSTONE = ScanSkystoneLeftSide();      //--- Scan the stones and determine the skystone location
        ShowMessage (""+SKYSTONE);
       
        if (SKYSTONE == 3)
        {
            ShowMessage("Skystone 3");

            int stoneOneDistFromWall        = 4;
            int stoneOneDistUnderBridge     = 76;
            int stoneTwoDistBack            = -52;
            int stoneTwoDistUnderBridge     = 51;
            int stoneThreeDistBack          = -41;
            int stoneThreeDistUnderBridge   = 41;
            int stoneFourDistBack           = -34;
            int stoneFourDistAdjustForScan  = 2;
            int stoneFourDistUnderBridge    = 34;

            //First Stone
            GyroDrive(1,stoneOneDistFromWall,GYRO_ANGLE_BACKWALL); //--- Move to stone
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneOneDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up, leaving stone
            //Second Stone
            GyroDrive(1,stoneTwoDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next skystone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            //--- *** HAD TO ADJUST HERE ***
            StrafeLeft(1,STRAFE_DIST-1,99);         //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, SHORTDIST_TIMEOUT); //--- Get a set distance from the stones
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneTwoDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
            //Third Stone
            GyroDrive(1,stoneThreeDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next stone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            //--- *** HAD TO ADJUST HERE ***
            StrafeLeft(1,STRAFE_DIST-1,99);         //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, SHORTDIST_TIMEOUT); //--- Get a set distance from the stones
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneThreeDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
            //Fourth Stone
            GyroDrive(1,stoneFourDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next stone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            StrafeLeft(1,STRAFE_DIST,99);           //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 500);    //--- Get a set distance from the stones
            GyroDrive(1,stoneFourDistAdjustForScan,GYRO_ANGLE_BACKWALL); //--- Back up to align with stone, note have to ajust back as distance sensor needed to be centered
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneFourDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
        }
        else if (SKYSTONE == 2)
        {
            ShowMessage("Skystone 2");

            int stoneOneDistFromWall        = 11;
            int stoneOneDistUnderBridge     = 65;
            int stoneTwoDistBack            = -40;
            int stoneTwoDistUnderBridge     = 44;
            int stoneThreeDistBack          = -52;
            int stoneThreeDistUnderBridge   = 52;
            int stoneFourDistBack           = -34;
            int stoneFourDistAdjustForScan  = 3;
            int stoneFourDistUnderBridge    = 36;

            //First Stone
            //StrafeLeft(1,1,99);
            GyroDrive(1,stoneOneDistFromWall,GYRO_ANGLE_BACKWALL); //--- Move to stone
            GyroTurn(GYRO_ANGLE_BACKWALL);
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneOneDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up, leaving stone
            //Second Stone
            GyroDrive(1,stoneTwoDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next skystone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            //--- *** HAD TO ADJUST HERE ***
            StrafeLeft(1,STRAFE_DIST-1,99);         //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 1500);   //--- Get a set distance from the stones
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneTwoDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
            //Third Stone
            GyroDrive(1,stoneThreeDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next stone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            //--- *** HAD TO ADJUST HERE ***
            StrafeLeft(1,STRAFE_DIST-1,99);         //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 250);   //--- Get a set distance from the stones
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneThreeDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
            //Fourth Stone
            GyroDrive(1,stoneFourDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next stone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            StrafeLeft(1,STRAFE_DIST,99);           //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 500);    //--- Get a set distance from the stones
            GyroDrive(1,stoneFourDistAdjustForScan,GYRO_ANGLE_BACKWALL); //--- Back up to align with stone, note have to ajust back as distance sensor needed to be centered
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneFourDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
        }
        else if (SKYSTONE == 1)
        {
            ShowMessage("Skystone 1");
            
            int stoneOneDistFromWall        = 18;
            int stoneOneDistUnderBridge     = 57;
            int stoneTwoDistBack            = -36;
            int stoneTwoDistAdjustForScan   = 4;
            int stoneTwoDistUnderBridge     = 36;
            int stoneThreeDistBack          = -44;
            int stoneThreeDistUnderBridge   = 41;
            int stoneFourDistBack           = -49;
            int stoneFourDistUnderBridge    = 50;
            
            //First Stone
            //StrafeLeft(1,2,99);
            GyroDrive(1,stoneOneDistFromWall,GYRO_ANGLE_BACKWALL); //--- Move to stone
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneOneDistUnderBridge,1500); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up, leaving stone
            //Second Stone
            GyroDrive(1,stoneTwoDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next skystone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            //--- *** HAD TO ADJUST HERE ***
            StrafeLeft(1,STRAFE_DIST-1,99);         //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 250);   //--- Get a set distance from the stones
            GyroDrive(1,stoneTwoDistAdjustForScan,99);
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneTwoDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
            //Third Stone
            GyroDrive(1,stoneThreeDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next stone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            //--- *** HAD TO ADJUST HERE ***
            StrafeLeft(1,STRAFE_DIST-1,99);         //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 250);    //--- Get a set distance from the stones
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneThreeDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
            //Fourth Stone
            GyroDrive(1,stoneFourDistBack,GYRO_ANGLE_BACKWALL); //--- Move back to next stone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            StrafeLeft(1,STRAFE_DIST,99);           //--- Strafe right to stone
            StrafeLeftToShortDistance(0.3, 250);    //--- Get a set distance from the stones
            LSidearmDown(500);                      //--- Skystone arm down
            StrafeRight(1,STRAFE_DIST,99);          //--- Strafe away from other stones
            GyroDrive(1,stoneFourDistUnderBridge,GYRO_ANGLE_BACKWALL); //--- Move forward under bridge
            LSidearmUp(500);                        //--- Skystone arm up
        }

        //Park
        robot.tapeForwardMotor.setPower(1);                //--- Tapemeasure over parking
        PlayVictorySound();
        SetMotorsToFloat();                         //--- Turn off Motor Brakes as we just want to go back!
        EncoderDrive(1,parkDistance,parkDistance);
        robot.tapeForwardMotor.setPower(0);                //--- Tapemeasure over parking

        SmartSleep(10000);
        StopMotors();
    }
}