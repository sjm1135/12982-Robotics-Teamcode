package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Launch;
import org.firstinspires.ftc.teamcode.Autonomous.mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@Config
@Autonomous(name = "LeftFarAuto2026", group = "autonomous")
public class LeftFarAuto2026 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //TODO: put the right values in idk man i'm guessing
        Pose2d initialPose = new Pose2d(-11.8, -72, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        //create mechanism objects
        Intake intake = new Intake(hardwareMap);
        Launch launch = new Launch(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        //action building time baby
        //action that drives us to shooting position from the starting pos
        TrajectoryActionBuilder traj1 = drive.actionBuilder(initialPose)
                .lineToY (12)
                .splineTo(new Vector2d(60, 50), Math.PI/3)
                .waitSeconds(5);
        //action that drives to the intake from shooting pos


        //runtime woohoo
        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(
                new SequentialAction(
                        traj1.build(), launch.runLaunch(), intake.runIntake(), shooter.runShoot(), shooter.runShoot()
                )
        );
    }
}
