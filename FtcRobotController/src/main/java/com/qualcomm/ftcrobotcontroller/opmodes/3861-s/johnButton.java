package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Administrator on 10/1/2016.
 */
public class johnButton extends OpMode
{

    DcMotor motorR;
    DcMotor motorL;
    Servo btn;

    @Override
    public void init()
    {

        motorR = hardwareMap.dcMotor.get("motorR");
        motorL = hardwareMap.dcMotor.get("motorL");
        btn = hardwareMap.servo.get("servo");

    }

    @Override
    public void loop()
    {
        double r = -gamepad1.right_stick_y;
        double l = -gamepad1.left_stick_y;

        if (gamepad1.y) {
            btn.setPosition(1);
        }

        else if (gamepad1.a) {
            btn.setPosition(0);
        }

        else {
            btn.setPosition(0.5);
        }

        motorR.setPower(r);
        motorL.setPower(l);

    }

    @Override
    public void stop()
    {

    }
}