package common;

import java.util.Random;

import battlecode.common.RobotController;

public class Utils {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static Random random = new Random();
	
	public static int nextRandomInt(int lower, int upper) {
		return random.nextInt(upper-lower+1);
	}
	
	public static String turnStamp(RobotController rc) {
		
		return "["+rc.getRobotCount()+" By robot "+rc.getType().toString()+" with id:"+rc.getID()+"]";
	}
	
	public static void printINFO(RobotController rc, String msg) {
		System.out.println(turnStamp(rc) + ": " + msg);
	}
	
	public static void printERROR(RobotController rc, String msg, Object... args) {
		System.out.printf(ANSI_RED+turnStamp(rc) + ": " + msg+"\n"+ANSI_RESET, args);	
	}
	
	public static void printWarning(RobotController rc, String msg, Object... args) {
		System.out.printf(ANSI_YELLOW+turnStamp(rc) + ": " + msg+"\n"+ANSI_RESET, args);
	}
}
