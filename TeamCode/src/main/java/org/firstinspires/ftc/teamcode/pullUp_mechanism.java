package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by radad on 08-Jul-17.
 */

public class pullUp_mechanism extends Thread{
    private Thread t;
    private String threadName;
    private DcMotor pullUpMechanismRight;
    private DcMotor pullUpMechanismLeft;
    private boolean UP;
    private boolean DOWN;
    private boolean exterminate;

    pullUp_mechanism (String name, DcMotor leftMotor, DcMotor rightMotor){
        threadName = name;
        pullUpMechanismRight = rightMotor;
        pullUpMechanismLeft = leftMotor;
    }

    public void start(){
        if(t == null){
            t = new Thread (this, threadName);
            exterminate = false;
            t.start();
        }
    }

    public void exterminate(){
        exterminate = true;
    }

    public void setUpDown(boolean up, boolean down){
        UP = up;
        DOWN = down;
    }

    public void run(){
        try{
            while (!exterminate){
                if (UP) {
                    pullUpMechanismLeft.setPower(-1);
                    pullUpMechanismRight.setPower(1);
                } else {
                    if (DOWN) {
                        pullUpMechanismLeft.setPower(1);
                        pullUpMechanismRight.setPower(-1);
                    } else {
                        pullUpMechanismLeft.setPower(0);
                        pullUpMechanismRight.setPower(0);
                    }
                }
            }
            pullUpMechanismRight.setPower(0);
            pullUpMechanismLeft.setPower(0);
        } catch (Exception e){
            pullUpMechanismRight.setPower(0);
            pullUpMechanismLeft.setPower(0);
        }
    }
}
