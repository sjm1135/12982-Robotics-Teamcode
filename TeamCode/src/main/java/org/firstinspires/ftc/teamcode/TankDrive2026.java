package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "TankDrive2026")
public class TankDrive2026 extends OpMode {


    private DcMotor left;
    private DcMotor right;
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
    private double max = 0;
    @Override
    public void init(){
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
    }
    @Override
    public void loop() {
        //basic driving
        double leftPower = -gamepad1.left_stick_y;
        double rightPower = gamepad1.right_stick_y;

        left.setPower(-leftPower);
        right.setPower(-rightPower);
        //mounted mechanism mechanisms

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
        double servoPower = gamepad2.left_stick_y;
        leftLaunchServo.setPower(servoPower);
        rightLaunchServo.setPower(servoPower);

        //emergency brake to avoid Stanley Time
        if (gamepad1.b) {
            left.setPower(0);
            right.setPower(0);
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