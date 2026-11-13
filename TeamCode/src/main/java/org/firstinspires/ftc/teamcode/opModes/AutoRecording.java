package org.firstinspires.ftc.teamcode.opModes;

import android.util.AtomicFile;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

final class AutoRecording {
    static final int SAMPLE_MS = 20;
    static final int MAX_MS = 30_000;
    private static final int MAX_FRAMES = MAX_MS / SAMPLE_MS + 2;
    private static final String HEADER = "BIOBUZZ_V1,ms,forward,right,turn,mechanisms";

    static final class Frame {
        final int ms;
        final float forward, right, turn;
        final int mechanisms;

        Frame(int ms, float forward, float right, float turn, int mechanisms) {
            this.ms = ms;
            this.forward = forward;
            this.right = right;
            this.turn = turn;
            this.mechanisms = mechanisms;
        }

        void apply(BioBuzzControls controls) {
            controls.apply(forward, right, turn, mechanisms);
        }
    }

    private static AtomicFile file(HardwareMap hardwareMap) {
        return new AtomicFile(new File(hardwareMap.appContext.getFilesDir(), "biobuzz-auto.csv"));
    }

    static void save(HardwareMap hardwareMap, List<Frame> frames, int durationMs)
            throws IOException {
        StringBuilder csv = new StringBuilder(HEADER).append('\n');
        for (Frame frame : frames) {
            csv.append(frame.ms).append(',').append(frame.forward).append(',')
                    .append(frame.right).append(',').append(frame.turn).append(',')
                    .append(frame.mechanisms).append('\n');
        }
        csv.append(durationMs).append(",0,0,0,0\n");
        AtomicFile target = file(hardwareMap);
        FileOutputStream output = target.startWrite();
        try {
            output.write(csv.toString().getBytes(StandardCharsets.UTF_8));
            target.finishWrite(output);
        } catch (IOException e) {
            target.failWrite(output);
            throw e;
        }
    }

    static List<Frame> load(HardwareMap hardwareMap) throws IOException {
        List<Frame> frames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                file(hardwareMap).openRead(), StandardCharsets.UTF_8))) {
            if (!HEADER.equals(reader.readLine())) throw new IOException("Unknown recording format");
            String line;
            int previousMs = -1;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 5 || frames.size() == MAX_FRAMES) {
                    throw new IOException("Invalid recording row or too many rows");
                }
                int ms = Integer.parseInt(fields[0]);
                float forward = parsePower(fields[1]);
                float right = parsePower(fields[2]);
                float turn = parsePower(fields[3]);
                int mechanisms = Integer.parseInt(fields[4]);
                if (ms <= previousMs || ms > MAX_MS || (previousMs < 0 && ms != 0)
                        || mechanisms < 0 || mechanisms > 7) {
                    throw new IOException("Invalid time or mechanism flags");
                }
                frames.add(new Frame(ms, forward, right, turn, mechanisms));
                previousMs = ms;
            }
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number in recording", e);
        }
        if (frames.size() < 2) throw new IOException("Recording is empty");
        Frame end = frames.get(frames.size() - 1);
        if (end.forward != 0 || end.right != 0 || end.turn != 0 || end.mechanisms != 0) {
            throw new IOException("Recording is missing its stop frame");
        }
        return frames;
    }

    private static float parsePower(String field) throws IOException {
        float value = Float.parseFloat(field);
        if (!(Math.abs(value) <= 1)) throw new IOException("Power out of range");
        return value;
    }
}
