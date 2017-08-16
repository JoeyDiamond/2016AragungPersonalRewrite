package org.usfirst.team3132.frc2016.subsystems;

import org.usfirst.team3132.frc2016.Constants;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Flywheel;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Hood;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Kicker;
import org.usfirst.team3132.frc2016.subsystems.encapsulatedSubsystems.Turret;
import org.usfirst.team3132.lib.Loopable;
import org.usfirst.team3132.lib.Subsystem;

import edu.wpi.first.wpilibj.Servo;


public class Shooter extends Subsystem implements Loopable{
	
	public Shooter() {
		super("shooter");
		
	}

	// setup for singleton
	private static Shooter ourInstance = new Shooter();

	public static Shooter getInstance() {
		return ourInstance;
	}
	
	private Flywheel flywheel = Flywheel.getInstance();
	private Hood hood = Hood.getInstance();
	private Turret turret = Turret.getInstance();
	private Kicker kicker = Kicker.getInstance();
	Servo servoBoulderLock = new Servo(Constants.BALL_HOLDER_SERVO_PWM);
	
	Intake intake = Intake.getInstance();
	
	protected double hoodTarget = 0;
	protected double wheelTarget = 0;
	protected double turretTarget = 90;
	protected double servoTarget = Constants.BALL_HOLDER_EXTENDED;
	protected boolean kickerTarget = false;
	
	protected boolean safeToFire = false;
	
	
	//////////////////////////////////////////////////////////////////////////////////
	// General Control
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public void update() {
		
		boolean fireKicker = kickerTarget;
		double localWheelTarget = wheelTarget;
		double localHoodTarget = hoodTarget;
		double localTurretTarget = turretTarget;
		
		flywheel.setTargetVelocity(localWheelTarget);
		servoBoulderLock.set(servoTarget);
		
		safeToFire = (getFlywheelCurrentVelocity() >= 50) &&
				(servoBoulderLock.get() == Constants.BALL_HOLDER_RETRACTED) &&
				(getHoodCurrentAngle() <= 70);
		
		if(fireKicker && safeToFire)
			kicker.fire();
		else
			kicker.reset();
		
		if(intake.safeForTurretToMove()){
			hood.setTargetPosition(localHoodTarget);
			turret.setTargetPosition(localTurretTarget);
		} 
		
		if(hood.isHoodSwitchPressed()){
			hood.resetZeroPosition();
		}
		
	}
	
	public void enable(){
		enabled = true;
		flywheel.enable();
		hood.enable();
		turret.enable();
		kicker.enable();
	}
	
	public void disable(){
		enabled = false;
		flywheel.disable();
		hood.disable();
		turret.disable();
		kicker.disable();
	}

	//////////////////////////////////////////////////////////////////////////////////
	// General Control
	//////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean isInPosition(){
		System.out.println("wheel: " + flywheel.isFlywheelAtSpeed() + 
				" hood: " + hood.isInPosition() + 
				" turret: " + turret.isInPosition() + 
				" kicker: " + kicker.isInPosition());
		return flywheel.isFlywheelAtSpeed() && 
				hood.isInPosition() && 
				turret.isInPosition() && 
				kicker.isInPosition();
	}
	
	public void setIntakePosition(){
		turretTarget = Constants.TURRET_INTAKE_POSITION;
		hoodTarget = Constants.HOOD_INTAKE_ANGLE;
		wheelTarget = Constants.FLYWHEEL_INTAKE_SPEED;
		kickerTarget = false;
		servoTarget = Constants.BALL_HOLDER_RETRACTED;
	}
	
	public void setStoragePosition(){
		turretTarget = Constants.TURRET_STORE_POSITION;
		hoodTarget = 0;
		wheelTarget = 0;
		kickerTarget = false;
	}
	
	public void setTurretHoodFlywheelKick(double turret, double hood, double flywheel, boolean kicker){
		turretTarget = turret;
		hoodTarget = hood;
		wheelTarget = flywheel;
		kickerTarget = kicker;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Flywheel Control
	//////////////////////////////////////////////////////////////////////////////////

	public void setFlywheelTargetVelocity(double target){
		wheelTarget = target;
	}
	
	public synchronized double getFlywheelTargetVelocity(){
		return flywheel.getTargetVelocity();
	}
	
	public synchronized double getFlywheelCurrentVelocity(){
		return flywheel.getCurrentVelocity();
	}
	
	public synchronized boolean isFlywheelAtSpeed(){
		return flywheel.isFlywheelAtSpeed();
	}

	//////////////////////////////////////////////////////////////////////////////////
	// Hood Control
	//////////////////////////////////////////////////////////////////////////////////

	public void setHoodTargetAngle(double target){
		hoodTarget = target;
	}
	
	public synchronized double getHoodTargetAngle(){
		return hood.getTargetPosition();
	}
	
	public synchronized double getHoodCurrentAngle(){
		return hood.getCurrentPosition();
	}
	
	public synchronized boolean isHoodInPosition(){
		return hood.isInPosition();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Turret Control
	//////////////////////////////////////////////////////////////////////////////////

	public void setTurretTargetAngle(double target){
		turretTarget = target;
	}
	
	public synchronized double getTurretTargetAngle(){
		return turret.getTargetPosition();
	}
	
	public synchronized double getTurretCurrentAngle(){
		return turret.getCurrentPosition();
	}
	
	public synchronized boolean isTurretInPosition(){
		return turret.isInPosition();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Kicker Control
	//////////////////////////////////////////////////////////////////////////////////

	public void setKickerFire(){
		kickerTarget = true;
	}
	
	public void setKickerReset(){
		kickerTarget = false;
	}
	

	//////////////////////////////////////////////////////////////////////////////////
	// Boulder Lock Control
	//////////////////////////////////////////////////////////////////////////////////
	
	public void setBoulderLockOff(){
		servoTarget = Constants.BALL_HOLDER_RETRACTED;
	}
	
	public void setBoulderLockOn(){
		servoTarget = Constants.BALL_HOLDER_EXTENDED;
	}

}
