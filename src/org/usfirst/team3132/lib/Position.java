package org.usfirst.team3132.lib;

/**
 * A class for defining a position given X,Y coordinates and a heading
 *
 * Created by sailo on 6/17/2016.
 */
public class Position {

    public double x = 0;
    public double y = 0;
    public double heading = 0;

    /**
     * Default constructor starts at position (0,0,0)
     */
    public Position(){
        this.x = 0;
        this.y = 0;
        this.heading = 0;
    }

    /**
     * Create a position object at a given starting point
     * @param x initial x position
     * @param y initial y position
     * @param heading initial heading
     */
    public Position(double x, double y, double heading){
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public void move(double distance, double angle){
        this.x += distance * Math.cos(Math.toRadians(angle));
        this.y += distance * Math.sin(Math.toRadians(angle));
    }

    public void move(double xDiff, double yDiff, double headingDiff){
        this.x += xDiff;
        this.y += yDiff;
        this.heading += headingDiff;
    }

    /**
     * Sets this instance equal to the passed instance
     * @param pos the position to be cloned
     */
    public void Clone(Position pos){
        this.x = pos.x;
        this.y = pos.y;
        this.heading = pos.heading;
    }

    /**
     * Returns the position vector in the form (X, Y, Heading)
     * @return
     */
    public double[] getPositionVector(){
        return new double[] {x,y,heading};
    }

    /**
     * Resets the position to zero
     */
    public void reset(){
        x = 0;
        y = 0;
        heading = 0;
    }
}
