package org.usfirst.team3132.frc2016.autonomous;

import org.usfirst.team3132.frc2016.Constants.AutoRoutines;
import org.usfirst.team3132.lib.ChooserHelper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoButtonChooser implements ChooserHelper{

	AutoRoutines myRoutine = AutoRoutines.EmptyAuto;
	
	@Override
	public void create() {
		
		SmartDashboard.putString("button based auto chooser", myRoutine.toString());
	}

	@Override
	public AutoRoutines getSelection() {
		return myRoutine;
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putString("button based auto chooser", myRoutine.toString());
	}

	
	public void incrementRoutine() {
		myRoutine = myRoutine.next();
	}
	
	public void decrementRoutine() {
		myRoutine = myRoutine.previous();
	}
	
}
