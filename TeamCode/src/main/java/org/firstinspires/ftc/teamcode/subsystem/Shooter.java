package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class Shooter {
    public static final double SHOOTER_POWER = 0.7;
    private final DcMotor normal;
    private final DcMotor bouncy;

    public Shooter(HardwareMap hardwareMap) {
        normal = MotorId.SHOOTER_NORMAL.require(hardwareMap);
        bouncy = MotorId.SHOOTER_BOUNCY.require(hardwareMap);
    }

    public void setActive(boolean active) {
        double power = active ? SHOOTER_POWER : 0;
        normal.setPower(power);
        bouncy.setPower(power);
    }

    public void stop() {
        setActive(false);
    }
}
