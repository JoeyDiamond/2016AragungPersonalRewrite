package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.lib.util.SystemUtil;

public class IntakeBoulder extends Action{

	@Override
	public void run() {
		System.out.println("IntakeBoulder: starting");
		
		if(!intake.hasBoulder()){
			
			shooter.setIntakePosition();
			intake.setIntakePosition();
			
			while(!shooter.isInPosition() && !intake.hasBoulder()){
				System.out.println("IntakeBoulder: waiting for shooter");
				SystemUtil.slowLoopDelay();
			}
			
			System.out.println("IntakeBoulder: turn on rollers");
			intake.setRollersIntakeFast();
			
			while(!global.cancelAutomation && !intake.hasBoulder()){
				System.out.println("IntakeBoulder: waiting for boulder");
				
				SystemUtil.slowLoopDelay();
			}
			
			SystemUtil.delay(400);
		}
		System.out.println("IntakeBoulder: setting back to storage");
		shooter.setBoulderLockOn();
		
		intake.setSafePosition();
		intake.setRollersStopped();
		
		shooter.setStoragePosition();
		
		global.automated = false;
		System.out.println("IntakeBoulder: finished");
		
	}

}
