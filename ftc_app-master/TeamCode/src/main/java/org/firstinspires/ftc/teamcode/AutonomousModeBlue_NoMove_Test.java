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
@Autonomous(name = "AutonomousModeBlue_NoMove_Test")
public class AutonomousModeBlue_NoMove_Test extends LinearOpMode {


    //Define Servo1
    private Servo Servo1;

    private Servo Servo2;

    //Defie Motor Left Front
    private DcMotor motorLF;

    //Definne Motor Right Fornt
    private DcMotor motorRF;

    //Define Motor Right Back
    private DcMotor motorLB;

    //Define Motor Right Back
    private DcMotor motorRB;

    private DcMotor motorLU;

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
        Servo2 = hardwareMap.servo.get("Servo2");
        ColorSensor = hardwareMap.colorSensor.get("color");
        motorLU = hardwareMap.dcMotor.get("LiftMotor");

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

            if(b != 2) {
                ColorServo.setPosition(0);
            }
           Thread.sleep(200);
            //If Color Is Red
            if (ColorSensor.red() >= 1 && b == 1)
            {
                ColorClockwise();
                b = 2;
            }
            //If Color Is Blue
            if (ColorSensor.blue() >= 1 && b == 1)
            {
                ColorCounterClockwise();
                b = 2;
            }

            else
            {
                Thread.sleep(1000);

                if(b == 1) {
                    motorLB.setPower(-0.2);
                    motorLF.setPower(-0.2);
                    motorRB.setPower(-0.2);
                    motorRF.setPower(-0.2);
                    Thread.sleep(200);
                    PowerOff();
                }
            }

        }
    }

    public void ColorClockwise() throws InterruptedException {
        TurnClockwise();
        Thread.sleep(300);
        ColorServo.setPosition(1);
        Thread.sleep(500);
        PowerOff();
    }
    public void ColorCounterClockwise() throws InterruptedException {
        TurnCounterClockwise();
        Thread.sleep(300);
        ColorServo.setPosition(1);
        Thread.sleep(500);
        PowerOff();
    }

    public void PowerAll()
    {
        motorLB.setPower(0.5);
        motorLF.setPower(0.5);
        motorRB.setPower(0.5);
        motorRF.setPower(0.5);
    }

    public void PowerOff()
    {
        motorLB.setPower(0);
        motorLF.setPower(0);
        motorRB.setPower(0);
        motorRF.setPower(0);
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
        motorLB.setPower(-0.5);
        motorLF.setPower(-0.5);
        motorRB.setPower(0.5);
        motorRF.setPower(0.5);
    }

    //Function That Calls Other Functions That Makes The Bot Drive Off The Platform And Into The Safe Zone
    public void MoveToBox() throws InterruptedException {

        //Power All Motors
        PowerAll();
        Thread.sleep(700);

        //Turn Robot Counter-Clockwise
        TurnCounterClockwise();
        Thread.sleep(300);
        PowerAll();
        Thread.sleep(100);
        PowerOff();


    }

    //Close The Crabber On The Lift
    public void CloseGrabber()
    {
        Servo1.setPosition(0);
        Servo2.setPosition(1);
    }

    //Move The lift Up
    public void MoveLift() throws InterruptedException {
        motorLU.setPower(1);
        Thread.sleep(500);
        motorLU.setPower(0);
    }

}
