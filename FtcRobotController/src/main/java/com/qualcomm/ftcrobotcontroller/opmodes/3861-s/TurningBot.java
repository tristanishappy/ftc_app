package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class TurningBot extends OpMode
{
    //Declares name of DcMotors
    DcMotor motorRF;            //Gamepad 1: right stick and left stick
    DcMotor motorLF;            //Gamepad 1: right stick and left stick
    DcMotor motorRB;            //Gamepad 1: right stick and left stick
    DcMotor motorLB;            //Gamepad 1: right stick and left stick
    private float back = 0;     //Gamepad 1: right bumper
    private boolean backwards = false;

    DcMotor motorTT;            //Gamepad 2: left stick
    DcMotor motorTB;            //Gamepad 2: left stick
    DcMotor motorPC1;            //Gamepad 2: left trigger and right trigger
    DcMotor motorPC2;            //Gamepad 2: left trigger and right trigger

    Servo servoAim;             //Gamepad 2: rightstick
    Servo servoHookrelease;     //Gamepad 2: Y button and B button
    Servo servoPeople;

    private float aim = 0;
    private float hookRelease = 0;
    private double people = 0;

    //private double plow = 0.5;

    //Servo servoZipline;         //Gamepad 2: X button and A button
    //private double zipline = 0.5;




    @Override
    public void init()
    {
        motorRF = hardwareMap.dcMotor.get("motorRF");//sets DcMotors to type of motor
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLB = hardwareMap.dcMotor.get("motorLB");

        motorTT = hardwareMap.dcMotor.get("motorTT");
        motorTB = hardwareMap.dcMotor.get("motorTB");
        motorPC1 = hardwareMap.dcMotor.get("motorPC1");
        motorPC2 = hardwareMap.dcMotor.get("motorPC2");
        
        servoAim = hardwareMap.servo.get("servoAim");
        servoPeople = hardwareMap.servo.get("servoPeople");

        servoHookrelease = hardwareMap.servo.get("servoHookrelease");
        //servoZipline = hardwareMap.servo.get("servoZipline");//N/A
        
        motorTT.setDirection(DcMotor.Direction.REVERSE);
        motorLB.setDirection(DcMotor.Direction.REVERSE);//changes left wheels so that they turn correctly
        motorLF.setDirection(DcMotor.Direction.REVERSE);//because they are flipped from the right side
    }

    @Override
    public void loop()
    {
        //DRIVING CONTROLS
        float movePowerL = -gamepad1.left_stick_y;
        float movePowerR = -gamepad1.left_stick_y;
        float turnLPower = -gamepad1.right_stick_x;
        float turnRPower = -gamepad1.right_stick_x;
        back += 1;

        if (gamepad1.right_bumper && !backwards && back >= 63)//Makes it so you can't switch driving direction every [less that a second]
        {
            backwards = true;//Switch direction
            back = 0;
        }
        else if (gamepad1.right_bumper && backwards && back >= 63)//Makes it so you can't switch driving direction every [less that a second]
        {
            backwards = false;//Switch direction
            back = 0;
        }
        if (backwards)//the switch direction code
        {
            movePowerL *= -1;
            movePowerR *= -1;
            turnLPower *= -1;
            turnRPower *= -1;
        }

        motorTT.setPower(-gamepad2.left_stick_y);
        motorTB.setPower(-gamepad2.left_stick_y);

        //AIM = the control for our robots aim of the tape measurer (up n down)
        if (-gamepad2.right_stick_y > 0 && aim < 0.996)
        {
            aim += 0.004;
            servoAim.setPosition(aim);
        }
        else if (-gamepad2.right_stick_y < 0 && aim > 0.004)
        {
            aim -= 0.004;
            servoAim.setPosition(aim);
        }

        /*if (-gamepad2.right_stick_y >= aim && -gamepad2.right_stick_y > 0)
        {
            servoAim.setPosition(-gamepad2.right_stick_y);
            aim = -gamepad2.right_stick_y;
        }
        else if (-gamepad2.right_stick_y < 0)
        {
            servoAim.setPosition(0);
            aim = 0;
        }*/
        //HOOK RELEASE = releases the large hook of our robot
        if(gamepad2.y && hookRelease < 0.99)
        {
            hookRelease += 0.01;
        }
        if(gamepad2.b && hookRelease > 0.01)
        {
            hookRelease -= 0.01;
        }
        servoHookrelease.setPosition(hookRelease);
        //PEOPLE = places people into bucket
        if(gamepad2.dpad_up && people < 0.99)
        {
            people += 0.01;
        }
        if(gamepad2.dpad_down && people > 0.01)
        {
            people -= 0.01;
        }
        servoPeople.setPosition(people);
        //ZIPLINE = the middle thing in our robot, this controls its left and right [Not Working]
        /*if(gamepad2.x)
        {
           zipline += 0.1;
        }
        if(gamepad2.a)
        {
            zipline -= 0.1;
        }
        servoZipline.setPosition(zipline);
        zipline = 0.5;
        */
        //Paracord puller
        motorPC1.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
        motorPC2.setPower(motorPC1.getPower());

        //DRIVING
        if (movePowerR > 0 && turnLPower >= 0)//upper right +/+
        {
            motorLF.setPower(-(movePowerR));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR));
            motorRF.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));
        }
        else if (movePowerR > 0 && turnLPower <= 0)//upper left +/-
        {
            motorLF.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));
            motorRF.setPower(-(movePowerR));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR));
        }
        else if (movePowerR < 0 && turnLPower <= 0)//lower left -/-
        {
            motorLF.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR + (2 * movePowerR) * turnLPower));
            motorRF.setPower(-(movePowerR));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR));
        }
        else if (movePowerR < 0 && turnLPower >= 0)//lower right -/+
        {
            motorLF.setPower(-(movePowerR));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(movePowerR));
            motorRF.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(movePowerR - (2 * movePowerR) * turnLPower));
        }
        else if (movePowerR == 0 && !backwards)
        {
            motorLF.setPower(-(turnLPower));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(turnLPower));
            motorRF.setPower(-(-turnLPower));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(-turnLPower));
        }
        else
        {
            motorLF.setPower(-(-turnLPower));//sets DcMotors to PL-TL or PL
            motorLB.setPower(-(-turnLPower));
            motorRF.setPower(-(turnLPower));//sets DcMotors to PR-TR or PR
            motorRB.setPower(-(turnLPower));
        }
    }

    @Override
    public void stop()
    {

    }

}