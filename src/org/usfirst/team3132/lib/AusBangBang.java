package org.usfirst.team3132.lib;

/**
 * Implements a BangBang controller. Commonly used for flywheels or continuous systems.
 *
 * Created by sailo on 6/20/2016.
 */
public class AusBangBang implements Controller{
    protected boolean inverted;
    protected double maxOutput;
    protected double minOutput;
    protected double target;
    protected double currentValue;

    /**
     * Generic constructor uses default configuration values
     */
    public AusBangBang(){
        maxOutput = 1.0;
        minOutput = 0.0;
        inverted = false;
    }

    /**
     * Advanced constructor
     * @param maxOutput output when current is less than target
     * @param minOutput output when current is greater than target
     * @param inverted inverts current/target relationship (i.e. for driving a system in reverse)
     */
    public AusBangBang(double maxOutput, double minOutput, boolean inverted){
        this.maxOutput = maxOutput;
        this.minOutput = minOutput;
        this.inverted = inverted;
    }

    @Override
    public double calcOutput(double current) {
        this.currentValue = current;
        if(!inverted){ // check for inversion
            // not inverted
            if(target > currentValue)  // needs more power
                return maxOutput;
            else                        // doesnt need more power
                return minOutput;
        } else {
            // is inverted
            if (target < currentValue)  // needs more power
                return maxOutput;
            else
                return minOutput;       // doesnt need more power
        }
    }

    @Override
    public void reset() {
        this.target = 0;
        this.currentValue = 0;
        this.inverted = false;
    }

    @Override
    public boolean isOnTarget() {
        // checks if current is above target
        if(!inverted){
            if(currentValue >= target)
                return true;
            else
                return false;
        }
        if(currentValue <= target)
            return true;
        else
            return false;
    }

    @Override
    public void setTarget(double target) {
        this.target = target;
    }

    @Override
    public double getTarget() {
        return this.target;
    }
}
