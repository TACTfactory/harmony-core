/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import java.util.Arrays;
import java.util.HashMap;

import com.tactfactory.harmony.utils.ConsoleUtils;

/** 
 * Harmony console class. <br/>
 * This class is use for transforming the parameters from command line to Harmony.<br/>
 * 3 types of parameters is available :
 * <ol><li>Harmony Options @see ConsoleOption </li>
 * <li>Command</li>
 * <li>Command options (multi separate by space char)</li></ol>
 * <br/><br>
 * eg. -V list
 * <ol><li>Harmony option : -V (Version display)</li>
 * <li>Command : list (show all available commands)</li></ol>
 */
public abstract class Console {

	/** NB Required commands constant. */
	private static final int REQUIRED_COMMANDS = 3;

	/** Harmony version. */
	protected static final String HARMONY_VERSION =
			"Harmony version " + Harmony.VERSION + "\n";

	/** Argument prefix. */
	protected static final String ARGUMENT_PREFIX = "--";

	/** Short argument prefix. */
	protected static final String ARGUMENT_PREFIX_SHORT = "-";

	/** Argument affectation. */
	protected static final String ARGUMENT_AFFECT = "=";

	/** Extract command line parameters for java core.
	 * @param args The arguments
	 * @throws Exception if current working path is unavailable.
	 */
	public static void main(final String[] args) throws Exception {
		ConsoleUtils.setConsole(true);

		// Check if has a parameter
		if (args.length == 0) {
			ConsoleUtils.displayLicence(HARMONY_VERSION);

			// If no valid parameters
			ConsoleUtils.display(
							"Usage:\n\t[options] command [arguments]\n");
			
			ConsoleUtils.display("\nOptions:\n"
					+ ConsoleOption.HELP
					+ ConsoleOption.QUIET
					+ ConsoleOption.VERBOSE
					+ ConsoleOption.VERSION
					+ ConsoleOption.ANSI
					+ ConsoleOption.NO_ANSI);
			
			ConsoleUtils.display("\nCommand:\n\tUse 'list' command to"
					 + " display available commands!\n");
			
			ConsoleUtils.display("\nArguments:\n\tUse '--help <command>' to"
					 + " display available arguments of command!\n");
			
		} else {
			final String commandOption = null;

			// Extract Argument
			final int currentPosition 	=
					Console.extractOptions(args);
			final String command 		=
					Console.extractCommand(args, currentPosition);
			final String[] commandArgs 	=
					Console.extractCommandArgument(args, currentPosition + 1);


			// Harmony command launch
			final Harmony harmony = Harmony.getInstance();
			harmony.initialize();
			harmony.findAndExecute(command, commandArgs, commandOption);
		}
	}

	/** Extract command line parameter at given position for java core.
	 * @param args The arguments
	 * @param currentPosition The argument position
	 * @return the argument
	 */
	private static String[] extractCommandArgument(final String[] args,
				final int currentPosition) {
		String[] commandArgs = null;

		// Extract optional command arguments
		if (args.length > currentPosition) {
			commandArgs = Arrays.copyOfRange(args,
					currentPosition,
					args.length);
		}

		return commandArgs;
	}

	/**
	 * Extract the command from the given args.
	 * @param args The arguments
	 * @param currentPosition The current position
	 * @return The command
	 */
	private static String extractCommand(final String[] args,
			final int currentPosition) {
		String command = args[currentPosition];

		// Extract command
		final String[] splitCommand = args[currentPosition].split(":");

		// Extract required command
		if (splitCommand.length == REQUIRED_COMMANDS) {
			//String.format("%s:%s:%s", bundle, subject, action);
			command = args[currentPosition];
		}
		return command;
	}

	/**
	 * Extract the option from the given arguments.
	 * @param args The arguments
	 * @return The new current position
	 */
	private static int extractOptions(final String[] args) {
		int currentPosition = 0;

		if (args[0].startsWith(ARGUMENT_PREFIX_SHORT)) {
			for (int i = 0; i < args.length; i++) {
				final String arg = args[i];

				if (arg.startsWith(ARGUMENT_PREFIX_SHORT)) {
					String key = arg.substring(1);
					if (arg.startsWith(ARGUMENT_PREFIX)) {
						key = arg.substring(2);
					}

					// Quiet mode
					if (ConsoleOption.QUIET.equal(key)) {
						ConsoleUtils.setQuiet(true);
					} else

					// Verbose mode
					if (ConsoleOption.VERBOSE.equal(key)) {
						ConsoleUtils.setDebug(true);
					} else

					// Version mode
					if (ConsoleOption.VERSION.equal(key)) {
						ConsoleUtils.displayLicence(HARMONY_VERSION);
					} else

					// ANSI mode (default)
					if (ConsoleOption.ANSI.equal(key)) {
						ConsoleUtils.setAnsi(true);
					} else

					// NO_ANSI mode
					if (ConsoleOption.NO_ANSI.equal(key)) {
						ConsoleUtils.setAnsi(false);
					}
				} else {
					currentPosition = i;
					break;
				}
			}
		} else {
			ConsoleUtils.displayDebug("No option found !");
		}

		return currentPosition;
	}

	/** Constructor.
	 * @throws Exception Bootstrap failure
	 */
	public Console() throws Exception {
		super();
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}

	/**
	 * Extract commandArgs and put them in an HashMap.
	 *
	 * @param args String array of command arguments with their identifier
	 * @return HashMap<String, String> arguments
	 */
	public static HashMap<String, String> parseCommandArgs(
			final String[] args) {

		final HashMap<String, String> commandArgs =
				new HashMap<String, String>();
		if (args != null && args.length != 0) {
			for (final String arg : args) {
				if (arg.startsWith(ARGUMENT_PREFIX_SHORT)) {
					final String[] key = arg.split(ARGUMENT_AFFECT);
					int prefixSize = 0;
					if (arg.startsWith(ARGUMENT_PREFIX)) {
						prefixSize = 2;
					} else {
						prefixSize = 1;
					}

					if (key.length > 1) {
						commandArgs.put(key[0].substring(prefixSize), key[1]);
					} else {
						commandArgs.put(key[0].substring(prefixSize), "");
					}
				}
			}
		}
		return commandArgs;
	}
}
