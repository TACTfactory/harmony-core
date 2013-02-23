/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.fixture.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.bundles.fixture.metadata.FixtureMetadata;
import com.tactfactory.mda.bundles.fixture.template.FixtureGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * Fixture bundle command class.
 */
@PluginImplementation
public class FixtureCommand extends BaseCommand {
	//bundle name
	/** Bundle name. */
	public static final String BUNDLE = "orm";
	/** Fixture subject. */
	public static final String SUBJECT = "fixture";

	//actions
	/** Init action. */
	public static final String ACTION_INIT = "init";
	/** Load action. */
	public static final String ACTION_LOAD = "load";
	/** Purge action. */
	public static final String ACTION_PURGE = "purge";
	/** Update action. */
	public static final String ACTION_UPDATE = "update";

	//commands
	/** Command: ORM:FIXTURE:INIT. */
	public static final String FIXTURE_INIT	= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_INIT;
	/** Command: ORM:FIXTURE:LOAD. */
	public static final String FIXTURE_LOAD	= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_LOAD;
	/** Command: ORM:FIXTURE:PURGE. */
	public static final String FIXTURE_PURGE	=
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_PURGE;
	/** Command: ORM:FIXTURE:UPDATE. */
	public static final String FIXTURE_UPDATE = 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_UPDATE;
	
	@Override
	public final void execute(final String action, 
			final String[] args, 
			final String option) {
		ConsoleUtils.display("> Fixture Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		if (action.equals(FIXTURE_INIT)) {
			this.init();
		} else if (action.equals(FIXTURE_LOAD)) {
			this.load();
		} else if (action.equals(FIXTURE_PURGE)) {
			this.purge();
		} else if (action.equals(FIXTURE_UPDATE)) {
			this.update();
		}
		
	}
	
	/**
	 * Init command.
	 */
	public final void init() {
		try {
			this.generateMetas();
			final FixtureMetadata fixtureMeta = new FixtureMetadata();
			//TODO : get type by user input
			fixtureMeta.type = "yml";
			if (this.getCommandArgs().containsKey("format")) {
				final String format = this.getCommandArgs().get("format");
				if (format.equals("xml") || format.equals("yml")) {
					fixtureMeta.type = format; 
				}
			}
			ApplicationMetadata.INSTANCE.options.put(
					fixtureMeta.getName(), fixtureMeta);
			new FixtureGenerator(new AndroidAdapter()).init();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Load command.
	 */
	public final void load() {
		try {
			new FixtureGenerator(new AndroidAdapter()).load();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Purge command.
	 */
	public final void purge() {
		try {
			this.generateMetas();
			new FixtureGenerator(new AndroidAdapter()).purge();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Update command.
	 */
	public final void update() {
		
	}

	@Override
	public final void summary() {
		ConsoleUtils.display("\n> FIXTURE \n" 
				+ "\t" + FIXTURE_INIT 
				+ "\t => Initialize fixtures, create loaders\n" 
				
				+ "\t" + FIXTURE_LOAD 
				+ "\t => Load fixtures into the projects (overwrite)\n"
				
				+ "\t" + FIXTURE_PURGE 
				+ "\t => Clear fixtures on the projects\n" 
				
				+ "\t" + FIXTURE_UPDATE 
				+ "\t => Update the fixtures in the project");
		
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return 	command.equals(FIXTURE_INIT) 
				|| command.equals(FIXTURE_LOAD)
				|| command.equals(FIXTURE_PURGE)
				|| command.equals(FIXTURE_UPDATE);
		
	}

}
