package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.sun.tools.javac.util.Position;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "TeleOpMode", group = "Test")
public class TeleOpMode extends LinearOpMode
{
    private DcMotor motorLeft1;
    private DcMotor motorRight1;
    private DcMotor motorLeft2;
    private DcMotor motorRight2;
    private DcMotor LiftMotor;
    private Servo Servo1;
    private Servo Servo2;
    private Servo Servo3;
    private Servo ColorServo;
    private TouchSensor touchSensor;
    private boolean SlowMode = false;
    private double MotorSpeed = 1;
    private boolean HasResetServo = false;
    private boolean GrabberClosed = false;


    @Override
    public void runOpMode() throws InterruptedException
    {

        //region Hardware Variables
        motorLeft1 = hardwareMap.dcMotor.get("motorLF");
        motorRight1 = hardwareMap.dcMotor.get("motorRF");
        motorLeft2 = hardwareMap.dcMotor.get("motorLB");
        motorRight2 = hardwareMap.dcMotor.get("motorRB");
        LiftMotor = hardwareMap.dcMotor.get("LiftMotor");
        ColorServo = hardwareMap.servo.get("colorServo");
        touchSensor = hardwareMap.touchSensor.get("touch");

        //Grabber Servo 1 (left)
        Servo1 = hardwareMap.servo.get("Servo1");

        //Grabber Servo 2 (right)
        Servo2 = hardwareMap.servo.get("Servo2");

        //TBD
        Servo3 = hardwareMap.servo.get("Servo3");

        //endregion

        motorLeft1.setDirection(DcMotor.Direction.REVERSE);
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive())
        {

            if(HasResetServo == false) {
                HasResetServo = true;
                ColorServo.setPosition(0.9);
            }
            //Region Setting Motors
            //Tank Drive
            motorLeft1.setPower(-gamepad1.left_stick_y / 2);
            motorLeft2.setPower(-gamepad1.left_stick_y /  2);
            motorRight1.setPower(-gamepad1.right_stick_y / 2);
            motorRight2.setPower(-gamepad1.right_stick_y / 2);

            //Move Forward
            if(gamepad1.dpad_up)
            {
                motorLeft1.setPower(0.5);
                motorRight1.setPower(0.5);
                motorLeft2.setPower(0.5);
                motorRight2.setPower(0.5);
            }

            if(gamepad2.a)
            {
                ColorServo.setPosition(1);
            }

            //Move Backward
            if(gamepad1.dpad_down)
            {
                motorLeft1.setPower(-0.5);
                motorRight1.setPower(-0.5);
                motorLeft2.setPower(-0.5);
                motorRight2.setPower(-0.5);
            }

            //Move Left
            if(gamepad1.b || gamepad1.right_bumper || gamepad1.dpad_right)
            {
                motorLeft1.setPower(-0.5);
                motorLeft2.setPower(0.5);
                motorRight1.setPower(0.5);
                motorRight2.setPower(-0.5);
            }

            //Move Right
            if(gamepad1.x || gamepad1.left_bumper || gamepad1.dpad_left)
            {
                motorLeft1.setPower(0.5);
                motorLeft2.setPower(-0.5);
                motorRight1.setPower(-0.5);
                motorRight2.setPower(0.5);
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
                Servo1.setPosition(0);
                Servo2.setPosition(1);

            }

            //Close Grabber
            if (gamepad2.right_bumper)
            {
                Servo1.setPosition(1);
                Servo2.setPosition(0);
            }

            //endregion
        }

    }


}

