/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Harmony;

@PluginImplementation
public class GeneralCommand extends BaseCommand {
	public static String LIST = "list";
	public static String HELP = "help";

	/*
	 * Display Help Message
	 */
	public void help() {
		System.out.println("\nUsage:\n" +
				"console [command[parameters]]\n");
		System.out.println("Usage example:\n" +
				"console project:init:android --name=test --namespace=com/tact/android/test --sdkdir=/root/android\n");
		
		System.out.println("Please use 'console list' to display available commands !");
	}

	/*
	 * Display list of All commands
	 */
	public void list() {
		Command general = Harmony.instance.bootstrap.get(GeneralCommand.class);
		
		System.out.print("\nAvailable Commands:\n");
		general.summary();
		
		for (Command baseCommand : Harmony.instance.bootstrap.values()) {
			if (baseCommand != general)
				baseCommand.summary();
		}
	}
	
	@Override
	public void summary() {
		System.out.print("\n> General \n");
		System.out.print("\t"+HELP+"\t => Display this help message\n");
		System.out.print("\t"+LIST+"\t => List all commands\n");
	}

	@Override
	public void execute(String action, String[] args, String option) {
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