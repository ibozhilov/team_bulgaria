package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Student on 7/8/2017.
 */

public class Ball_collecting_mechanism_operator extends Thread {
    private float MIDDLE = 0.5f;
    private float RIGHT = 0.7f;
    private float LEFT = 0.3f;
    private Thread t;
    private String threadName;
    private DcMotor collectingBallMechanismMotor;
    private Servo colorBallSelectionServo;
    private ColorSensor colorSensor;
    private boolean running;

    Ball_collecting_mechanism_operator ( String name , DcMotor motor, Servo servo, ColorSensor sensor) {
        threadName = name;
        collectingBallMechanismMotor = motor;
        colorBallSelectionServo = servo;
        colorSensor = sensor;
    }

    public void start() {
        t = new Thread (this, threadName);
        running = true;
        t.start();
    }

   public void pause() {
       running = false;
   }

    public void run() {
        try {
            collectingBallMechanismMotor.setPower(1);
            while(running) {
                if (colorSensor.blue() > 100){
                    colorBallSelectionServo.setPosition(LEFT);
                    collectingBallMechanismMotor.setPower(0);
                    Thread.sleep(400);
                    collectingBallMechanismMotor.setPower(1);
                    colorBallSelectionServo.setPosition(MIDDLE);
                }
                if (colorSensor.red() > 100) {
                    colorBallSelectionServo.setPosition(RIGHT);
                    collectingBallMechanismMotor.setPower(0);
                    Thread.sleep(400);
                    collectingBallMechanismMotor.setPower(1);
                    colorBallSelectionServo.setPosition(MIDDLE);
                }
            }
            collectingBallMechanismMotor.setPower(0);
        } catch (InterruptedException e){
            collectingBallMechanismMotor.setPower(0);
        }
    }
}
