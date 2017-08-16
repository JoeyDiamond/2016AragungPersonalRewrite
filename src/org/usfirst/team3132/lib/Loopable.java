package org.usfirst.team3132.lib;

/**
 * Interface to be run by a Looper object.
 *
 * Based heavily off of 254's looper architecture
 *
 * Created by sailo on 6/17/2016.
 */
public interface Loopable {

    public String getName();

    public void update();
}
