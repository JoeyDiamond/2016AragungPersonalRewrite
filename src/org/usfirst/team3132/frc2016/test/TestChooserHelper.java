package org.usfirst.team3132.frc2016.test;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.ChooserHelper;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestChooserHelper implements ChooserHelper{
	// chooser
	SendableChooser<String> testChooser = new SendableChooser<String>();
    
	@Override
	public void create(){
		testChooser.addDefault(Constants.testDrivebaseRaw, Constants.testDrivebaseRaw);
		testChooser.addObject(Constants.testDrivebaseSpeed, Constants.testDrivebaseSpeed);
		testChooser.addObject(Constants.testFlywheel, Constants.testFlywheel);
		testChooser.addObject(Constants.testHood, Constants.testHood);
		testChooser.addObject(Constants.testKicker,Constants.testKicker);
		testChooser.addObject(Constants.testTurret,Constants.testTurret);
		testChooser.addObject(Constants.testIntake,Constants.testIntake);
		testChooser.addObject(Constants.testShooter, Constants.testShooter);
		SmartDashboard.putData("Test Selection", testChooser);
	}

	@Override
	public String getSelection() {
		return (String) testChooser.getSelected();
	}
}
