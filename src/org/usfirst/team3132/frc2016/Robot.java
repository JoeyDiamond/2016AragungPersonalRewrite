
package org.usfirst.team3132.frc2016;

import org.usfirst.team3132.frc2016.Constants.AutoRoutines;
import org.usfirst.team3132.frc2016.autonomous.*;
import org.usfirst.team3132.frc2016.behaviors.*;
import org.usfirst.team3132.frc2016.subsystems.*;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.*;
import org.usfirst.team3132.frc2016.test.*;

import org.usfirst.team3132.lib.EmptyTest;
import org.usfirst.team3132.lib.LogitechGamepadF310;
import org.usfirst.team3132.lib.MultiLooper;
import org.usfirst.team3132.lib.Test;
import org.usfirst.team3132.lib.util.MathUtil;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    
	GlobalSingleton global = GlobalSingleton.getInstance();
	
	// subsystems
	Drivebase drivebase = Drivebase.getInstance();
	Shooter shooter = Shooter.getInstance();
	Intake intake = Intake.getInstance();
	MultiLooper subsystems = new MultiLooper("subsystems", 1.0/100.0);
	
	// autonomous
	AutonomousRunner autoRunner = new AutonomousRunner();
	AutoChooserHelper autoChooser = new AutoChooserHelper();
	AutoButtonChooser autoButtonChooser = new AutoButtonChooser();
	AutonomousRoutine autoSelectedSmartDashboard;
	AutoRoutines autoSelectedButton;
	
	
	// test
	TestChooserHelper testChooser = new TestChooserHelper();
	String testSelected;
	Test test;
	
	// joysticks
	LogitechGamepadF310 driver = new LogitechGamepadF310(0);
	LogitechGamepadF310 operator = new LogitechGamepadF310(1);
	
	// automation
	Thread automation = new Thread();
	boolean driverIntakeFirstPress = true;
	
    public void robotInit() {
        testChooser.create();
        autoChooser.create();
        autoButtonChooser.create();
        
        subsystems.addLoopable(drivebase);
        subsystems.addLoopable(shooter);
    }
    
    public void robotPeriodic() {
    	
    }
    
	public void autonomousInit() {
		enableAllSubsystems();
		subsystems.start();
		autoRunner.start();
		
    	
		System.out.println("Auto selected: " + autoSelectedSmartDashboard);
    }

    public void autonomousPeriodic() {
    	// we dont need to do anything here :)
    }
    
    public void teleopInit(){
    	
    	enableAllSubsystems();
    	subsystems.start();
    	automation = new Thread(new CalibrateIntakeAndTurret());
    	global.calibrating = true;
    	automation.start();
    	global.manualShotActive = false;
    	global.manualShotTurretAngle = 0;
    }

    public void teleopPeriodic() {
    	// control drivebase
        if(!global.autoDrive){
        	switch(driver.getPOVVal()){
        		case 0:
        			drivebase.setThrottle(0.2, 0.2);
        			break;
        		case 90:
        			drivebase.setThrottle(0.2, -0.2);
        			break;
        		case 180:
        			drivebase.setThrottle(-0.2, -0.2);
        			break;
        		case 270:
        			drivebase.setThrottle(-0.2, 0.2);
        			break;
    			default:
    				drivebase.driveWheel(driver.getLeftYScaled(), driver.getRightXScaled(),
    		        		driver.getLeftStickClick() || driver.getRightStickClick());
        	}	
        }
        
        // control intake for CDF crossing
        if(driver.getTriggerLeftBtn() && driverIntakeFirstPress){
        	driverIntakeFirstPress = false;
        	intake.setTargetAngle(Constants.INTAKE_CDF_POSITION);
        } else if(!driver.getTriggerLeftBtn() && !driverIntakeFirstPress){
        	driverIntakeFirstPress = true;
        	intake.setTargetAngle(Constants.INTAKE_SAFE_POSITION);
        }
        
        // driver OKs shot
        if(driver.getTriggerRightBtn()){
        	System.out.println("Driver OKs shot");
        	global.shotReadyToFire = true;
        } else {
        	global.shotReadyToFire = false;
        }
        
        // manually adjust turret angle if in mode, otherwise ignore input
        if(global.manualShotActive){
        	if(operator.getPOVLeft()){
        		global.manualShotTurretAngle -= 1;
        	}
        	if(operator.getPOVRight()){
        		global.manualShotTurretAngle += 1;
        	}
        	
        	global.manualShotTurretAngle = MathUtil.limitValue(global.manualShotTurretAngle, 90, -180);
        }
        
        // automation
        if(!global.automated && !global.calibrating){
        	
        	if(operator.getTriggerLeftBtn()){
        		System.out.println("Robot: trying to start intake routine");
        		global.automated = true;
        		automation = new Thread(new IntakeBoulder());
        		automation.start();
        	}
        	
        	if(operator.getYellowButton()){
        		System.out.println("Robot: trying to start batter shot");
        		global.automated = true;
        		automation = new Thread(new ShootBatterShot());
        		automation.start();
        	}
        	
        	if(operator.getRedButton()){
        		System.out.println("Robot: trying to start manual shot");
        		global.automated = true;
        		automation = new Thread(new ShootManualShot());
        		automation.start();
        	}
        	
        	if(operator.getBlueButton()){
        		System.out.println("Robot: trying to start corner shot");
        		global.automated = true;
        		automation = new Thread(new ShootLeftCornerShot());
        		automation.start();
        	}
        	
        	if(operator.getGreenButton()){
        		System.out.println("Robot: trying to start Outerworks shot");
        		global.automated = true;
        		automation = new Thread(new ShootOuterworksShot());
        		automation.start();
        	}
        	
        	
        } else {
        	if(operator.getStartButton() && operator.getBackButton()){
        		global.cancelAutomation = true;
        	} else {
        		global.cancelAutomation = false;
        	}
        }
        
    }
    
    @SuppressWarnings("deprecation")
	public void disabledInit(){
    	subsystems.stop();
    	global.autoDrive = false;
    	global.automated = false;
    	global.shotReadyToFire = false;
    	
    	if(automation != null && automation.isAlive()){
    		automation.stop();
    		automation = null;
    	}
    	
    	disableAllSubsystems();
    }
    
    
    boolean autoIncrementerButtonEnabled = true;
    
    public void disabledPeriodic() {
    	/**
    	 * Testing two different types of automous selection, one using
    	 * the smart dashboard, one using the buttons on a gamepad
    	 */
    	
    	// smart dashboard testing
    	autoSelectedSmartDashboard = autoChooser.getSelection();
    	SmartDashboard.putString("smart dashboard auto:", autoSelectedSmartDashboard.toString());
    	autoRunner.setAutoRoutine(autoSelectedSmartDashboard);
    	
    	
    	// gamepad button testing
    	if(driver.getYellowButton() && autoIncrementerButtonEnabled) {
    		autoIncrementerButtonEnabled = false;
    		autoButtonChooser.incrementRoutine();
    	} else if(driver.getGreenButton() && autoIncrementerButtonEnabled) {
    		autoIncrementerButtonEnabled = false;
    		autoButtonChooser.decrementRoutine();
    	} else if(!driver.getYellowButton() && !driver.getGreenButton()) {
    		autoIncrementerButtonEnabled = true;
    		
    	}
    	
    	autoButtonChooser.updateSmartDashboard();
    
    	//autoSelectedSmartDashboard = autoChooser.getSelection();
    }
    
    
    public void testInit() {
    	testSelected = testChooser.getSelection();
    	disableAllSubsystems();
    	
    	switch(testSelected){
    		case Constants.testDrivebaseRaw:
    			test = drivebase;
    			break;
    		case Constants.testDrivebaseSpeed:
    			test = new DrivebaseSpeedTest();
    			break;
    		case Constants.testFlywheel:
    			test = new FlywheelTest();
    			break;
    		case Constants.testHood:
    			test = new HoodTest();
    			break;
    		case Constants.testIntake:
    			test = new IntakeTest();
    			break;
    		case Constants.testKicker:
    			test = new KickerTest();
    			break;
    		case Constants.testTurret:
    			test = new TurretTest();
    			break;
    		case Constants.testShooter:
    			test = new ShooterTest();
    			break;
			default:
				test = new EmptyTest();
    			break;
    	}
    	
    	test.testInit();
    }
    
    public void testPeriodic() {
    	if(test != null){
    		test.testPeriodic();
    	}
    }
    
    /**
     * Disables all subsystem in preparation for testing
     */
    public void disableAllSubsystems(){
    	drivebase.disable();
    	shooter.disable();
    	intake.disable();
    	
    	Flywheel.getInstance().disable();
    	Hood.getInstance().disable();
    	Kicker.getInstance().disable();
    	Turret.getInstance().disable();
    }
    
    public void enableAllSubsystems(){
    	drivebase.enable();
    	shooter.enable();
    	intake.enable();
    }
    
}
