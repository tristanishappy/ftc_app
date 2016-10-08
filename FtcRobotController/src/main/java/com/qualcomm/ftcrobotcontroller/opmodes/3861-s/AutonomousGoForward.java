package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Tristan on 1/23/2016.
 */
public class AutonomousGoForward extends LinearOpMode
{

    DcMotor motorRF;//Declares name of DcMotors
    DcMotor motorLF;
    DcMotor motorRB;
    DcMotor motorLB;

    //TouchSensor touchSensor;
    //OpticalDistanceSensor opticalDistanceSensor;
    //LightSensor light;

    int RB = 1;

    Servo servoPeople;

    @Override
    public void runOpMode() throws InterruptedException
    {
        motorRF = hardwareMap.dcMotor.get("motorRF");//sets DcMotors to type of motor
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLB = hardwareMap.dcMotor.get("motorLB");
        servoPeople = hardwareMap.servo.get("servoPeople");

        motorLB.setDirection(DcMotor.Direction.REVERSE);//changes left wheels so that they turn correctly
        motorLF.setDirection(DcMotor.Direction.REVERSE);

        //light = hardwareMap.lightSensor.get("light");
        //light.enableLed(true);
        //servo = hardwareMap.servo.get("servo");
        //touchSensor = hardwareMap.touchSensor.get("sensor_touch");
        //opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_distance");

        waitForStart();

        bothWheels(-1, -1);

        sleep(6000);

        stops();
    }
    private void bothWheels(double x, double y)
    {
        leftWheels(x);
        rightWheels(y);
    }
    private void rightWheels(double x)
    {
        float y = (float) x;
        motorRF.setPower(y);
        motorRB.setPower(y);
    }
    private void leftWheels(double x)
    {
        float y = (float) x;
        motorLF.setPower(y);
        motorLB.setPower(y);
    }
    private void stops()
    {
        bothWheels(0, 0);
    }
    private void redAuto()
    {

    }
}
