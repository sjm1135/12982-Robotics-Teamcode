package org.firstinspires.ftc.teamcode.Autonomous.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Launch {
    DcMotorEx launchMotor;
    public Launch(HardwareMap hardwareMap) {
        launchMotor  = hardwareMap.get(DcMotorEx.class, "launchLaunchMotor");
    }
    public class RunLaunch implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //TODO: put the right values in
            launchMotor.setVelocity(0.1);
            return false;
        }
    }
    public Action runLaunch() { return new Launch.RunLaunch(); }
    public class StopLaunch implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            launchMotor.setVelocity(3000 * 28 / 60);
            return false;
        }
    }
    public Action stopLaunch() { return new Launch.StopLaunch(); }
}
