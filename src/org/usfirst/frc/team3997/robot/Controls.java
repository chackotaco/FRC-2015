package org.usfirst.frc.team3997.robot;
//import edu.wpi.first.wpilibj.*;

public class Controls {
	
	/*Button maps*/
	
	// Grabber, intake, supports
	public static final int grabberButton = 5;
	public static final int intakeButton = 6;
	public static final int intakeMotorOut = 3;
	public static final int intakeMotorIn = 1;
	public static final int supportButton = 2;
	
	// Lifter
	public static final int threePosA[] = {4, 5, 6}; 	 //Operator panel pins for the top three position switch
	public static final int threePosB[] = {7, 42, 8};    //Operator panel pins (excluding 42) for the bottom three position switch (middle button is true if other two are not active)
	public static final int upToteButton = 2;		     //Operator panel input for tote up button
	public static final int downToteButton = 1;			 //Operator panel input for tote down button
	public static final int downAllButton = 3;			 //Operator panel input for tote down all button
	
	
	
	
	/* Motor and encoder pin variables */
	
	public static final int frontLeftPin = 6, rearLeftPin = 9, frontRightPin = 3, rearRightPin = 0; // Drivetrain Talon pins
	public static final int liftMotorPins[] = {1, 2};	// Talon lifter PWM pins
	public static final int liftEncPins[] = {0, 1, 2};	
	public static final int intakeMotorPins[] = {7, 8}; //{left, right}
	
	/* Pneumatic pin variables */  //{forward pin, reverse pin}
	
	public static final int burglarPins[] = {1, 0}; // Grabber bumper pins
	public static final int intakePins[] = {5, 4}; // Intake bumper pins   
	public static final int supportPins[] = {3, 2}; // support bumper pins   
 
	
	
	/*Preferences*/
	
	// Robot:
	// use gamepad or stick
	final static boolean useGamepad = true;
			
	// Square inputs for easier robot control
	public final static boolean squareInputs = true;
	
	
	//Lifter:
	// Number of claws
	public static final int numClaws = 6;
	
	//Speed of intake motors
	public static final double intakeSpeed = 1.0;
	
	//Supports:
	//automatically extend pistons when going up
	public static boolean autoSupports = true;
	public static int liftStateThresh = 2;
	
	
}