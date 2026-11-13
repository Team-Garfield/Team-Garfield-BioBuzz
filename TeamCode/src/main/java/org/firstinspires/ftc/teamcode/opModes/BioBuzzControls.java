package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Shooter;
import org.firstinspires.ftc.teamcode.subsystem.Storage;

final class BioBuzzControls {
    static final int INTAKE = 1;
    static final int STORAGE = 2;
    static final int SHOOTER = 4;

    private final Drive drive;
    private final Intake intake;
    private final Storage storage;
    private final Shooter shooter;

    BioBuzzControls(HardwareMap hardwareMap) {
        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap);
        storage = new Storage(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }

    void apply(double forward, double right, double turn, int mechanisms) {
        drive.drive(forward, right, turn);
        intake.setActive((mechanisms & INTAKE) != 0);
        storage.setActive((mechanisms & STORAGE) != 0);
        shooter.setActive((mechanisms & SHOOTER) != 0);
    }

    void stop() {
        apply(0, 0, 0, 0);
    }
}
