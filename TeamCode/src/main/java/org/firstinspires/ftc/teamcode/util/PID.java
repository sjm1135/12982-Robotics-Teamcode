package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.ElapsedTime;


public class PID {

    private boolean init = false; // used to set up controller in the first loop it is used in.
    private double kp;  // proportional modifier
    private double ki;  // integral modifier
    private double kd;  // derivative modifier
    private double integralSum = 0;
    private final double INTEGRAL_MAX = 10000; // Used to limit integral term from getting too high
    private double lastError = 0; // used to futz integral and derivative term
    private double lastTime = 0; // used to futz derivative term
    ElapsedTime timer = new ElapsedTime();

    // constructor
    public PID (double p, double i, double d) {
        kp = p;
        ki = i;
        kd = d;
    }

    public double PIDControl(double error) {

        if (!init) { // resetting values if this is the first loop
            lastError = error;
            timer.reset();
            integralSum = 0;
            lastTime = 0;
            init = true;
        }

        double time = timer.seconds(); // recording current time

        double proportional = kp * error;

        // calculating integral term as we would a riemann sum
        integralSum += ki * error * (time - lastTime);

        // limiting integral term from going too high
        if (integralSum > INTEGRAL_MAX) {
            integralSum = INTEGRAL_MAX;
        }
        else if (integralSum < -INTEGRAL_MAX) {
            integralSum = -INTEGRAL_MAX;
        }

        // calculating derivative term as we would the slope of a secant line
        double derivative = kd * (error - lastError) / (time - lastTime);

        // setting up lastTime and lastError for next loop
        lastError = error;
        lastTime = time;

        // returning full sum
        return proportional + integralSum + derivative;
    }

    // function to reset init to false
    public void reset() {
        init = false;
    }

}
