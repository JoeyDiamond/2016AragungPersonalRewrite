package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Kicker;
import org.usfirst.team3132.lib.Test;

import edu.wpi.first.wpilibj.Timer;

public class KickerTest implements Test{

	Kicker kicker = Kicker.getInstance();
	Timer t = new Timer();
	
	@Override
	public void testInit() {
		t.reset();
		t.start();
		kicker.enable();
		
		kicker.fire();
	}

	@Override
	public void testPeriodic() {
		if(t.get() < 3){
			kicker.fire();
			System.out.println("t: " + t.get() + "  kicker firing");
		} else if(t.get() < 6) {
			kicker.reset();
			System.out.println("t: " + t.get() + "  kicker reseting");
		} else {
			t.reset();
		}
	}

	
}
