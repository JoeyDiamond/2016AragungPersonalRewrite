package org.usfirst.team3132.frc2016.autonomous;

public class AutonomousRunner {
	private AutonomousRoutine routine = new EmptyAutonomous();
	private Thread thread;
	
	public void setAutoRoutine(AutonomousRoutine newRoutine){
		routine = newRoutine;
	}
	
	public void start(){
		if(thread == null){
			thread = new Thread(routine);
			thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void kill(){
		if(thread != null){
			try{
				thread.stop();
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		thread = null;
	}
	
	public boolean isRunning(){
		return thread.isAlive();
	}
}
