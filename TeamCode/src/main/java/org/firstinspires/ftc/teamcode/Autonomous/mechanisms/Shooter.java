package org.firstinspires.ftc.teamcode.Autonomous.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    CRServo leftLaunchServo;
    CRServo rightLaunchServo;
    public Shooter(HardwareMap hardwareMap) {
        leftLaunchServo = hardwareMap.get(CRServo.class, "launchLeftServo");
        rightLaunchServo = hardwareMap.get(CRServo.class, "launchRightServo");
    }
    public class RunShoot implements Action {
        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //see idk what power carter usually drags it in on but I have to assume its full power so we'll go with that
            leftLaunchServo.setPower(1);
            rightLaunchServo.setPower(1);
            return false;
        }
    }
    public Action runShoot() { return new Shooter.RunShoot(); }
    public class StopShoot implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //i dont think this is necessary in this case but yeah estoy bailando
            leftLaunchServo.setPower(0);
            rightLaunchServo.setPower(0);
            return false;
        }
    }
    public Action stopShoot() { return new Shooter.StopShoot(); }
}
