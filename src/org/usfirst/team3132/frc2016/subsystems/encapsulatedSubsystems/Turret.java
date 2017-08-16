package org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.frc2016.subsystems.Intake;
import org.usfirst.team3132.lib.Subsystem;
import org.usfirst.team3132.lib.util.MathUtil;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


public class Turret extends Subsystem{

	// setup for singleton
	private static Turret ourInstance = new Turret();

	public static Turret getInstance() {
		return ourInstance;
	}

	Intake intake = Intake.getInstance();

	CANTalon turret = new CANTalon(Constants.TURRET_MOTOR_CAN_ID);
	
	boolean isCalibrated = false;
	double turretTolerance = 0.5;
	int turretToleranceCounter = 0;
	int turretToleranceCount = 5;

	double kP = 3;
	double kI = 0.001;
	double kD = 0;
	int iZone = 1;
	
	public Turret() {
		super("turret");
		turret.reverseOutput(true);
		turret.setInverted(true);
		turret.reverseSensor(true);
		turret.ConfigFwdLimitSwitchNormallyOpen(true);
		turret.ConfigRevLimitSwitchNormallyOpen(true);
		turret.configMaxOutputVoltage(12);
		turret.enableLimitSwitch(true, true);
		turret.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		turret.changeControlMode(TalonControlMode.Position);
		turret.setPID(kP, kI, kD);
		turret.setIZone(iZone);
		turret.setF(0.0);
		turret.configEncoderCodesPerRev(Constants.TURRET_TICKS_PER_ROTATION);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// General Control
	//////////////////////////////////////////////////////////////////////////////////
	public synchronized void enable(){
		enabled = true;
		turret.changeControlMode(TalonControlMode.Position);
		turret.setSetpoint(turret.getPosition());
	}
	
	public synchronized void disable(){
		enabled = false;
		turret.changeControlMode(TalonControlMode.PercentVbus);
		turret.set(0.0);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Interfacing
	//////////////////////////////////////////////////////////////////////////////////
	public synchronized void setTargetPosition(double position){
		if(!intake.safeForTurretToMove() || !isCalibrated || !enabled){
			System.out.println("turrent not allowed to move!!!");
			return;
		}
		position = MathUtil.limitValue(position, Constants.TURRET_FWD_LIMIT_ANGLE, Constants.TURRET_REV_LIMIT_ANGLE);
		turret.setSetpoint(convertDegreesToRaw(position));
	}
	
	public synchronized double getCurrentPosition(){
		return convertRawToDegrees(turret.getPosition());
	}
	
	public synchronized double getCurrentRawPosition() {
		return turret.getPosition();
	}
	
	public synchronized double getTargetPosition(){
		return convertRawToDegrees(turret.getSetpoint());
	}
	
	public synchronized double getTargetRawPosition(){
		return turret.getSetpoint();
	}
	
	public synchronized boolean isInPosition(){
		if(Math.abs(getTargetPosition() - getCurrentPosition()) < turretTolerance){
			turretToleranceCounter++;
		} else
			turretToleranceCounter = 0;
		
		return turretToleranceCounter >= turretToleranceCount;
	}
	
	protected double convertDegreesToRaw(double degrees){
		return (Constants.TURRET_DEGREES_FROM_FWD_SWITCH_TO_ZERO - degrees) * -0.7;
	}
	
	protected double convertRawToDegrees(double raw){
		return (raw / 0.7) + Constants.TURRET_DEGREES_FROM_FWD_SWITCH_TO_ZERO;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// Calibration Control
	//////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void calibrateZeroPosition(){
		//zeroAnglePosition = turret.getPosition() - convertDegreesToRaw(Constants.TURRET_DEGREES_FROM_FWD_SWITCH_TO_ZERO); 
		turret.setPosition(0.0);
		isCalibrated = true;
	}
	
	public synchronized void calibrateZeroPosition(double position){
		//zeroAnglePosition = position;
		turret.setPosition(position);
		isCalibrated = true;
	}
	
	public synchronized void putInCalibrateMode(){
		unCalibrate();
		turret.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public synchronized void putInGameMode(){
		turret.changeControlMode(TalonControlMode.Position);
	}
	
	public synchronized void setMovePower(double speed){
		speed = MathUtil.limitValue(speed);
		putInCalibrateMode();
		
		if(!intake.safeForTurretToMove()){
			turret.set(0.0);
			return;
		}
		
		turret.set(speed);
	}
	
	public synchronized boolean isRevLimSwitchPressed(){
		return turret.isFwdLimitSwitchClosed();
	}
	
	public synchronized boolean isFwdLimSwitchPressed(){
		return turret.isRevLimitSwitchClosed();
	}
	
	public void unCalibrate(){
		isCalibrated = false;
	}
	
	public synchronized TalonControlMode getMode(){
		return turret.getControlMode();
	}
	
	public synchronized void setRawTargetPosition(double raw){
		if(!intake.safeForTurretToMove()){
			return;
		}
		turret.setSetpoint(raw);
	}
}
