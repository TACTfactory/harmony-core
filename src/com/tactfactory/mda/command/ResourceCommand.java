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

import com.tactfactory.mda.Console;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * 
 *
 */
@PluginImplementation
public class ResourceCommand extends BaseCommand {

	/** Bundle name. */
	public static final String BUNDLE = "resource";
	/** Subject. */
	public static final String SUBJECT = "generate";

	/** Image action. */
	public static final String ACTION_IMAGE = "image";
	/** Translate action. */
	public static final String ACTION_TRANSLATE = "translate";

	//commands
	/** Command : RESOURCE:GENERATE:IMAGE. */
	public static final String GENERATE_IMAGE 	=
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_IMAGE;
	/** Command : RESOURCE:GENERATE:TRANSLATE. */
	public static final String GENERATE_TRANSLATE	= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_TRANSLATE;

	//internal
	/** Adapter. */
	private final BaseAdapter adapter = new AndroidAdapter();
	
	@Override
	public final void summary() {
		ConsoleUtils.display("\n> " + BUNDLE.toUpperCase() + " \n" 
				+ "\t" + GENERATE_IMAGE 
				+ "\t => Generate all resize of the HD images\n" 
				
				+ "\t" + GENERATE_TRANSLATE 
				+ "\t => Generate translate\n");
	}

	@Override
	public final void execute(final String action, 
			final String[] args, 
			final String option) {
		ConsoleUtils.display("> Resource Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		
		try {
			if (action.equals(GENERATE_IMAGE)) {
				this.adapter.resizeImage();
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return  command.equals(GENERATE_IMAGE)
				|| command.equals(GENERATE_TRANSLATE);
	}
}
