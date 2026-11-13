package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.subsystem.MotorId;

@TeleOp(name = "BioBuzz Motor Test", group = "Team Garfield")
public final class BioBuzzMotorTest extends OpMode {
    private static final double TEST_POWER = 0.25;
    private static final MotorId[] MOTORS = {
        MotorId.FRONT_LEFT, MotorId.FRONT_RIGHT, MotorId.REAR_LEFT, MotorId.REAR_RIGHT,
        MotorId.INTAKE, MotorId.STORAGE, MotorId.SHOOTER_NORMAL, MotorId.SHOOTER_BOUNCY
    };
    private static final String[] BUTTONS = {"D-pad Up", "D-pad Right", "D-pad Left", "D-pad Down",
            "X", "Y", "A", "B"};
    private final DcMotor[] motors = new DcMotor[MOTORS.length];
    private boolean ready;
    private int running = -1;

    @Override
    public void init() {
        for (int i = 0; i < motors.length; i++) {
            motors[i] = MOTORS[i].find(hardwareMap);
            telemetry.addData(BUTTONS[i], MOTORS[i].configName
                    + (motors[i] == null ? " (missing)" : ""));
        }
        telemetry.addLine("Lift the wheels and unload the robot. Hold one button on gamepad 1.");
        telemetry.update();
    }

    @Override
    public void loop() {
        boolean[] pressed = {gamepad1.dpad_up, gamepad1.dpad_right,
                gamepad1.dpad_left, gamepad1.dpad_down, gamepad1.x, gamepad1.y, gamepad1.a, gamepad1.b};
        int count = 0;
        int selected = -1;
        for (int i = 0; i < pressed.length; i++) {
            if (pressed[i]) {
                count++;
                selected = i;
            }
        }
        if (count == 0) ready = true;
        int next = ready && count == 1 && motors[selected] != null ? selected : -1;
        if (next != running) {
            if (running >= 0) motors[running].setPower(0);
            if (next >= 0) motors[next].setPower(TEST_POWER);
            running = next;
        }

        String status = "Idle";
        if (!ready) status = "Release all test buttons";
        else if (count > 1) status = "Hold only one test button";
        else if (count == 1) status = MOTORS[selected].configName
                + (next >= 0 ? " running at 25%" : " missing");
        telemetry.addData("Motor test", status);
        for (int i = 0; i < motors.length; i++) {
            telemetry.addData(BUTTONS[i], MOTORS[i].configName
                    + (motors[i] == null ? " (missing)" : ""));
        }
        telemetry.update();
    }

    @Override
    public void stop() {
        for (DcMotor motor : motors) {
            if (motor != null) motor.setPower(0);
        }
    }
}
