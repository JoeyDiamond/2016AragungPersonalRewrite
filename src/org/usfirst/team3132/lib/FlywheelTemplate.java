package org.usfirst.team3132.lib;

public abstract class FlywheelTemplate extends Subsystem{
	
	public FlywheelTemplate() {
		super("flywheel");
	}

	protected double flywheelTolerance = 50;
	protected int flywheelToleranceCount = 5;
	protected int flywheelToleranceCounter = 0;
	
	
	public abstract double getCurrentVelocity();
	
	public abstract double getTargetVelocity();
	
	public abstract void setTargetVelocity(double target);
	
	public boolean isFlywheelAtSpeed(){
		if(Math.abs(getTargetVelocity() - getCurrentVelocity()) < flywheelTolerance){
			flywheelToleranceCounter++;
		} else
			flywheelToleranceCounter = 0;
		
		return flywheelToleranceCounter >= flywheelToleranceCount;
	}
	
	public boolean isInPosition(){
		return isFlywheelAtSpeed();
	}

}
