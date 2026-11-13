package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class ShooterSubsystem {
    public static final double SHOOTER_POWER = 0.7;
    private final DcMotor normal;
    private final DcMotor bouncy;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        normal = hardwareMap.get(DcMotor.class, "Kanone_Normal");
        bouncy = hardwareMap.get(DcMotor.class, "Kanone_Bouncy");
        normal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bouncy.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        stop();
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
