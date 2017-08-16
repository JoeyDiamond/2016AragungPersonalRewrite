package org.usfirst.team3132.frc2016.autonomous;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.ChooserHelper;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooserHelper implements ChooserHelper {
	SendableChooser<AutonomousRoutine> autoChooser = new SendableChooser<AutonomousRoutine>();;

	
	@Override
	public void create() {
		autoChooser.addDefault(Constants.AutoRoutines.EmptyAuto.toString(),new EmptyAutonomous());
        
        SmartDashboard.putData("Auto choices faster chooser", autoChooser);
		
	}

	/**
	 * Returns the currently selected autonomous routine
	 */
	@Override
	public AutonomousRoutine getSelection() {
		return autoChooser.getSelected();
	}
	
}
