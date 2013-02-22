/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.rest.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.bundles.rest.parser.RestCompletor;
import com.tactfactory.mda.bundles.rest.parser.RestParser;
import com.tactfactory.mda.bundles.rest.template.RestGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

@PluginImplementation
public class RestCommand extends BaseCommand {
	
	//bundle name
	public static final String BUNDLE = "rest";
	public static final String SUBJECT = "generate";

	//actions
	public static final String ACTION_ADAPTERS = "adapters";

	//commands
	public static final String GENERATE_ADAPTERS	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ADAPTERS;

	@Override
	public void execute(final String action, final String[] args, final String option) {
		ConsoleUtils.display("> Adapters Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(GENERATE_ADAPTERS)) {
			try {
				this.generateAdapters();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/**
	 * Generate java code files from parsed Entities
	 */
	protected void generateAdapters() {

		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.entities != null) {
			try {
				new RestGenerator(new AndroidAdapter()).generateAll();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	@Override
	public void generateMetas() {
		this.registerParser(new RestParser());
		super.generateMetas();
		new RestCompletor().generateApplicationRestMetadata(ApplicationMetadata.INSTANCE);
	}
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> REST \n" 
				+ "\t" + GENERATE_ADAPTERS + "\t => Generate Adapters");
		
	}

	@Override
	public boolean isAvailableCommand(final String command) {
		return command.equals(GENERATE_ADAPTERS);
	}

}
