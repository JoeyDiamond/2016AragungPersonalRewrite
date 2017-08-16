package org.usfirst.team3132.frc2016.behaviors;


public class CalibrateIntake extends Action{

	@Override
	public void run() {
		intake.uncalibrate();
		System.out.println("calibrating turret");
		intake.putInCalibrateMode();
		
		// move off the limit switch if on it
		while(intake.getLeftSwitch() || intake.getRightSwitch()){
			if(intake.getLeftSwitch()){
				intake.setLeftArmPower(0.1);
			} else {
				intake.setLeftArmPower(0.0);
			}
			
			if(intake.getRightSwitch()){
				intake.setRightArmPower(0.1);
			} else {
				intake.setRightArmPower(0.0);
			}
			
		}

		System.out.println("finding lim switch");
		// move towards the limit switch until hit
		while(!intake.getLeftSwitch() && !intake.getRightSwitch()){
			if(intake.getLeftSwitch()){
				intake.setLeftArmPower(0.0);
			} else {
				intake.setLeftArmPower(-0.2);
			}
			
			if(intake.getRightSwitch()){
				intake.setRightArmPower(0.0);
			} else {
				intake.setRightArmPower(-0.2);
			}
			
		}
		// stop motor and set calibration
		intake.calibrateZeroPosition();
		
		intake.putInGameMode();
		System.out.println("calibrated");
		
	}

}
