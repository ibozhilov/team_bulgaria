package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ThreadPool;

/**
 * Created by tjones on 4/3/2017.
 */
@TeleOp(name="My First Op Mode", group="Practice-Bot")
public class FirstOpMode extends LinearOpMode {
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private DcMotor liftPullUpMechanismMotor;
    private DcMotor motorBallMechanism;
    private DcMotor pullUpMechanismRight;
    private DcMotor pullUpMechanismLeft;
    private CRServo expansionServoLeft;
    private CRServo expansionServoRight;
    private Servo colorBallSelectionServo;
    private ColorSensor colorSensor;
    private ElapsedTime period = new ElapsedTime();


    //    variables for servo position
    private float MIDDLE = 0.5f;
    private float RIGHT = 0.7f;
    private float LEFT = 0.3f;
    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome
     * with a regular periodic tick. This is used to compensate for varying
     * processing times for each cycle. The function looks at the elapsed cycle time,
     * and sleeps for the remaining time interval.
     *
     * @param periodMs Length of wait cycle in mSec.
    FIRST Global Java SDK Startup Guide - Rev 0 Copyright 2017 REV Robotics, LLC 18
     */
    private void waitForTick(long periodMs) throws java.lang.InterruptedException {
        long remaining = periodMs - (long)period.milliseconds();
// sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            Thread.sleep(remaining);
        }
// Reset the cycle clock for the next pass.
        period.reset();
    }

//    public void startPullUpMechanism (DcMotor servoLeft, DcMotor servoRight, double power ){
//
//    }

    public void SynchronouslyMoveServos(CRServo servo1, CRServo servo2, double power){
        double diff=0;
//        diff = servo1.getController().getServoPosition(servo1.getPortNumber()) - servo2.getController().getServoPosition(servo2.getPortNumber());
        servo1.setPower(power-diff);
        servo2.setPower(power+diff);
    }

    public void Rotate(boolean leftBumper, boolean rightBumper){
        if (leftBumper) {
            leftFrontMotor.setPower(1);
            rightFrontMotor.setPower(-1);
            leftBackMotor.setPower(1);
            rightBackMotor.setPower(-1);
        }
        if (rightBumper) {
            leftFrontMotor.setPower(-1);
            rightFrontMotor.setPower(1);
            leftBackMotor.setPower(-1);
            rightBackMotor.setPower(1);
        }
        else{
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        double leftStickX;
        double leftStickY;
        boolean leftBumper;
        boolean rightBumper;
        double powerLF;
        double powerLB;
        boolean isCollecting = false;
        boolean isLiftingPullUpMechanism = false;

//        map motors
        leftFrontMotor = hardwareMap.dcMotor.get("frontLeft");
        rightFrontMotor = hardwareMap.dcMotor.get("frontRight");
        leftBackMotor = hardwareMap.dcMotor.get("backLeft");
        rightBackMotor = hardwareMap.dcMotor.get("backRight");
        motorBallMechanism = hardwareMap.dcMotor.get("ballMechanism");
        expansionServoLeft = hardwareMap.crservo.get("expansionServoLeft");
        expansionServoRight = hardwareMap.crservo.get("expansionServoRight");
        colorBallSelectionServo = hardwareMap.servo.get("colorBallSelectionServo");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        liftPullUpMechanismMotor = hardwareMap.dcMotor.get("liftPullUpMechanismMotor");
        pullUpMechanismLeft = hardwareMap.dcMotor.get("pullUpMechanismLeft");
        pullUpMechanismRight = hardwareMap.dcMotor.get("pullUpMechanismRight");

        Ball_collecting_mechanism_operator ballMechanism = new Ball_collecting_mechanism_operator("ballMechanism",motorBallMechanism,colorBallSelectionServo,colorSensor);
        Move_robot move = new Move_robot("move",leftFrontMotor,rightFrontMotor,leftBackMotor,rightBackMotor);
        pullUp_mechanism pullUp = new pullUp_mechanism("pullUp",pullUpMechanismLeft,pullUpMechanismRight);

//        reverse motors
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        expansionServoRight.setDirection(CRServo.Direction.REVERSE);

//        change mode move with encoder
        pullUpMechanismLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullUpMechanismRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set all motors to zero power
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);
        liftPullUpMechanismMotor.setPower(0);
        motorBallMechanism.setPower(0);
        pullUpMechanismRight.setPower(0);
        pullUpMechanismLeft.setPower(0);
        expansionServoLeft.setPower(0);
        expansionServoRight.setPower(0);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver"); //
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        try {
// run until the end of the match (driver presses STOP)

            telemetry.addData("Set servo position.","");
            telemetry.update();
            expansionServoLeft.getController().setServoPosition(expansionServoLeft.getPortNumber(),0);
            expansionServoRight.getController().setServoPosition(expansionServoRight.getPortNumber(),0);

            liftPullUpMechanismMotor.setPower(1);
            Thread.sleep(1000);
            colorBallSelectionServo.setPosition(MIDDLE);

            move.start();
            pullUp.start();

           ; while (opModeIsActive()) {
                // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)

//                telemetry.addData("period", period);


                move.setXY(-gamepad1.left_stick_x,gamepad1.left_stick_y);
                pullUp.setUpDown(gamepad1.dpad_up, gamepad1.dpad_down);

                if (gamepad1.dpad_right){
                    SynchronouslyMoveServos(expansionServoLeft, expansionServoRight, -0.7);
                } else {
                    if (gamepad1.dpad_left) {
                        SynchronouslyMoveServos(expansionServoLeft, expansionServoRight, 0.7);
                    } else {
                        SynchronouslyMoveServos(expansionServoLeft, expansionServoRight, 0);
                    }
                }

                leftBumper = gamepad1.left_bumper;
                rightBumper = gamepad1.right_bumper;
                Rotate(leftBumper, rightBumper);

                if (gamepad1.y) {
                    if (isCollecting) {
                        ballMechanism.pause();
                    } else {
                        ballMechanism.start();
                    }
                    isCollecting = !isCollecting;
                    while (gamepad1.y) {
                    }
                }


// Send telemetry message to signify robot running

//                expansionServoRight.getController().setServoPosition(expansionServoLeft.getPortNumber(),0.3);
//                telemetry.addData("servoLeft", "%f",
//                        expansionServoLeft.getController().getServoPosition(expansionServoLeft.getPortNumber()));
//                telemetry.addData("servoRight", "%f",
//                        expansionServoRight.getController().getServoPosition(expansionServoRight.getPortNumber()));
//                telemetry.addData("running", ballMechanism.running);
//                telemetry.addData("isCollecting", isCollecting);
//                telemetry.addData("color green", "%d", colorSensor.green());
//                telemetry.addData("color blue", "%d", colorSensor.blue());
//                telemetry.addData("color red", "%d", colorSensor.red());
//                telemetry.addData("colorServo", colorBallSelectionServo.getPosition());
//                telemetry.addData("motor","%d",leftFrontMotor.getCurrentPosition());
//                telemetry.update();
// Pause for metronome tick. 40 mS each cycle = update 25 times a second.
                waitForTick(40);
            }
        }
        catch (InterruptedException exc)
        {
            return;
        }
        finally {
            move.exterminate();
            pullUp.exterminate();
            ballMechanism.pause();
            Thread.sleep(100);
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
            liftPullUpMechanismMotor.setPower(0);
            motorBallMechanism.setPower(0);
            pullUpMechanismRight.setPower(0);
            pullUpMechanismLeft.setPower(0);
            expansionServoLeft.setPower(0);
            expansionServoRight.setPower(0);
              }
    }
}