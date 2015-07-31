
package org.usfirst.frc.team3997.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;


public class Lifter {
	private static int numClaws;							// Number of claws on the robot
	
	public static final double clawSpacing = 14;    	//claw spacing
	public static final double floorHeight = 0;				//Variable exits for reference reasons
	public static final double platformHeight = 2;			//Height of scoring platform in inches
	public static final double stepHeight = 5.75;			//Height of step in inches
	
	public static final double readyDistance = 12.5;			//Distance between limit switch and ready position
	private static double offsetHeight;						//The current offset height in inches based on 3 position switch
	private static double liftPosition;						//The current position of the lifter in inches
	public static int state = 0;
	
	private static LiftMotorPID liftMotorObj;
	
	public static final double resetLifterDistance = -300;
	private static Encoder liftEncoder;						//Encoder object for use with the PID controller
	private static final double distanceScale = 0.015963812;//Inches per pulse {old: 0.02298789}
	public static DigitalInput limitSwitch;
	public static PIDController liftControl;				//PIDController object to control position of lifter
	private static double liftP = 0.55;						//Lifter PID constants  //0.80
	private static double liftI = 0.00;
	private static double liftD = 0.35;												//0.43
	private static final double outMax = 0.6;				// Outmax changed from 0.5 to 0.4 on friday of alamo
	private static final double outMin = -0.50;				//Output range limits
	public static final double tolerance = 0.5;				//Tolerance in inches allowable for lifter to be "on target"

	private static Joystick operatorPanel;					//Operator panel joystick object
	
	private static Debounce upDebounce;						//Debounce objects for the up and down tote buttons
	private static Debounce downDebounce;
	private static Debounce downAllDebounce;
	
	
	public static double lastPosition = 0.0;
	public static boolean manControl = false;
	public static double manPosition;
		
	/*******************************LiftMotorPID********************************
	 * Purpose:
	 * 		This class combines however many motor controllers are required into
	 * 		a PIDOutput-compatible interface.  The object can be passed into a 
	 *		PIDController object to allow it to control multiple Talons as the
	 *		control variable.
	 **************************************************************************/
	public static class LiftMotorPID implements PIDOutput {
		private Talon liftMotors[];
		
		/****************************LiftMotorPID()*****************************
		 * Purpose:
		 * 		Constructor for the LiftMotorPID class.  This method initializes
		 * 		Talon motor controllers for each PWM pin passed in.
		 * Parameters:
		 * 		int liftMotorPins[]:  An array containing an arbitrary number of
		 * 								PWM pins.
		 * Returns:
		 * 		N/A
		 * Notes:
		 * Type: Constructor		
		 **********************************************************************/
		public LiftMotorPID(int liftMotorPins[]) {
			/* Create the required number of Talon objects for the lifter motors */
			liftMotors = new Talon[liftMotorPins.length];
			
			/* Initialize the Talon objects to the correct pins */
			for(int i = 0; i < liftMotorPins.length; i++) {
				liftMotors[i] = new Talon(liftMotorPins[i]); 
			}
			SmartDashboard.putString("Lift Talon Pins:", "" + liftMotorPins[0]
					+ ", " + liftMotorPins[1]);
		} //LiftMotorPID() [constructor]
		
		/*****************************pidWrite()********************************
		 * Purpose:
		 * 		This is the interface to PIDOutput which makes it compatible
		 * 		with the PIDController output parameter.  This method sets the
		 * 		the output speeds for all Talon objects.
		 * Paremeters:
		 * 		double output:  The output speed for the motor controllers.
		 * 						Range is -1 to 1.
		 * Returns:
		 * 		N/A
		 * Notes:
		 *  
		 **********************************************************************/
		public void pidWrite(double output) {
			SmartDashboard.putNumber("Lift Talon Output:", output);
			
			for(int i = 0; i < liftMotors.length; i++) {
				liftMotors[i].set(-output);
				SmartDashboard.putNumber("Talon val:", liftMotors[i].get());
			}
		} //pidWrite()
		
		public void set(double output) {
			this.pidWrite(output);
		}
	}  //LiftMotorPID [class]
	
	/*****************************lifterInit()**********************************                                
	 * Purpose:
	 * 		This function initializes the claw objects, lifter motor Talons, and
	 * 		PID controller for the lifter object.
	 * Parameters:
	 * 		int numClaws: The number of claws on the lifter chain
	 * 		int liftMotorPins[]: Array containing the PWM pins for the motors
	 *		int liftEncPins[]: Array containing channel A, B, and index pins
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Uses class global arrays 'claws' and 'liftMotors'.
	 **************************************************************************/
	public static void lifterInit(Joystick operatorPanel, int numClaws, int liftMotorPins[], int liftEncPins[]) {
		SmartDashboard.putString("Lifter Status:", "Initializing Lifter...");
		
		SmartDashboard.putNumber("Lifter P:", liftP);
		SmartDashboard.putNumber("Lifter I:", liftI);
		SmartDashboard.putNumber("Lifter D:", liftD);;
		
		/* Copy the operator panel joystick object from the Robot class */
		Lifter.operatorPanel = operatorPanel;
		
		/* Record the number of claws on the lifter */
		Lifter.numClaws = numClaws;
		
		/* Initialize second input for the limit switch so we can confirm lifter reset to 0 */
        limitSwitch = new DigitalInput(liftEncPins[2]);

        /* Initialize the lifter encoder and set limitSwitch as index */
        liftEncoder = new Encoder(liftEncPins[0], liftEncPins[1]);
        liftEncoder.setIndexSource(limitSwitch, Encoder.IndexingType.kResetWhileLow);
		
		/* Set the encoder as a distance source, instead of a rate or angle source */
		liftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		
		/* Set the distance per pulse conversion for the encoder to return inches */
		liftEncoder.setDistancePerPulse(distanceScale);
		
		/* Construct a PIDOutput compatible class with the required number of Talons */
		liftMotorObj = new LiftMotorPID(liftMotorPins);	
		
		/* Create the PID controller using liftEncoder as source and liftMotors as output */
		liftControl = new PIDController(liftP, liftI, liftD, liftEncoder, liftMotorObj);
		liftControl.setOutputRange(outMin, outMax);
		liftControl.setAbsoluteTolerance(tolerance);
		
		liftControl.enable();
		
		/* Construct debounce objects */
		upDebounce = new Debounce(operatorPanel, Controls.upToteButton);
		downDebounce = new Debounce(operatorPanel, Controls.downToteButton);
		downAllDebounce = new Debounce(operatorPanel, Controls.downAllButton);
		
		/* Initialize tracking variables */
		offsetHeight = 0;
		liftPosition = 0;
		
		SmartDashboard.putString("Lifter Status:", "Initialized");
	} //lifterInit()
	
	
	/******************************runLifter()*********************************
	 * Purpose:
	 * 		Controls when claws are raised and lowered based on operator input
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 
	 **************************************************************************/
	public static void runLifter() {
		SmartDashboard.putString("Lifter Status:", "Updating");
		SmartDashboard.putNumber("Encoder:", liftEncoder.getDistance());
		getOPInputs();
		if(!manControl){
			setLifter(state, offsetHeight);
		}
	}	

	/*****************************getOPInputs()*********************************
	 * Purpose:
	 * 		This function updates the state and offsetHeight variables based on
	 * 		operator panel inputs in teleop mode.
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Although this does not return anything, state and offsetHeight are
	 * 		modified for use in setLifter().
	 **************************************************************************/
	private static void getOPInputs() {
		//setPID();
		SmartDashboard.putNumber("Lifter state:", state);	
		/* Set height offset based on 3-position switches */
		if(ifThreePosSwitch(Controls.threePosB, 0) || ifThreePosSwitch(Controls.threePosB, 2)){
			manControl = false;
			
			if(!Controls.autoSupports){
				Controls.autoSupports = true;
			}
			
			if(!liftControl.isEnable()){
				liftControl.enable();
			}
			/* Check operator panel buttons to see if we have to do anything */
			if(upDebounce.getRise() && !downDebounce.getRise()) {
				/* If only tote up button is pressed, try to set the lifter up one tote */
				if (state < numClaws){
					state += 1;
				}
			}
			else if(!upDebounce.getRise() && downDebounce.getRise()) {
				/* If only tote down is pressed, try to set the lifter down one */
				if (state > 0){
					state -= 1;
				}			
			}
			
			if(downAllDebounce.getRise()) {
				liftEncoder.reset();
				lastPosition = 1.0;
				state = -1;
			}
			
			
			if(ifThreePosSwitch(Controls.threePosA, 0)) {
				/* If position 1, set offset to 0 */
				offsetHeight = 0;
			}
			else if(ifThreePosSwitch(Controls.threePosA, 1)) {
				/* If position 2, set offset to platform height */
				offsetHeight = platformHeight;
			}
			else if(ifThreePosSwitch(Controls.threePosA, 2)) {
				/* If position 3, set offset to step height */
				offsetHeight = stepHeight;
			}
			else {
				offsetHeight = 0;
			}
			manPosition = liftEncoder.getDistance();
			
		}
		else if(ifThreePosSwitch(Controls.threePosB, 1)){
			manControl = true;
			
			if(Controls.autoSupports){
				Controls.autoSupports = false;
				Supports.dropSupports();
			}
			
			if(liftControl.isEnable()){
				liftControl.disable();
			}
			
			
			//manually control lifter upwards
			if(ifThreePosSwitch(Controls.threePosA, 0)){
				if(upDebounce.getValue() && !downDebounce.getValue()) {
					liftMotorObj.pidWrite(0.3);
				}
				else if(!upDebounce.getValue() && downDebounce.getValue()) {
					liftMotorObj.pidWrite(-0.3);
				}
				else liftMotorObj.pidWrite(0);
			}
			
			if(ifThreePosSwitch(Controls.threePosA, 1)){
				if(upDebounce.getValue() && !downDebounce.getValue()) {
					liftMotorObj.pidWrite(0.45);
				}
				else if(!upDebounce.getValue() && downDebounce.getValue()) {
					liftMotorObj.pidWrite(-0.35);
				}
				else liftMotorObj.pidWrite(0);
			}
			
			if(ifThreePosSwitch(Controls.threePosA, 2)){
				if(upDebounce.getValue() && !downDebounce.getValue()) {
					liftMotorObj.pidWrite(0.6);
				}
				else if(!upDebounce.getValue() && downDebounce.getValue()) {
					liftMotorObj.pidWrite(-0.50);
				}
				else liftMotorObj.pidWrite(0);
			}
			
			if(!upDebounce.getValue() && !downDebounce.getValue()){
				if(!liftControl.isEnable()){
					liftControl.enable();
				}
			}
			else{
				manPosition = liftEncoder.getDistance();
			}
			
			liftControl.setSetpoint(manPosition);
		}	
		SmartDashboard.putNumber("Offset Height:", offsetHeight);
	} //getOPInputs()
	
	/*****************************setLifter()***********************************
	 * Purpose:
	 * 		This function sets the lifter PID object's setpoint based on a given
	 * 		state number and offset height.
	 * Parameters:
	 * 		int currentState:  A number between 0-(numClaws + 1) which gives a
	 * 							derived first claw position
	 * 		double offsetHeight:  Height in inches to the working surface
	 * Returns:
	 * 		N/A
	 * Notes:
	 **************************************************************************/
	public static void setLifter(int currentState, double currentOffset) {
		SmartDashboard.putBoolean("Limit Switch:", limitSwitch.get());
		SmartDashboard.putNumber("Lifter State:", currentState);
		if(currentState == -1) {
			liftPosition = resetLifterDistance;
			if(lastPosition < liftEncoder.getDistance()) {
				state = 0;
			}
			lastPosition = liftEncoder.getDistance();
		}
		
		if (currentState > 0 && currentState <= (numClaws + 1)){
			//add variables to liftPosition
			liftPosition = readyDistance + currentOffset + clawSpacing*(currentState-1);
		}
		
		else if (currentState == 0){
			//add variables to liftPosition
			liftPosition = currentOffset ;
		}
		
		//SmartDashboard.putNumber("Lifter Position:", liftPosition);
		
		liftControl.setSetpoint(liftPosition);
		SmartDashboard.putNumber("Lifter Position:",  liftControl.getSetpoint());
	} //setLifter()
	
	/*****************************ifThreePosSwitch()***********************************
	 * Purpose:
	 * 		Returns true if operator panel is enabling a button
	 * Parameters:
	 * 		int "button"
	 * Returns:
	 * 		true or false
	 * Notes:
	 **************************************************************************/
	public static boolean ifThreePosSwitch(int Button[], int Position){
		
		if(Position==0 || Position==2){
			if(operatorPanel.getRawButton(Button[Position])){
				return true;
			}
			else return false;
		}
		else if(Position==1){
			if(!operatorPanel.getRawButton(Button[0]) && !operatorPanel.getRawButton(Button[2])){
				return true;
			}
			else return false;
		}
		else{
			return false;
		}
	}
	
	
    /******************************* setPID() *********************************
     * Purpose:
     *         Reads in new PID values from the smart dashboard, if specified.
     * Parameters:
     *         N/A
     * Returns:
     *         N/A
     * Notes:
     *         If new values are not specified, it will default back to originals
 **************************************************************************/
    public static void setPID() {
        /* Read in PID values from the dashboard, or the defaults if they're unspecified */
    	liftP = SmartDashboard.getNumber("LiftP:", liftP);
    	liftI = SmartDashboard.getNumber("LiftI:", liftI);
    	liftD = SmartDashboard.getNumber("LiftD:", liftD);
        liftControl.setPID(liftP, liftI, liftD);
    }

	
	/******************************onTarget()***********************************
	 * Purpose:
	 * 		Forwards the onTarget() flag from the PID controller (private) to
	 * 		the caller
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		boolean liftControl.onTarget():  The onTarget() from the PID object
	 * Notes:
	 * 
	 **************************************************************************/
	public static boolean onTarget() {
		return liftControl.onTarget();
	}
	
	
	
}