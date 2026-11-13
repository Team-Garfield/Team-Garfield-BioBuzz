package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/** A small, easy-to-calibrate autonomous starting point. */
@Autonomous(name = "BioBuzz Timed Auto", group = "Team Garfield")
public final class BioBuzzTimedAuto extends LinearOpMode {
    private static final double DRIVE_POWER = 0.25;
    private static final double DRIVE_SECONDS = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        DriveSubsystem drive = new DriveSubsystem(hardwareMap);
        telemetry.addLine("Timed auto: drive forward for 1 second. Check the path first.");
        telemetry.update();
        try {
            waitForStart();
            if (!opModeIsActive()) return;
            ElapsedTime timer = new ElapsedTime();
            timer.reset();
            drive.drive(DRIVE_POWER, 0, 0);
            while (opModeIsActive() && timer.seconds() < DRIVE_SECONDS) {
                idle();
            }
        } finally {
            drive.stop();
        }
    }
}
