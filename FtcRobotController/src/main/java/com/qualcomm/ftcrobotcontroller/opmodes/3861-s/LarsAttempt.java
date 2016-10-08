// this program clips the x+y before it adds the turn variable, then does a final clip. It also adds a sin curve b?zier effect to stick movement.

package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class LarsAttempt extends OpMode
{
    //Declares name of DcMotors
    DcMotor motorRF;            //Gamepad 1: right stick and left stick
    DcMotor motorLF;            //Gamepad 1: right stick and left stick
    DcMotor motorRB;            //Gamepad 1: right stick and left stick
    DcMotor motorLB;            //Gamepad 1: right stick and left stick

    @Override
    public void init()
    {
        motorRF = hardwareMap.dcMotor.get("motorRF");//sets DcMotors to type of motor
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLB = hardwareMap.dcMotor.get("motorLB");
        motorRB.setDirection(DcMotor.Direction.REVERSE);
        motorRF.setDirection(DcMotor.Direction.REVERSE);

    }

//                                          CHEAT SHEET!!!
//      GAMEPAD 1 (change all inputs gamepad1.x to gamepad2.x for use of gamepad 2)
//  gamepad1.left_stick_y and gamepad1.right_stick_y range from -1 to 1, where -1 is full up, and 1 is full down
//  gamepad1.left_stick_x and gamepad1.right_stick_x range from -1 to 1, where -1 is full left  and 1 is full right
//  if (gamepad1.a) {}, gamepad1.b, gamepad1.x, gamepad1.y: respective buttons pressed
//  gamepad1.dpad_up, _down, _left, _right: top hat
//  gamepad1.left_bumper, gamepad1.right_bumper: upper buttons on top of controller
//  gamepad1.left_trigger, gamepad1.right_trigger: lower buttons on top of controller, gradual
//
//  float direction = gamepad1.left_stick_x;
//  float right = throttle - direction;
//  right = Range.clip(right, -1, 1);
//
//  Clip: Range.clip(whatever, min, max); can never exceed that range
//
//
// LF: u=u, d=d, l=d, r=u
// RF: u=u, d=d, l=u, r=d
// LB: u=u, d=d, l=u, r=d
// RB: u=u, d=d, l=d, r=u

    @Override
    public void loop()
    {
        //basic movement, right-hand game stick
        double y = -gamepad1.right_stick_y;
        double x = gamepad1.right_stick_x;

        //basic turn, clipping is important since you're adding/subtracting this to the basic movement variables
        double turn = gamepad1.left_stick_x;

        x = Range.clip(x, -1, 1);  //clipping, just to be safe...
        y = Range.clip(y, -1, 1);
        turn = Range.clip(turn, -1, 1);

        double lf = y+x;  //first addition, simple movement
        double lb = y-x;
        double rf = y-x;
        double rb = y+x;

        lf = Range.clip(lf, -1, 1);  //first real clip
        lb = Range.clip(lb, -1, 1);
        rf = Range.clip(rf, -1, 1);
        rb = Range.clip(rb, -1, 1);

        lf = lf+turn;  //second addition, simple turn
        lb = lb+turn;
        rf = rf-turn;
        rb = rb-turn;

        lf = Range.clip(lf, -1, 1);  //second clip, hopefully fixes diagonal turning
        lb = Range.clip(lb, -1, 1);
        rf = Range.clip(rf, -1, 1);
        rb = Range.clip(rb, -1, 1);

//        lf = Math.sin(lf/2*Math.PI);  //gradation on a sine curve, basically a b?zier curve of movement
//        lb = Math.sin(lb/2*Math.PI);
//        rf = Math.sin(rf/2*Math.PI);
//        rb = Math.sin(rb/2*Math.PI);

        lf = Range.clip(lf, -1, 1);  //third and final clip, just in case...
        lb = Range.clip(lb, -1, 1);
        rf = Range.clip(rf, -1, 1);
        rb = Range.clip(rb, -1, 1);

        motorLF.setPower(lf);
        motorLB.setPower(lb);
        motorRF.setPower(rf);
        motorRB.setPower(rb);
    }

    @Override
    public void stop()
    {

    }

}