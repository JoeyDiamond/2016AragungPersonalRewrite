package org.usfirst.team3132.lib;

/**
 * Created by sailo on 6/1/2016.
 */
public abstract class Subsystem {

    protected String name;
    protected boolean enabled = false;
    /**
     * General constructor
     * @param name name of the instance
     */
    public Subsystem(String name){
        this.name = name;
    }

    /**
     * enables subsystem operation
     */
    public void enable(){
        enabled = true;
    }

    /**
     * disables subsystem without affecting controlling object
     */
    public void disable(){
        enabled = false;
    }

    /**
     *
     * @return the state of the subsystem
     */
    public boolean isEnabled(){
        return enabled;
    }

    /**
     *
     * @return the name of the instance
     */
    public String getName(){
        return name;
    }
    
    public abstract boolean isInPosition();
    
}
