package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** Shared hardware names and motor directions for regular operation and testing. */
public enum MotorId {
    FRONT_LEFT("Vorne_Links", true, true),
    FRONT_RIGHT("Vorne_Rechts", false, true),
    REAR_LEFT("Hinten_Links", true, true),
    REAR_RIGHT("Hinten_Rechts", false, true),
    INTAKE("Sterne_Intake", false, false),
    STORAGE("Sterne_Storage", false, false),
    SHOOTER_NORMAL("Kanone_Normal", false, false),
    SHOOTER_BOUNCY("Kanone_Bouncy", false, false);

    public final String configName;
    private final boolean reversed;
    private final boolean driveMotor;

    MotorId(String configName, boolean reversed, boolean driveMotor) {
        this.configName = configName;
        this.reversed = reversed;
        this.driveMotor = driveMotor;
    }

    DcMotor require(HardwareMap hardwareMap) {
        return configure(hardwareMap.get(DcMotor.class, configName));
    }

    public DcMotor find(HardwareMap hardwareMap) {
        DcMotor motor = hardwareMap.tryGet(DcMotor.class, configName);
        return motor == null ? null : configure(motor);
    }

    private DcMotor configure(DcMotor motor) {
        motor.setPower(0);
        motor.setDirection(reversed ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (driveMotor) motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        return motor;
    }
}
