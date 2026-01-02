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
@Autonomous(name = "LeftFarAuto2026", group = "autonomous")
public class LeftFarAuto2026 extends LinearOpMode {
    //define motors excluding drive cause i dont have time for this


    @Override
    public void runOpMode() throws InterruptedException {
        //TODO: put the right values in idk man i'm guessing
        Pose2d initialPose = new Pose2d(-11.8, -72, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        //create mechanism objects
        Intake intake = new Intake(hardwareMap);
        Launch launch = new Launch(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Util util = new Util();

        //action building time baby
        //action that drives us to shooting position from the starting pos
        TrajectoryActionBuilder traj1 = drive.actionBuilder(initialPose)
                .lineToY (40);
                //.splineTo(new Vector2d(-30, 50), Math.PI/4);
        Action trajectoryActionCloseout = traj1.endTrajectory().build();
        Action chosenTraj = traj1.build();

        //runtime woohoo
        waitForStart();

        Action fire = new SequentialAction(
                intake.runIntake(),
                util.pause(1),
                shooter.runShoot(),
                util.pause(1),
                shooter.stopShoot(),
                util.pause(3),
                shooter.runShoot(),
                util.pause(1),
                shooter.stopShoot()
        );
        Actions.runBlocking(
                new SequentialAction(
                    //go to shooter pos and start flywheel
                    launch.runLaunch(),
                    //fire!
                    fire,
                    intake.stopIntake(),
                    launch.stopLaunch(),
                    util.pause(20),
                    trajectoryActionCloseout
                )
        );
    }
}
