package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Created by radad on 12-Jul-17.
 */

public class Expansion_container extends Thread{
    private Thread t;
    private String threadName;
    private CRServo crServoLeft;
    private CRServo crServoRight;
    private static boolean exterminate;
    private double leftStickY;
    private double rightStickY;

    Expansion_container(String name, CRServo leftServo, CRServo rightServo){
        threadName = name;
        crServoLeft = leftServo;
        crServoRight = rightServo;
    }

    public void start(){
        if(t == null){
            t = new Thread(this, threadName);
            exterminate = false;
            t.start();
        }
    }

    public static void exterminate(){
        exterminate = true;
    }

    public void setSticksY(double leftY, double rightY){
        leftStickY = leftY;
        rightStickY = rightY;
    }

    public void run(){
        try{
            while(!exterminate){
                crServoLeft.setPower(leftStickY);
                crServoRight.setPower(rightStickY);
                crServoRight.setPower(0);
                crServoLeft.setPower(0);
            }
        }catch (Exception e){
            crServoRight.setPower(0);
            crServoLeft.setPower(0);
        }
    }
}
