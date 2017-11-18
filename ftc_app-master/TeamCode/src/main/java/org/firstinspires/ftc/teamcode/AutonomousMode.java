package org.firstinspires.ftc.teamcode;

//region Imports
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
@Autonomous(name = "AutonomousMode")
public class AutonomousMode extends LinearOpMode {

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

    //Define Servo That Has the Color Sensor Attached
    private Servo ColorServo;

    private ColorSensor ColorSensor;

    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

    public boolean HasMoved = false;

    //All OpMode Code Is Stored Here And Ran From Here
    @Override
    public void runOpMode() throws InterruptedException {

        //Assign Motor Variables To The Motor Names
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorLB = hardwareMap.dcMotor.get("motorLB");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        ColorServo = hardwareMap.servo.get("CS");
        ColorSensor = hardwareMap.colorSensor.get("color");

        //region Vuforia Variables
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXYyTwz/////AAAAGSZopKRDQkUWstJxKlGUqq0dBRoXhOdaJSvoDa+Wq00FKnLAUYSqP4OENg" +
                "v1Q9BVMXFj9LG6L69Wc3fbJuVL7ZetjnVLIzjd9Cn9hvh5rp6HiSJ1rFrlfx0sgtkHda7a/B7HivbiVjfXq+Dta1L3IgQ+" +
                "GSEmvdkXioXG6kA5ZDpQ8yG2o4cyzzvWTBzBQbrHumt1ek8qcGYAiv+552WCDMdTvrMC+NQf5R+" +
                "CQdzub9pHC1rHEu6fCpQT9oq+zM6Vk3TMwxQ8KWhII0AXh5815A0yCvSyMqQFX++empRQj9o/hT6rSf" +
                "z6hsHCeSg/RxptC2TpsfSos14e6rswW/Z/dh45fI3YTNfxxrkfzPjr2NAY";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        //endregion
        motorLF.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);

        //Wait Till Start Button Is Pressed
        waitForStart();

        //Actiavte The Relic Trackable
        relicTrackables.activate();

        while (opModeIsActive()) {
            setUpVuforia(relicTemplate);
        }

    }

    //region Vuforia
    public void setUpVuforia(VuforiaTrackable relicTemplate) throws InterruptedException {
        /**
         * See if any of the instances of {@link relicTemplate} are currently visible.
         * {@link RelicRecoveryVuMark} is an enum which can have the following values:
         * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
         * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
         */
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
            telemetry.addData("VuMark", "%s visible", vuMark);

                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
            telemetry.addData("Pose", format(pose));

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                // Extract the X, Y, and Z components of the offset of the target relative to the robot
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);

                // Extract the rotational components of the target relative to the robot
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            }
        }
        else {
            telemetry.addData("VuMark", "not visible");
        }
        telemetry.update();

        if(vuMark.toString() == "CENTER")
        {
            telemetry.addData("It's The Center", "");
            if(HasMoved != true) {
                MoveIfCenter();
            }
        }

        if(vuMark.toString() == "LEFT")
        {
            telemetry.addData("It's The Left", "");
            if(HasMoved != true) {
                MoveIfLeft();
            }
        }

        if(vuMark.toString() == "RIGHT")
        {
            telemetry.addData("It's The Right", "");

            if(HasMoved != true)
            {
                MoveIfRight();
            }

        }
    }
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    //endregion

    public void MoveIfCenter() throws InterruptedException {
        HasMoved = true;
        motorLF.setPower(1);
        motorRF.setPower(1);
        motorLB.setPower(1);
        motorRB.setPower(1);
        Thread.sleep(75);
        motorLF.setPower(0);
        motorRF.setPower(0);
        motorLB.setPower(0);
        motorRB.setPower(0);
    }

    public void MoveIfLeft() throws InterruptedException {
        HasMoved = true;
        motorLF.setPower(1);
        motorRF.setPower(1);
        motorLB.setPower(1);
        motorRB.setPower(1);
        Thread.sleep(50);
        motorLF.setPower(0);
        motorRF.setPower(0);
        motorLB.setPower(0);
        motorRB.setPower(0);
    }

    public void MoveIfRight() throws InterruptedException {
        HasMoved = true;
        motorLF.setPower(1);
        motorRF.setPower(1);
        motorLB.setPower(1);
        motorRB.setPower(1);
        Thread.sleep(100);
        motorLF.setPower(0);
        motorRF.setPower(0);
        motorLB.setPower(0);
        motorRB.setPower(0);
    }

}

