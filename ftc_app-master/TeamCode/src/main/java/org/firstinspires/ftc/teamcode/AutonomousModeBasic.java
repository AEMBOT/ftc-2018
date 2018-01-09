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
@Autonomous(name = "AutonomousModeBasic")
public class AutonomousModeBasic extends LinearOpMode {

    //region Vuforia Stuffs
    public static final String TAG = "AutonomousMode";

    //Define The Robots Last location
    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;
    //endregion

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

    private DcMotor LiftMotor;

    //Define Servo That Has the Color Sensor Attached
    private Servo ColorServo;

    private ColorSensor ColorSensor;


    //All OpMode Code Is Stored Here And Ran From Here
    @Override
    public void runOpMode() throws InterruptedException {

        //Assign Motor Variables To The Motor Names
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorLB = hardwareMap.dcMotor.get("motorLB");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        ColorServo = hardwareMap.servo.get("colorServo");
        ColorSensor = hardwareMap.colorSensor.get("color");
        LiftMotor = hardwareMap.dcMotor.get("LiftMotor");

        int b = 1;


        motorLF.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);

        //Wait Till Start Button Is Pressed
        waitForStart();

        ColorServo.setPosition(1);

        motorLF.setPower(0.5);
        motorRF.setPower(0.5);
        motorLB.setPower(0.5);
        motorRB.setPower(0.5);
        Thread.sleep(700);

    }




}

