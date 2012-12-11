package com.tactfactory.mda;

public class ConsoleUtils {
	public static boolean quiet = false;
	
	public static void display(String value) {
		if (!quiet)
			System.out.println(value);
	}
	
	public static void displayWarning(String value) {
		if (!quiet)
			System.out.println(value);
	}
	
	public static void displayDebug(String value) {
		if (!quiet && Harmony.DEBUG)
			System.out.println("[DEBUG] " +value);
	}
	
	public static void displayError(String value) {
		if (!quiet) {
			System.out.println("[ERROR] " + value);
		}
	}

}
