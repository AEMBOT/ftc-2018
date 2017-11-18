package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.sun.tools.javac.util.Position;

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


    @Override
    public void runOpMode() throws InterruptedException
    {

        //region Hardware Variables
        motorLeft1 = hardwareMap.dcMotor.get("motorLF");
        motorRight1 = hardwareMap.dcMotor.get("motorRF");
        motorLeft2 = hardwareMap.dcMotor.get("motorLB");
        motorRight2 = hardwareMap.dcMotor.get("motorRB");
        LiftMotor = hardwareMap.dcMotor.get("LiftMotor");

        //Grabber Servo 1 (left)
        Servo1 = hardwareMap.servo.get("servo1");

        //Grabber Servo 2 (right)
        Servo2 = hardwareMap.servo.get("servo2");

        //TBD
        Servo3 = hardwareMap.servo.get("servo3");

        //endregion

        motorLeft1.setDirection(DcMotor.Direction.REVERSE);
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        Boolean SwitchMode = false;

        while(opModeIsActive())
        {
            //region Change Servo Position
            if (gamepad2.dpad_right)
            {
                Servo1.setPosition(Servo1.getPosition() + 0.01);
                Servo2.setPosition(Servo2.getPosition() - 0.01);
            }
            if (gamepad2.dpad_left)
            {
                Servo1.setPosition(Servo1.getPosition() - 0.01);
                Servo2.setPosition(Servo2.getPosition() + 0.01);
            }
            //endregion

            //region Setting Motors

            //Tank Drive
            motorLeft1.setPower(-gamepad1.left_stick_y);
            motorLeft2.setPower(-gamepad1.left_stick_y);
            motorRight1.setPower(-gamepad1.right_stick_y);
            motorRight2.setPower(-gamepad1.right_stick_y);

            //Reverse Opposite Motors
            motorLeft1.setDirection(DcMotor.Direction.REVERSE);
            motorLeft2.setDirection(DcMotor.Direction.REVERSE);

            //Move Forward
            if(gamepad1.dpad_up)
            {
                motorLeft1.setPower(1);
                motorRight1.setPower(1);
                motorLeft2.setPower(1);
                motorRight2.setPower(1);
            }

            //Move Backward
            if(gamepad1.dpad_down)
            {
                motorLeft1.setPower(-1);
                motorRight1.setPower(-1);
                motorLeft2.setPower(-1);
                motorRight2.setPower(-1);
            }

            //Move Left
            if(gamepad1.dpad_right)
            {
                motorLeft1.setPower(-1);
                motorLeft2.setPower(1);
                motorRight1.setPower(1);
                motorRight2.setPower(-1);
            }

            //Move Right
            if(gamepad1.dpad_left)
            {
                motorLeft1.setPower(1);
                motorLeft2.setPower(-1);
                motorRight1.setPower(-1);
                motorRight2.setPower(1);
            }

            if (gamepad2.dpad_up)
            {
                LiftMotor.setPower(1);
                Thread.sleep(500);
                LiftMotor.setPower(0);
            }

            if (gamepad2.dpad_down)
            {
                LiftMotor.setPower(-1);
                Thread.sleep(500);
                LiftMotor.setPower(0);
            }

            if (gamepad2.left_bumper)
            {
                Servo1.setPosition(1);
                Servo2.setPosition(1);
            }
            if (gamepad2.right_bumper)
            {
                Servo1.setPosition(0);
                Servo2.setPosition(0);
            }


            //endregion
        }

    }


}

