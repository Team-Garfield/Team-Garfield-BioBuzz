package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class StorageSubsystem {
    public static final double STORAGE_POWER = 0.6;
    private final DcMotor motor;

    public StorageSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "Sterne_Storage");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(0);
    }

    public void setActive(boolean active) {
        motor.setPower(active ? STORAGE_POWER : 0);
    }

    public void stop() {
        setActive(false);
    }
}
