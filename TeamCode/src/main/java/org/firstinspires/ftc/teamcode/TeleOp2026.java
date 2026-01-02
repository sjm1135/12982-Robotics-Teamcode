package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "TeleOp2026")
public class TeleOp2026 extends OpMode {


    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotorEx launchMotor;
    private DcMotorEx intakeMotor;
    private CRServo leftLaunchServo;
    private CRServo rightLaunchServo;
    private Servo leftPosControl;
    private Servo rightPosControl;
    private double speedControl = 0.4;
    private double TARGET_RPM = 3000;
    private final double WHEEL_WHEEL_DIAMETER = 4.094; //in inches
    private final double TICKS_PER_REVOLUTION_WHEELS = 2000; //for wheels
    private final double FLYWHEEL_DIAMETER = 3.78;
    private final double FLYWHEEL_TICKS_PER_REVOLUTION = 28;
    private final double INTAKE_TARGET_RPM = 100;
    private final double INTAKE_TICKS_PER_REVOLUTION = 537.7;
    private final double INTAKE_TICKS_PER_SECOND = INTAKE_TICKS_PER_REVOLUTION * INTAKE_TARGET_RPM / 60;
    boolean toggle = false;
    boolean intakeToggle = false;
    private double max = 0;
    @Override
    public void init(){
        frontLeft = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        frontRight = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        backLeft = hardwareMap.get(DcMotor.class, "leftBackDrive");
        backRight = hardwareMap.get(DcMotor.class, "rightBackDrive");
        launchMotor = hardwareMap.get(DcMotorEx.class, "launchLaunchMotor");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeIntakeMotor");
        leftLaunchServo = hardwareMap.get(CRServo.class, "launchLeftServo");
        rightLaunchServo = hardwareMap.get(CRServo.class, "launchRightServo");
        leftPosControl = hardwareMap.get(Servo.class, "leftPos");
        rightPosControl = hardwareMap.get(Servo.class, "rightPos");
        telemetry.addData("Hardware: ", "Initialized");

        launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLaunchServo.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void loop() {
        //basic driving
        double y = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
        double x = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * .5;

        double frontLeftPower  = y + x + rx;
        double frontRightPower = y - x - rx;
        double backLeftPower   = y - x + rx;
        double backRightPower  = y + x - rx;

        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
        //mounted mechanism mechanisms

        //flywheel of greatness
        if (gamepad2.left_trigger > 0) //start flywheel
        {
            toggle = true;
        }
        else if (gamepad2.right_trigger > 0) //stop flywheel
        {
            toggle = false;
        }

        double TICKS_PER_SECOND = FLYWHEEL_TICKS_PER_REVOLUTION * TARGET_RPM / 60;

        if (toggle) {
            launchMotor.setVelocity(TICKS_PER_SECOND);
        }
        else {
            launchMotor.setVelocity(0);
        }

        //intake mechanism
        if (gamepad2.left_bumper == true) //start intake
        {
            intakeToggle = true;
        }
        else if (gamepad2.right_bumper == true) //stop intake
        {
            intakeToggle = false;
        }
        if (intakeToggle)
        {
            intakeMotor.setVelocity(INTAKE_TICKS_PER_SECOND);
        }
        else
        {
            intakeMotor.setVelocity(0);
        }

        double servoPower = gamepad2.left_stick_y;
        leftLaunchServo.setPower(servoPower);
        rightLaunchServo.setPower(servoPower);

        //emergency brake to avoid Stanley Time
        if (gamepad1.b) {
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            launchMotor.setVelocity(0);
            leftLaunchServo.setPower(0);
            rightLaunchServo.setPower(0);
        }
        double servoPos = .8;
        if (gamepad1.left_trigger > 0)
        {
            leftPosControl.setPosition(servoPos);
            rightPosControl.setPosition(1-servoPos);
            TARGET_RPM = 3700;
        }
        else if (gamepad1.right_trigger > 0)
        {
            leftPosControl.setPosition(0);
            rightPosControl.setPosition(1);
            TARGET_RPM = 3000;
        }
        telemetry.addData("rightPosition: ", rightPosControl.getPosition());
        telemetry.addData("leftPosition: ", leftPosControl.getPosition());
        telemetry.update();
    }
}