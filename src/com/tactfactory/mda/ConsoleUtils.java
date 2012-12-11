package com.tactfactory.mda;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

public class ConsoleUtils {
	public static boolean quiet = false;
	
	private static ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();
	
	public static void display(String value) {
		if (!quiet)
			cp.println(value);
	}
	
	public static void displayWarning(String value) {
		if (!quiet)
			cp.println(value, Attribute.NONE, FColor.YELLOW, BColor.NONE);
	}
	
	public static void displayDebug(String value) {
		if (!quiet && Harmony.DEBUG)
			cp.println("[DEBUG]\t" + value + "\n", Attribute.NONE, FColor.BLUE, BColor.NONE);
	}
	
	public static void displayError(String value) {
		if (!quiet) {
			cp.println("[ERROR]\t" + value + "\n", Attribute.NONE, FColor.RED, BColor.NONE);
		}
	}

	public static void displayLicence(String value) {
		if (!quiet)
			cp.println(value+ "\n", Attribute.BOLD, FColor.GREEN, BColor.NONE);
	}

}
