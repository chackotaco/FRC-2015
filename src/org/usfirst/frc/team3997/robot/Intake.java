package org.usfirst.frc.team3997.robot;
import edu.wpi.first.wpilibj.*;

public class Intake {
	private static Joystick gamePad;
	public static DoubleSolenoid sIntake;
	public static Talon mIntakeLeft;
	public static Talon mIntakeRight;
	private static Debounce intakeDebounce;	
	private static Debounce intakeMotorInDebounce;
	private static Debounce intakeMotorOutDebounce;	
	private static int forward = 1;
	private static int reverse = -1;
	private static int updateMotorDirection = 1;
	private static int intakeMotorPos = 0;
	


	/****************************intakeInit()******************************
	 * Purpose:
	 * 		
	 * Parameters:
	 * 		
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void intakeInit(Joystick gamePad, int intakePins[], int intakeMotorPins[]){
		Intake.gamePad = gamePad;
		
		// Solenoid initalization
		sIntake = new DoubleSolenoid(intakePins[0], intakePins[1]);
		sIntake.set(DoubleSolenoid.Value.kOff);
		
		// bring intake back
		retractIntake();
		
		// Motor initialization
		mIntakeLeft = new Talon(intakeMotorPins[0]);
		mIntakeRight = new Talon(intakeMotorPins[1]);
		
		intakeDebounce = new Debounce(Intake.gamePad, Controls.intakeButton);
		intakeMotorInDebounce = new Debounce(Intake.gamePad, Controls.intakeMotorIn);
		intakeMotorOutDebounce = new Debounce(Intake.gamePad, Controls.intakeMotorOut);
	}
	
	/****************************runIntakePistons()*******************************
	 * Purpose:
	 * 		Runs constantly in teleop. Toggles intake position.
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void runIntakePistons(){
		if(intakeDebounce.getRise()){
			if(ifIntakeOff()){
				extendIntake();
			}
			else if(ifIntakeForward()){
				
				intakeMotorPos = 0;	//turn off motors
				intakeMotorsManual(intakeMotorPos);
				
				
				retractIntake();
				
				
			}
			else if(ifIntakeReverse()){
				
				updateMotorDirection = 1; //reset motor direction
				
				extendIntake();
			}
		}
	}
	/****************************runIntakeMotors()*******************************
	 * Purpose:
	 * 		Runs motors in opposite directions while intake pistons are extended.
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		also allows for intakeMotorButton to spin wheels while pistons are retracted.
	 * 		pressing this button should cycle the motors between off, intake, and outake
	 * Type: Method	
	 **********************************************************************/
	public static void runIntakeMotors(){
		
		if(ifIntakeForward() ){ 
			mIntakeNormal(); //not sure if this should be here or in runIntakePistons (iterating through sets this each time it iterates)
			if(intakeMotorInDebounce.getRise()){
				updateMotorDirection = -updateMotorDirection;
			}
		}
		else if(intakeMotorOutDebounce.getRise() && ifIntakeReverse() ){
			intakeMotorPos++;
			intakeMotorsManual(intakeMotorPos % 3);
		}
		
	}
	
	
	/****************************intakeMotorsManual()*******************************
	 * Purpose:
	 * 		-
	 * Parameters:
	 * 		motorPos:  0 for off, 1 for intake, 2 to push out
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void intakeMotorsManual(int motorPos){
		switch(motorPos){
		case 0:
			mIntakeStop();
			break;
		case 1:
			mIntakeLeftMotor(reverse);
			mIntakeRightMotor(forward);
			break;
		case 2:
			mIntakeLeftMotor(forward);
			mIntakeRightMotor(reverse);
		default:
			mIntakeStop();
			break;
		}
	}
	
	/****************************check intake states*******************************
	 * Purpose:
	 * 		-
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	
	public static boolean ifIntakeOff(){
		if(sIntake.get() == DoubleSolenoid.Value.kOff){
			return true;
		}
		return false;
	}
	
	public static boolean ifIntakeForward(){
		if(sIntake.get() == DoubleSolenoid.Value.kForward){
			return true;
		}
		return false;
	}
	
	public static boolean ifIntakeReverse(){
		if(sIntake.get() == DoubleSolenoid.Value.kReverse){
			return true;
		}
		return false;
	}

	/****************************extendIntake()*******************************
	 * Purpose:
	 * 		-extends intake outwards
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void extendIntake(){
		sIntake.set(DoubleSolenoid.Value.kForward);
	}
	
	/****************************retractIntake()*******************************
	 * Purpose:
	 * 		-brings intake back into robot
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void retractIntake(){
		sIntake.set(DoubleSolenoid.Value.kReverse);
	}
	
	
	/****************************mIntakeCustom()*******************************
	 * Purpose:
	 * 		-allows for custom input of speed and direction for both intake motors.
	 * Parameters:
	 * 		speed, direction
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void mIntakeCustom(double speedLeft, double speedRight){
		mIntakeLeft.set(speedLeft);
		mIntakeRight.set(speedRight);
	}
	
	/****************************mIntakeStop()*******************************
	 * Purpose:
	 * 		-stops both intake motors 
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void mIntakeStop(){
		mIntakeCustom(0, 0);
	}
	
	/****************************mIntakeNormal()*******************************
	 * Purpose:
	 * 		-enables both intake motors at intake speed in opposite directions 
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void mIntakeNormal(){
		mIntakeLeftMotor(reverse * updateMotorDirection);
		mIntakeRightMotor(forward * updateMotorDirection);
	}
	
	/****************************mIntakeLeftMotor()*******************************
	 * Purpose:
	 * 		-enables left motor at intake speed
	 * Parameters:
	 * 		-1 for reverse, 1 for forward
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void mIntakeLeftMotor(int direction){
		mIntakeLeft.set(Controls.intakeSpeed * direction);
	}
	
	/****************************mIntakeLeftMotor()*******************************
	 * Purpose:
	 * 		-enables right motor at intake speed
	 * Parameters:
	 * 		-1 for reverse, 1 for forward
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		
	 * Type: Method	
	 **********************************************************************/
	public static void mIntakeRightMotor(int direction){
		mIntakeRight.set(Controls.intakeSpeed * direction);
	}
	
}