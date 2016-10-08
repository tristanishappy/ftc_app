package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class TurningBotNoServo extends OpMode
{

    DcMotor motorRF;//Declares name of DcMotors
    DcMotor motorLF;
    DcMotor motorRB;
    DcMotor motorLB;
    DcMotor motorTT;
    DcMotor motorTB;

    Servo servoHookrelease;
    //private float hookRelease = 0;
    Servo servoAim;
    private float aim = 0;
    Servo servoPlow;
    //private float plow = 0;
    Servo servoZipline;
    //private float zipline = 0;
    
    private float back = 0;
    private boolean backwards = false;
    
    @Override
    public void init()
    {
        motorRF = hardwareMap.dcMotor.get("motorRF");//sets DcMotors to type of motor
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLB = hardwareMap.dcMotor.get("motorLB");

        motorTT = hardwareMap.dcMotor.get("motorTT");
        motorTB = hardwareMap.dcMotor.get("motorTB");

        servoAim = hardwareMap.servo.get("servoAim");
        servoPlow = hardwareMap.servo.get("servoPlow");
        servoHookrelease = hardwareMap.servo.get("servoHookRelease");
        servoZipline = hardwareMap.servo.get("servoZipline");
        
        motorTT.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);//changes left wheels so that they turn correctly
        motorLF.setDirection(DcMotor.Direction.REVERSE);//because they are flipped from the right side
    }

    @Override
    public void loop()
    {
        float movePowerL = -gamepad1.right_stick_y;
        float movePowerR = -gamepad1.right_stick_y;
        float turnLPower = gamepad1.left_stick_x;
        float turnRPower = gamepad1.left_stick_x;
        back += 1;

        if (gamepad1.right_bumper && !backwards && back >= 63)
        {
            backwards = true;
            back = 0;
        }
        else if (gamepad1.right_bumper && backwards && back >= 63)
        {
            backwards = false;
            back = 0;
        }

        if (backwards) {
            movePowerL *= -1;
            movePowerR *= -1;
            turnLPower *= -1;
            turnRPower *= -1;
        }
        motorTT.setPower(gamepad2.left_stick_y);
        motorTB.setPower(gamepad2.left_stick_y);
        //AIM
        if (-gamepad2.right_stick_y >= 0 && aim < 0.99)
        {
            aim += 0.01;
        }
        else if (gamepad2.right_stick_y < 0 && aim > -0.99 )
        {
            aim -= 0.01;
        }
        servoAim.setPosition(aim);

        if (movePowerR >= 0 && turnLPower >= 0)//upper right +/+
        {
            motorLF.setPower(-(movePowerR));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR));
            motorRF.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));
        } else if (movePowerR >= 0 && turnLPower <= 0)//upper left +/-
        {
            motorLF.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));
            motorRF.setPower(-(movePowerR));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR));
        } else if (movePowerR <= 0 && turnLPower <= 0)//lower left -/-
        {
            motorLF.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));
            motorRF.setPower(-(movePowerR));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR));
        } else if (movePowerR <= 0 && turnLPower >= 0)//lower right -/+
        {
            motorLF.setPower(-(movePowerR));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR));
            motorRF.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));
        }
    }

    @Override
    public void stop()
    {

    }

}