package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.util.SystemUtil;

public class ShootLeftCornerShot extends Action{

	@Override
	public void run() {
		System.out.println("ShootLeftCornerShot: starting");
		
		if(!intake.safeForTurretToMove()){
			System.out.println("ShootLeftCornerShot: moving intake out of the way");
			
			intake.setSafePosition();
		}
		
		while(!intake.safeForTurretToMove()){
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("ShootLeftCornerShot: setting shooter");
		shooter.setTurretHoodFlywheelKick(Constants.TURRET_LCORNER_SHOT_ANGLE, 
				Constants.HOOD_LCORNER_SHOT_ANGLE,
				Constants.FLYWHEEL_LCORNER_SHOT_SPEED,
				false);
		
		SystemUtil.delay(50);
		
		boolean wasShooter = false;
		boolean wasDriver = false;
		while((!shooter.isInPosition() || !global.shotReadyToFire) && !global.cancelAutomation){
			if(!shooter.isInPosition() && !wasShooter){
				System.out.println("ShootLeftCornerShot: waiting for shooter");
				wasShooter = true;
			} else if(!global.shotReadyToFire && !wasDriver){
				System.out.println("ShootLeftCornerShot: waiting for driver");
				wasShooter = false;
				wasDriver = true;
			} else {
				wasDriver = false;
			}
			
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("ShootLeftCornerShot: fire shot");
		new FireShotAndStore().run();
		
		global.automated = false;
		System.out.println("ShootLeftCornerShot: finished");
		
	}

}
