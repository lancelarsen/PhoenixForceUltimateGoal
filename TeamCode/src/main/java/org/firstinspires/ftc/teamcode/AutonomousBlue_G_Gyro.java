package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Autonomous(name="(BLUE) G [2+ Skystones->P2] GYRO", group="Blue")
public class AutonomousBlue_G_Gyro extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Face Stones (back edge align on arena crack)
    //--- Get the close skystone, deliver to foundation, turn foundation, 
    //---  pull 45 degree diagnal back and park in P1
    //----------------------------------------------------------------------
    static final double GYRO_ANGLE_BACKWALL = 0.0;
    static final double GYRO_ANGLE_FRONTWALL = 180.0;
    static final double GYRO_ANGLE_CENTER = -90.0;
    static final double GYRO_ANGLE_STARTWALL = 90.0;
    static final double STRAFE_DIST = 4;
    static final double SHORTDIST_TIMEOUT = 1000;
    int    SKYSTONE = 1;
    
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

        ShowMessage("(BLUE) G [2+ Skystones->P2] GYRO");
        robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
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
        SpacerDown();                           //--- Brick spacer down (allows lift lock arm to fall)
        LifterToPosition(1000);                 //--- Raise lifter (releases arm)
        GripArmRightGrabReady();
        Strafe135(-1);                          //--- *** Strafe backwards 135 degrees
        SmartSleep(1200);                       //--- ...for seconds
        LifterToPosition(0);                    //--- Lower lifter
        SpacerUp();                             //--- Brick spacer up 
        StopMotors();                           //--- Stop moving
        
        DriveBackwards(0.7);                    //--- Align back against wall
        SmartSleep(200);                        //--- ...for seconds
        StopMotors();                           //--- Stop moving

        StrafeRightToShortDistance(0.3, 5000);  //--- Get a set distance from the stones
        SKYSTONE = ScanSkystoneRightSide();     //--- Scan the stones and determine the skystone location

        if (SKYSTONE == 3)
        {
            ShowMessage("Skystone 3");

            int stoneReadyArmDist           = 30;

            int stoneOneDistFromWall        = 4; //4-2
            int stoneOneDistUnderBridge     = 105; //105
            int stoneOneDistStrafe          = 8;
            
            int stoneTwoDistBack            = -85; //-82
            int stoneTwoDistUnderBridge     = 83; //80
            int stoneTwoDistStrafe          = 5;
            
            int stoneThreeDistBack          = -67; //-63
            int stoneThreeDistUnderBridge   = 69; //62
            int stoneThreeDistStrafe        = 0;
            
            int parkDistance                = -16;
            
            //------------------------------------------------------------
            //--- First Stone
            //GyroDrive(1,2,GYRO_ANGLE_BACKWALL); //--- Move forward so we can ready the arm
            //GripArmRightGrabReady();
            StrafeLeft(1,7,99);
            GyroDrive(1,stoneOneDistFromWall,GYRO_ANGLE_BACKWALL); //--- Move to stone
            GripArmRightGrab();
            GyroDriveStoneSpecial(1, stoneOneDistUnderBridge, GYRO_ANGLE_BACKWALL, stoneReadyArmDist, true, false);  //--- Move forward over foundation
            GripArmRightDrop(stoneOneDistStrafe);   //--- Drop stone

            //------------------------------------------------------------
            //--- Second Stone
            GyroDriveStoneSpecial(1, stoneTwoDistBack, GYRO_ANGLE_BACKWALL, stoneReadyArmDist, true, true);  //--- Move back to next skystone
            GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Straighten
            GripArmRightGrab();
            GyroDriveStoneSpecial(1, stoneTwoDistUnderBridge, GYRO_ANGLE_BACKWALL, stoneReadyArmDist, true, false);  //--- Move forward under bridge
            GripArmRightDrop(stoneTwoDistStrafe);   //--- Drop stone
            
            //------------------------------------------------------------
            //--- Third Stone
            GyroDriveStoneSpecial(1, stoneThreeDistBack, GYRO_ANGLE_BACKWALL, stoneReadyArmDist * 0.7, true, true);  //--- Move back to next skystone
            GyroTurn(GYRO_ANGLE_BACKWALL);      //--- Straighten
            GripArmRightGrab();
            GyroDriveStoneSpecial(1, stoneThreeDistUnderBridge, GYRO_ANGLE_BACKWALL, stoneReadyArmDist, true, false);  //--- Move forward under bridge
            GripArmRightDrop(stoneThreeDistStrafe); //--- Drop stone
        }
        else if (SKYSTONE == 2)
        {
            ShowMessage("Skystone 2");

        }
        else if (SKYSTONE == 1)
        {
            ShowMessage("Skystone 1");

        }
    
        SmartSleep(20000);
        StopMotors();
    }
    
    /*
    void GripArmRightGrabReady()
    {
        robot.rightSkystoneServo.setPosition(0.12); //--- Position ready to grab
        robot.gripperServo.setPosition(0);          //--- Open gripper 
    }

    void GripArmRightGrab()
    {
        robot.rightSkystoneServo.setPosition(0);    //--- Lower to stone
        robot.gripperServo.setPosition(0.355);      //--- Close on stone
        sleep(400);
        robot.rightSkystoneServo.setPosition(0.60); //--- Bring stone into robot
    }
    
    void GripArmRightDropReady()
    {
        robot.rightSkystoneServo.setPosition(0.15); //--- Position ready to drop
    }
    
    void GripArmRightDrop()
    {
        robot.rightSkystoneServo.setPosition(0.08);
        robot.gripperServo.setPosition(0);
        sleep(200);
        robot.rightSkystoneServo.setPosition(0.60); //--- Bring stone into robot
        robot.gripperServo.setPosition(0.355);      //--- Open
    }
    */
}

    
    
