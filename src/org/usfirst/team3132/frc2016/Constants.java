package org.usfirst.team3132.frc2016;


public class Constants {

	
	// test chooser
	public final static String testDrivebaseRaw = "drivebase raw";
	public final static String testDrivebaseSpeed = "drivebase speed";
	public final static String testFlywheel = "flywheel";
	public final static String testHood = "hood";
	public final static String testKicker = "kicker";
	public final static String testTurret = "turret";
	public final static String testIntake = "intake";
	public final static String testShooter = "shooter";
	
	// auto chooser
	public final static String defaultAuto = "Default";
	public final static String customAuto = "My Auto";
	public final static String joeysAuto = "Joeys Auto";
	
	public enum AutoRoutines {
		EmptyAuto;
		
		
		public AutoRoutines next() {
			if (ordinal() >= values().length - 1) return values()[0];
			return values()[ordinal() + 1];
		}
		
		public AutoRoutines previous() {
			if (ordinal() <= 0) return values()[values().length - 1];
			return values()[ordinal() - 1];
		}
	}
	
	// kicker variables:
	public static final boolean KICKER_EXTENDED = true;
	public static final boolean KICKER_RETRACTED = false;
	
	// boulder holder variables:
	public static final double BALL_HOLDER_EXTENDED = 0.33;
	public static final double BALL_HOLDER_RETRACTED = 0.89;
	
	// turret variables
	public static final int TURRET_TICKS_PER_ROTATION = 1440*7/360;// was 5000/360 // want to be able to input degrees to the motor
	public static final double TURRET_FWD_LIMIT_ANGLE = 90;
	public static final double TURRET_REV_LIMIT_ANGLE = -180;
	public static final int TURRET_DEGREES_FROM_FWD_SWITCH_TO_ZERO = 94;
	public static final double TURRET_INTAKE_POSITION = 90;
	public static final double TURRET_BATTER_SHOT_ANGLE = 0;
	public static final double TURRET_STORE_POSITION = 90;
	public static final double TURRET_LCORNER_SHOT_ANGLE = 90;
	
	// intake
	public static final double INTAKE_INTAKE_POSITION = 7;
	public static final double INTAKE_SAFE_POSITION = 45;
	public static final double INTAKE_STORE_POSITION = 90;
	public static final double INTAKE_CDF_POSITION = -22;
	public static final double INTAKE_DEGREES_FROM_SWITCH_TO_ZERO = 90;
	public static final double INTAKE_LOW_BAR_POSITION = -20;
	
	// flywheel
	public static final double FLYWHEEL_INTAKE_SPEED = -0.5;
	public static final double FLYWHEEL_BATTER_SHOT_SPEED = 140;
	public static final double FLYWHEEL_MANUAL_SHOT_SPEED = 160;
	public static final double FLYWHEEL_OUTER_SHOT_SPEED = 175;
	public static final double FLYWHEEL_LCORNER_SHOT_SPEED = 165;
	
	// hood
	public static final double HOOD_DOWN_ANGLE = 0;
	public static final double HOOD_INTAKE_ANGLE = 80;
	public static final double HOOD_BATTER_SHOT_ANGLE = 10;
	public static final double HOOD_MANUAL_SHOT_ANGLE = 25;
	public static final double HOOD_OUTER_SHOT_ANGLE = 36;
	public static final double HOOD_LCORNER_SHOT_ANGLE = 36;
	
	//////////////////////////////////////////////////////////////////////////////////
	// Robot Map
	//////////////////////////////////////////////////////////////////////////////////
	
	// drivebase
    public static final int DRIVE_LEFT_MASTER_CAN_ID = 1;	// master talon on left side (CAN ID)
	public static final int DRIVE_LEFT_SLAVE_1_CAN_ID = 2;	// first left hand slave
	public static final int DRIVE_LEFT_SLAVE_2_CAN_ID = 3;	// second left hand slave
	public static final int DRIVE_RIGHT_MASTER_CAN_ID = 4;	// master talon on right side (CAN ID)
	public static final int DRIVE_RIGHT_SLAVE_1_CAN_ID = 5;	// first right hand slave
	public static final int DRIVE_RIGHT_SLAVE_2_CAN_ID = 6;	// second right hand slave
	
	// intake
	public static final int INTAKE_LEFT_LIFT_MOTOR_CAN_ID = 30;
	public static final int INTAKE_RIGHT_LIFT_MOTOR_CAN_ID = 31;
	public static final int INTAKE_MOTOR_CAN_ID = 32;
	public static final int INTAKE_BALL_IN_SENSOR_PORT = 2;
	
	// shooter
	public static final int FLYWHEEL_MOTOR_CAN_ID = 10;
	public static final int BALL_HOLDER_SERVO_PWM = 9;
	public static final int PCM_CAN_ID = 61;
	public static final int KICKER_PCM_ID = 0;
	public static final int TURRET_MOTOR_CAN_ID = 12;
	public static final int HOOD_MOTOR_CAN_ID = 11;
	
	// Power distribution board (PDB)
	public static final int PDP_CAN_ID = 62;
		
	
	
	
}
