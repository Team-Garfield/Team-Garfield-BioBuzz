package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "BioBuzz Record Auto", group = "Team Garfield")
public final class BioBuzzRecordAuto extends BioBuzzTeleOp {
    private final ElapsedTime timer = new ElapsedTime();
    private final List<AutoRecording.Frame> frames = new ArrayList<>();

    @Override
    public void init() {
        super.init();
        telemetry.addLine("START records; STOP saves over the previous routine.");
        telemetry.update();
    }

    @Override
    public void start() {
        frames.clear();
        timer.reset();
    }

    @Override
    protected void onCommand(float forward, float right, float turn, int mechanisms) {
        int ms = (int) timer.milliseconds();
        if (ms < AutoRecording.MAX_MS) {
            if (frames.isEmpty()) {
                frames.add(new AutoRecording.Frame(0, forward, right, turn, mechanisms));
            } else if (ms - frames.get(frames.size() - 1).ms >= AutoRecording.SAMPLE_MS) {
                frames.add(new AutoRecording.Frame(ms, forward, right, turn, mechanisms));
            }
        }
        telemetry.addData("Recording", "%d / %d s; %d frames",
                Math.min(ms, AutoRecording.MAX_MS) / 1000, AutoRecording.MAX_MS / 1000, frames.size());
        if (ms >= AutoRecording.MAX_MS) telemetry.addLine("Recording limit reached. Press STOP to save.");
    }

    @Override
    public void stop() {
        super.stop();
        if (frames.isEmpty()) return;
        int lastMs = frames.get(frames.size() - 1).ms;
        int durationMs = Math.min(AutoRecording.MAX_MS,
                Math.max(lastMs + 1, (int) timer.milliseconds()));
        try {
            AutoRecording.save(hardwareMap, frames, durationMs);
            telemetry.addLine("Auto recording saved.");
        } catch (IOException e) {
            RobotLog.ee("BioBuzz", e, "Unable to save auto recording");
            telemetry.addData("Auto recording could not be saved", e.getMessage());
        }
        telemetry.update();
    }
}
