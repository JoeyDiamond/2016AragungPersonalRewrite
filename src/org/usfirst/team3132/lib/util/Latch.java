package org.usfirst.team3132.lib.util;

/**
 * Created by sailo on 5/14/2016.
 *
 * class for latching a boolean
 */
public class Latch {
    private boolean lastVal;
    private boolean lastResult;

    public boolean update(boolean newVal) {
        boolean result = newVal && !lastVal;
        lastResult = result;
        lastVal = newVal;
        return result;
    }

    public boolean get(){
        return lastResult;
    }
}
