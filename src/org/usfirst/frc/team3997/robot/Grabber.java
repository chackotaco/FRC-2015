package org.usfirst.frc.team3997.robot;
import edu.wpi.first.wpilibj.*;

public class Grabber {
	private static Joystick gamePad;
	public static DoubleSolenoid Burglars;
	private static Debounce grabberDebounce;

	/****************************grabberInit()******************************
	 * Purpose:
	 * 		-grabs gamePad for use in Grabber class
	 * Parameters:
	 * 		gamePad, burglarPins, toggleInput
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in robotInit() in Robot Class
	 * Type: Method	
	 **********************************************************************/
	public static void grabberInit(Joystick gamePad, int burglarPins[]){
    	Grabber.gamePad = gamePad;
    	
		Burglars = new DoubleSolenoid(burglarPins[0], burglarPins[1]);
		Burglars.set(DoubleSolenoid.Value.kOff);
		
		grabberDebounce = new Debounce(Grabber.gamePad, Controls.grabberButton);
	}
	
	/****************************runGrabber()*******************************
	 * Purpose:
	 * 		-Reads gamepad to move grabbers
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in runTeleOP() in TeleOP Class (which runs in teleopPeriodic in Robot Class)
	 * Type: Method	
	 **********************************************************************/
	public static void runGrabber(){
		if(grabberDebounce.getRise()){
			
			if(Burglars.get() == DoubleSolenoid.Value.kOff){
				dropGrabbers();
			}
			else if(Burglars.get() == DoubleSolenoid.Value.kForward){
				liftGrabbers();
			}
			else if(Burglars.get() == DoubleSolenoid.Value.kReverse){
				dropGrabbers();
			}
			
		}
		
	}
	
	/****************************dropGrabbers()*******************************
	 * Purpose:
	 * 		-Drops grabbers
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void dropGrabbers(){
		Burglars.set(DoubleSolenoid.Value.kForward);
	}
	
	/****************************liftGrabbers()*******************************
	 * Purpose:
	 * 		-lifts grabbers
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void liftGrabbers(){
		Burglars.set(DoubleSolenoid.Value.kReverse);
	}
		
}