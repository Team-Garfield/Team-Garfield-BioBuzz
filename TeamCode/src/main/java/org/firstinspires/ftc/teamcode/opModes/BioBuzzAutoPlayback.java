package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Autonomous(name = "BioBuzz Auto Playback", group = "Team Garfield")
public final class BioBuzzAutoPlayback extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        List<AutoRecording.Frame> frames;
        try {
            frames = AutoRecording.load(hardwareMap);
        } catch (FileNotFoundException e) {
            telemetry.addLine("No recording. Run BioBuzz Record Auto first.");
            telemetry.update();
            waitForStart();
            return;
        } catch (IOException e) {
            telemetry.addData("Cannot play recording", e.getMessage());
            telemetry.update();
            waitForStart();
            return;
        }

        BioBuzzControls controls = new BioBuzzControls(hardwareMap);
        int endMs = frames.get(frames.size() - 1).ms;
        telemetry.addData("Recording", "%.2f s", endMs / 1000.0);
        telemetry.addLine("Use the same starting position and heading as the recording.");
        telemetry.update();
        try {
            waitForStart();
            if (!opModeIsActive()) return;
            ElapsedTime timer = new ElapsedTime();
            int index = 0;
            int applied = -1;
            while (opModeIsActive()) {
                double ms = timer.milliseconds();
                if (ms >= endMs) break;
                while (frames.get(index + 1).ms <= ms) index++;
                if (index != applied) {
                    frames.get(index).apply(controls);
                    applied = index;
                }
                idle();
            }
        } finally {
            controls.stop();
        }
    }
}
