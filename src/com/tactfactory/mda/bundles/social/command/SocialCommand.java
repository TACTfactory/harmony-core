/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.social.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.bundles.social.parser.SocialParser;
import com.tactfactory.mda.bundles.social.template.SocialGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * Social Bundle command class.
 */
@PluginImplementation
public class SocialCommand extends BaseCommand {
	
	//bundle name
	/** Bundle name. */
	public static final String BUNDLE = "social";
	/** Generation subject. */
	public static final String SUBJECT = "generate";

	//actions
	/** Social action. */
	public static final String ACTION_SOCIAL = "social";

	//commands
	/** Command : SOCIAL:GENERATE:SOCIAL.*/
	public static final String GENERATE_SOCIAL	=
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_SOCIAL;

	@Override
	public final void execute(final String action, 
			final String[] args,
			final String option) {
		ConsoleUtils.display("> Social Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		if (action.equals(GENERATE_SOCIAL)) {
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
		if (ApplicationMetadata.INSTANCE.entities != null) {
			try {
				new SocialGenerator(new AndroidAdapter()).generateMenu();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	@Override
	public final void generateMetas() {
		this.registerParser(new SocialParser());
		super.generateMetas();
	}
	
	

	@Override
	public final void summary() {
		ConsoleUtils.display("\n> Social \n" 
				+ "\t" + GENERATE_SOCIAL + "\t => Generate Social");
		
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return command.equals(GENERATE_SOCIAL);
	}

}
