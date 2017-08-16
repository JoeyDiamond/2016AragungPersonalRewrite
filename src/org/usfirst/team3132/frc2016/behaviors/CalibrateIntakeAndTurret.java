package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.lib.util.SystemUtil;

public class CalibrateIntakeAndTurret extends Action{

	Thread hoodCalibrate = new Thread(new CalibrateHood());
	
	@Override
	public void run() {
		global.calibrating = true;
		
		new CalibrateIntake().run();
		
		SystemUtil.delay(100);
		
		intake.setSafePosition();
		
		while(!intake.safeForTurretToMove()){
			SystemUtil.slowLoopDelay();
		}
		
		hoodCalibrate.start();
		
		new CalibrateTurret().run();
		
		while(hoodCalibrate.isAlive()){
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("CalibrateIntakeAndTurret: done");
		
		
		global.calibrating = false;
	}

}
