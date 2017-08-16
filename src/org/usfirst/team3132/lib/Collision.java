package org.usfirst.team3132.lib;

/**
 * Created by sailo on 6/20/2016.
 *
 * This class defines a single collision case. Designed to be added
 * to a CollisionHandler to check and prevent the robot breaking itself.
 */
public abstract class Collision {

    protected String name;
    protected String TAG = "Collision";
    protected String standardLogMessage = " checked collision status";
    protected boolean shouldPrint = true;
    protected boolean enabled = true;

    /**
     * General constructor
     * @param name the name of the instance
     */
    public Collision(String name){
        this.name = name;
    }

    /**
     * the method called by the CollisionHandler on each cycle.
     * Put condition checking and collision resolution code here
     * OVERRIDE ME!
     */
    public abstract void update();

    /**
     * resets the collision to a "fresh" state
     */
    public abstract void reset();

    /**
     * enables collision checking
     */
    public void enable(){
        enabled = true;
    }

    /**
     * disables collision checking without affecting the controlling object
     */
    public void disable(){
        enabled = false;
    }

    /**
     * Turns on and off logcat logging
     * @param shouldLog true = will log; false = wont log
     */
    public void setShouldPrint(boolean shouldLog){
        this.shouldPrint = shouldLog;
    }

    /**
     *
     * @return name + " checked collision status"
     */
    public String getStandardLogMessage(){
        return name + standardLogMessage;
    }
}
