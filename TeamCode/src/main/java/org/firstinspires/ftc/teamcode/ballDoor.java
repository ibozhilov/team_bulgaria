package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by radad on 09-Jul-17.
 */

public class ballDoor extends Thread{
    private Thread t;
    private String threadName;
    private float CLOSED = 0.5f;
    private float ORANGE = 0.8f;
    private float BLUE = 0.2f;
    private Servo ballDoorServo;
    private boolean exterminate;
    private boolean closeDoor;
    private boolean orangeBallsRelease;
    private boolean blueBallsRelease;

    ballDoor (String name, Servo servo){
        threadName = name;
        ballDoorServo = servo;
    }

    public void start(){
        if(t == null){
            t = new Thread (this, threadName);
            exterminate = false;
            t.start();
        }
    }

    public void setButtons(boolean close, boolean orange, boolean blue){
        closeDoor = close;
        orangeBallsRelease = orange;
        blueBallsRelease = blue;
    }

    public void run(){
        try{
            while(!exterminate){
                if(closeDoor){
                    ballDoorServo.setPosition(CLOSED);
                }
                if(orangeBallsRelease){
                    ballDoorServo.setPosition(ORANGE);
                }
                if(blueBallsRelease){
                    ballDoorServo.setPosition(BLUE);
                }
            }
        }catch (Exception e){
            ballDoorServo.setPosition(CLOSED);
        }
    }
}
