package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "funny_hack_job")
public class FunnyHackJob extends LinearOpMode {

    private DcMotor left;
    private DcMotor right;
    private DcMotorEx launchMotor;
    private CRServo leftLaunchServo;
    private CRServo rightLaunchServo;
    private Servo leftPosControl;
    private Servo rightPosControl;
    private final double TARGET_RPM = 3000;
    private final double WHEEL_DIAMETER = 4.094; //in inches
    private final double TICKS_PER_REVOLUTION_WHEELS = 2000; //for wheels
    private final double TICKS_PER_INCH_WHEELS = WHEEL_DIAMETER * Math.PI / TICKS_PER_REVOLUTION_WHEELS;
    private final double WHEEL_POWER = 0.4;
    private final double QUIT_TIME = 5; // seconds after which to quit a move

    private final double FLYWHEEL_DIAMETER = 3.78;
    private final double FLYWHEEL_TICKS_PER_REVOLUTION = 28;
    private final double TICKS_PER_SECOND = FLYWHEEL_TICKS_PER_REVOLUTION * TARGET_RPM / 60;
    boolean toggle = false;
    private double max = 0;
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        left = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        right = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        launchMotor = hardwareMap.get(DcMotorEx.class, "launchLaunchMotor");
        leftLaunchServo = hardwareMap.get(CRServo.class, "launchLeftServo");
        rightLaunchServo = hardwareMap.get(CRServo.class, "launchRightServo");
        leftPosControl = hardwareMap.get(Servo.class, "leftPos");
        rightPosControl = hardwareMap.get(Servo.class, "rightPos");
        telemetry.addData("Hardware: ", "Initialized");

        launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLaunchServo.setDirection(DcMotorSimple.Direction.REVERSE);

        right.setDirection(DcMotorSimple.Direction.REVERSE);

        leftPosControl.setPosition(0);
        rightPosControl.setPosition(1);

        waitForStart();

        encoderDriveForward(3, WHEEL_POWER); // Drive Forward

        launchMotor.setVelocity(TICKS_PER_SECOND);

        sleep(4000);

        for(int i = 0; i < 3; i++) {

            leftLaunchServo.setPower(1);
            rightLaunchServo.setPower(1);

            sleep(500);

            leftLaunchServo.setPower(0);
            rightLaunchServo.setPower(0);

            sleep(500);
        }
    }

    public void encoderDriveForward(double inches, double power) {

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setTargetPosition((int) (inches / TICKS_PER_INCH_WHEELS));
        right.setTargetPosition((int) (inches / TICKS_PER_INCH_WHEELS));

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setPower(power);
        right.setPower(power);

        timer.reset();

        while ((left.getCurrentPosition() < inches / TICKS_PER_INCH_WHEELS || right.getCurrentPosition() < inches / TICKS_PER_INCH_WHEELS) && timer.seconds() < QUIT_TIME) {
            telemetry.addData("leftPos: ", left.getCurrentPosition());
            telemetry.addData("rightPos: ", right.getCurrentPosition());
            telemetry.update();
        }

        left.setPower(0);
        right.setPower(0);
    }
}
