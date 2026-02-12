package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name="ServoPos", group = "Autonomous")
public class ServoPos extends LinearOpMode {
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
    private double TARGET_RPM = 4000;
    private final double WHEEL_WHEEL_DIAMETER = 4.094; //in inches
    private final double TICKS_PER_REVOLUTION_WHEELS = 2000; //for wheels
    private final double FLYWHEEL_DIAMETER = 3.78;
    private final double FLYWHEEL_TICKS_PER_REVOLUTION = 28;
    private final double INTAKE_TARGET_RPM = 100;
    private final double INTAKE_TICKS_PER_REVOLUTION = 537.7;
    private final double INTAKE_TICKS_PER_SECOND = INTAKE_TICKS_PER_REVOLUTION * INTAKE_TARGET_RPM / 60;
    private final double FLYWHEEL_TICKS_PER_SECOND = FLYWHEEL_TICKS_PER_REVOLUTION * TARGET_RPM / 60;
    boolean toggle = false;
    boolean intakeToggle = false;
    private double wheelPow = 0.5;
    private double max = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        frontRight = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        backLeft = hardwareMap.get(DcMotor.class, "leftBackDrive");
        backRight = hardwareMap.get(DcMotor.class, "rightBackDrive");
        launchMotor = hardwareMap.get(DcMotorEx.class, "launchLaunchMotor");
        leftLaunchServo = hardwareMap.get(CRServo.class, "launchLeftServo");
        rightLaunchServo = hardwareMap.get(CRServo.class, "launchRightServo");
        leftPosControl = hardwareMap.get(Servo.class, "leftPos");
        rightPosControl = hardwareMap.get(Servo.class, "rightPos");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeIntakeMotor");
        telemetry.addData("Hardware: ", "Initialized");

        launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLaunchServo.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        leftPosControl.setPosition(0);
        rightPosControl.setPosition(1);
        double servoPos = 0.4;
        double servoPo = 1;
        leftPosControl.setPosition(servoPos);
        rightPosControl.setPosition(1-servoPos);
        sleep(4000);
        leftPosControl.setPosition(servoPo);
        rightPosControl.setPosition(1-servoPo);
        boolean on = true;
        while (on)
        {
            double leftPos = leftPosControl.getPosition();
            double rightPos = rightPosControl.getPosition();
            telemetry.addData("LeftPos: ", leftPos);
            telemetry.addData("RightPos: ", rightPos);
        }
    }
}
