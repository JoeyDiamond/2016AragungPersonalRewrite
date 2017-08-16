package org.usfirst.team3132.lib;


import java.util.Vector;

/**
 * Created by sailo on 6/20/2016.
 *
 * Class based off of Multilooper setup. Specifically designed to monitor a set of
 * Collision objects. Each Collision object should do all of the control of the systems
 * this just provides a structure for running them.
 */
public class CollisionHandler implements Loopable {

    protected String TAG = "CollisionHandler";
    protected boolean enabled = true;
    Vector<Collision> collisions = new Vector<Collision>();
    protected boolean shouldPrint = true;
    protected Looper looper;

    /**
     * General Constructor
     * @param name name of CollisionHandler instance
     * @param period update period of the collisionHandler process
     */
    public CollisionHandler(String name, double period) {
        looper = new Looper(name,this,period);
    }

    @Override
    /**
     * @return returns the name of this instance
     */
    public String getName() {
        return looper.name;
    }

    @Override
    /**
     * checks all collision cases encapuslated in this class.
     */
    public void update() {
        if(!enabled) {
            System.out.println(TAG + ": update: disabled");
            return;
        }
        if(shouldPrint){
        	System.out.println(TAG + ": starting update");
        }
        for(int i=0;i<collisions.size();i++){
            collisions.elementAt(i).update();
        }
        if(shouldPrint){
        	System.out.println(TAG + ": finished update");
        }
    }

    /**
     * runs the reset() method in all collisions
     */
    public void resetAll(){
        if(shouldPrint){
        	System.out.println(TAG + ": starting reset");
        }
        for(int i=0;i<collisions.size();i++){
            collisions.elementAt(i).reset();
        }
        if(shouldPrint){
        	System.out.println(TAG + ": finished reset");
        }
    }

    /**
     *
     * @param collision the type of collision desired
     * @return the contained collision that is an instance of the type passed in
     */
    public Collision getCollisionOccurance(Collision collision){
        if(!collisions.contains(collision))
            return null;
        int index = collisions.indexOf(collision);
        return collisions.elementAt(index);
    }

    /**
     * Adds a collision to the set being checked
     * @param collision the collision to be added
     */
    public void addCollision(Collision collision){
        collisions.add(collision);
    }

    /**
     * starts the collisionHandler
     */
    public void enable(){
        looper.start();
    }

    /**
     * Stops the collisionHandler
     */
    public void disable(){
        looper.stop();
    }
}
