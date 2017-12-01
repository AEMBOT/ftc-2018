package org.firstinspires.ftc.teamcode;

//region Imports
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
//endregion

//Define Program Name
@Autonomous(name = "AutonomousModeBlue")
public class AutonomousModeBlue_Left extends LinearOpMode {


    //Define Servo1
    private Servo Servo1;

    //Defie Motor Left Front
    private DcMotor motorLF;

    //Definne Motor Right Fornt
    private DcMotor motorRF;

    //Define Motor Right Back
    private DcMotor motorLB;

    //Define Motor Right Back
    private DcMotor motorRB;

    //Define Servo That Has the Color Sensor Attached
    private Servo ColorServo;

    private ColorSensor ColorSensor;


    //All OpMode Code Is Stored Here And Ran From Here
    @Override
    public void runOpMode() throws InterruptedException {

        //region Assign Variable Names To Hardware Names
        //Assign Motor Variables To The Motor Names
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorLB = hardwareMap.dcMotor.get("motorLB");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        ColorServo = hardwareMap.servo.get("colorServo");
        ColorSensor = hardwareMap.colorSensor.get("color");
        //endregion

        //Define Color Values
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        int b = 1;

        //Reverse Direction Of Left Front And Left Back For Tank Drive
        motorLF.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);

        //Wait Till Start Button Is Pressed
        waitForStart();

        //Run Code Inside When Play Button Is Pressed
        while (opModeIsActive())
        {
            //Convert Color Scheme From HSV To rGB
            Color.RGBToHSV(ColorSensor.red() * 8, ColorSensor.green() * 0, ColorSensor.blue() * 8, hsvValues);
            telemetry.addData("Red ", ColorSensor.red());
            telemetry.addData("Green ", ColorSensor.green());
            telemetry.addData("Blue ", ColorSensor.blue());
            telemetry.update();

           ColorServo.setPosition(0);
           Thread.sleep(200);
            //If Color Is Red
            if (ColorSensor.red() >= 2 && b == 1)
            {
                ColorCounterClockwise();
                b = 2;
            }
            //If  Color Is Blue
            else if (ColorSensor.blue() >= 2 && b == 1)
            {
                ColorClockwise();
                b = 2;
            }
            //If Neither Do Nothing
            else
            {
                motorLF.setPower(0);
                motorRF.setPower(0);
                motorLB.setPower(0);
                motorRB.setPower(0);
            }

        }
    }

    public void ColorClockwise() throws InterruptedException {
        TurnClockwise();
        Thread.sleep(200);
        ColorServo.setPosition(1);

    }
    public void ColorCounterClockwise() throws InterruptedException {
        TurnCounterClockwise();
        Thread.sleep(200);
        ColorServo.setPosition(1);

    }

    public void TurnClockwise()
    {
        motorLB.setPower(1);
        motorLF.setPower(1);
        motorRB.setPower(-1);
        motorRF.setPower(-1);
    }

    public void TurnCounterClockwise ()
    {
        motorLB.setPower(-1);
        motorLF.setPower(-1);
        motorRB.setPower(1);
        motorRF.setPower(1);
    }

}
