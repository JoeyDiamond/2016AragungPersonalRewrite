package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Flywheel;
import org.usfirst.team3132.lib.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class FlywheelTest implements Test{

	Flywheel flywheel = Flywheel.getInstance();
	Joystick stick = new Joystick(0);
	
	Timer t = new Timer();
	
	double wheelSet = 0.0;
	int updateIndex = 0;
	
	@Override
	public void testInit() {
		flywheel.enable();
		t.reset();
		t.start();
	}

	@Override
	public void testPeriodic() {
		if(stick.getRawButton(4)){
    		wheelSet = 165;
    	} else if(stick.getRawButton(3)){
    		wheelSet = 140;
    	} else if(stick.getRawButton(2)){
    		wheelSet = 70;
    	} else if(stick.getRawButton(1)){
    		wheelSet = 0.0;
    	} else if(stick.getRawButton(5)){
    		wheelSet = -0.5;
    	}
    	
    	flywheel.setTargetVelocity(wheelSet);
    	
    	if(updateIndex >= 5){
	    	updateIndex = 1;
    		System.out.print(t.get());
	    	System.out.print("	" + wheelSet + "	" + flywheel.getCurrentVelocity());
	    	System.out.println("	" + flywheel.getBusVoltage() + "	" + flywheel.getOutputVoltage() + "	" + flywheel.getOutputCurrent());
    	} else {
    		updateIndex++;
    	}
    	
		
	}

}
