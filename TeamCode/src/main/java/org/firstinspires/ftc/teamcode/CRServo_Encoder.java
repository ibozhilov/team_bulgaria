package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Created by radad on 09-Jul-17.
 */

public class CRServo_Encoder extends Thread{
    private Thread t;
    private String threadName;
    private CRServo crServo;
    private CRServo_Encoder servo2;
    private boolean exterminate;
    private double encoderRotations;
    private double encoderPrevious;
    private double encoderNow;
    private double diffEncoder;
    private double diffPower;
    private double powerSyncMove;
    private boolean moveSynchronously;

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

    public double numberRotations(){
        return encoderRotations;
    }

    public void setPower(double power){
        crServo.setPower(power);
    }

    public void resetServoEncoder(){
        encoderRotations = 0;
    }

    public double getPosition(){
        return crServo.getController().getServoPosition(crServo.getPortNumber());
    }

    public void set2Servo (CRServo_Encoder secondServo){
        servo2 = secondServo;
    }

    public void setPowerSync(boolean Sync, double power){
        Sync = moveSynchronously;
        power = powerSyncMove;
    }

    public void run(){
        try{
            encoderPrevious = crServo.getController().getServoPosition(crServo.getPortNumber());
            Thread.sleep(50);
            double error = 0;
            while(!exterminate){
                encoderNow = crServo.getController().getServoPosition(crServo.getPortNumber());
                diffEncoder = encoderPrevious - encoderNow;
                if(Math.abs(diffEncoder) > 0.5){
                    if (diffEncoder>0) {
                        encoderRotations = encoderRotations + (1 - encoderPrevious) + encoderNow;
                    } else {
                        encoderRotations = encoderRotations  - (1 - encoderPrevious) - encoderNow;
                    }
                } else {
                    encoderRotations = encoderRotations + diffEncoder;
                }
                encoderPrevious = encoderNow;
                if (moveSynchronously && servo2 != null) {
                    error = this.numberRotations() - servo2.numberRotations();
                    crServo.setPower(powerSyncMove - diffPower);
                    servo2.setPower(powerSyncMove + diffPower);
                }
                Thread.sleep(50);
            }
            crServo.setPower(0);
        } catch(Exception e){
            crServo.setPower(0);
        }
    }
}
