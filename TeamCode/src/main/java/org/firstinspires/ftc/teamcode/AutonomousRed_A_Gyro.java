package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@Disabled
@Autonomous(name="(RED) A [Full->*P1(Wall)] GYRO", group="Red")
public class AutonomousRed_A_Gyro extends PhoenixBotSharedCode
{
    //----------------------------------------------------------------------
    //--- Strategy Details ---
    //--- Starting Postion: P2 - Face Center (back edge align on arena crack)
    //--- Get the close skystone, deliver to foundation, turn foundation, 
    //---  pull 45 degree diagnal back and park in P1
    //----------------------------------------------------------------------
    static final double GYRO_ANGLE_BACKWALL = -90.0;
    static final double GYRO_ANGLE_FRONTWALL = 90.0;
    static final double GYRO_ANGLE_CENTER = 0.0;
    static final double GYRO_ANGLE_STARTWALL = 180.0;
    
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
        //InitSkystoneArms();
        
        ShowMessage("(RED) A [Full->*P1(Wall)] GYRO");
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
        SpacerDown();                           //--- Brick spacer down 
        LifterToPosition(500);                  //--- Raise lifter (releases arm)
        GyroDrive(0.8, 26, GYRO_ANGLE_CENTER);  //--- Move forward to stones
        GyroTurn(GYRO_ANGLE_CENTER);            //--- Face the stones
        ScanForSkystone(0.2,-24,0,10);          //--- *** Color Sensor Scan for Skystone
        
        //--- After scanning for Skystone, correct to center of stone
        if (robot.TIME_SKYSTONE_SEARCH < 2300)  //--- Skystone 1
        {
            StrafeLeft(1,1,5);                 //--- *** Strafe to center
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 2301 && robot.TIME_SKYSTONE_SEARCH < 3200) //--- Skystone 2
        {
            StrafeRight(1,3,5);                  //--- *** Strafe to center
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 3201) //--- Skystone 3
        {
            StrafeLeft(1,1,5);                 //--- *** Strafe to center
        }

        EncoderDriveAccel(0.5,3,3,3,0);         //--- Move forward slightly to stones
        BrickIntakeInStart();                   //--- Intake servos on
        LifterToPosition(0);                    //--- Pick up stone
        SmartSleep(1500);                       //--- Wait while picking up stone
        BrickIntakeStop();                      //--- Intake servos stop
        
        EncoderDrive(1,-5,-5);                  //--- Move back to give space for turn
        GyroTurn(GYRO_ANGLE_BACKWALL);          //--- Face the back wall

        //--- Drive under the bridge, distance based on which Skystone we found
        if (robot.TIME_SKYSTONE_SEARCH < 2300)  //--- Skystone 1
        {
            GyroDrive(1, 75, GYRO_ANGLE_BACKWALL);
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 2301 && robot.TIME_SKYSTONE_SEARCH < 3200) //--- Skystone 2
        {
            GyroDrive(1, 83, GYRO_ANGLE_BACKWALL);
        }
        else if (robot.TIME_SKYSTONE_SEARCH > 3201) //--- Skystone 3
        {
            GyroDrive(1, 91, GYRO_ANGLE_BACKWALL);
        }

        LifterToPosition(1000);                 //--- Raise lifter
        SpacerUp();                             //--- Move spacers up
        GyroTurn(GYRO_ANGLE_CENTER);            //--- Turn to face founcation

        CaptureFoundation(2300);                //--- Move forward, using touch sensors to grab foundation
        LifterToPosition(100);                  //--- Lower stone to foundation
        
        Strafe45(-1);                           //--- *** Strafe backwards with the foundation away from wall
        SmartSleep(1000);                       //--- ...for seconds
        
        EncoderDrive(1,-30,-30);                //--- Drag foundation backwards
        GyroTurn(1, GYRO_ANGLE_BACKWALL, 5000); //--- Turn foundation towards back wall
        EncoderDrive(1,15,15);                  //--- Push foundation into wall

        SpacerUp();                             //--- Release the foundation
        OpenWhiskers();                         //--- Release the foundation
        DropStoneOnFoundation();                //--- Raise lifter, releasing stone without dislodging
        
        EncoderDrive(1,-5,-5);                  //--- Move away from foundation
        GyroTurn(1, 80, 5000);                  //--- *** Turn towards bridge

        LifterToPosition(0);                    //--- Lower arm before parking

        robot.tapeForwardMotor.setPower(1);            //--- Tapemeasure over parking
        GyroTurn(GYRO_ANGLE_FRONTWALL);         //--- Straighten out to face bridge
        PlayVictorySound();
        SmartSleep(1500);
        robot.tapeForwardMotor.setPower(0);

        SmartSleep(10000);
        StopMotors();
    }
}