package org.firstinspires.ftc.teamcode.TestModes;

import org.firstinspires.ftc.teamcode.util.PID;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "RPM_Test", group = "test_modes")
@Config
public class RPMTest extends LinearOpMode {
    private DcMotorEx launcher = null;
    private final double MOTOR_TICKS_PER_REVOLUTION = 28; // Used to calculate RPM
                                                          // All calculations for control are done
                                                          // In terms of RPM
    public static double targetRPM = 0;
    private double power = 0;
    public static class Params {
        public double kp = 0.01;
        public double ki = 0;
        public double kd = 0;
        public double imax = 1;
    }

    ElapsedTime timer = new ElapsedTime();
    Params params = new Params();
    PID controller = new PID(params.kp, params.ki, params.kd, params.imax);

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");

        // set up hardware
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        timer.reset();

        while(opModeIsActive()) {

            // Calculating motor RPM
            double RPM = launcher.getVelocity() / MOTOR_TICKS_PER_REVOLUTION * 60;

            // Plugging position into PID controller
            power += controller.PIDControl(RPM, targetRPM) * timer.seconds();

            // Limiting motor power to 1
            if (power > 1) {
                power = 1;
            }
            else if (power < -1) {
                power = -1;
            }

            // Sending desired power to motor
            launcher.setPower(power);


            /*
             * TUNING
             *
             * The following code sends the current motor velocity and the target velocity
             * to ftc dashboard
             *
             * graphing velocity and target velocity should give a good idea of how well
             * the controller is working
             */
            FtcDashboard dashboard = FtcDashboard.getInstance();
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Velocity (RPM): ", RPM);
            packet.put("Target Velocity (RPM): ", targetRPM);
            dashboard.sendTelemetryPacket(packet);

            timer.reset();

        }
    }
}
