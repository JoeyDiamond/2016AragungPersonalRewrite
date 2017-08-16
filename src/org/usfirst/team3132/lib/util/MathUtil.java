package org.usfirst.team3132.lib.util;

/**
 * Created by sailo on 5/9/2016.
 */
public class MathUtil {

    /**
     *  Scales the value in to the specified range
     * @param valueIn the value to be scaled
     * @param baseMin the minimum input value
     * @param baseMax the maximum input value
     * @param limitMin the scaled minimum output
     * @param limitMax the scaled maximum output
     * @return the scaled value
     */
    public static double scale(final double valueIn, final double baseMin, final double baseMax, final double limitMin, final double limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }

    /**
     * Scales the value in to the specified range and limits the output to the max and min specified
     * @param valueIn the value to be scaled
     * @param baseMin the minimum input value
     * @param baseMax the maximum input value
     * @param limitMin the scaled minimum output
     * @param limitMax the scaled maximum output
     * @return the scaled value
     */
    public static double scaleClipped(final double valueIn, final double baseMin, final double baseMax, final double limitMin, final double limitMax) {
        double calculatedValue = ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
        return limitValue(calculatedValue, limitMax, limitMin);
    }

    /**
     * Scales the input value to the standard range for a joystick value. Used often enough to be handy
     * @param valueIn typically a joystick axis
     * @return a scaled value from -1.0 to 1.0
     */
    public static double scaleClipped(final double valueIn){
        return scaleClipped(valueIn, -1.27, 1.28, -1.0, 1.0);
    }

    /**
     * Limits the input value between -1.0 and 1.0
     * @param val the value to be limited
     * @return the limited value
     */
    public static double limitValue(double val) {
        return limitValue(val, 1.0);
    }

    /**
     * Limits the input value between -max to +max
     * @param val the value to be limited
     * @param max the absolute value of the limit
     * @return the limited value
     */
    public static double limitValue(double val, double max) {
        if(val > max) {
            return max;
        } else if(val < -max) {
            return -max;
        } else {
            return val;
        }
    }

    /**
     * Limits the input value between the input min and input max
     * @param val the value to be limited
     * @param max the maximum output value
     * @param min the minimum output value
     * @return the limited value
     */
    public static double limitValue(double val, double max, double min) {
        if(val > max) {
            return max;
        } else if(val < min) {
            return min;
        } else {
            return val;
        }
    }

    /**
     * Squares the input value while keeping the +/- sign
     * @param val the value to be squared
     * @return the squared value
     */
    public static double squareMaintainSign(double val) {
        double output = val * val;

        //was originally negative
        if(val < 0) {
            output = -output;
        }

        return output;
    }

    /**
     * Cubes the input value while keeping the +/- sign
     * @param val the value to be cubed
     * @return the cubed value
     */
    public static double power3MaintainSign(double val){
        double output = val*val*val;
        return output;
    }

    /**
     * Returns the maximum value of the three passed in
     * @param a input 1
     * @param b input 2
     * @param c input 3
     * @return the max of a, b, c
     */
    public static double max(double a, double b, double c) {
        a = Math.abs(a);
        b = Math.abs(b);
        c = Math.abs(c);
        if(a > b && a > c) {
            return a;
        } else if(b > c) {
            return b;
        } else {
            return c;
        }
    }
}
