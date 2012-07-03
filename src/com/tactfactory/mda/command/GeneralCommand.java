/**
 * This file is part of the Symfodroid package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;

import com.tactfactory.mda.Harmony;

public class GeneralCommand extends BaseCommand {
	public static String LIST = "list";
	public static String HELP = "help";

	public void help() {
		System.out.print("Welcome to TACT MDA !\n" +
				"You can generate a android project base on Model Driven Application. \n\n" +
				"For list all available command launch : \n" +
				"$ java console list");
	}

	public void list() {
		for (BaseCommand baseCommand : Harmony.instance.bootstrap.values()) {
			baseCommand.summary();
		}
	}

	@Override
	public void summary() {
		System.out.print("\n> GENERAL\n");
		System.out.print("help\t => Help\n");
		System.out.print("list\t => List all commands\n");
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
				command.equals(HELP) );
	}

}