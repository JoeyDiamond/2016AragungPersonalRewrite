package org.usfirst.team3132.lib;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechGamepadF310 {
	// joystick squaring function parameters
	protected double deadzone = 0.005;
	protected double deadzoneSqd = (1 + deadzone) * (1 + deadzone); 
	protected double axisAsButtonThreshhold = 0.5;
	
	private Joystick joystick;
	
	public LogitechGamepadF310(int portNum){
		this.joystick = new Joystick(portNum);
	}
	
	// returns a squared function with deadzone of a value
	// pre: input is from -1 to 1
	// post: output is from -1 to 1
	public double calcAxisParabola(double value) {
		double out = (value * value * deadzoneSqd) - (deadzoneSqd - 1);
		if(out > 0)
			return out * Math.signum(value);
		else
			return 0.0;
	}
	
	// returns true if an axis (such as a trigger) is moved
	// sufficiently off of its natural resting place
	public boolean calcAxisAsButton(double value){
		if(Math.abs(value) >= axisAsButtonThreshhold)
			return true;
		else
			return false;
	}
	
	//////////////////////////////////////////////////
	// modified getters
	//////////////////////////////////////////////////

	public double getLeftXScaled() {
		return calcAxisParabola(getLeftX());
	}
	
	public double getLeftYScaled() {
        return calcAxisParabola(getLeftY());
    }
    
    public double getTriggerLeftScaled() {
    	return calcAxisParabola(getTriggerLeft());
    }
    
    public double getTriggerRightScaled(){
    	return calcAxisParabola(getTriggerRight());
    }
    
    public double getRightXScaled() {
        return calcAxisParabola(getRightX());
    }
    
    public double getRightYScaled() {
        return calcAxisParabola(getRightY());
    }
    
    public boolean getTriggerRightBtn(){
    	return calcAxisAsButton(getTriggerRight());
    }
    
    public boolean getTriggerLeftBtn(){
    	return calcAxisAsButton(getTriggerLeft());
    }
    
    
	//////////////////////////////////////////////////
	// general getters
	//////////////////////////////////////////////////
	
	public double getLeftX() {
        return this.joystick.getRawAxis(0);
    }
    
    public double getLeftY() {
        return -this.joystick.getRawAxis(1);
    }
    
    public double getTriggerLeft() {
    	return this.joystick.getRawAxis(2);
    }
    
    public double getTriggerRight(){
    	return this.joystick.getRawAxis(3);
    }
    
    public double getRightX() {
        return this.joystick.getRawAxis(4);
    }
    
    public double getRightY() {
        return -this.joystick.getRawAxis(5);
    }
    
    public boolean getButton(int btnNum) {
        return this.joystick.getRawButton(btnNum);
    }
    
    public boolean getGreenButton() {
        return this.joystick.getRawButton(1);
    }
    
    public boolean getBlueButton() {
        return this.joystick.getRawButton(3);
    }
    
    public boolean getRedButton() {
        return this.joystick.getRawButton(2);
    }
    
    public boolean getYellowButton() {
        return this.joystick.getRawButton(4);
    }
    
    public boolean getBackButton() {
        return this.joystick.getRawButton(7);
    }
    
    public boolean getStartButton() {
        return this.joystick.getRawButton(8);
    }
    
    public boolean getLeftBumper() {
        return this.joystick.getRawButton(5);
    }
    
    public boolean getRightBumper() {
        return this.joystick.getRawButton(6);
    }
    
    public boolean getLeftStickClick() {
        return this.joystick.getRawButton(9);
    }
    
    public boolean getRightStickClick() {
        return this.joystick.getRawButton(10);
    }
    
    public int getPOVVal(){
    	return this.joystick.getPOV(0);
    }
    
    public boolean getPOVDown(){
    	return (this.joystick.getPOV(0) == 180);
    }
    
    public boolean getPOVRight(){
    	return (this.joystick.getPOV(0) == 90);
    }
    
    public boolean getPOVUp(){
    	return (this.joystick.getPOV(0) == 0);
    }
    
    public boolean getPOVLeft(){
    	return (this.joystick.getPOV(0) == 270);
    }
}
