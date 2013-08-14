package com.tactfactory.harmony.command;

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
	public void execute(String action, String[] args, String option) {
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
		this.generateMetas();
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
	public void summary() {
		ConsoleUtils.display(
				"\n> BUNDLE \n"
				+ "\t" + GENERATE_EMPTY_BUNDLE + "\t => Generate Empty Bundle\n"
				);
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return GENERATE_EMPTY_BUNDLE.equals(command);
	}

}
