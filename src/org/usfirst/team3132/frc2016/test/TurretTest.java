package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.behaviors.CalibrateTurret;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Turret;
import org.usfirst.team3132.lib.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class TurretTest implements Test {

	Turret turret = Turret.getInstance();
	Joystick stick = new Joystick(0);
	Timer t = new Timer();
	
	Thread calibrate;
	double positionSet = 0;
	
	@Override
	public void testInit() {
		t.reset();
		t.start();
		turret.enable();
		calibrate = new Thread(new CalibrateTurret());
		calibrate.start();
	}

	@Override
	public void testPeriodic() {
		System.out.println("t:	" + t.get() + 
				"	theSet:	" + positionSet + 
				"	set:	" + turret.getTargetPosition() + 
				"	curr:	" + turret.getCurrentPosition() + 
				"	setRaw:	" + turret.getTargetRawPosition() +
				"	curRaw:	" + turret.getCurrentRawPosition());// +
				//"	inPos:	" + turret.isInPosition() + 
				//"	mode:	" + turret.getMode());
		
		if(calibrate.isAlive()){
			System.out.println("still calibrating");
			return;
		}
		
		if(stick.getRawButton(4)){
    		positionSet = 0;
    		
    	} else if(stick.getRawButton(3)){
    		positionSet = -45;
    		
    	} else if(stick.getRawButton(2)){
    		positionSet = 45;
    		
    	} else if(stick.getRawButton(1)){
    		positionSet = -90;
    	}
		
		turret.setTargetPosition(positionSet);
		
	}

}
