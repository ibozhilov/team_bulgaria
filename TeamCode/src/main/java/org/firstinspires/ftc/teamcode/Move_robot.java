package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Student on 7/8/2017.
 */

public class Move_robot extends Thread {
    private Thread t;
    private String threadName;
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private double X;
    private double Y;
    private double powerLF;
    private double powerLB;
    private boolean leftBumper;
    private boolean rightBumper;
    private boolean exterminate;

    Move_robot (String name, DcMotor motorLF, DcMotor motorRF, DcMotor motorLB, DcMotor motorRB){
        threadName = name;
        leftFrontMotor = motorLF;
        rightFrontMotor = motorRF;
        leftBackMotor = motorLB;
        rightBackMotor = motorRB;
    }

    public void start(){
        if (t == null){
            t = new Thread(this, threadName);
            exterminate = false;
            t.start();
        }
    }

    public void exterminate(){
        exterminate = true;
    }

    public void setXY(double x, double y){
        X = x;
        Y = y;
    }

    public void setBumpers(boolean LBumper, boolean RBumper){
        leftBumper = LBumper;
        rightBumper = RBumper;
    }

    public void run(){
        try {
            while(!exterminate) {
                powerLF = (X + Y) / Math.sqrt(2);
                powerLB = (Y - X) / Math.sqrt(2);
                leftFrontMotor.setPower(powerLF);
                rightFrontMotor.setPower(powerLB);
                leftBackMotor.setPower(powerLF);
                rightBackMotor.setPower(powerLB);
            }
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        } catch (Exception e){
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        }

    }
}
