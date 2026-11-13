package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class Storage {
    public static final double STORAGE_POWER = 0.6;
    private final DcMotor motor;

    public Storage(HardwareMap hardwareMap) {
        motor = MotorId.STORAGE.require(hardwareMap);
    }

    public void setActive(boolean active) {
        motor.setPower(active ? STORAGE_POWER : 0);
    }

    public void stop() {
        setActive(false);
    }
}
