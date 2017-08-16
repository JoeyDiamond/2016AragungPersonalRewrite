package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.subsystems.Drivebase;
import org.usfirst.team3132.lib.LogitechGamepadF310;
import org.usfirst.team3132.lib.Looper;
import org.usfirst.team3132.lib.Test;

public class DrivebaseSpeedTest implements Test{

	Drivebase drivebase = Drivebase.getInstance();
	Looper looper = new Looper("drivebase",drivebase,1.0/100.0);
			
	LogitechGamepadF310 gamepad = new LogitechGamepadF310(0);
	
	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		drivebase.enable();
		drivebase.setVelocityMode();
		//drivebase.setOpenLoop();
		looper.start();
	}

	@Override
	public void testPeriodic() {
		// TODO Auto-generated method stub
		drivebase.driveWheel(gamepad.getLeftYScaled(), gamepad.getRightXScaled(), 
				gamepad.getLeftStickClick() || gamepad.getRightStickClick());
		
		System.out.println("leftS: " + drivebase.getLeftTarget() + 
				" leftV: " + drivebase.getLeftSpeed() + 
				" rightS: " + drivebase.getRightTarget() + 
				" rightV: " + drivebase.getRightSpeed());
	}

}
