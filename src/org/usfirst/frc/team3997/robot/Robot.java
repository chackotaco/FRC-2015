package org.usfirst.frc.team3997.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 **/
public class Robot extends IterativeRobot {
	static RobotDrive driveTrain;
	
	/* Joystick USB ports */
	Joystick gamePad = new Joystick(0);
	Joystick operatorPanel = new Joystick(1);
	Joystick moveStick = new Joystick(2);
	Joystick rotateStick = new Joystick(3);
	
	/* Drivetrain constants*/
	final static double strafeScale = Math.sqrt(2); 
	
	
    /**
     * robotInit():
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	/* Drivetrain object */
    	driveTrain = new RobotDrive(Controls.frontLeftPin,Controls.rearLeftPin,Controls.frontRightPin,Controls.rearRightPin);  
    	driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true); //|Used to invert right motors
    	driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);  //|Used to invert right motors
    	
    	/* Initialize lifter, grabber, supports, intake*/
    	Lifter.lifterInit(operatorPanel, Controls.numClaws, Controls.liftMotorPins, Controls.liftEncPins);
    	Grabber.grabberInit(gamePad, Controls.burglarPins);
    	Intake.intakeInit(gamePad, Controls.intakePins, Controls.intakeMotorPins);
    	Supports.supportInit(gamePad, Controls.supportPins);
    	
    	/* Initialize autonomous radio button selector */
    	Auto.autoChooser = new SendableChooser();
    	
    	/* Start counting autonomous (Timer is reset during autoInit) */
    	Auto.autoCountDown.start();
    	
    } // end of robotInit()
    
	/**
	 * This function is called when entering disabled mode
	 */
	public void disabledInit() {
		
		printString("FMS Mode:", "Entering Disabled");
	}
	
	/**
	 * This function is called periodically during disabled
	 */
	public void disabledPeriodic() {
		Auto.loadAutoModeOptions();
		Auto.autoCountDown.reset(); //Start counting autonomous seconds from 0.
	}   
	
    /**
	 * This function is called when entering autonomous mode
	 */
	public void autonomousInit() {
		Auto.autoInit();
		printNumber("AutoMode:", Auto.autoMode);
	} //autonomousInit()
	
	
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	Auto.autoPeriodic();
    } //autonomousPeriodic()
       
    /**
	 * This function is called when entering teleoperated mode
	 */
    public void teleopInit() {
    	Teleop.initTeleOP(gamePad, moveStick, rotateStick);
    	printString("FMS Mode:", "Entering Teleop");
	}
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	Teleop.runTeleOP();
    } //teleopPeriodic()
   
	/**
	 * This function is called when entering test mode
	 */
	public void testInit() {
		
	}
	
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
  
    /*****************************stopDriving()***********************************
	 * Purpose:
	 * 		Sets all mecanum speed values on the drive train to zero.
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Created for ease of use.
	 ****************************************************************************/
    public static void stopDriving(){
    	driveTrain.mecanumDrive_Cartesian(0, 0, 0, 0); 
    }
    
    /*****************************drive()***********************************
	 * Purpose:
	 * 		Creates easy to use method for controlling the drive train.
	 * Parameters:
	 * 		x, y, z, r
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		1)Values have domains of -1 through 1
	 * 		2)Speed values for y are inverted for easy human readability
	 * 		3)Speed values for x are edited for consistency by speed ratio for strafing.
	 ****************************************************************************/
    public static void drive(double x, double y, double z, double r){
    	driveTrain.mecanumDrive_Cartesian(x * strafeScale, -y, z, r); 
    }
    

    /*****************************printString()***********************************
	 * Purpose:
	 * 		Easy to use method to print strings to the smart dashboard.
	 * Parameters:
	 * 		label, text
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 	
	 ****************************************************************************/
    public static void printString(String label, String text){
    	SmartDashboard.putString(label, text);
    }
    
    /*****************************printNumber()***********************************
	 * Purpose:
	 * 		Easy to use method to print numbers to the smart dashboard.
	 * Parameters:
	 * 		label, number
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 	
	 ****************************************************************************/
    public static void printNumber(String label, double number){
    	SmartDashboard.putNumber(label, number);
    }
    
    /*****************************printBoolean()***********************************
	 * Purpose:
	 * 		Easy to use method to print true or false to the smart dashboard.
	 * Parameters:
	 * 		label, state
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 	
	 ****************************************************************************/
    public static void printBoolean(String label, boolean state){
    	SmartDashboard.putBoolean(label, state);
    }
}
