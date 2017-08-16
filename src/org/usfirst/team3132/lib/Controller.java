package org.usfirst.team3132.lib;

/**
 * Created by sailo on 6/19/2016.
 */
public interface Controller {

    /**
     *
     * @param current input the current value of the object being controlled
     * @return the output to be passed to the controlled object
     */
    public double calcOutput(double current);

    /**
     * resets the controller to a "fresh" state
     */
    public void reset();

    /**
     *
     * @return is the current value at the target specified
     */
    public boolean isOnTarget();

    public void setTarget(double target);

    public double getTarget();
}
