/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.fixture.command;

import java.util.LinkedHashMap;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.command.BaseCommand;
import com.tactfactory.harmony.fixture.metadata.FixtureMetadata;
import com.tactfactory.harmony.fixture.template.FixtureGenerator;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

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
			fixtureMeta.setType("yml");
			if (this.getCommandArgs().containsKey("format")) {
				final String format = this.getCommandArgs().get("format");
				if (format.equals("xml") || format.equals("yml")) {
					fixtureMeta.setType(format);
				}
			}
			boolean force = false;
			if (this.getCommandArgs().containsKey("force")) {
				final String bool = this.getCommandArgs().get("force");
				if (bool.equals("true")) {
					force = true;
				}
			}
			ApplicationMetadata.INSTANCE.getOptions().put(
					fixtureMeta.getName(), fixtureMeta);

			new FixtureGenerator(new AndroidAdapter()).init(force);
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
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		commands.put(FIXTURE_INIT,	"Initialize fixtures, create loaders :\n" 
				+ "\t\t use --format=(xml|yml)"
				+ " to specify a format (default : yml)\n"
				+ "\t\t use --force=(true|false)"
				+ " to overwrite existing fixture loaders (default : false)");
		commands.put(FIXTURE_LOAD,	"Load fixtures into the projects (overwrite)");
		commands.put(FIXTURE_PURGE,		"Clear fixtures on the projects");
		commands.put(FIXTURE_UPDATE,	"Update the fixtures in the project");
		
		ConsoleUtils.displaySummary(
				SUBJECT,
				commands);
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return 	command.equals(FIXTURE_INIT)
				|| command.equals(FIXTURE_LOAD)
				|| command.equals(FIXTURE_PURGE)
				|| command.equals(FIXTURE_UPDATE);

	}

}
