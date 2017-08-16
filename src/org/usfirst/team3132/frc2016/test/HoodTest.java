package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Flywheel;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Hood;
import org.usfirst.team3132.lib.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class HoodTest implements Test{

	Hood hood = Hood.getInstance();
	Flywheel flywheel = Flywheel.getInstance();
	Joystick stick = new Joystick(0);
	
	Timer t = new Timer();
	int updateIndex = 0;
	double positionSet = 0;
	boolean runFlywheel = false;
	
	
	@Override
	public void testInit() {
		t.reset();
		t.start();
		hood.enable();
		
		hood.resetZeroPosition();
		
		runFlywheel = true;
	}

	@Override
	public void testPeriodic() {
		if(stick.getRawButton(9)){
			hood.resetZeroPosition();
		}
		
		if(stick.getPOV() == 180){
			hood.putInCalibrateMode();
			if(!hood.isHoodSwitchPressed()){
				hood.setRawPower(-0.1);
			} else {
				hood.resetZeroPosition();
			}
			
			return;
		} else {
			if(!hood.isInGameMode())
				hood.putInGameMode();
		}
		
		if(stick.getRawButton(4)){
    		positionSet = 90;
    		
    	} else if(stick.getRawButton(3)){
    		positionSet = 60;
    		
    	} else if(stick.getRawButton(2)){
    		positionSet = 30;
    		
    	} else if(stick.getRawButton(1)){
    		positionSet = 0;
    		runFlywheel = false;
    	}
		
		if(stick.getRawButton(9)){
			runFlywheel = true;
		} else if(stick.getRawButton(10)){
			runFlywheel = false;
		}
		
		hood.setTargetPosition(positionSet);
		
		if(updateIndex >= 1){// && positionSet != 0){
	    	updateIndex = 1;
    		System.out.print(t.get());
	    	System.out.print("	" + positionSet + "	" + hood.getTargetPosition() + "	" + hood.getCurrentPosition());
	    	System.out.print("	isOnTarget:	" + hood.isInPosition() + "	switch:	" + hood.isHoodSwitchPressed());
	    	System.out.println("	" + hood.getBusVoltage() + "	" + hood.getOutputVoltage() + "	" + hood.getOutputCurrent());
	    	//System.out.println("raw: " + hood.getRawSetPoint() + "	" +  hood.getRawPosition());
	    	
    	}
		updateIndex++;
		
		if(runFlywheel){
			flywheel.setTargetVelocity(-1.0);
		} else {
			flywheel.setTargetVelocity(0.0);
		}
    	
		
	}

}
