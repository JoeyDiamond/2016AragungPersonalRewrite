package org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Kicker extends Subsystem{
	// setup for singleton
	private static Kicker ourInstance = new Kicker();
	Timer t = new Timer();

	public static Kicker getInstance() {
		return ourInstance;
	}
	
	Solenoid kicker = new Solenoid(Constants.PCM_CAN_ID,Constants.KICKER_PCM_ID);

	public Kicker() {
		super("kicker");
		
		//kicker.clearAllPCMStickyFaults();
		t.start();
	}
	
	public synchronized void fire(){
		if(enabled){
			kicker.set(Constants.KICKER_EXTENDED);
			t.reset();
		}
	}
	
	public synchronized void reset(){
		if(enabled){
			kicker.set(Constants.KICKER_RETRACTED);
			t.reset();
		}
	}
	
	public boolean isInPosition(){
		return true;//t.get() > 0.05;
	}

}
