package org.usfirst.frc.team3997.robot;
import edu.wpi.first.wpilibj.*;

public class Supports {
	private static Joystick gamePad;
	public static DoubleSolenoid toteSupports;
	private static Debounce supportDebounce;
	static Timer supportsCount = new Timer();

	/****************************supportInit()******************************
	 * Purpose:
	 * 		-grabs gamePad for use in Supports class
	 * Parameters:
	 * 		gamePad, supportsPins
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in robotInit() in Robot Class
	 * Type: Method	
	 **********************************************************************/
	public static void supportInit(Joystick gamePad, int supportPins[]){
    	Supports.gamePad = gamePad;
    	
		toteSupports = new DoubleSolenoid(supportPins[0], supportPins[1]);
		toteSupports.set(DoubleSolenoid.Value.kOff);
		
		supportDebounce = new Debounce(Supports.gamePad, Controls.supportButton);
		
		supportsCount.start();
	}
	
	
	
	/****************************runSupports()*******************************
	 * Purpose:
	 * 		-Reads gamepad to move supports
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in runTeleOP() in TeleOP Class (which runs in teleopPeriodic in Robot Class)
	 * Type: Method	
	 **********************************************************************/
	public static void runSupports(){
		if(!Controls.autoSupports){ // autoSupports = false
			if(supportDebounce.getRise()){
			
				if(toteSupports.get() == DoubleSolenoid.Value.kOff){
					liftSupports();
				}
				else if(toteSupports.get() == DoubleSolenoid.Value.kForward){
					dropSupports();
				}
				else if(toteSupports.get() == DoubleSolenoid.Value.kReverse){
					liftSupports();
				}				
			}
		}
		else { //autoSupports = true
			if(Lifter.state >= Controls.liftStateThresh && Lifter.onTarget()) {
				liftSupports();
			}
			else {
				dropSupports();
			}
		}
	}

	
	/****************************dropSupports()*******************************
	 * Purpose:
	 * 		-drop supports
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void dropSupports(){
		toteSupports.set(DoubleSolenoid.Value.kReverse);
	}
	
	/****************************liftSupports()*******************************
	 * Purpose:
	 * 		-lifts supports
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void liftSupports(){
		toteSupports.set(DoubleSolenoid.Value.kForward);
	}
		
}