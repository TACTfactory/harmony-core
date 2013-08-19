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

import com.google.common.base.Strings;
import com.tactfactory.harmony.utils.ConsoleUtils;

/** Harmony console class. */
public abstract class Console /*extends Harmony*/ {
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

	/** Possible options. */
	protected static enum Option {
		//name		Internal	Short	Help message
		/** Help command. */
		HELP		("help",	"h",	"Display this help message."),
		/** Quiet command. */
		QUIET		("quiet",	"q",	"Do not output any message."),
		/** Verbose command. */
		VERBOSE		("verbose",	"v",	"Increase verbosity of messages."),
		/** Show version Command. */
		VERSION		("version",	"V",	"Display this application version."),
		/** ANSI command. */
		ANSI		("ansi",	null,	"Force ANSI output."),
		/** No-ANSI command. */
		NO_ANSI		("no-ansi",	null,	"Disable ANSI output.");

		// "\t--no-interaction -n Do not ask any interactive question.\n" +
		// "\t--shell\t\t -s Launch the shell.\n" +
		// "\t--env\t\t -e The Environment name.\n" +
		// "\t--no-debug \t    Switches off debug mode.\n\n"

		/** Option full name. */
		private String fullName;

		/** Option short name. */
		private String shortName;

		/** Option description. */
		private String description;

		/**
		 * Constructor.
		 * @param fName The option full name
		 * @param sName The option short name
		 * @param desc The option description
		 */
		private Option(final String fName,
				final String sName,
				final String desc) {
			this.fullName = fName;
			this.shortName = sName;
			this.description = desc;
		}

		@Override
		public String toString() {
			final StringBuilder result = new StringBuilder();
			result.append('\t');
			result.append(ARGUMENT_PREFIX);
			result.append(this.fullName);

			result.append("\t\t");
			if (!Strings.isNullOrEmpty(this.shortName)) {
				result.append(ARGUMENT_PREFIX_SHORT);
				result.append(this.shortName);
			}

			result.append("\t\t");
			result.append(this.description);

			result.append('\n');
			return result.toString();
		}

		/**
		 * Check if given command exists.
		 * @param value The name of the command
		 * @return True if it exists
		 */
		public boolean equal(final String value) {
			return value.equals(this.shortName) || value.equals(this.fullName);
		}

		/**
		 * Get option from its full name.
		 * @param value The full name
		 * @return The option
		 */
		public static Option fromFullName(final String value) {
			Option ret = null;
			if (value != null) {
				for (final Option option : Option.values()) {
					if (value.equalsIgnoreCase(option.fullName)) {
						ret = option;
					}
				}
			}

			return ret;
		}

		/**
		 * Get option from its short name.
		 * @param value The short name
		 * @return The option
		 */
		public static Option fromShortName(final String value) {
			Option ret = null;
			if (value != null) {
				for (final Option option : Option.values()) {
					if (value.equalsIgnoreCase(option.shortName)) {
						ret = option;
					}
				}
			}

			return ret;
		}
	}

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
			ConsoleUtils.display("Usage:\n\t[options] command [arguments]\n");
			ConsoleUtils.display("\nOptions:\n"
					+ Option.HELP
					+ Option.QUIET
					+ Option.VERBOSE
					+ Option.VERSION
					+ Option.ANSI
					+ Option.NO_ANSI);

			ConsoleUtils.display("Tips : please use 'list' command to"
					 + " display available commands!\n");

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
					if (Option.QUIET.equal(key)) {
						ConsoleUtils.setQuiet(true);
					} else

					// Verbose mode
					if (Option.VERBOSE.equal(key)) {
						ConsoleUtils.setDebug(true);
					} else

					// Version mode
					if (Option.VERSION.equal(key)) {
						ConsoleUtils.displayLicence(HARMONY_VERSION);
					} else

					// ANSI mode (default)
					if (Option.ANSI.equal(key)) {
						ConsoleUtils.setAnsi(true);
					} else

					// NO_ANSI mode
					if (Option.NO_ANSI.equal(key)) {
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
				if (arg.startsWith(ARGUMENT_PREFIX)) {
					final String[] key = arg.split(ARGUMENT_AFFECT);

					if (key.length > 1) {
						commandArgs.put(key[0].substring(2), key[1]);
					}
				}
			}
		}
		return commandArgs;
	}
}
