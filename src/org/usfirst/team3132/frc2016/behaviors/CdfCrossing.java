package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.util.SystemUtil;

import edu.wpi.first.wpilibj.Timer;

public class CdfCrossing extends Action{

	Timer t = new Timer();
	
	@Override
	public void run() {
		System.out.println("CdfCrossing: starting");
		global.autoDrive = true;
		
		System.out.println("CdfCrossing: setting intake");
		intake.setTargetAngle(Constants.INTAKE_CDF_POSITION);
		SystemUtil.delay(100);
		
		t.reset();
		t.start();
		
		while(!intake.isInPosition()){
			System.out.println("CdfCrossing: backing");
			intake.setTargetAngle(Constants.INTAKE_CDF_POSITION);
			SystemUtil.slowLoopDelay();
			drivebase.setThrottle(-0.2, -0.2);
			
			if(t.get() > 1)
				break;
		}
		
		System.out.println("CdfCrossing: charging");
		drivebase.setThrottle(0.9, 0.9);
		intake.setSafePosition();
		SystemUtil.delay(750);
		
		global.autoDrive = false;
		global.automated = false;
		System.out.println("CdfCrossing: finished");
		
	}

}
