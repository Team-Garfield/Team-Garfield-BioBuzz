package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** Four-wheel mecanum drive. Inputs are robot-relative: forward, right, clockwise. */
public final class Drive {
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    public Drive(HardwareMap hardwareMap) {
        frontLeft = MotorId.FRONT_LEFT.require(hardwareMap);
        frontRight = MotorId.FRONT_RIGHT.require(hardwareMap);
        backLeft = MotorId.REAR_LEFT.require(hardwareMap);
        backRight = MotorId.REAR_RIGHT.require(hardwareMap);
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
