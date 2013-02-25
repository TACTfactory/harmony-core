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

/**
 * Command class for rest bundle.
 */
@PluginImplementation
public class RestCommand extends BaseCommand {
	
	//bundle name
	/** Bundle name. */
	public static final String BUNDLE = "rest";
	/** Generation subject. */
	public static final String SUBJECT = "generate";

	//actions
	/** Adapters action. */
	public static final String ACTION_ADAPTERS = "adapters";

	//commands
	/** Command: REST:GENERATE:ADAPTERS.*/
	public static final String GENERATE_ADAPTERS	= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ADAPTERS;

	@Override
	public final void execute(final String action,
			final String[] args,
			final String option) {
		ConsoleUtils.display("> Adapters Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
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
	 * Generate java code files from parsed Entities.
	 */
	protected final void generateAdapters() {

		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.getEntities() != null) {
			try {
				new RestGenerator(new AndroidAdapter()).generateAll();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	@Override
	public final void generateMetas() {
		this.registerParser(new RestParser());
		super.generateMetas();
		new RestCompletor().generateApplicationRestMetadata(
				ApplicationMetadata.INSTANCE);
	}
	
	

	@Override
	public final void summary() {
		ConsoleUtils.display("\n> REST \n" 
				+ "\t" + GENERATE_ADAPTERS + "\t => Generate Adapters");
		
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return command.equals(GENERATE_ADAPTERS);
	}

}
