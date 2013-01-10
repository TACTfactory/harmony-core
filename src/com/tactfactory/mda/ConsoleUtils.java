/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

public class ConsoleUtils {
	public static boolean quiet = false;
	public static boolean ansi = true;
	
	private static ColoredPrinter cp = new ColoredPrinter.Builder(0, false).build();
	
	public static void display(String value) {
		if (!quiet)
			if (ansi) {
				cp.println(value);
				cp.clear();
			} else {
				System.out.println(value);
			}
	}
	
	public static void displayWarning(String value) {
		if (!quiet)
			if (ansi) {
				cp.println("[WARNING]\t" + value + "\n", Attribute.NONE, FColor.YELLOW, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
	}
	
	public static void displayDebug(String value) {
		if (!quiet && Harmony.debug)
			if (ansi) {
				cp.println("[DEBUG]\t" + value + "\n", Attribute.NONE, FColor.BLUE, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[DEBUG]\t" + value);
			}
	}
	
	public static void displayError(String value) {
		if (!quiet) 
			if (ansi) {
				cp.println("[ERROR]\t" + value + "\n", Attribute.NONE, FColor.RED, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[ERROR]\t" + value);
			}
	}

	public static void displayLicence(String value) {
		if (!quiet)
			if (ansi) {
				cp.println(value + "\n", Attribute.BOLD, FColor.GREEN, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
	}

}
