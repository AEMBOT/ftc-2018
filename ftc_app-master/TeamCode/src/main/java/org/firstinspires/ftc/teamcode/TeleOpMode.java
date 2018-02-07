package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "TeleOpMode", group = "Test")
public class TeleOpMode extends LinearOpMode
{
    private DcMotor motorLeft1;
    private DcMotor motorRight1;
    private DcMotor motorLeft2;
    private DcMotor motorRight2;
    private DcMotor LiftMotor;
    private Servo servo1;
    private Servo servo2;
    private Servo servo3;
    private Servo servo4;
    private Servo colorServo;
    private TouchSensor touchSensor;
    private boolean fastMode = false;
    private double MotorSpeed = 2;
    private double HalfMotor = 0.5;
    private boolean HasResetServo = false;
    private boolean GrabberClosed = false;
    private boolean RelicGrabberBend1 = false;
    private boolean RelicGrabberBend2 = false;
    private DcMotor RelicMotor;
    private boolean  Avaliable = true;


    @Override
    public void runOpMode() throws InterruptedException
    {

        //region Hardware Variables
        motorLeft1 = hardwareMap.dcMotor.get("motorLF");
        motorRight1 = hardwareMap.dcMotor.get("motorRF");
        motorLeft2 = hardwareMap.dcMotor.get("motorLB");
        motorRight2 = hardwareMap.dcMotor.get("motorRB");
        LiftMotor = hardwareMap.dcMotor.get("LiftMotor");
        colorServo = hardwareMap.servo.get("colorServo");
        touchSensor = hardwareMap.touchSensor.get("touch");
        RelicMotor = hardwareMap.dcMotor.get("RelicMotor");

        //Grabber Servo 1 (left)
        servo1 = hardwareMap.servo.get("Servo1");
        //Grabber Servo 2 (right
        servo2 = hardwareMap.servo.get("Servo2");
        //Relic Serv
        servo3 = hardwareMap.servo.get("Servo3");
        servo4 = hardwareMap.servo.get("Servo4");

        //endregion

        motorLeft1.setDirection(DcMotor.Direction.REVERSE);
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive())
        {

            //Changing the speed of the robot to make it faster or slower
            if(fastMode == true)
            {
                MotorSpeed = 1;
                HalfMotor = 1;
            }
            else
            {
                MotorSpeed = 2;
                HalfMotor = 0.5;
            }

            if(HasResetServo == false) {
                HasResetServo = true;
                colorServo.setPosition(0.9);
            }
            //Region Setting Motors
            //Tank Drive
            motorLeft1.setPower(-gamepad1.left_stick_y / MotorSpeed);
            motorLeft2.setPower(-gamepad1.left_stick_y /  MotorSpeed);
            motorRight1.setPower(-gamepad1.right_stick_y / MotorSpeed);
            motorRight2.setPower(-gamepad1.right_stick_y / MotorSpeed);

            //Move Forward
            if(gamepad1.dpad_up)
            {
                motorLeft1.setPower(HalfMotor);
                motorRight1.setPower(HalfMotor);
                motorLeft2.setPower(HalfMotor);
                motorRight2.setPower(HalfMotor);
            }




            //Move Backward
            if(gamepad1.dpad_down)
            {
                motorLeft1.setPower(-HalfMotor);
                motorRight1.setPower(-HalfMotor);
                motorLeft2.setPower(-HalfMotor);
                motorRight2.setPower(-HalfMotor);
            }

            //Move Left
            if(gamepad1.b || gamepad1.right_bumper || gamepad1.dpad_right)
            {
                motorLeft1.setPower(-HalfMotor);
                motorLeft2.setPower(HalfMotor);
                motorRight1.setPower(HalfMotor);
                motorRight2.setPower(-HalfMotor);
            }

            //Move Right
            if(gamepad1.x || gamepad1.left_bumper || gamepad1.dpad_left)
            {
                motorLeft1.setPower(HalfMotor);
                motorLeft2.setPower(-HalfMotor);
                motorRight1.setPower(-HalfMotor);
                motorRight2.setPower(HalfMotor);
            }

            if(gamepad2.left_trigger > 0) {

                //Up
                LiftMotor.setPower(-gamepad2.left_trigger);
            }
            else if (gamepad2.right_trigger > 0) {
                //Down
                LiftMotor.setPower(gamepad2.right_trigger);
            }
            else {
                LiftMotor.setPower(0);
            }

            //Open Grabber
            if (gamepad2.left_bumper)
            {
                servo1.setPosition(.2);
                servo2.setPosition(.8);

            }

            //Close Grabber
            if (gamepad2.right_bumper)
            {
                servo1.setPosition(1);
                servo2.setPosition(0);
            }

            //endregion
        }

    }

}

