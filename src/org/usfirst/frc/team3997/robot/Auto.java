package org.usfirst.frc.team3997.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	/* Autonomous mode variable */
	public static int autoMode;
	
	/* Set timer variable for autonomous */
	static Timer autoCountDown = new Timer();
	
	/* Auto variables for Autonomous Program 2 */
	public static double auto2Xa,auto2Ya,auto2Za;
	public static double auto2Xb,auto2Yb,auto2Zb;
	public static double auto2Xc,auto2Yc,auto2Zc;
	public static double driveTimeAM2;
	/* Auto variables for Autonomous Program 3 */
	public static double driveA1TimeAM3, driveA2TimeAM3;
	public static double driveAX1TimeAM3, driveAX2TimeAM3;
	public static double driveB1TimeAM3, driveB2TimeAM3;
	public static double driveC1TimeAM3, driveC2TimeAM3;
	public static double driveD1TimeAM3, driveD2TimeAM3;
	public static double driveE1TimeAM3, driveE2TimeAM3;
	public static double driveEX1TimeAM3, driveEX2TimeAM3;
	public static double driveF1TimeAM3, driveF2TimeAM3;
	public static double driveG1TimeAM3, driveG2TimeAM3;
	public static double driveH1TimeAM3, driveH2TimeAM3;
	public static double driveI1TimeAM3, driveI2TimeAM3;
	public static double driveJ1TimeAM3, driveJ2TimeAM3;
	
	/* Auto variables for Autonomous Program 4 */
	public static double driveA1TimeAM4, driveA2TimeAM4;
	public static double driveB1TimeAM4, driveB2TimeAM4;
	public static double driveC1TimeAM4, driveC2TimeAM4;
	public static double driveD1TimeAM4, driveD2TimeAM4;
	public static double driveE1TimeAM4, driveE2TimeAM4;
	public static double driveF1TimeAM4, driveF2TimeAM4;
	public static double driveG1TimeAM4, driveG2TimeAM4;
	public static double driveH1TimeAM4, driveH2TimeAM4;
	public static double driveI1TimeAM4, driveI2TimeAM4;
	public static double driveJ1TimeAM4, driveJ2TimeAM4;
	public static double AM4BackupTime;
	
	/* Auto variables for Autonomous Program 5 */
	public static double auto5Xa,auto5Ya,auto5Za;
	public static double auto5Xb,auto5Yb,auto5Zb;
	public static double auto5Xc,auto5Yc,auto5Zc;
	public static double driveTimeAM5;
	
	/* Auto variables for Autonomous Program 6 */
	public static double driveA1TimeAM6, driveA2TimeAM6;
	public static double driveB1TimeAM6, driveB2TimeAM6;
	public static double driveC1TimeAM6, driveC2TimeAM6;
	
	/* Auto variables for Autonomous Program 7 */
	public static double driveA1TimeAM7, driveA2TimeAM7;
	public static double driveB1TimeAM7, driveB2TimeAM7;
	public static double driveC1TimeAM7, driveC2TimeAM7;
	public static double driveD1TimeAM7, driveD2TimeAM7;
	public static double driveE1TimeAM7, driveE2TimeAM7;
	public static double driveF1TimeAM7, driveF2TimeAM7;
	public static double driveG1TimeAM7, driveG2TimeAM7;
	public static double driveH1TimeAM7, driveH2TimeAM7;
	public static double driveI1TimeAM7, driveI2TimeAM7;
	public static double driveJ1TimeAM7, driveJ2TimeAM7;
	
	/* Auto variables for Autonomous Program 9 */
	public static double driveA1TimeAM9, driveA2TimeAM9;
	public static double driveB1TimeAM9, driveB2TimeAM9;
	public static double driveC1TimeAM9, driveC2TimeAM9;
	public static double driveD1TimeAM9, driveD2TimeAM9;
	public static double driveE1TimeAM9, driveE2TimeAM9;
	public static double driveF1TimeAM9, driveF2TimeAM9;
	
	public static int lastState = 0;

	
	
	/* load autonomous chooser */
	static SendableChooser autoChooser;
	
	/****************************loadAutoModeOptions()**********************
	 * Purpose:
	 * 		-Defaults and manually sets autoMode to 1 (drive forward program)
	 * 	  	-Reads smartdashboard input and sets automode to that reading
	 * 		-Creates a bunch of options on the smartDashboard for custom autonomous program selection
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in disabledPeriodic() in Robot Class
	 * 		autoMode is changed to equal radio button selection in autoInit()
	 * Type: Method	
	 **********************************************************************/
	public static void loadAutoModeOptions(){
		//set default autoMode  (slightly redundant, because of addDefault below, but wahteever)
		autoMode = 1;
		
		//This puts up a bunch of radio buttons to the smart dashboard to allow the user to choose which auto program will run
		//the number to the right of the string in the "addObjects" will be the active autoMode if that choice is selected in the dashboard.
		autoChooser.addDefault("Do nuthin (Default)", 1); //default option
		
		autoChooser.addObject("Container to Auto Zone", 4); //other possible options:
		autoChooser.addObject("Container to Auto Zone (robot on platform)", 40);
		autoChooser.addObject("One tote to Auto Zone (needs testing)", 6);
		
		autoChooser.addObject("Strafe to the right 2s", 2);		
		autoChooser.addObject("Drive forward 2s", 5);
		autoChooser.addObject("Grab Containers from step. (uncalibrated)", 9);
		
		autoChooser.addObject("Landfill program (strafe left)", 70);
		autoChooser.addObject("Landfill program (strafe right)", 71);
		autoChooser.addObject("Landfill program (auto zone strafe left)", 72);
		autoChooser.addObject("Landfill program (auto zone strafe right)", 73);
		
		autoChooser.addObject("Two Tote (doesn't work)", 3); 
		//Put all these radio buttons in the smart dashboard
		SmartDashboard.putData("Autonomous Selector", autoChooser); 	
		
		//reads the radio button and sets the selected value to autoMode
		autoMode = (int) autoChooser.getSelected();
	}
	
	/****************************autoInit()*********************************
	 * Purpose:
	 * 		-Sets time and speed values for various autonomous programs 
	 * 		-Starts autonomous timer
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in autonomousInit() in Robot Class
	 * Type: Method	
	 **********************************************************************/
	public static void autoInit(){	
		
		lastState = 0;
		/**
		* Autonomous Program 2 values
		* 
		* Notes: Only uses part A.
		*/
		driveTimeAM2 = 2.0;
		//Part A - Strafe to the right
		auto2Xa = 0.5;
		auto2Ya = 0;
		auto2Za = 0;
		//Part B - empty
		auto2Xb = 0;
		auto2Yb = 0;
		auto2Zb = 0;
		//Part C - empty
		auto2Xc = 0;
		auto2Yc = 0;
		auto2Zc = 0;
		/* end of Auto program 2 */
		
		/**
		* Autonomous Program 3 time values
		* 
		* Notes: 
		* 	Speed values are entered directly in autonomous periodic.
		* 	delay must be greater than 0.
		*/
										//Format:		//|delay X: time to wait before running X|\\
														//|time  X: time spent actually running X|\\
		driveA1TimeAM3 = 0.2;							//delay A
		driveA2TimeAM3 = driveA1TimeAM3 + 0.3;			//run 	A
		driveAX1TimeAM3 = driveA1TimeAM3 + 0.1;		    //delay AX
		driveAX2TimeAM3 = driveAX1TimeAM3 + 1.0;		//run   AX
		driveB1TimeAM3 = driveAX2TimeAM3 + 0.1;			//delay B
		driveB2TimeAM3 = driveB1TimeAM3 + 1.5;			//run 	B
		driveC1TimeAM3 = driveB2TimeAM3 + 0.1;			//delay C
		driveC2TimeAM3 = driveC1TimeAM3 + 0.75;			//run 	C
		driveD1TimeAM3 = driveC2TimeAM3 + 0.1;			//delay D	
		driveD2TimeAM3 = driveD1TimeAM3 + 1.53;			//run 	D
		driveE1TimeAM3 = driveD2TimeAM3 + 0.1;			//delay E	
		driveE2TimeAM3 = driveE1TimeAM3 + 3.5;			//run 	E
		driveEX1TimeAM3 = driveE2TimeAM3 + 0.1;         //delay EX
		driveEX2TimeAM3 = driveEX1TimeAM3 + 0.4;		//run   EX
		driveF1TimeAM3 = driveEX2TimeAM3 + 0.1;			//delay F
		driveF2TimeAM3 = driveF1TimeAM3 + 2.0;			//run 	F
		driveG1TimeAM3 = driveF2TimeAM3 + 0.1;				//delay G
		driveG2TimeAM3 = driveG1TimeAM3 + 0.3;			//run 	G
		driveH1TimeAM3 = driveG2TimeAM3 + 0.1;			//delay H
		driveH2TimeAM3 = driveH1TimeAM3 + 1.0;			//run   H
		driveI1TimeAM3 = driveH2TimeAM3 + 0.1;			//delay I
		driveI2TimeAM3 = driveI1TimeAM3 + 0.0;			//run   I //1.0
		driveJ1TimeAM3 = driveI2TimeAM3 + 0.1;			//delay J
		driveJ2TimeAM3 = driveJ1TimeAM3 + 0.0;			//run   J //0.6
		//if autoMode is 3, set the first claw to ready position
		if(autoMode==3){
    		Lifter.setLifter(1, Lifter.floorHeight);
    	}
		/* end of Auto program 3 */
		
		/**
		* Autonomous Program 4 time values
		* 
		* Notes: 
		* 	Speed values are entered directly in autonomous periodic.
		* 	delay must be greater than 0.
		*/
		
		
		
		if(autoMode==40){
			AM4BackupTime = 2.0;
		}else{
			AM4BackupTime = 1.55;
		}
										//Format:		//|delay X: time to wait before running X|\\
														//|time  X: time spent actually running X|\\
		driveA1TimeAM4 = 0.2;							//delay A
		driveA2TimeAM4 = driveA1TimeAM4 + 0.4;			//run 	A		//run 	C
		driveB1TimeAM4 = driveA2TimeAM4 + 0.5;			//delay B	
		driveB2TimeAM4 = driveB1TimeAM4 + AM4BackupTime;			//run 	B
		driveC1TimeAM4 = driveB2TimeAM4 + 0.1;			//delay C	
		driveC2TimeAM4 = driveC1TimeAM4 + 0.4;			//run 	C

		/* end of Auto program 4 */
		
		/**
		* Autonomous Program 5 values
		* 
		* Notes: Only uses part A.
		*/
		driveTimeAM5 = 2.0; // Amount of time to run AM5
		//Part A - go forward
		auto5Xa = 0;
		auto5Ya = 0.5;
		auto5Za = 0;
		//Part B - empty 
		auto5Xb = 0;
		auto5Yb = 0;
		auto5Zb = 0;
		//Part C - empty
		auto5Xc = 0;
		auto5Yc = 0;
		auto5Zc = 0;
		/* end of Auto program 5 */
		
		/**
		* Autonomous Program 6 time values
		* 
		* Notes: 
		* 	Speed values are entered directly in autonomous periodic.
		* 	delay must be greater than 0.
		*/
										//Format:		//|delay X: time to wait before running X|\\
														//|time  X: time spent actually running X|\\
		driveA1TimeAM6 = 0.2;							//delay A
		driveA2TimeAM6 = driveA1TimeAM6 + 0.4;			//run 	A
		driveB1TimeAM6 = driveA2TimeAM6 + 0.5;			//delay B	
		driveB2TimeAM6 = driveB1TimeAM6 + 2.0;			//run 	B
		driveC1TimeAM6 = driveB2TimeAM6 + 0.1;			//delay C	
		driveC2TimeAM6 = driveC1TimeAM6 + 0.4;			//run 	C
		//if autoMode is 6, set the first claw to ready position
		if(autoMode==6){
    		Lifter.setLifter(1, Lifter.floorHeight);
    	}
		/* end of Auto program 6 */
		
		/**
		* Autonomous Program 7 time values
		* 
		* Notes: 
		* 	Speed values are entered directly in autonomous periodic.
		* 	delay must be greater than 0.
		*
		*/
		if(autoMode == 7 || (autoMode >= 70 && autoMode <= 73)) {
			lastState = 3;
		}
					//Format:		//|delay X: time to wait before running X|\\
					//|time  X: time spent actually running X|\\
			driveA1TimeAM7 = 0.2;							//delay A
			driveA2TimeAM7 = driveA1TimeAM7 + 0.3;			//run 	A
			driveB1TimeAM7 = driveA2TimeAM7 + 0.2;		    //delay B
			driveB2TimeAM7 = driveB1TimeAM7 + 0.0;			//run   B
			driveC1TimeAM7 = driveB2TimeAM7 + 0.0;			//delay C
			driveC2TimeAM7 = driveC1TimeAM7 + 0.45;			//run 	C
			driveD1TimeAM7 = driveC2TimeAM7 + 0.5;			//delay D	
			driveD2TimeAM7 = driveD1TimeAM7 + 1.3;			//run 	D
			driveE1TimeAM7 = driveD2TimeAM7 + 0.3;			//delay E	
			driveE2TimeAM7 = driveE1TimeAM7 + 0.4;			//run   E
			driveF1TimeAM7 = driveE2TimeAM7 + 0.1;			//delay F
			driveF2TimeAM7 = driveF1TimeAM7 + 1.0;			//run 	F
			driveG1TimeAM7 = driveF2TimeAM7 + 0.2;			//delay G
			driveG2TimeAM7 = driveG1TimeAM7 + 0.4;			//run 	G
			driveH1TimeAM7 = driveG2TimeAM7 + 0.1;			//delay H
			driveH2TimeAM7 = driveH1TimeAM7 + 1.0;			//run   H
			driveI1TimeAM7 = driveH2TimeAM7 + 0.1;			//delay I
			driveI2TimeAM7 = driveI1TimeAM7 + 2.;			//run   I
			driveJ1TimeAM7 = driveI2TimeAM7 + 0.1;			//delay J
			driveJ2TimeAM7 = driveJ1TimeAM7 + 1.5;			//run   J
		if(autoMode==70 || autoMode==71 || autoMode==72 || autoMode==73){
    		Lifter.setLifter(1, Lifter.floorHeight);
    	}
		/* end of Auto program 7 */
		
		/**
		* Autonomous Program 9 time values
		* 
		* Notes: 
		* 	Speed values are entered directly in autonomous periodic.
		* 	delay must be greater than 0.
		*/
										//Format:		//|delay X: time to wait before running X|\\
														//|time  X: time spent actually running X|\\
		driveA1TimeAM9 = 0.0;							//delay A
		driveA2TimeAM9 = driveA1TimeAM9 + 0.3;			//run 	A
		driveB1TimeAM9 = driveA2TimeAM9 + 2.5;			//delay B	
		driveB2TimeAM9 = driveB1TimeAM9 + 1.5;			//run 	B
		driveC1TimeAM9 = driveB2TimeAM9 + 0.0;			//delay C	
		driveC2TimeAM9 = driveC1TimeAM9 + 0.1;			//run 	C
		driveD1TimeAM9 = driveC2TimeAM9 + 0.0;			//delay D	
		driveD2TimeAM9 = driveD1TimeAM9 + 0.1;			//run 	D
		/* end of Auto program 9 */
		autoCountDown.reset();
	}

	/****************************autoPeriodic()*****************************
	 * Purpose:
	 * 		-Runs the selected autoMode
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes:
	 * 		Runs in autonomousPeriodic() in Robot Class
	 * 		autoMode is manually set to 1 and then possibly edited by radio buttons so the default case in this method should never trigger
	 * Type: Method	
	 **********************************************************************/
	public static void autoPeriodic(){
		switch(autoMode) {
			case 1:
				Robot.stopDriving();
				break;
			case 2:
				autoProgram2();
				break;
			case 3:
				autoProgram3();
				break;
			case 4:
				autoProgram4();
				break;
			case 40:
				autoProgram4();
				break;
			case 5:
				autoProgram5();
				break;
			case 9:
				autoProgram9();
				break;
			case 70:
				autoProgram7();
				break;
			case 71:
				autoProgram7();
				break;
			case 72:
				autoProgram7();
				break;
			case 73:
				autoProgram7();
				break;
			default:
				Robot.stopDriving();
				break;
		}
	}
	
	
	
	
	
	/*****************************autoProgram2()***********************************
	 * Purpose:
	 * 		Robot strafes to the right in autonomous
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Time: 3s 				    -- driveTimeAM1
	 * 		Direction: Right  			-- auto2Xa
	 * 		Speed: 0.5 					-- auto2Xa
	 ******************************************************************************/
	public static void autoProgram2(){
		if(autoCountDown.get() <= driveTimeAM2){
			Robot.drive(auto2Xa, auto2Ya, auto2Za, 0); 
		}
	}
	
	/*****************************autoProgram3()***********************************
	 * Purpose:
	 * 		Drives forward in autonomous;
	 * Parameters:
	 * 		Robot is set on the staging zone in between the alliance wall and the 
	 * 		far right yellow tote so claw is facing tote.
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Does not work because robot doesn't fit
	 * 		Speed and time values need to be calibrated.
	 ******************************************************************************/
	public static void autoProgram3(){
		Robot.printNumber("Autotimer:", autoCountDown.get());
		//A)set lifter to 1.
		if(isBetween(autoCountDown.get(), driveA1TimeAM3, driveA2TimeAM3)){	
			for(int i=0; i<1; i++){
				Lifter.setLifter(1, Lifter.floorHeight);
			}
		}
		//AX)drive forward very little to align with the tote
		else if(isBetween(autoCountDown.get(), driveAX1TimeAM3, driveAX2TimeAM3)){	
			Robot.drive(0, 0.2, 0, 0);
		}
		//B)lift twice so the lifted tote is higher than the adjacent container
		else if(isBetween(autoCountDown.get(), driveB1TimeAM3, driveB2TimeAM3)){    			
			for(int i=0; i<1; i++){
				Lifter.setLifter(3, 12);
			}
		}	
		//C)backup slightly
		else if(isBetween(autoCountDown.get(), driveC1TimeAM3, driveC2TimeAM3)){
			Robot.drive(0, -0.3, 0, 0);
		}
		//D)strafe left to the next yellow tote
		else if(isBetween(autoCountDown.get(), driveD1TimeAM3, driveD2TimeAM3)){
			Robot.drive(-0.7, 0, 0, 0);
		}
		//E)drive forward pushing the tote to the auto zone
		else if(isBetween(autoCountDown.get(), driveE1TimeAM3, driveE2TimeAM3)){
			Robot.drive(0, 0.4, 0, 0);
		}
		//EX) backup slightly
		else if(isBetween(autoCountDown.get(), driveE1TimeAM3, driveE2TimeAM3)){
			Robot.drive(0, -0.3, 0, 0);
		}
		//F)drop totes
		else if(isBetween(autoCountDown.get(), driveF1TimeAM3, driveF2TimeAM3)){
			//The for loop exists to make sure that the program only sets the lifter state to 0 once.	
			for(int i=0; i<1; i++){
				Lifter.setLifter(1, 12);
			}
		}
		//G)back up slightly 
		else if(isBetween(autoCountDown.get(), driveG1TimeAM3, driveG2TimeAM3)){
			Robot.drive(0, -0.3, 0, 0);
		}
		//H)rotate 
		else if(isBetween(autoCountDown.get(), driveH1TimeAM3, driveH2TimeAM3)){
			Robot.drive(0, 0, 0.4, 0);
		}
		//I)strafe left
		else if(isBetween(autoCountDown.get(), driveI1TimeAM3, driveI2TimeAM3)){
			Robot.drive(-0.4, 0, 0, 0);
		}
		//J)strafe Right to back off
		else if(isBetween(autoCountDown.get(), driveJ1TimeAM3, driveJ2TimeAM3)){
			Robot.drive(0.3, 0, 0, 0);
		}
		//End
		else{
			Robot.stopDriving();
		}
	}
	
	/*****************************autoProgram4()***********************************
	 * Purpose:
	 * 		Grabs container and puts it in auto zone
	 * Parameters:
	 * 		Robot is set right behind green container sitting in field and lifts it to the autozone.
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Speed and time values need to be calibrated.
	 ******************************************************************************/
	public static void autoProgram4(){
		
		Robot.printNumber("Autotimer:", autoCountDown.get());
		//A)lift container.
		if(isBetween(autoCountDown.get(), driveA1TimeAM4, driveA2TimeAM4)){	
			for(int i=0; i<1; i++){
				Lifter.setLifter(2, Lifter.platformHeight);
			}
		}
		//B)backup to autozone
		else if(isBetween(autoCountDown.get(), driveB1TimeAM4, driveB2TimeAM4)){
			Robot.drive(0, -0.5, 0, 0);
		}
		//C)drop container while backing up
		else if(isBetween(autoCountDown.get(), driveC1TimeAM4, driveC2TimeAM4)){
			Robot.drive(0, -0.4, 0, 0);
			for(int i=0; i<1; i++){
				Lifter.setLifter(1, Lifter.floorHeight);
			}
		}
		else{
			Robot.stopDriving();
		}
	}
	
	
	/*****************************autoProgram5()***********************************
	 * Purpose:
	 * 		Robot drives forward in autonomous;
	 * Parameters:
	 * 		N/A
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Time: 3s 					-- driveTimeAM5
	 * 		Direction: Forward  		-- auto1Ya
	 * 		Speed: 0.5 					-- auto1Ya
	 ******************************************************************************/
	public static void autoProgram5(){
		if(autoCountDown.get() <= driveTimeAM5){
			Robot.drive(auto5Xa, auto5Ya, auto5Za, 0); 
		}
	}
	
	/*****************************autoProgram6()***********************************
	 * Purpose:
	 * 		Grabs one yellow tote and puts it in auto zone
	 * Parameters:
	 * 		Robot is set right behind yellow sitting in staging zone lifts it to the autozone.
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Speed and time values need to be calibrated.
	 ******************************************************************************/
	public static void autoProgram6(){
		Robot.printNumber("Autotimer:", autoCountDown.get());
		//A)lift tote.
		if(isBetween(autoCountDown.get(), driveA1TimeAM6, driveA2TimeAM6)){	
			for(int i=0; i<1; i++){
				Lifter.setLifter(2, Lifter.floorHeight);
			}
		}
		//B)backup to autozone
		else if(isBetween(autoCountDown.get(), driveB1TimeAM6, driveB2TimeAM6)){
			Robot.drive(0, -0.5, 0, 0);
		}
		//C)drop tote while backing up
		else if(isBetween(autoCountDown.get(), driveC1TimeAM6, driveC2TimeAM6)){
			Robot.drive(0, -0.4, 0, 0);
			for(int i=0; i<1; i++){
				Lifter.setLifter(1, Lifter.floorHeight);
			}
		}
		else{
			Robot.stopDriving();
		}
	}
	
	/*****************************autoProgram7()***********************************
	 * Purpose:
	 * 		Takes two totes from landfill then (strafes) or (backs up then strafes)
	 * Parameters:
	 * 		Robot is set in far staging zone behind two hamburger totes near wall
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Speed and time values need to be calibrated.
	 ******************************************************************************/
	public static void autoProgram7(){
		Robot.printNumber("Autotimer:", autoCountDown.get());
		//A)set lifter to ready position
		if(isBetween(autoCountDown.get(), driveA1TimeAM7, driveA2TimeAM7)){	
			for(int i=0; i<1; i++){
				Lifter.setLifter(1, Lifter.floorHeight);
			}
		}
		//B)drive forward to align with two hamburger totes
		else if(isBetween(autoCountDown.get(), driveB1TimeAM7, driveB2TimeAM7)){
			Robot.drive(0, 0.4, 0, 0);
		}
		//C)drive slow line up
		else if(isBetween(autoCountDown.get(), driveC1TimeAM7, driveC2TimeAM7)){
			Robot.drive(0, 0.25, 0, 0);
			
		}
		//D)lift tote #1
		else if(isBetween(autoCountDown.get(), driveD1TimeAM7, driveD2TimeAM7)){
			Robot.drive(0, 0.1, 0, 0);
			for(int i=0; i<1; i++){
				Lifter.setLifter(2, Lifter.stepHeight);
			}			
		}
		//E)drive to tote #2 but not all the way
		else if(isBetween(autoCountDown.get(), driveE1TimeAM7, driveE2TimeAM7)){
			Robot.drive(0, 0.4, 0, 0);			
		}
		//F)drop tote #1 
		else if(isBetween(autoCountDown.get(), driveF1TimeAM7, driveF2TimeAM7)){
			for(int i=0; i<1; i++){
				Lifter.setLifter(2, Lifter.floorHeight);
			}	
		}
		//G)go forward a bit more to align the claws
		else if(isBetween(autoCountDown.get(), driveG1TimeAM7, driveG2TimeAM7)){
			Robot.drive(0, 0.15, 0, 0);		
		}
		//H)Lift totes
		else if(isBetween(autoCountDown.get(), driveH1TimeAM7, driveH2TimeAM7)){
			Robot.drive(0, 0.22, 0, 0);
			for(int i=0; i<1; i++){
				Lifter.setLifter(3, Lifter.stepHeight);
			}			
		}//I back up(to autozone variable)
		else if(isBetween(autoCountDown.get(), driveI1TimeAM7, driveI2TimeAM7)){
			if(autoMode==72 || autoMode==73){ // if autoZone
				Robot.drive(0, -0.4, 0, 0);
			}else{
				Robot.drive(0, -0.2, 0, 0);
			}			
		}//J strafe (left or right variable)
		else if(isBetween(autoCountDown.get(), driveJ1TimeAM7, driveJ2TimeAM7)){
			if(autoMode==71 || autoMode==73){ // if strafe right
				Robot.drive(0.50, 0, 0, 0);
			}else{
				Robot.drive(-0.50, 0, -0.1, 0);
			}
		}
		else{
			Robot.stopDriving();
		}
	}
	
	/*****************************autoProgram9()***********************************
	 * Purpose:
	 * 		Container grabber
	 * Parameters:
	 * 		
	 * Returns:
	 * 		N/A
	 * Notes: 
	 * 		Speed and time values need to be calibrated.
	 ******************************************************************************/
	public static void autoProgram9(){
		//robot is set up backwards so mild things of doom are facing the containers.
		Robot.printNumber("Autotimer:", autoCountDown.get());
		
		//A)drop grabbers
		if(isBetween(autoCountDown.get(), driveA1TimeAM9, driveA2TimeAM9)){
			Grabber.dropGrabbers();
		}
		//B)drive forward fast to auto zone
		else if(isBetween(autoCountDown.get(), driveB1TimeAM9, driveB2TimeAM9)){
			Robot.drive(0, 0.4, 0, 0);		
		}
		//C)lift grabbers
		else if(isBetween(autoCountDown.get(), driveC1TimeAM9, driveC2TimeAM9)){
			Robot.stopDriving();
		}
		else{
			Robot.stopDriving();
		}
	}
	
	
	
	
	/*****************************isBetween()***********************************
		 * Purpose:
		 * 		Tell if an input number is in between two values.
		 * Parameters:
		 * 		input, p1, p2
		 * Returns:
		 * 		true:  If input is greater than or equal to p1 and less than or equal to p2
		 * 		false:  If input is not within the range of p1 and p2.
		 * Notes: 
		 * 		Mainly used in autonomous.
		 **************************************************************************/
	 static boolean isBetween(double input, double p1, double p2) {
	    	if(input >= p1 && input <= p2) 
	    		return true;
	    	return false;
	    }	
	
	   
	    
}
