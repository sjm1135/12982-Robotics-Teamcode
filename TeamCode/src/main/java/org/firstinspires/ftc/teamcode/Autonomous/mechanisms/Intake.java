package org.firstinspires.ftc.teamcode.Autonomous.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Intake {
    DcMotorEx intakeMotor;
    public Intake(HardwareMap hardwareMap) {
        intakeMotor  = hardwareMap.get(DcMotorEx.class, "intakeIntakeMotor");
    }
    public class RunIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //TODO: put the right values in
            intakeMotor.setVelocity(537.7 * 100 / 60);
            return false;
        }
    }
    public Action runIntake() { return new RunIntake(); }
    public class StopIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeMotor.setVelocity(0);
            return false;
        }
    }
    public Action stopIntake() { return new StopIntake(); }
}
