/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.util.ArrayList;

import com.tactfactory.mda.Harmony;

public class GeneralCommand extends BaseCommand {
	public static String LIST = "list";
	public static String HELP = "help";

	public void help() {
		System.out.println("\nUsage:\n" +
				"console [command[parameters]]\n");
		System.out.println("Usage example:\n" +
				"console project:init:android --name=test --namespace=com/tact/android/test --sdkdir=/root/android\n");
		
		System.out.println("Please use 'console list' to display available commands !");
	}

	public void list() {

		System.out.print("\nAvailable Commands:\n");
		for (BaseCommand baseCommand : Harmony.instance.bootstrap.values()) {
			baseCommand.summary();
		}
	}
	
	@Override
	public void summary() {
		System.out.print("\n> General:\n");
		System.out.print("\t"+HELP+"\t => Display this help message\n");
		System.out.print("\t"+LIST+"\t => List all commands\n");
	}

	@Override
	public void execute(String action, ArrayList<CompilationUnit> entities) {
		if (action.equals(LIST)) {
			this.list();
		} else
			
		if (action.equals(HELP)) {
			this.help();
		} else
			
		{
			
		}
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(LIST) ||
				command.equals(HELP)
				);
	}

}