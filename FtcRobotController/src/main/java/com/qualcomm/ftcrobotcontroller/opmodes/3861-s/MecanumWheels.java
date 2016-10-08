package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.GyroSensor;

public class MecanumWheels extends OpMode
{

    //Declares name of DcMotors
    DcMotor motorRF;            //Gamepad 1: right stick and left stick
    DcMotor motorLF;            //Gamepad 1: right stick and left stick
    DcMotor motorRB;            //Gamepad 1: right stick and left stick
    DcMotor motorLB;            //Gamepad 1: right stick and left stick

    MecanumDrive drive;

    //GyroSensor gyro;


    I2cDevice gyro;

    GyroSensor aGyro;

    //Servo buttonServo;

    //DcMotor motorUL;
    //DcMotor motorUR;
    
    private double lift = 0;
    @Override
    public void init()
    {
        drive = new MecanumDrive(hardwareMap.dcMotor.get("motorRF"), hardwareMap.dcMotor.get("motorLF"), hardwareMap.dcMotor.get("motorRB"), hardwareMap.dcMotor.get("motorLB"));

        //motorUL = hardwareMap.dcMotor.get("motorUL");
        //motorUR = hardwareMap.dcMotor.get("motorUR");

        //motorUR.setDirection(DcMotor.Direction.REVERSE);

        //buttonServo = hardwareMap.servo.get("bServo");
        gyro = hardwareMap.i2cDevice.get("aGyro");
        aGyro = hardwareMap.gyroSensor.get("aGyro");
        //DeviceInterfaceModule
    }

    @Override
    public void loop()
    {
        //Moving
        double gyr = 0;
            if(gamepad1.right_bumper) {
                //gyr = aGyro.getRotation();
            }
            double y = -gamepad1.right_stick_y;
            double x = -gamepad1.right_stick_x;
            double turn = gamepad1.left_stick_x;
            drive.move(x, y, turn, gyr);
        //Servo Button
            double button = 0.5;
            if(gamepad2.x)
            {
                button = 0.7;
            }
            if(gamepad2.b)
            {
                button = 0.3;
            }
            //buttonServo.setPosition(button);
        //Ball
            lift = gamepad2.right_stick_y;
            //motorUL.setPower(lift);
            //motorUR.setPower(lift);

//        motorLF.setPower(magnitude * Math.cos(radians) + turn);
//        motorLB.setPower(magnitude * Math.sin(radians) + turn);
//        motorRF.setPower(magnitude * Math.sin(radians) - turn);
//        motorRB.setPower(magnitude * Math.cos(radians) - turn);
    }

    @Override
    public void stop()
    {

    }

}