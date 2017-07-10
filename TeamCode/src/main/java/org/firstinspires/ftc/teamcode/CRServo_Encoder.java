package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Created by radad on 09-Jul-17.
 */

public class CRServo_Encoder extends Thread{
    private Thread t;
    private String threadName;
    private CRServo crServo;
    private CRServo_Encoder synchronizeServo;
    private boolean exterminate;
    private int encoderRotations;
    private double encoderPrevious;
    private double encoderNow;
    private double diffEncoder;
    private double diffPower;
    private double powerServo;

    CRServo_Encoder(String name, CRServo servo){
        threadName = name;
        crServo = servo;
    }

    public void start(){
        if (t == null){
            t = new Thread(this, threadName);
            exterminate = false;
            encoderRotations = 0;
            t.start();
        }
    }

    public void exterminate(){
        exterminate = true;
    }

    public int numberRotations(){
        return encoderRotations;
    }

    public void setServo(CRServo servo){
        crServo = servo;
    }

    public void setPower(double power){
        crServo.setPower(power);
    }

    public void resetServo(){
        encoderRotations = 0;
    }

    public double getPosition(){
        return crServo.getController().getServoPosition(crServo.getPortNumber());
    }

    public void synchronouslyMoveServos(CRServo_Encoder servo2, double power){
        synchronizeServo = servo2;
        powerServo = power;
        double diffPower = 0;
        diffPower = this.numberRotations() - servo2.numberRotations();
        crServo.setPower(power - diffPower);
        servo2.setPower(power + diffPower);
    }

    public void run(){
        try{
            encoderPrevious = crServo.getController().getServoPosition(crServo.getPortNumber());
            Thread.sleep(50);
            while(!exterminate){
                encoderNow = crServo.getController().getServoPosition(crServo.getPortNumber());
                diffEncoder = encoderPrevious - encoderNow;
                if(diffEncoder == 1 || diffEncoder == -1){
                    encoderRotations = encoderRotations + 1;
                }
                encoderPrevious = encoderNow;
                Thread.sleep(50);
            }
            crServo.setPower(0);
        } catch(Exception e){
            crServo.setPower(0);
        }
    }
}
