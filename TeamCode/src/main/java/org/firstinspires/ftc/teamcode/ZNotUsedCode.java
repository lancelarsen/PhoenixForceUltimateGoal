package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;


public class ZNotUsedCode {

    /*
     * This method scales the joystick input so for low joystick values, the 
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
        
        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        
        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
    
    /*
        void MoveReachArm()
    {
        //--- gamepad2
        //--- right_trigger, right_bumper
        
        //-- Horizontal (Left/Right)
        double shoulderPostion = 0;
        double elbowPostion = 0;
        if (gamepad2.left_trigger > 0.5)
        {
            shoulderPostion = robot.reachArmShoulderServo.getPosition() + 0.03;
            elbowPostion = robot.reachArmElbowServo.getPosition() - 0.03;
            
            if (shoulderPostion > 0.62) shoulderPostion = 0.62;

            robot.reachArmShoulderServo.setPosition(shoulderPostion);
            robot.reachArmElbowServo.setPosition(elbowPostion);
        }
        else if (gamepad2.left_bumper)
        {
            shoulderPostion = robot.reachArmShoulderServo.getPosition() - 0.03;
            elbowPostion = robot.reachArmElbowServo.getPosition() + 0.03;
            
            if (elbowPostion > 0.65) elbowPostion = 0.65;
            
            robot.reachArmShoulderServo.setPosition(shoulderPostion);
            robot.reachArmElbowServo.setPosition(elbowPostion);
            
            if (!soundPlaying)
            {
                PlaySound(soundXFile); // ** Song Sound
            }
            robot.blinkinLedDriver1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
        }
    }
    */
    
    /*
    void SafePostionForEye()
    {
        ShowMessage("Eye Safe");
        robot.horizontalEyeServo.setPosition(0.386);    //-- Safe (Centered'ish)
        robot.verticalEyeServo.setPosition(1);          //-- Safe (Down)
        robot.blinkEyeServo.setPosition(0.869);         //-- Closed
    }
    */
    
    /*
    void MoveEye()
    {
        //--- gamepad2
        //--- right_stick_x, right_stick_y, right_stick_button
        
        //-- Vertical (Up/Down)
        double verticalPostion = 0;
        if (gamepad2.right_stick_y > 0.1)
        {
            verticalPostion = robot.verticalEyeServo.getPosition() + 0.007;
            robot.verticalEyeServo.setPosition(verticalPostion);
        }
        else if (gamepad2.right_stick_y < -0.1)
        {
            verticalPostion = robot.verticalEyeServo.getPosition() - 0.007;
            if (verticalPostion < 0.3) verticalPostion = 0.3;
            robot.verticalEyeServo.setPosition(verticalPostion);
        }

        //-- Horizontal (Left/Right)
        double horizontalPostion = 0;
        if (gamepad2.right_stick_x > 0.1)
        {
            horizontalPostion = robot.horizontalEyeServo.getPosition() + 0.007;
            if (horizontalPostion > 0.55) horizontalPostion = 0.55;
            robot.horizontalEyeServo.setPosition(horizontalPostion);
        }
        else if (gamepad2.right_stick_x < -0.1)
        {
            horizontalPostion = robot.horizontalEyeServo.getPosition() - 0.007;
            robot.horizontalEyeServo.setPosition(horizontalPostion);
        }
        
        //--- Blink
        if (gamepad2.right_stick_button)
        {
            ShowMessage("Close Eye");
            robot.blinkEyeServo.setPosition(0.869); //-- Closed
        }
        else if (gamepad2.x)
        {
            ShowMessage("Open Eye");
            robot.blinkEyeServo.setPosition(0.7); //-- Open            
        }
        
        //boolean changed = false, on = false; //Outside of loop()
        //if(gamepad1.a && !changed) {
        //    servo.setPosition(on ? 1 : 0);
        //    on = !on;
        //    changed = true;
        //} else if(!gamepad1.a) changed = false;
    }
*/

/*
    void EyeToSafePosition()
    {
        //--- gamepad2
        //--- left_stick_button
        
        //--- Safe position
        if (gamepad2.left_stick_button)
        {
            SafePostionForEye();
        }        
    }
*/

/*
    void TestMotors()
    {
        if (gamepad1.a)
        {
            robot.leftFrontMotor.setPower(0.2);
        }
        else if (gamepad1.b)
        {
            robot.rightFrontMotor.setPower(0.2);
        }
        else if (gamepad1.x)
        {
            robot.leftRearMotor.setPower(0.2);
        }
        else if (gamepad1.y)
        {
            robot.rightRearMotor.setPower(0.2);
        }
        else
        {
            StopMotors();
        }
    }
*/

/*
  
      public void EncoderDrive2(
        double speed, double leftInches, double rightInches, double AccelerationInches, int Direction) 
    {
        int NewLeftTarget;
        int NewRightTarget;
        int RightPosition;
        int LeftPosition;
        double LeftPower;
        double RightPower;

        //----------------------------------------------------------------------
        //--- Reset Encoders
        //----------------------------------------------------------------------
        ResetEncoders();

        //-- Checks to make sure that encoders are reset
        while(robot.leftFrontMotor.getCurrentPosition() > 1 && robot.rightFrontMotor.getCurrentPosition()> 1)
        {
            sleep(25);
        }

        if (opModeIsActive()) 
        {
            //----------------------------------------------------------------------
            // Determine new target position, and pass to motor controller
            // Calculates the needed encoder ticks by multiplying a pre-determined amount of CountsPerInches,
            // and the method input gets the actual distance travel in inches
            //----------------------------------------------------------------------
            NewLeftTarget = robot.leftFrontMotor.getCurrentPosition() + (int) (leftInches * robot.COUNTS_PER_INCH);
            NewRightTarget = robot.rightFrontMotor.getCurrentPosition() + (int) (rightInches * robot.COUNTS_PER_INCH);

            // Gets the current position of the encoders at the beginning of the EncoderDrive method
            LeftPosition = robot.leftFrontMotor.getCurrentPosition();
            RightPosition = robot.rightFrontMotor.getCurrentPosition();

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            SetTargetPosition(NewLeftTarget, NewRightTarget);

            ResetEncoders();

            //-- Checks to make sure that encoders are reset
            while(robot.leftFrontMotor.getCurrentPosition() > 1 && robot.rightFrontMotor.getCurrentPosition()> 1)
            {
                sleep(25);
            }

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunToPosition();

            // reset the timeout time and start motion.
            runtime.reset();
            
            // This gets where the motor encoders will be at full position when it will be at full speed.
            double LeftEncoderPositionAtFullSpeed = ((AccelerationInches*(robot.COUNTS_PER_INCH)) + LeftPosition);
            double RightEncoderPositionAtFullSpeed = ((AccelerationInches*(robot.COUNTS_PER_INCH)) + RightPosition);
            boolean Running = true;


            // This gets the absolute value of the encoder positions at full speed - the current speed, and while it's greater than 0, it will continues increasing the speed.
            // This allows the robot to accelerate over a set number of inches, which reduces wheel slippage and increases overall reliability
            while (opModeIsActive() && Running &&
                (robot.leftFrontMotor.isBusy() && robot.leftRearMotor.isBusy() && robot.rightFrontMotor.isBusy() && robot.rightRearMotor.isBusy()))
            {
                // While encoders are not at position
                if (((Math.abs(speed)) - (Math.abs(robot.leftFrontMotor.getPower()))) > .05)
                {
                    // This allows the robot to accelerate over a set distance, rather than going full speed.  This reduces wheel slippage and increases reliability.
                    LeftPower = (Range.clip(Math.abs((robot.leftFrontMotor.getCurrentPosition()) / (LeftEncoderPositionAtFullSpeed)), .15, speed));
                    RightPower =(Range.clip(Math.abs((robot.rightFrontMotor.getCurrentPosition()) / (RightEncoderPositionAtFullSpeed)), .15, speed));

                    robot.leftFrontMotor.setPower(LeftPower*Direction);
                    robot.leftRearMotor.setPower(LeftPower*Direction);
                    robot.rightFrontMotor.setPower(RightPower*Direction);
                    robot.rightRearMotor.setPower(RightPower*Direction);

                    //telemetry.addData("In Accel loop CM", +Distance.getDistance(DistanceUnit.CM));
                    //telemetry.addData("Accelerating", RightEncoderPositionAtFullSpeed);
                    //telemetry.addData("Path1", "Running to %7d :%7d", NewLeftTarget, NewRightTarget);
                    //telemetry.addData("Path2", "Running at %7d :%7d", DriveLeft.getCurrentPosition(), DriveRight.getCurrentPosition());
                    //telemetry.addData("Sections Complete:", +SectionsCompleted);
                    //telemetry.update();
                }
                else if (Math.abs(NewLeftTarget) - Math.abs(robot.leftFrontMotor.getCurrentPosition()) < -1) 
                {
                    Running = false;
                }
                else
                {
                    // Multiplies the speed desired by the direction, which has a value of either 1, or -1, and allows for backwards driving with the ramp up function
                    robot.leftFrontMotor.setPower(speed*Direction);
                    robot.leftRearMotor.setPower(speed*Direction);
                    robot.rightFrontMotor.setPower(speed*Direction);
                    robot.rightRearMotor.setPower(speed*Direction);
                    
                    //telemetry.addData("In Reg loop CM", +Distance.getDistance(DistanceUnit.CM));
                    //telemetry.addData("Path1", "Running to %7d :%7d", NewLeftTarget, NewRightTarget);
                    //telemetry.addData("Path2", "Running at %7d :%7d", DriveLeft.getCurrentPosition(), DriveRight.getCurrentPosition());
                    //telemetry.addData("Sections Complete:", +SectionsCompleted);
                    //telemetry.update();
                }
            }
            
            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            StopMotors();
        }
    }
*/    
    
    
/*
    void EncoderDriveTurn(double speed, double leftInches, double rightInches, double timeout)
    {
        if (opModeIsActive())
        {
            //----------------------------------------------------------------------
            //--- Calculate Rotation Distance
            //----------------------------------------------------------------------
            int left = (int)(leftInches * robot.COUNTS_PER_INCH);
            int right = (int)(rightInches * robot.COUNTS_PER_INCH);

            EncoderDriveByRotationTurn(speed, left, right, timeout);
        }
    }

    void EncoderDriveByRotationTurn(double speed, int encoderLeft, int encoderRight, double timeout)
    {
        if (opModeIsActive())
        {
            ShowMessage("--- Encoder Drive: Start... ---");

            //----------------------------------------------------------------------
            //--- Reset Encoders
            //----------------------------------------------------------------------
            ResetEncoders();

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            SetTargetPosition(encoderLeft, encoderRight);

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunToPosition();

            //----------------------------------------------------------------------
            //--- Start Motion
            //----------------------------------------------------------------------
            robot.leftFrontMotor.setPower(Math.abs(speed));
            robot.leftRearMotor.setPower(Math.abs(speed));
            robot.rightFrontMotor.setPower(Math.abs(speed));
            robot.rightRearMotor.setPower(Math.abs(speed));

            //----------------------------------------------------------------------
            //--- Loop While Active, Time is Left, and Motors are Running
            //----------------------------------------------------------------------
            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftFrontMotor.isBusy() || robot.leftRearMotor.isBusy() || robot.rightFrontMotor.isBusy() || robot.rightRearMotor.isBusy()))
            {
                //--- Display Progress
                //telemetry.addData("",  "--- Encoder Drive ---");
                //telemetry.addData("",  "Running to %7d :%7d", encoderLeft,  encoderRight);
                //telemetry.addData("",  "Running at %7d :%7d",
                //        robot.frontLeftMotor.getCurrentPosition(),
                //        robot.frontRightMotor.getCurrentPosition());
                //telemetry.update();
            }

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            StopMotors();

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            RunUsingEncoders();

            ShowMessage("--- Encoder Drive: Complete! ---");

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
    }
*/    
    
    
    /*
    if (gamepad2.dpad_up)
    {
        //--- Up (no restrictions)
        robot.lifterMotor.setPower(0.3);
    }
    else if (gamepad2.dpad_down)
    { 
        //--- Down (no restrictions)
        robot.lifterMotor.setPower(-0.3);
    }
    else if (gamepad2.left_stick_y > 0 && robot.lifterMotor.getCurrentPosition() >= 0)
    {
        //--- Down (limit how low it can go)
        robot.lifterMotor.setPower(-gamepad2.left_stick_y);
    }
    else if (gamepad2.left_stick_y < 0 && robot.lifterMotor.getCurrentPosition() <= robot.LIFTERARM_TOP_COUNT)
    {
        //--- Up (limit how high it can go)
        robot.lifterMotor.setPower(-gamepad2.left_stick_y);
    }
    else if (gamepad2.left_stick_button)
    {
        //--- Reset position
        robot.lifterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        ShowMessage("Reset Position");
    }
    else
    {
        robot.lifterMotor.setPower(0);   
    }
    */
    
    /*
    boolean LiferArmLower(double timeoutSeconds)
    {
        return EncoderCollectorPosition(0.5,-600, timeoutSeconds);
    }

    void LifterArmRaise(double timeoutSeconds)
    {
        EncoderCollectorPosition(0.5,820, timeoutSeconds);
    }

    boolean EncoderCollectorPosition(double speed, int distance, double timeoutSeconds)
    {
        boolean successful = false;
        if (opModeIsActive())
        {
            ShowMessage("--- Encoder Collector: Start... ---");

            //distance = -1 * distance;

            //----------------------------------------------------------------------
            //--- Reset Encoders
            //----------------------------------------------------------------------
            //robot.rotateArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            //robot.rotateArmMotor.setTargetPosition(distance);

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            //robot.rotateArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //----------------------------------------------------------------------
            //--- Start Motion
            //----------------------------------------------------------------------
            //robot.rotateArmMotor.setPower(Math.abs(speed));

            //----------------------------------------------------------------------
            //--- Loop While Active, Time is Left, and Motors are Running
            //----------------------------------------------------------------------
            //runtime.reset();
            //while (opModeIsActive() && (runtime.seconds() < timeoutSeconds) && (robot.rotateArmMotor.isBusy()))
            //{
                //--- Display Progress
                //telemetry.addData("",  "--- Encoder Collector ---");
                //telemetry.addData("",  "Running to %7d", distance);
                //telemetry.addData("",  "Running at %7d", robot.rotateArmMotor.getCurrentPosition());
                //telemetry.addData("",  "Time at " + runtime.seconds() + " of " + timeoutSeconds);
                //telemetry.update();
            //}

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            //robot.rotateArmMotor.setPower(0);

            //--- Check to see that we were able to move to the specified encoder position
            //successful = (Math.abs(robot.rotateArmMotor.getCurrentPosition()) >= Math.abs(distance));

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            //robot.rotateArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            ShowMessage("--- Encoder Collector: Complete! ---");

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
        return successful;
    }
    */
    
    /*


    void EncoderLifterPosition(double speed, int distance, double timeout)
    {
        if (opModeIsActive())
        {
            ShowMessage("--- Encoder Lifter: Start... ---");

            //distance = -1 * distance;

            //----------------------------------------------------------------------
            //--- Reset Encoders
            //----------------------------------------------------------------------
            robot.lifterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //----------------------------------------------------------------------
            //--- Set the Target Position
            //----------------------------------------------------------------------
            robot.lifterMotor.setTargetPosition(distance);

            //----------------------------------------------------------------------
            //--- Turn On RUN_TO_POSITION
            //----------------------------------------------------------------------
            robot.lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //----------------------------------------------------------------------
            //--- Start Motion
            //----------------------------------------------------------------------
            robot.lifterMotor.setPower(Math.abs(speed));

            //----------------------------------------------------------------------
            //--- Loop While Active, Time is Left, and Motors are Running
            //----------------------------------------------------------------------
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < timeout) && (robot.lifterMotor.isBusy()))
            {

                //--- Display Progress
                //telemetry.addData("",  "--- Encoder Lifter ---");
                //telemetry.addData("",  "Running to %7d", distance);
                //telemetry.addData("",  "Running at %7d",
                //        robot.liftMotor.getCurrentPosition());
                //telemetry.update();
            }

            //----------------------------------------------------------------------
            //--- Stop all Motion
            //----------------------------------------------------------------------
            robot.lifterMotor.setPower(0);

            //----------------------------------------------------------------------
            // Turn off RUN_TO_POSITION
            //----------------------------------------------------------------------
            robot.lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            ShowMessage("--- Encoder Lifter: Complete! ---");

            if (robot.SLEEP_BETWEEN_MOVES) { sleep(3000); }
        }
    }    
    */
    
    
}
