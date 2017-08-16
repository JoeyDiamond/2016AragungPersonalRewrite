package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.behaviors.CalibrateIntake;
import org.usfirst.team3132.frc2016.subsystems.Intake;
import org.usfirst.team3132.lib.LogitechGamepadF310;
import org.usfirst.team3132.lib.Test;

import edu.wpi.first.wpilibj.Timer;

public class IntakeTest implements Test {

	Intake intake = Intake.getInstance();
	LogitechGamepadF310 stick = new LogitechGamepadF310(0);
	Timer t = new Timer();
	
	Thread calibrate;
	double positionSet = 0;
	
	@Override
	public void testInit() {
		intake.enable();
		//intake.calibrateZeroPosition();
		t.reset();
		t.start();
		
		calibrate = new Thread(new CalibrateIntake());
		calibrate.start();
	}

	@Override
	public void testPeriodic() {
		System.out.println("t:	" + t.get() + 
				"	theSet:	" + positionSet + 
				"	Set:	" + intake.getTargetPosition() + 
				"	Pos:	" + intake.getCurrentPosition() + 
				"	LS:	" + intake.getLeftSwitch() + 
				"	RS:	" + intake.getRightSwitch());
				
		if(calibrate.isAlive()){
			System.out.println("calibrating");
			return;
		}
		
		// Test Rollers
		if(stick.getPOVUp()){
			intake.setRollersEject();
		} else if(stick.getPOVDown()){
			intake.setRollersIntakeFast();
		} else {
			intake.setRollersStopped();
		}
		
		
		if(stick.getYellowButton()){
    		positionSet = 90;
    		
    	} else if(stick.getRedButton()){
    		positionSet = 0;
    		
    	} else if(stick.getBlueButton()){
    		positionSet = 45;
    		
    	} else if(stick.getGreenButton()){
    		positionSet = -20;

    	}
		
		intake.setTargetAngle(positionSet);
		
	}

}
