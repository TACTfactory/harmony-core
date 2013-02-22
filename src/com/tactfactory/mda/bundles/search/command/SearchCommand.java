/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.search.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.bundles.search.parser.SearchParser;
import com.tactfactory.mda.bundles.search.template.SearchGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

@PluginImplementation
public class SearchCommand  extends BaseCommand {
	
	//bundle name
	public static final String BUNDLE = "search";
	public static final String SUBJECT = "generate";

	//actions
	public static final String ACTION_ACTIVITIES = "activities";

	//commands
	public static final String GENERATE_ACTIVITIES = 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ACTIVITIES;

	@Override
	public void execute(final String action, 
			final String[] args, 
			final String option) {
		ConsoleUtils.display("> Adapters Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		if (action.equals(GENERATE_ACTIVITIES)) {
			try {
				this.generateLoaders();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/**
	 * Generate java code files from parsed Entities.
	 */
	protected void generateLoaders() {

		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.entities != null) {
			try {
				new SearchGenerator(new AndroidAdapter()).generateAll();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	@Override
	public void generateMetas() {
		this.registerParser(new SearchParser());
		super.generateMetas();
		//new SearchCompletor().generateApplicationRestMetadata(Harmony.metas);
	}
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> Search \n" 
				+ "\t" + GENERATE_ACTIVITIES + "\t => Generate Activities");
		
	}

	@Override
	public boolean isAvailableCommand(final String command) {
		return command.equals(GENERATE_ACTIVITIES);
	}

}
