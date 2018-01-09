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

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
//endregion

//Define Program Name
@Autonomous(name = "AutonomousModeBlue_Right")
public class AutonomousModeBlue_Right extends LinearOpMode {


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
                //If Its Red Turn Counter Clockwise
                ColorClockwise();


                //And Set Run Variable Equal To A Number Other Than 1
                b = 2;
            }
            //If  Color Is Blue
            else if (ColorSensor.blue() >= 2 && b == 1)
            {
                //If Its Blue Turn Clockwise
                ColorCounterClockwise();

                //And Set Run Variable Equal To A Number Other Than 1
                b = 2;
            }

            //If Its Not Red Or Blue, Do Nothing
            else
            {
                motorLF.setPower(0);
                motorRF.setPower(0);
                motorLB.setPower(0);
                motorRB.setPower(0);
            }

        }
    }

    //Define Function That Contains All Of The Code To Move The Robot Based Color, Clockwise
    public void ColorClockwise() throws InterruptedException {

        //Tell Robot To Turn Clockwise
        TurnClockwise();

        //Wait 300 Milliseconds
        Thread.sleep(300);

        //Set Servo That Has The  Color Sensor Attached To It's Upright Position
        ColorServo.setPosition(0.9);

        //Wait 500 Milliseconds
        Thread.sleep(500);

        //Turn Robot Counter Clockwise
        TurnCounterClockwise();

        //Wait 1700 Milliseconds
        Thread.sleep(1700);

        //Power All Motors
        PowerAll();

        //Wait 700 Milliseconds
        Thread.sleep(700);
    }

    //Define Function That Contains All Of The Code To Move The Robot Based Color, Counter Clockwise
    public void ColorCounterClockwise() throws InterruptedException {

        //Turn Bot Counter Clockwise
        TurnCounterClockwise();

        //Wait 0.3 Seconds
        Thread.sleep(300);

        //Set Servo That Holds The Color Sensor To It's Upright Position
        ColorServo.setPosition(0.9);

        //Wait 0.5 Seconds
        Thread.sleep(500);

        //Turn Robot Clockwise
        TurnClockwise();

        //Wait 1 Second
        Thread.sleep(1000);

        //Power All Motors
        PowerAll();

        //Wait 0.7 Seconds
        Thread.sleep(700);

        //Shutoff All Motors
        PowerOff();

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
        motorLB.setPower(1);
        motorLF.setPower(1);
        motorRB.setPower(-1);
        motorRF.setPower(-1);
    }

    //Turn The  Robot Counter Clockwise
    public void TurnCounterClockwise ()
    {
        motorLB.setPower(-0.5);
        motorLF.setPower(-0.5);
        motorRB.setPower(0.5);
        motorRF.setPower(0.5);
    }

    //This Is The Function That Holds All The Code That Tells The Bot To Drive Off The Platform And Into The Safe Zone
    public void MoveToBox() throws InterruptedException {

        //Power All Motors
        PowerAll();

        //Wait 0.7 Seconds
        Thread.sleep(700);

        //Turn Robot Counter-Clockwise
        TurnCounterClockwise();

        //Wait 0.3 Seconds
        Thread.sleep(300);

        //Power All Motors
        PowerAll();

        //Wait 0.1 Seconds
        Thread.sleep(100);

        //Shutoff All Motors
        PowerOff();


    }

    //Close The Crabber On The Lift
    public void CloseGrabber()
    {
        //Set Both Servo Positions To 0 And 1, Thus Closing The Grabber
        Servo1.setPosition(0);
        Servo2.setPosition(1);
    }

    //Move The lift Up
    public void MoveLift() throws InterruptedException {

        //Set The Lift Motor Power To 1
        motorLU.setPower(1);

        //Wait 0.5 Seconds
        Thread.sleep(500);

        //Stop Lift
        motorLU.setPower(0);
    }

}
