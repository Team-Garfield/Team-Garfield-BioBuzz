package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** Four-wheel mecanum drive. Inputs are robot-relative: forward, right, clockwise. */
public final class DriveSubsystem {
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    public DriveSubsystem(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "Vorne_Links");
        frontRight = hardwareMap.get(DcMotor.class, "Vorne_Rechts");
        backLeft = hardwareMap.get(DcMotor.class, "Hinten_Links");
        backRight = hardwareMap.get(DcMotor.class, "Hinten_Rechts");

        // Verify these directions with the robot lifted before driving on the floor.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        for (DcMotor motor : new DcMotor[] {frontLeft, frontRight, backLeft, backRight}) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setPower(0);
        }
    }

    public void drive(double forward, double right, double clockwise) {
        double fl = forward + right + clockwise;
        double fr = forward - right - clockwise;
        double bl = forward - right + clockwise;
        double br = forward + right - clockwise;
        double scale = Math.max(1.0, Math.max(Math.max(Math.abs(fl), Math.abs(fr)),
                Math.max(Math.abs(bl), Math.abs(br))));
        frontLeft.setPower(fl / scale);
        frontRight.setPower(fr / scale);
        backLeft.setPower(bl / scale);
        backRight.setPower(br / scale);
    }

    public void stop() {
        drive(0, 0, 0);
    }
}
