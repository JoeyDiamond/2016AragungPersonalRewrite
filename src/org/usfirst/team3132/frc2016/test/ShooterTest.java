package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.behaviors.CalibrateIntakeAndTurret;
import org.usfirst.team3132.frc2016.subsystems.Intake;
import org.usfirst.team3132.frc2016.subsystems.Shooter;
import org.usfirst.team3132.lib.LogitechGamepadF310;
import org.usfirst.team3132.lib.Looper;
import org.usfirst.team3132.lib.Test;

public class ShooterTest implements Test{

	LogitechGamepadF310 stick = new LogitechGamepadF310(0);
	Shooter shooter = Shooter.getInstance();
	Intake intake = Intake.getInstance();
	
	Thread calibrate;
	Looper looper = new Looper("testLooper", shooter, 1.0/100.0);
	
	
	@Override
	public void testInit() {
		intake.enable();
		shooter.enable();
		
		calibrate = new Thread(new CalibrateIntakeAndTurret());
		calibrate.start();
		looper.start();
		
	}

	@Override
	public void testPeriodic() {
		
		if(calibrate.isAlive()){
			System.out.println("calibrating");
			return;
		}
		
		if(stick.getYellowButton()){
			intake.setUpPosition();
		} else if(stick.getGreenButton()){
			intake.setSafePosition();
		}
		
		if(stick.getPOVUp()){
			shooter.setTurretTargetAngle(0.0);
		} else if(stick.getPOVLeft()){
			shooter.setTurretTargetAngle(-90);
		} else if(stick.getPOVRight()){
			shooter.setTurretTargetAngle(90);
		} else if(stick.getPOVDown()){
			shooter.setTurretTargetAngle(-180);
		}
		
		if(stick.getRightBumper()){
			shooter.setTurretTargetAngle(shooter.getTurretTargetAngle() + 1);
		} else if(stick.getLeftBumper()){
			shooter.setTurretTargetAngle(shooter.getTurretTargetAngle() - 1);
		}
		
	}

}
