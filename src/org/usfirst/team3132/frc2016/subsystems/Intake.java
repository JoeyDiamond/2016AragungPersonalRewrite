package org.usfirst.team3132.frc2016.subsystems;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.lib.Subsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;

public class Intake extends Subsystem{

	// setup for singleton
	private static Intake ourInstance = new Intake();

	public static Intake getInstance() {
		return ourInstance;
	}

	CANTalon leftArm = new CANTalon(Constants.INTAKE_LEFT_LIFT_MOTOR_CAN_ID);
	CANTalon rightArm = new CANTalon(Constants.INTAKE_RIGHT_LIFT_MOTOR_CAN_ID);
	CANTalon roller = new CANTalon(Constants.INTAKE_MOTOR_CAN_ID);
	DigitalInput ballSwitch = new DigitalInput(Constants.INTAKE_BALL_IN_SENSOR_PORT);

	double kP = 0.01;
	double kI = 0.0;
	double kD = 0.0;
	
	boolean isCalibrated = false;
	double intakeTolerance = 2;
	int intakeToleranceCounter = 0;
	int intakeToleranceCount = 5;

	double EncoderTicksToDegrees = -2558.7;
	boolean calibrated = true;
	
	public Intake() {
		super("intake");
		
		//leftArm.clearStickyFaults();
		//rightArm.clearStickyFaults();
		//roller.clearStickyFaults();
		
		leftArm.reverseOutput(true);
		leftArm.setInverted(true);
		leftArm.reverseSensor(true);
		leftArm.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		leftArm.ConfigFwdLimitSwitchNormallyOpen(true);
		leftArm.enableLimitSwitch(false, true);  // Only the forward limit switch is enabled.
		leftArm.changeControlMode(TalonControlMode.Position);
		leftArm.setPID(kP, kI, kD);

		rightArm.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightArm.changeControlMode(TalonControlMode.Position);
		rightArm.ConfigFwdLimitSwitchNormallyOpen(true);
		rightArm.enableLimitSwitch(true, false);  // Only the forward limit switch is enabled.
		rightArm.setPID(kP, kI, kD);

	}
	
	public synchronized void enable(){
		enabled = true;
		leftArm.changeControlMode(TalonControlMode.Position);
		rightArm.changeControlMode(TalonControlMode.Position);
		
		leftArm.setSetpoint(leftArm.getPosition());
		rightArm.setSetpoint(rightArm.getPosition());
	}
	
	public synchronized void disable(){
		enabled = false;
		leftArm.changeControlMode(TalonControlMode.PercentVbus);
		rightArm.changeControlMode(TalonControlMode.PercentVbus);
		
		leftArm.set(0.0);
		rightArm.set(0.0);
		roller.set(0.0);
	}

	//////////////////////////////////////////////////////////////////////////////////
	// General Position Control
	//////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean isInPosition() {
		if(Math.abs(getTargetPosition() - getCurrentPosition()) < intakeTolerance){
			intakeToleranceCounter++;
		} else
			intakeToleranceCounter = 0;
		
		return intakeToleranceCounter >= intakeToleranceCount;
	}
	
	public void setIntakePosition() {
		setTargetAngle(Constants.INTAKE_INTAKE_POSITION);
	}
	
	public void setUpPosition() {
		setTargetAngle(Constants.INTAKE_STORE_POSITION);
	}
	
	public void setSafePosition(){
		setTargetAngle(Constants.INTAKE_SAFE_POSITION);
	}
	
	public synchronized boolean hasBoulder(){
		return !ballSwitch.get();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Arm Control
	//////////////////////////////////////////////////////////////////////////////////

	public synchronized void setTargetAngle(double degrees){
		if(!enabled){
			return;
		}
		leftArm.setSetpoint(convertDegreesToRaw(degrees));
		rightArm.setSetpoint(convertDegreesToRaw(degrees));
	}
	
	public synchronized boolean safeForTurretToMove(){
		boolean notInTheWay = getCurrentPosition() <= Constants.INTAKE_SAFE_POSITION; 
		boolean notGoingInTheWay = getTargetPosition() <= Constants.INTAKE_SAFE_POSITION;
		return  notInTheWay && notGoingInTheWay && calibrated;
	}
	
	public double getCurrentPosition(){
		return (getLeftPosition() + getRightPosition()) / 2;
	}
	
	public double getTargetPosition(){
		return getLeftTarget();
	}
	
	public synchronized double getLeftPosition(){
		return convertRawToDegrees(leftArm.getPosition());
	}
	
	public synchronized double getRightPosition(){
		return convertRawToDegrees(rightArm.getPosition());
	}
	
	public synchronized double getLeftTarget(){
		return convertRawToDegrees(leftArm.getSetpoint());
	}
	
	public synchronized double getRightTarget(){
		return convertRawToDegrees(rightArm.getSetpoint());
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// Roller Control
	//////////////////////////////////////////////////////////////////////////////////

	
	public synchronized void setRollersIntakeFast(){
		if(enabled)
			roller.set(1.0);
		else
			roller.set(0.0);
	}
	
	public synchronized void setRollersStopped(){
		roller.set(0.0);
	}
	
	public synchronized void setRollersEject(){
		if(enabled)
			roller.set(-1.0);
		else
			roller.set(0.0);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Calibration Control
	//////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void calibrateZeroPosition(){
		rightArm.setPosition(0.0);
		leftArm.setPosition(0.0);
		calibrated = true;
	}
	
	public synchronized void uncalibrate(){
		calibrated = false;
	}
	
	public synchronized void putInCalibrateMode(){
		uncalibrate();
		leftArm.changeControlMode(TalonControlMode.PercentVbus);
		rightArm.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public synchronized void putInGameMode(){
		leftArm.changeControlMode(TalonControlMode.Position);
		rightArm.changeControlMode(TalonControlMode.Position);
	}
	
	public synchronized void setLeftArmPower(double power){
		leftArm.set(power);
	}
	
	public synchronized void setRightArmPower(double power){
		rightArm.set(power);
	}
	
	public synchronized boolean getLeftSwitch(){
		return leftArm.isFwdLimitSwitchClosed();
	}
	
	public synchronized boolean getRightSwitch(){
		return rightArm.isRevLimitSwitchClosed();
	}
	
	public synchronized double getLeftRawPosition(){
		return leftArm.getPosition();
	}
	
	public synchronized double getRightRawPosition(){
		return rightArm.getPosition();
	}
	
	public synchronized double getLeftRawTarget(){
		return leftArm.getSetpoint();
	}
	
	public synchronized double getRightRawTarget(){
		return rightArm.getSetpoint();
	}
	
	protected double convertRawToDegrees(double raw){
		return Constants.INTAKE_DEGREES_FROM_SWITCH_TO_ZERO + (raw / EncoderTicksToDegrees);
	}
	
	protected double convertDegreesToRaw(double degrees){
		return (degrees - Constants.INTAKE_DEGREES_FROM_SWITCH_TO_ZERO) * EncoderTicksToDegrees;
	}
	
}
