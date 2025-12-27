package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name="TimeBased2026")
public class TimeBased2026 extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotorEx launchMotor;
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
    boolean toggle = false;
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
        telemetry.addData("Hardware: ", "Initialized");

        launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLaunchServo.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        drive();
    }
    public void drive() {
        frontLeft.setPower(wheelPow);
        frontRight.setPower(wheelPow);
        backLeft.setPower(wheelPow);
        backRight.setPower(wheelPow);
        sleep(500);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
