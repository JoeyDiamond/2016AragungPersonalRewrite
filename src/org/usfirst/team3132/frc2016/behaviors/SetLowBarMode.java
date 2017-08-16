package org.usfirst.team3132.frc2016.behaviors;

import org.usfirst.team3132.frc2016.Constants;

public class SetLowBarMode extends Action{

	@Override
	public void run() {
		shooter.setStoragePosition();
		intake.setTargetAngle(Constants.INTAKE_LOW_BAR_POSITION);
		
		
	}

}
