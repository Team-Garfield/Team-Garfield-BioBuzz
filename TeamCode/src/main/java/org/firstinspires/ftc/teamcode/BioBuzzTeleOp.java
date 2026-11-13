package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz TeleOp", group = "Team Garfield")
public final class BioBuzzTeleOp extends OpMode {
    private DriveSubsystem drive;
    private IntakeSubsystem intake;
    private StorageSubsystem storage;
    private ShooterSubsystem shooter;

    @Override
    public void init() {
        drive = new DriveSubsystem(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        storage = new StorageSubsystem(hardwareMap);
        shooter = new ShooterSubsystem(hardwareMap);
        telemetry.addLine("BioBuzz ready; verify motor directions with wheels lifted.");
        telemetry.update();
    }

    @Override
    public void loop() {
        // FTC stick Y is negative when pushed forward.
        drive.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        intake.setActive(gamepad1.right_trigger > 0.1);
        storage.setActive(gamepad2.right_bumper);
        shooter.setActive(gamepad2.right_trigger > 0.1);

        telemetry.addData("Intake", gamepad1.right_trigger > 0.1 ? "ON" : "OFF");
        telemetry.addData("Storage", gamepad2.right_bumper ? "ON" : "OFF");
        telemetry.addData("Shooter", gamepad2.right_trigger > 0.1 ? "ON" : "OFF");
        telemetry.update();
    }

    @Override
    public void stop() {
        if (drive != null) drive.stop();
        if (intake != null) intake.stop();
        if (storage != null) storage.stop();
        if (shooter != null) shooter.stop();
    }
}
