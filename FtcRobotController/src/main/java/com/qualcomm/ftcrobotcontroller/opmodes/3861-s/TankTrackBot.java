package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TankTrackBot extends OpMode
{

    DcMotor motorRF;//Declares name of DcMotors
    DcMotor motorLF;
    DcMotor motorRB;
    DcMotor motorLB;


    @Override
    public void init()
    {
        motorRF = hardwareMap.dcMotor.get("motorRF");//sets DcMotors to type of motor
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLB = hardwareMap.dcMotor.get("motorLB");

        motorLB.setDirection(DcMotor.Direction.REVERSE);//changes left wheels so that they turn correctly
        motorLF.setDirection(DcMotor.Direction.REVERSE);//because they are flipped from the right side
    }

    @Override
    public void loop()
    {
        float movePowerL = -gamepad1.left_stick_y;
        float movePowerR = -gamepad1.left_stick_y;
        float turnLPower = gamepad1.left_stick_x;
        float turnRPower = gamepad1.left_stick_x;

        int ForOrBack = 1;

        if (movePowerR <= 0)
        {
            ForOrBack = -1;
        }
        if (turnRPower >= 0)//1 => lor=1|-1 => lor-1
        {
            motorLF.setPower(-(movePowerR));
            motorLB.setPower(-(movePowerR));
            motorRF.setPower(-(ForOrBack*(movePowerR-turnRPower/2) - turnRPower));
            motorRB.setPower(-(ForOrBack*(movePowerR-turnRPower/2) - turnRPower));
        }
        else
        {
            motorLF.setPower(-(ForOrBack*(movePowerL-turnLPower/2) + turnLPower));
            motorLB.setPower(-(ForOrBack*(movePowerL-turnLPower/2) + turnLPower));
            motorRF.setPower(-(movePowerL));
            motorRB.setPower(-(movePowerL));
        }
    }

    @Override
    public void stop()
    {

    }

}
