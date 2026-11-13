package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class IntakeSubsystem {
    public static final double INTAKE_POWER = 0.7;
    private final DcMotor motor;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "Sterne_Intake");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(0);
    }

    public void setActive(boolean active) {
        motor.setPower(active ? INTAKE_POWER : 0);
    }

    public void stop() {
        setActive(false);
    }
}
