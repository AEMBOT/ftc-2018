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
@Autonomous(name = "AutonomousModeRed_Right")
public class AutonomousModeRed_Right extends LinearOpMode {


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

    private DcMotor RelicMotor;

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
        Servo1 = hardwareMap.servo.get("Servo1");
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
            //Convert Color Scheme From HSV To RGB
            Color.RGBToHSV(ColorSensor.red() * 8, ColorSensor.green() * 0, ColorSensor.blue() * 8, hsvValues);
            telemetry.addData("Red ", ColorSensor.red());
            telemetry.addData("Green ", ColorSensor.green());
            telemetry.addData("Blue ", ColorSensor.blue());
            telemetry.update();



            //Check If It Hasn't Sensed A Color
            if(b != 2) {
                ColorServo.setPosition(0);

            }
            //Wait 200 Milliseconds For Servo to Lower
           Thread.sleep(200);

            //If Color Is Red
            if (ColorSensor.red() >= 2 && b == 1)
            {
                //If It's Red Turn Counter Clockwise
                ColorCounterClockwise();


                //And Set Run Variable Equal To A Number Other Than 1
                b = 2;
            }
            //If  Color Is Blue
            else if (ColorSensor.blue() >= 2 && b == 1)
            {
                //If It's Blue Turn Clockwise
                ColorClockwise();

                //And Set Run Variable Equal To A Number Other Than 1
                b = 2;
            }

            //If the color sensor doesn't
            else if (b==1)
            {
                Thread.sleep(1000);

                    motorLB.setPower(-0.1);
                    motorLF.setPower(-0.1);
                    motorRB.setPower(-0.1);
                    motorRF.setPower(-0.1);
                    Thread.sleep(200);
                    PowerOff();
            }

        }
    }

    //Define Function That Contains All Of The Code To Move The Robot Based Color, Clockwise
    public void ColorClockwise() throws InterruptedException {
        //Close grabber to grip block
        CloseGrabber();

        //Wait one second
        Thread.sleep(1000);

        //After waiting move lift upwards to avoid hitting the ground when driving off the platform
        MoveLift();

        //Call function that spins wheels
        TurnClockwise();

        //Wait 300 Milliseconds
        Thread.sleep(500);

        //Set Servo That Has The  Color Sensor Attached To It's Upright Position
        ColorServo.setPosition(0.9);

        //Power All Motors
        PowerAll();

        //Wait 700 Milliseconds
        Thread.sleep(700);

        PowerOff();

        ColorServo.setPosition(0);
    }

    //Define Function That Contains All Of The Code To Move The Robot Based Color, Counter Clockwise
    public void ColorCounterClockwise() throws InterruptedException {

        CloseGrabber();
        Thread.sleep(1000);
        MoveLift();

        //Turn Bot Counter Clockwise
        TurnCounterClockwise();

        Thread.sleep(700);

        //Set Servo That Holds The Color Sensor To It's Upright Position
        ColorServo.setPosition(1);
        //Wait 0.3 Seconds
        Thread.sleep(2000);

        //Power All Motors
        PowerAll();
        //Wait 700 Milliseconds
        Thread.sleep(700);

        PowerOff();

        ColorServo.setPosition(0);

    }

    //This Is The Function That Contains All The Code To Make All The Wheels Move Forward
    public void PowerAll()
    {
        motorLB.setPower(0.5);
        motorLF.setPower(0.5);
        motorRB.setPower(0.5);
        motorRF.setPower(0.5);
    }

    //This Sets All Of The Motors Power To 0
    public void PowerOff()
    {
        motorLB.setPower(0);
        motorLF.setPower(0);
        motorRB.setPower(0);
        motorRF.setPower(0);
    }

    //Turn The Robot Clockwise
    public void TurnClockwise()
    {
        motorLB.setPower(0.5);
        motorLF.setPower(0.5);
        motorRB.setPower(-0.5);
        motorRF.setPower(-0.5);
    }

    //Turn The  Robot Counter Clockwise
    public void TurnCounterClockwise ()
    {
        motorLB.setPower(-0.5);
        motorLF.setPower(-0.5);
        motorRB.setPower(0.5);
        motorRF.setPower(0.5);
    }

    //Close The Crabber On The Lift
    public void CloseGrabber()
    {
        //Set Both Servo Positions To 0 And 1, Thus Closing The Grabber
        Servo1.setPosition(1);
        Servo2.setPosition(0);
    }

    //Move The lift Up
    public void MoveLift() throws InterruptedException {

        //Set The Lift Motor Power To 1
        motorLU.setPower(-1);

        //Wait 0.5 Seconds
        Thread.sleep(500);

        //Stop Lift
        motorLU.setPower(0);
    }

}
