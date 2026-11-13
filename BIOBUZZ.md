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

## Motor test

Lift the drive wheels off the ground and unload the mechanisms. Select
**BioBuzz Motor Test**, press START, then release all test buttons once. Hold
one button on gamepad 1 to run its motor at 25% power in the direction configured
for normal driving or operation.

| Gamepad 1 | Motor |
| --- | --- |
| D-pad Up | `Vorne_Links` |
| D-pad Right | `Vorne_Rechts` |
| D-pad Left | `Hinten_Links` |
| D-pad Down | `Hinten_Rechts` |
| X | `Sterne_Intake` |
| Y | `Sterne_Storage` |
| A | `Kanone_Normal` |
| B | `Kanone_Bouncy` |

Release the button to cut power. Pressing multiple test buttons cuts power to
all motors. The Driver Station shows the active motor and missing configuration
names. Check motor directions in `subsystem/MotorId.java`.

## Autonomous without odometry pods

**BioBuzz Timed Auto** drives straight at 25% power for one second and stops.
Adjust `DRIVE_POWER` and `DRIVE_SECONDS` in `BioBuzzTimedAuto.java` after
measuring the actual travel distance.

For a longer routine, **BioBuzz Record Auto** and **BioBuzz Auto Playback**
use the timestamped record and replay idea from FROG-Decode:

1. Put the robot in a repeatable starting position and note its heading.
2. Start **BioBuzz Record Auto** from the TeleOp list and drive using the normal
   controls. It records drive, intake, storage, and shooter commands for up to
   30 seconds. Press STOP to save over the previous recording on the Robot Controller.
3. Return the robot to the same position and heading. Select **BioBuzz Auto
   Playback** from the Autonomous list, check the duration during INIT, then
   press START. It cuts power at the end of the recording or when you press STOP.

Playback uses the recorded commands without measuring robot position. Battery
voltage, traction, and small differences in the starting pose can change the
result, so keep the routine short and test it on the field.

For future improvements, the Control Hub IMU can provide field-oriented
TeleOp once the hub mounting direction is configured. The drive motor encoders
can also help with short, calibrated autonomous movements.
