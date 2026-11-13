package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz TeleOp", group = "Team Garfield")
public class BioBuzzTeleOp extends OpMode {
    private BioBuzzControls controls;

    @Override
    public void init() {
        controls = new BioBuzzControls(hardwareMap);
        telemetry.addLine("BioBuzz ready; verify motor directions with wheels lifted.");
        telemetry.update();
    }

    @Override
    public void loop() {
        boolean intake = gamepad1.right_trigger > 0.1;
        boolean storage = gamepad2.right_bumper;
        boolean shooter = gamepad2.right_trigger > 0.1;
        int mechanisms = (intake ? BioBuzzControls.INTAKE : 0)
                | (storage ? BioBuzzControls.STORAGE : 0)
                | (shooter ? BioBuzzControls.SHOOTER : 0);

        // FTC stick Y is negative when pushed forward.
        float forward = -gamepad1.left_stick_y;
        float right = gamepad1.left_stick_x;
        float turn = gamepad1.right_stick_x;
        controls.apply(forward, right, turn, mechanisms);

        telemetry.addData("Intake", intake ? "ON" : "OFF");
        telemetry.addData("Storage", storage ? "ON" : "OFF");
        telemetry.addData("Shooter", shooter ? "ON" : "OFF");
        onCommand(forward, right, turn, mechanisms);
        telemetry.update();
    }

    protected void onCommand(float forward, float right, float turn, int mechanisms) {
        // The recording OpMode uses the same controls and captures the applied commands.
    }

    @Override
    public void stop() {
        if (controls != null) controls.stop();
    }
}
