package org.firstinspires.ftc.teamcode.Autonomous;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Launch;
import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Util;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@Config
@Autonomous(name = "LaunchTester", group = "autonomous")
public class LaunchTester extends LinearOpMode{
    public void runOpMode() throws InterruptedException {
        Launch launch = new Launch(hardwareMap);
        Util util = new Util();
        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        launch.runLaunch(),
                        util.pause(5),
                        launch.stopLaunch()
                )
        );
    }
}
