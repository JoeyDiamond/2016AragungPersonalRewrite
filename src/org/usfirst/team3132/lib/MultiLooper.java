package org.usfirst.team3132.lib;


import java.util.Vector;

/**
 * A Looper capable of running many Loopables at once with only one thread's worth of overhead.
 *
 * Based heavily off of 254's Looper architecture
 *
 * Created by sailo on 6/19/2016.
 */
public class MultiLooper implements Loopable {

    private boolean shouldPrint;
    private Looper looper;      // single to looper runs a vector of loopables
    private java.util.Vector<Loopable> loopables = new Vector<Loopable>();

    /**
     * General Constructor
     * @param name name of the instance
     * @param period the period between updates
     */
    public MultiLooper(String name, double period){
        looper = new Looper(name,this,period);
        shouldPrint = false;
    }

    @Override
    /**
     * returns the name of the instance
     */
    public String getName() {
        return looper.name;
    }

    @Override
    /**
     * runs all loopables encapsulated by the multilooper
     */
    public void update() {
        for(int i=0; i<loopables.size();i++){
            Loopable c = loopables.elementAt(i);
            if (c != null) {
                c.update();
                if(shouldPrint)
                    System.out.println("multilooper: " + looper.name + " updated: " + c.getName());
            } else {
            	
            }
        }
    }

    /**
     * starts the looper
     */
    public void start(){
        looper.start();
    }

    /**
     * stops the looper
     */
    public void stop(){
        looper.stop();
    }

    /**
     * add a loopable to the set
     * @param loopable the loopable to add
     */
    public void addLoopable(Loopable loopable){
        loopables.add(loopable);
    }

    /**
     * enable logcat logging
     */
    public void enablePrinting(){
        shouldPrint = true;
    }

    /**
     * disable logcat logging
     */
    public void disablePrinting(){
        shouldPrint = false;
    }
}
