# Team Garfield BioBuzz

This is the official FIRST Tech Challenge Robot Controller project with a small
`TeamCode` implementation for BioBuzz. Open the repository root in Android Studio.

## Hardware configuration

Create eight **DC motor** entries on the Robot Controller with these exact names:

| Motor | Configuration name |
| --- | --- |
| Front left | `Vorne_Links` |
| Front right | `Vorne_Rechts` |
| Rear left | `Hinten_Links` |
| Rear right | `Hinten_Rechts` |
| Intake | `Sterne_Intake` |
| Storage | `Sterne_Storage` |
| Shooter 1 | `Kanone_Normal` |
| Shooter 2 | `Kanone_Bouncy` |

All names came from the team list, with spaces replaced by underscores. The
drive code assumes four mecanum wheels. It reverses the left drive motors and
runs the right drive motors forward. **Lift the robot and test each motor before
placing it on the field.** Reverse the appropriate direction or power constant
if a mechanism runs backward. The three mechanism power constants are in their
subsystem classes.

## Driver controls

| Controller | Input | Action |
| --- | --- | --- |
| 1 | Left stick | Forward/backward and strafe |
| 1 | Right stick X | Turn |
| 1 | R2 | Intake while held |
| 2 | R1 | Storage while held |
| 2 | R2 | Both shooter motors while held |

`BioBuzzTeleOp` uses robot-relative driving. Releasing any mechanism button
stops that motor. Stopping the OpMode stops all eight motors.

`BioBuzzTimedAuto` drives straight at 25% power for one second, then stops.
It is a calibration starting point, not a match strategy. Check the start pose
and clear path before running it. Adjust `DRIVE_POWER` and `DRIVE_SECONDS` in
that class after measuring the actual travel distance.

## Autonomous ideas without odometry pods

1. **Field-oriented TeleOp:** the Control Hub IMU can rotate the left-stick
   command into the robot frame. This gives driver-relative or field-relative
   control without tracking position. The hub's physical mounting direction
   must be configured correctly first; the existing FTC sample
   `RobotTeleopMecanumFieldRelativeDrive` shows the SDK API.
2. **Short encoder segments:** built-in drive motor encoders can estimate a
   straight or strafe distance. An IMU can check turns. This is open-loop or
   simple heading-corrected driving, not a full path follower, and mecanum
   strafing needs on-robot calibration.
3. **Recorded driver routine:** record *timed drive commands and mechanism
   states* during a practice run, then replay them from the same starting pose.
   Use a fixed sample interval (for example 20 ms), save a versioned file, and
   always stop motors on interruption or at the end. Recording the commands
   instead of raw wheel powers makes the file easier to inspect and tune.
   Repetition will still drift as battery voltage, floor grip, and game pieces
   change; a short routine with stops is easier to repeat than a long one.

The timed autonomous is intentionally the only autonomous implementation for
now. It offers a baseline to test the drive direction and distance before
building a recorded routine.
