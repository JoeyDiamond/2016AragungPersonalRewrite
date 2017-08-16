package org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.Subsystem;
import org.usfirst.team3132.lib.util.MathUtil;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


public class Hood extends Subsystem{
	// setup for singleton
	private static Hood ourInstance = new Hood();

	public static Hood getInstance() {
		return ourInstance;
	}

	CANTalon hood = new CANTalon(Constants.HOOD_MOTOR_CAN_ID);
	
	double HOOD_MIN_RAW_POS = 0;
	public static final double HOOD_ENCODER_SCALE = 270.0/1024/2;
	
	double previousHood = 0.0;
	double hoodTolerance = 0.5;
	int hoodToleranceCounter = 0;
	int hoodToleranceCount = 5;
	
	boolean calibrated = false;
	double kP = 25;
	double kI = 0.015;
	double kD = 300;
	
	public Hood() {
		super("hood");
		hood.clearStickyFaults();
		
		hood.setFeedbackDevice(FeedbackDevice.AnalogPot);
		hood.changeControlMode(TalonControlMode.Position);
		hood.reverseOutput(false);
		hood.setInverted(false);
		hood.setIZone(50);
		hood.setPID(kP,kI,kD);//18,0.002,300);//13,0.0025,100);
		hood.setF(0);
		hood.configNominalOutputVoltage(0, 0);
		hood.configPeakOutputVoltage(12, -3); //(8,-4);
		hood.enableLimitSwitch(false, false);
		hood.enableForwardSoftLimit(true);
		hood.enableReverseSoftLimit(true);
		hood.setForwardSoftLimit(1000);
		hood.setReverseSoftLimit(30); //
		
		resetZeroPosition();
	}
	
	// control
	public synchronized void enable(){
		enabled = true;
		hood.changeControlMode(TalonControlMode.Position);
		setTargetPosition(getCurrentPosition());
	}
	
	public synchronized void disable(){
		enabled = false;
		hood.changeControlMode(TalonControlMode.PercentVbus);
		hood.set(0.0);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// Interfacing
	//////////////////////////////////////////////////////////////////////////////////
	public synchronized void setTargetPosition(double positionDegrees){
		// set limits
		positionDegrees = MathUtil.limitValue(positionDegrees, 95, 0);
		
		// use zero I if going down to storage position
		if(positionDegrees == 0){
			hood.setI(0.0);
		} else {
			hood.setI(kI);
		}
		
		// only update motor if enabled
		if(enabled && calibrated){
			
			// if going to a position that is far away from current value, clear PID i
			if(Math.abs(previousHood - positionDegrees) > 3){
				hood.clearIAccum();
				hood.ClearIaccum();
			}
			
			hood.setSetpoint(degreesToRaw(positionDegrees));
		}
		
		previousHood = positionDegrees;
	}
	
	public synchronized double getTargetPosition() {
		return rawToDegrees(hood.getSetpoint());
    }
	
	public synchronized double getCurrentPosition() {
    	return rawToDegrees(hood.getPosition());
    }
	
	public synchronized boolean isInPosition(){
		if(getTargetPosition() == Constants.HOOD_INTAKE_ANGLE){
			return getCurrentPosition() > Constants.HOOD_INTAKE_ANGLE - 20;
		}
		
		if(Math.abs(getTargetPosition() - getCurrentPosition()) < hoodTolerance){
			hoodToleranceCounter++;
		} else
			hoodToleranceCounter = 0;
		
		return hoodToleranceCounter >= hoodToleranceCount;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// testing & monitoring information
	//////////////////////////////////////////////////////////////////////////////////
	public synchronized double getOutputVoltage(){
		return hood.getBusVoltage();
	}
	
	public synchronized double getOutputCurrent(){
		return hood.getOutputCurrent();
	}
	
	public synchronized double getBusVoltage(){
		return hood.getBusVoltage();
	}
	
	public synchronized double getRawPosition(){
		return hood.getPosition();
	}
	
	public synchronized double getRawSetPoint(){
		return hood.getSetpoint();
	}
	
	public synchronized void resetZeroPosition(){
		HOOD_MIN_RAW_POS = hood.getPosition();
		calibrated = true;
	}
	
	public synchronized boolean isHoodSwitchPressed(){
		return hood.isRevLimitSwitchClosed();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Calibration
	//////////////////////////////////////////////////////////////////////////////////
	public synchronized void putInCalibrateMode(){
		calibrated = false;
		hood.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public synchronized void setRawPower(double power){
		putInCalibrateMode();
		power = MathUtil.limitValue(power);
		hood.set(power);
	}
	
	public synchronized void putInGameMode(){
		hood.changeControlMode(TalonControlMode.Position);
	}
	
	public synchronized boolean isInGameMode(){
		return hood.getControlMode() == TalonControlMode.Position;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Conversion Utilities
	//////////////////////////////////////////////////////////////////////////////////
	private double rawToDegrees(double volts) {
    	return (volts - HOOD_MIN_RAW_POS) * HOOD_ENCODER_SCALE;
    }
    
    private double degreesToRaw(double degrees) {
    	return (degrees / HOOD_ENCODER_SCALE) + HOOD_MIN_RAW_POS;
    }
    

}
