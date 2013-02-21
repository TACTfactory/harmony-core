/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import java.util.Arrays;
import java.util.HashMap;

import com.google.common.base.Strings;
import com.tactfactory.mda.utils.ConsoleUtils;

/** Harmony console class*/
public abstract class Console extends Harmony {
	private static final int REQUIRED_COMMANDS = 3;
	protected static final String HARMONY_VERSION = 
			"Harmony version " + Harmony.VERSION + "\n";
	protected static final String ARGUMENT_PREFIX = "--";
	protected static final String ARGUMENT_PREFIX_SHORT = "-";
	protected static final String ARGUMENT_AFFECT = "=";
	
	protected static enum Option {
		//name		Internal	Short	Help message
		HELP		("help",	"h",	"Display this help message."),
		QUIET		("quiet",	"q",	"Do not output any message."),
		VERBOSE		("verbose",	"v",	"Increase verbosity of messages."),
		VERSION		("version",	"V",	"Display this application version."),
		ANSI		("ansi",	null,	"Force ANSI output."),
		NO_ANSI		("no-ansi",	null,	"Disable ANSI output.");
		
		// "\t--no-interaction -n Do not ask any interactive question.\n" + 
		// "\t--shell\t\t -s Launch the shell.\n" + 
		// "\t--env\t\t -e The Environment name.\n" + 
		// "\t--no-debug \t    Switches off debug mode.\n\n"
		
		private String fullName;
		private String shortName;
		private String description;
		
		private Option(final String fullName, 
				final String shortName, 
				final String description) {
			this.fullName = fullName;
			this.shortName = shortName;
			this.description = description;
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
	
		public boolean equal(final String value) {
			return value.equals(this.shortName) || value.equals(this.fullName);
		}
		
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
	
	/** Extract command line parameters for java core
	 * @param args
	 * @throws Exception 
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
			final Harmony harmony = new Harmony();
			harmony.initialize();
			harmony.findAndExecute(command, commandArgs, commandOption);
		}
	}

	/**
	 * @param args
	 * @param commandArgs
	 * @return
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
	 * @param args
	 * @param command
	 * @param currentPosition
	 * @return
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
	 * @param args
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
	
	/** Constructor */
	public Console() throws Exception {
		super();
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}
	
	/**
	 * Extract commandArgs and put them in an HashMap
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
