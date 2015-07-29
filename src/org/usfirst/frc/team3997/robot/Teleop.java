package org.usfirst.frc.team3997.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Teleop {
	/* Boolean flags for configuring drivetrain control */

	private static Joystick gamePad;
	private static Joystick moveStick;
	private static Joystick rotateStick;
	
	/* Drivetrain component variables */
	static double xVal;
	static double yVal;
	static double rotVal;
	
	/****************************initTeleOP()*******************************
	 * Purpose:
	 * 		-grabs joysticks for use in Teleop class
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in teleopInit() in Robot Class
	 * Type: Method	
	 **********************************************************************/
	public static void initTeleOP(Joystick gamePad, Joystick moveStick, Joystick rotateStick){
		Teleop.gamePad = gamePad;
		Teleop.moveStick = moveStick;
		Teleop.rotateStick = rotateStick;
		
		Lifter.state = Auto.lastState;
	}

	/****************************runTeleOP()*******************************
	 * Purpose:
	 * 		-Runs and enables lifter and grabber for use in teleop
	 * 		-Takes input from joysticks to manually move the robot around
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in robotInit() in Robot Class
	 * Type: Method	
	 **********************************************************************/
	public static void runTeleOP(){
		
    	/* Run the lifter, grabber, and supports*/
    	Lifter.runLifter();
    	Grabber.runGrabber();
    	Intake.runIntakePistons();
    	Intake.runIntakeMotors();
    	Supports.runSupports();
    	
        /* Read drive values from specified joystick */
    	if(Controls.useGamepad) {
    		xVal = gamePad.getX();
    		yVal = gamePad.getY();
    		rotVal = gamePad.getZ();
    	}
    	else {
    		xVal = moveStick.getX();
    		yVal = moveStick.getY();
    		rotVal = rotateStick.getX();
    	}
    	
    	/* Square the joystick values if requested for better sensitivity */
    	if(Controls.squareInputs) {
    		xVal = squareInput(xVal);
    		yVal = squareInput(yVal);
    		rotVal = squareInput(rotVal);
    	}
    	
    	/* Send cartesian values to mecanum drive */ 
    	Robot.driveTrain.mecanumDrive_Cartesian(xVal,  yVal,  rotVal,  0);
    	
    	
	}
	
	  /****************************** squareInput ********************************
     * Purpose:
     * 		This function takes a linear joystick input and transforms it into
     * 		a parabola, but with sign preserved.
     * Parameters:
     * 		double input:  The linear joystick input
     * Returns:
     * 		double:  The input times its absolute value (to preserve sign)
     **************************************************************************/
    public static double squareInput(double input) {
    			return input * Math.abs(input);
    }
    
	
}

