package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.util.SystemUtil;

public class ShootManualShot extends Action{

	@Override
	public void run() {
		System.out.println("ShootManualShot: starting");
		global.shotReadyToFire = false;
		global.manualShotActive = true;
		
		
		while(!intake.safeForTurretToMove()){
			System.out.println("ShootManualShot: moving intake out of the way");
			intake.setSafePosition();
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("ShootManualShot: setting shooter");
		while((!shooter.isInPosition() || !global.shotReadyToFire) && !global.cancelAutomation){
			shooter.setTurretHoodFlywheelKick(global.manualShotTurretAngle,
					Constants.HOOD_MANUAL_SHOT_ANGLE,
					Constants.FLYWHEEL_BATTER_SHOT_SPEED,
					false);
			
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("ShootManualShot: fire shot");
		new FireShotAndStore().run();
		
		global.manualShotActive = false;
		global.automated = false;
		
		System.out.println("ShootManualShot: finished");
		
	}

}
