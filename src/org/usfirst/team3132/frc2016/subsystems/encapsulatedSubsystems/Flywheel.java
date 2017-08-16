package org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.FlywheelTemplate;
import org.usfirst.team3132.lib.util.MathUtil;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


public class Flywheel extends FlywheelTemplate{

	// setup for singleton
	private static Flywheel ourInstance = new Flywheel();

	public static Flywheel getInstance() {
		return ourInstance;
	}


	private CANTalon flywheel = new CANTalon(Constants.FLYWHEEL_MOTOR_CAN_ID);
	private TalonControlMode prevControlMode = TalonControlMode.PercentVbus;

	// variables
	double p = 75;
	double i = 0.0;
	double d = 0.0;
	double f = 8.25;
	double maxForwardVelocity = 220;
	double maxRevVelocity = -1.0;

	public Flywheel() {
		flywheel.clearStickyFaults();
		
		flywheel.changeControlMode(TalonControlMode.Speed);
		flywheel.reverseSensor(false);
		flywheel.reverseOutput(true);
		flywheel.enableBrakeMode(false);
		flywheel.setFeedbackDevice(FeedbackDevice.EncRising);
		flywheel.enableLimitSwitch(false, false);
		flywheel.configEncoderCodesPerRev(6);
		flywheel.setPID(p,i,d);
		flywheel.setF(f);
		flywheel.configMaxOutputVoltage(12.0);
		flywheel.configNominalOutputVoltage(0.0, 0.0);
		
		flywheelTolerance = 3;
	}

	// general use methods
	public synchronized void setTargetVelocity(double target){
		if(!enabled){
			setOpenLoop();
			flywheel.set(0.0);
			return;
		}
		
		// set reasonable limits
		MathUtil.limitValue(target, maxForwardVelocity, maxRevVelocity);
		
		// if want wheel to run backwards, uses percentVbus mode
		if(target <= 0.0){
			setOpenLoop();
			flywheel.set(-target);
		} else {
			setClosedLoop();
			flywheel.set(convertRPSToTicks(target));
		}
	}
	
	@Override
	public synchronized double getTargetVelocity() {
		return convertTicksToRPS(flywheel.getSetpoint());
	}
	
	public synchronized double getCurrentVelocity(){
		return convertTicksToRPS(flywheel.getSpeed());
	}
	
	public boolean isFlywheelAtSpeed(){
		if(flywheel.getControlMode() == TalonControlMode.PercentVbus){
			return true;
		}
		
		//System.out.println("wheelTar: " + getTargetVelocity() + " wheelCur: " + getCurrentVelocity());
		
		if(Math.abs(getTargetVelocity() - getCurrentVelocity()) < flywheelTolerance){
			flywheelToleranceCounter++;
		} else
			flywheelToleranceCounter = 0;
		
		return flywheelToleranceCounter >= flywheelToleranceCount;
	}
	
	
	
	// control methods
	public synchronized void disable(){
		enabled = false;
		prevControlMode = flywheel.getControlMode();
		setOpenLoop();
		flywheel.set(0.0);
	}
	
	public synchronized void enable(){
		flywheel.changeControlMode(prevControlMode);
		flywheel.set(0.0);
		enabled = true;
	}
	
	public synchronized void setOpenLoop(){
		flywheel.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public synchronized void setClosedLoop(){
		flywheel.changeControlMode(TalonControlMode.Speed);
	}
	
	
	// test methods
	public synchronized double getOutputCurrent(){
		return flywheel.getOutputCurrent();
	}
	
	public synchronized double getOutputVoltage(){
		return flywheel.getOutputVoltage();
	}
	
	public synchronized double getBusVoltage(){
		return flywheel.getBusVoltage();
	}
	
	
	// conversion tools
	public static double convertRPSToTicks(double rps) {
		return rps*60;
	}

	public static double convertTicksToRPS(double ticks) {
		return ticks/60;
	}
}
