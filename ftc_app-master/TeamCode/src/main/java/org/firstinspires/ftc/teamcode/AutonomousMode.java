package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

@Autonomous(name = "AutonomousMode")
public class AutonomousMode extends LinearOpMode {

    //region Vuforia Stuffs
    public static final String TAG = "AutonomousMode";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;
    //endregion

    private Servo Servo1;
    private DcMotor motorLF;
    private DcMotor motorRF;
    private DcMotor motorLB;
    private DcMotor motorRB;

    @Override
    public void runOpMode() throws InterruptedException {

        //region Vuforia Variables
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AXYyTwz/////AAAAGSZopKRDQkUWstJxKlGUqq0dBRoXhOdaJSvoDa+Wq00FKnL" +
                "AUYSqP4OENgv1Q9BVMXFj9LG6L69Wc3fbJuVL7ZetjnVLIzjd9Cn9hvh5rp6HiSJ1rFrlfx" +
                "0sgtkHda7a/B7HivbiVjfXq+Dta1L3IgQ+GSEmvdkXioXG6kA5ZDpQ8yG2o4cyzzvWTBzBQbr" +
                "Humt1ek8qcGYAiv+552WCDMdTvrMC+NQf5R+CQdzub9pHC1rHEu6fCpQT9oq+zM6Vk3TMwxQ8KWhII0AXh5815A0yCvSyMqQFX++empRQ" +
                "j9o/hT6rSfz6hsHCeSg/RxptC2TpsfSos14e6rswW/Z/dh45fI3YTNfxxrkfzPjr2NAY";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        //endregion

        Servo1 = hardwareMap.servo.get("Servo1");
        motorLF = hardwareMap.dcMotor.get("motorLeft1");
        motorRF = hardwareMap.dcMotor.get("motorRight1");
        motorLB = hardwareMap.dcMotor.get("motorLeft2");
        motorRB = hardwareMap.dcMotor.get("motorRight2");

        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive())
        {
            setUpVuforia(relicTemplate);
        }
    }

    //region Vuforia
    public void setUpVuforia(VuforiaTrackable relicTemplate) {

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
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
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
        } else {
            telemetry.addData("VuMark", "not visible");
        }

        telemetry.update();
    }
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
    //endregion
}

