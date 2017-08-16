package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Hood;
import org.usfirst.team3132.lib.util.SystemUtil;

public class CalibrateHood extends Action{

	Hood hood = Hood.getInstance();
	
	@Override
	public void run() {
		System.out.println("calibrating hood");
		hood.putInCalibrateMode();
		
		boolean movedOff = false;
		if(hood.isHoodSwitchPressed())
			movedOff = true;
		
		while(hood.isHoodSwitchPressed()){
			System.out.println("hood moving off switch");
			hood.setRawPower(0.1);
			SystemUtil.slowLoopDelay();
		}
		
		if(movedOff)
			SystemUtil.delay(50);
		
		while(!hood.isHoodSwitchPressed()){
			System.out.println("hood finding switch");
			hood.setRawPower(-0.1);
			SystemUtil.slowLoopDelay();
		}
		
		System.out.println("hood found switch");
		hood.setRawPower(0.0);
		hood.resetZeroPosition();
		hood.putInGameMode();
		hood.setTargetPosition(Constants.HOOD_DOWN_ANGLE);
		
		System.out.println("hood calibrated");
		
	}

}
