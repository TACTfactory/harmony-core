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
import com.tactfactory.mda.utils.ConsoleUtils;

@PluginImplementation
public class GeneralCommand extends BaseCommand {
	public final static String LIST = "list";
	public final static String HELP = "help";

	/**
	 * Display Help Message
	 */
	public void help() {
		ConsoleUtils.display("Usage:\n" +
				"\tconsole [--options] command [--parameters]\n" +
				"\nUsage example:\n" +
				"\tconsole --verbose project:init:android --name=test --namespace=com/tact/android/test --sdkdir=/root/android\n" +
				"\nPlease use 'console list' to display available commands !");
	}

	/**
	 * Display list of All commands
	 */
	public void list() {
		final Command general = Harmony.instance.getCommand(GeneralCommand.class);
		
		ConsoleUtils.display("Available Commands:");
		general.summary();
		
		for (final Command baseCommand : Harmony.instance.getCommands()) {
			if (baseCommand != general){
				baseCommand.summary();
			}
		}
	}
	
	@Override
	public void summary() {
		ConsoleUtils.display("\n> General \n"+
				"\t"+HELP+"\t\t\t => Display this help message\n"+
				"\t"+LIST+"\t\t\t => List all commands");
	}

	@Override
	public void execute(final String action, final String[] args, final String option) {
		if (action.equals(LIST)) {
			this.list();
		} else
			
		if (action.equals(HELP)) {
			this.help();
		}
	}

	@Override
	public boolean isAvailableCommand(final String command) {
		return 	command.equals(LIST) ||
				command.equals(HELP) ;
	}

}
