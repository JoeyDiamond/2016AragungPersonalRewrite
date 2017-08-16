package org.usfirst.team3132.lib;

/**
 * A class for working with Vectors
 *
 * Created by sailo on 5/10/2016.
 */
public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector(Vector clone) {
        this.x = clone.x;
        this.y = clone.y;
    }

    public Vector add(Vector b) {
        return new Vector(this.x + b.x, this.y + b.y);
    }

    public Vector sub(Vector b) {
        return new Vector(this.x - b.x, this.y - b.y);
    }

    public Vector scalarMult(double c){
        return new Vector (this.x * c, this.y *c);
    }

    public static Vector fromAngle(double c) {
        c = Math.toRadians(c);
        return new Vector(Math.cos(c), Math.sin(c));
    }

    public Vector unit() {
        double magnitude = this.mag();
        return new Vector(this.x / magnitude, this.y / magnitude);
    }

    public double magSq() {
        return this.dot(this);
    }

    public double dot(Vector b) {
        return this.x * b.x + this.y * b.y;
    }

    public double mag() {
        return Math.sqrt(this.magSq());
    }

    public double scalarProjectOnto(Vector b) {
        return this.dot(b) / b.mag();
    }

    public Vector projectOnto(Vector b) {
        return b.scalarMult(this.dot(b) / b.magSq());
    }

    public Vector rotate(double degrees) {
        double angle = Math.toRadians(degrees);
        double x = this.x*Math.cos(angle)-this.y*Math.sin(angle);
        double y = this.x*Math.sin(angle)+this.y*Math.cos(angle);
        return new Vector(x,y);
    }

    public double radiansFrom(double theta){
        double current = Math.atan(this.y / this.x);
        return theta - current;
    }

    public double getRadians(){
        return Math.atan(this.y / this.x);
    }

    public double getDegrees(){
        return Math.toDegrees(getRadians());
    }

    public double radiansFrom(Vector b){
        return this.getRadians() - b.getRadians();
    }

    public double degreesFrom(double theta){
        return this.getDegrees() - theta;
    }

    public double degreesFrom(Vector b){
        return this.getDegrees() - b.getDegrees();
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
