package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Turret;
import org.usfirst.team3132.lib.util.SystemUtil;

public class CalibrateTurret extends Action{
	
	Turret turret = Turret.getInstance();
	
	@Override
	public void run() {
		System.out.println("calibrating turret");
		turret.putInCalibrateMode();
		
		// move off the limit switch if on it
		if(turret.isFwdLimSwitchPressed()){
			System.out.println("started on lim switch, moving off");
			while(turret.isFwdLimSwitchPressed()){
				turret.setMovePower(-0.2);
			}
			
			SystemUtil.delay(100);
			
			//turret.setMovePower(0.0);
			System.out.println("off lim switch");
		}
		
		System.out.println("finding lim switch");
		// move towards the limit switch until hit
		while(!turret.isFwdLimSwitchPressed()){
			turret.setMovePower(0.15);
		}
		
		// stop motor and set calibration
		turret.setMovePower(0.0);
		turret.calibrateZeroPosition();
		
		turret.putInGameMode();
		turret.setTargetPosition(Constants.TURRET_STORE_POSITION);
		System.out.println("calibrated");
	}

}
