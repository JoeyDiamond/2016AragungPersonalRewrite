package org.usfirst.team3132.lib;


import java.util.*;
import java.util.Timer;

/**
 * Creates a thread that executes a Loopable's update() method on a given rate.
 *
 * Based heavily off of 254's Looper architecture
 *
 * Created by sailo on 6/19/2016.
 */
public class Looper {
    Loopable loopable;
    java.util.Timer looperUpdater;
    String name;
    double period;
    boolean shouldPrint;

    /**
     * General constructor
     * @param name name of instance
     * @param loopable the loopable to be run
     * @param period the rate at which to run
     */
    public Looper(String name, Loopable loopable, double period){
        this.name = name;
        this.loopable = loopable;
        this.period = period;
        this.shouldPrint = false;
    }

    /**
     * Scheduler for the looper
     */
    private class UpdaterTask extends TimerTask {
        Looper looper;

        UpdaterTask(Looper looper){
            if(looper == null){
                throw new NullPointerException(name + " was given null looper");
            }
            this.looper = looper;
        }

        @Override
        public void run() {
            looper.update();
        }
    }

    /**
     * Start the looper running
     */
    public void start(){
        // if not already running
        if(looperUpdater == null){
            looperUpdater = new Timer("Looper - " + name);
            // schedule periodic calls
            looperUpdater.schedule(new UpdaterTask(this), 0L,(long)(this.period*1000));
        }
    }

    /**
     * Stops the looper from running
     */
    public void stop(){
        if(looperUpdater != null){      // if running
            looperUpdater.cancel();     // cancel future runs
            looperUpdater = null;       // nullify the scheduler
        }
    }

    /**
     * Called by the UpdaterTask
     */
    public void update(){
        loopable.update();  // run loopable
        if(shouldPrint){   // log if supposed to
            System.out.println("looper: " + name + " updated: " + loopable.getName());
        }
    }

    /**
     * Enables logcat logging
     */
    public void enablePrinting(){
        shouldPrint = true;
    }

    /**
     * Disables logcat logging
     */
    public void disablePrinting(){
        shouldPrint = false;
    }
}
