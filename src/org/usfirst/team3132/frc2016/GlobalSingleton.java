package org.usfirst.team3132.frc2016;

public class GlobalSingleton {
	
	// autonomous Chooser
	
	
	
	
	// drivebase
	public boolean autoDrive = false;
	public boolean drivebaseAllowedToDrive = true;
	public boolean drivebaseVRamping = false;
	
	// automation
	public boolean cancelAutomation = false;
	public boolean automated = false;
	public boolean calibrating = false;
	
	// shooting
	public boolean shotReadyToFire = false;
	public double manualShotTurretAngle = 0.0;
	public boolean manualShotActive = false;
	
	// setup for singleton
	private static GlobalSingleton ourInstance = new GlobalSingleton();
	
	public static GlobalSingleton getInstance() {
		return ourInstance;
	}

	private GlobalSingleton() {
		
	}
	
	
}
