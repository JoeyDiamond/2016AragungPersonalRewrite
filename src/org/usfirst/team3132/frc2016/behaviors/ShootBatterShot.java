package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.util.SystemUtil;

public class ShootBatterShot extends Action{

	@Override
	public void run() {
		System.out.println("ShootBatterShot: starting");
		
		if(!intake.safeForTurretToMove()){
			System.out.println("ShootBatterShot: moving intake out of the way");
			
			intake.setSafePosition();
		}
		
		while(!intake.safeForTurretToMove()){
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("ShootBatterShot: setting shooter");
		shooter.setTurretHoodFlywheelKick(Constants.TURRET_BATTER_SHOT_ANGLE, 
				Constants.HOOD_BATTER_SHOT_ANGLE,
				Constants.FLYWHEEL_BATTER_SHOT_SPEED,
				false);
		
		SystemUtil.delay(50);
		
		boolean wasShooter = false;
		boolean wasDriver = false;
		while((!shooter.isInPosition() || !global.shotReadyToFire) && !global.cancelAutomation){
			if(!shooter.isInPosition() && !wasShooter){
				System.out.println("ShootBatterShot: waiting for shooter");
				wasShooter = true;
			} else if(!global.shotReadyToFire && !wasDriver){
				System.out.println("ShootBatterShot: waiting for driver");
				wasShooter = false;
				wasDriver = true;
			} else {
				wasDriver = false;
			}
			
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("ShootBatterShot: fire shot");
		new FireShotAndStore().run();
		
		global.automated = false;
		System.out.println("ShootBatterShot: finished");
		
	}

}
