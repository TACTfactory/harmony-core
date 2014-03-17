/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.util.LinkedHashMap;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.plateforme.AndroidAdapter;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.BundleGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;

import net.xeoh.plugins.base.annotations.PluginImplementation;

/**
 * Bundle Code Generator.
 */
@PluginImplementation
public class BundleCommand extends BaseCommand {

	/** Bundle name. */
	public static final String BUNDLE = "bundle";
	/** Subject. */
	public static final String SUBJECT = "generate";

	/** Action entity. */
	public static final String ACTION_EMPTY_BUNDLE = "emptybundle";

	//commands
	/** Command : BUNDLE:GENERATE:EMPTYBUNDLE. */
	public static final String GENERATE_EMPTY_BUNDLE =
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_EMPTY_BUNDLE;

	/** Adapter. */
	private BaseAdapter adapter = new AndroidAdapter();


	@Override
	public final void execute(final String action,
			final String[] args,
			final String option) {
		ConsoleUtils.display("> Bundle generator ");

		this.setCommandArgs(Console.parseCommandArgs(args));

		if (GENERATE_EMPTY_BUNDLE.equals(action)) {
			this.generateEmptyBundle();
		}
	}


	/**
	 * Generate an empty bundle.
	 */
	private void generateEmptyBundle() {
		//this.generateMetas();
		// Confirmation
		if (ConsoleUtils.isConsole()) {
			final String bundleOwnerName =
					ConsoleUtils.getUserInput("Bundle's owner ?");

			final String bundleName =
					ConsoleUtils.getUserInput("Name of your Bundle ?");

			final String bundleNameSpace =
					ConsoleUtils.getUserInput("Namespace of your Bundle ?");

			try {
				new BundleGenerator(this.adapter).generateBundleFiles(
						bundleOwnerName,
						bundleName,
						bundleNameSpace);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		commands.put(GENERATE_EMPTY_BUNDLE, "Generate Empty Bundle");
		
		ConsoleUtils.displaySummary(
				BUNDLE,
				commands);
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return GENERATE_EMPTY_BUNDLE.equals(command);
	}

}
