package org.usfirst.team3132.lib;

public abstract class Drivebase extends Subsystem{

	public Drivebase(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public abstract void setLeft(double velocity);
	
	public abstract void setRight(double velocity);
	
	public abstract double getLeft();
	
	public abstract double getRight();
}
