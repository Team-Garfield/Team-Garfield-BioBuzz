package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class Intake {
    public static final double INTAKE_POWER = 0.7;
    private final DcMotor motor;

    public Intake(HardwareMap hardwareMap) {
        motor = MotorId.INTAKE.require(hardwareMap);
    }

    public void setActive(boolean active) {
        motor.setPower(active ? INTAKE_POWER : 0);
    }

    public void stop() {
        setActive(false);
    }
}
