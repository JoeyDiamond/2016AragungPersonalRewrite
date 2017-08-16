package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.lib.util.SystemUtil;

public class FireShotAndStore extends Action{

	@Override
	public void run() {
		shooter.setBoulderLockOff();
		
		SystemUtil.delay(400);
		
		shooter.setKickerFire();
		
		SystemUtil.delay(500);
		
		shooter.setStoragePosition();
		
	}

}
