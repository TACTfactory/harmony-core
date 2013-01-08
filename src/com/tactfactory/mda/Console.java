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

/** Harmony console class*/
public class Console extends Harmony {
	protected final static String HARMONY_VERSION = "Harmony version " + Harmony.VERSION + "\n";
	protected final static String ARGUMENT_PREFIX = "--";
	protected final static String ARGUMENT_PREFIX_SHORT = "-";
	protected final static String ARGUMENT_AFFECT = "=";
	
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
		
		private Option(String fullName, String shortName, String description) {
			this.fullName = fullName;
			this.shortName = shortName;
			this.description = description;
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("\t" + ARGUMENT_PREFIX + this.fullName);
			
			if (!Strings.isNullOrEmpty(this.shortName)) {
				result.append("\t\t" + ARGUMENT_PREFIX_SHORT + this.shortName);
			} else {
				result.append("\t\t\t\t");
			}
			
			result.append(this.description);
			
			result.append("\n");
			return result.toString();
		}
	
		public boolean equal(String value) {
			return (value.equals(this.shortName) || value.equals(this.fullName));
		}
		
		public static Option fromFullName(String value){
			if (value!= null) {
				for (Option option : Option.values()) {
					if (value.equalsIgnoreCase(option.fullName)) {
						return option;
					}    
				}
			}
			
			return null;
		}
		
		public static Option fromShortName(String value){
			if (value!= null) {
				for (Option option : Option.values()) {
					if (value.equalsIgnoreCase(option.shortName)) {
						return option;
					}    
				}
			}
			
			return null;
		}
	}
	
	/** Extract command line parameters for java core
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Harmony.isConsole = true;

		// Check if has a parameter
		if (args.length == 0) {
			ConsoleUtils.displayLicence(HARMONY_VERSION);
			
			// If no valid parameters
			ConsoleUtils.display("Usage:\n\t[options] command [arguments]\n");
			ConsoleUtils.display("\nOptions:\n" + 
					Option.HELP + 
					Option.QUIET + 
					Option.VERBOSE + 
					Option.VERSION +
					Option.ANSI + 
					Option.NO_ANSI);

			//ConsoleUtils.display("Usage : console testBundle:com/tactfactory/mdatest/android:User");
			ConsoleUtils.display("Tips : please use 'list' command to display available commands!\n");
			//throw new Exception("Usage Exception, please launch help !");
			
		} else {
			String commandOption = null;
			
			// Extract Argument
			int currentPosition 	= Console.extractOptions(args);
			String command 			= Console.extractCommand(args, currentPosition);
			String[] commandArgs 	= Console.extractCommandArgument(args, currentPosition+1);

			
			// Harmony command launch
			Harmony harmony = new Harmony();
			harmony.initialize();
			harmony.findAndExecute(command, commandArgs, commandOption);
		}
	}

	/**
	 * @param args
	 * @param commandArgs
	 * @return
	 */
	private static String[] extractCommandArgument(String[] args, int currentPosition) {
		String[] commandArgs = null;
		
		// Extract optional command arguments
		if(args.length > currentPosition){
			commandArgs = Arrays.copyOfRange(args, currentPosition, args.length);
		}
		
		return commandArgs;
	}

	/**
	 * @param args
	 * @param command
	 * @param currentPosition
	 * @return
	 */
	private static String extractCommand(String[] args, int currentPosition) {
		String command = args[currentPosition];
		
		// Extract command
		String[] splitCommand = args[currentPosition].split(":");

		// Extract required command
		if(splitCommand.length == 3) {
			command = args[currentPosition]; //String.format("%s:%s:%s", bundle, subject, action);
		} else
			
		// Extract optional command
		if(splitCommand.length > 3){
			//platformTmp = cmd[3];
			//platform = TargetPlatform.parse(platformTmp);
		} 
		
		return command;
	}

	/**
	 * @param args
	 */
	private static int extractOptions(String[] args) {
		int currentPosition = 0;
		
		if (args[0].startsWith(ARGUMENT_PREFIX_SHORT)) {
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				
				if(arg.startsWith(ARGUMENT_PREFIX_SHORT)) {
					String key = arg.substring(1);
					if (arg.startsWith(ARGUMENT_PREFIX)) {
						key = arg.substring(2);
					}
					
					// Help call
					if (Option.HELP.equal(key)) {
						
					} else
					
					// Quiet mode
					if (Option.QUIET.equal(key)) {								
						ConsoleUtils.quiet = true;
					} else
					
					// Verbose mode
					if (Option.VERBOSE.equal(key)) {
						Harmony.debug = true;
					} else
					
					// Version mode
					if (Option.VERSION.equal(key)) {
						ConsoleUtils.displayLicence(HARMONY_VERSION);
					} else
						
					// ANSI mode (default)
					if (Option.ANSI.equal(key)) {
						ConsoleUtils.ansi = true;
					} else
						
					// NO_ANSI mode
					if (Option.NO_ANSI.equal(key)) {
						ConsoleUtils.ansi = false;
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
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}
	
	/**
	 * Extract commandArgs and put them in an HashMap
	 * 
	 * @param args String array of command arguments with their identifier
	 * @return HashMap<String,String> arguments
	 */
	public static HashMap<String,String> parseCommandArgs(String[] args){
		
		HashMap<String,String> commandArgs = new HashMap<String,String>();
		if (args!=null && args.length!=0){
			for (String arg : args){
				if (arg.startsWith(ARGUMENT_PREFIX)){
					String[] key = arg.split(ARGUMENT_AFFECT);
					
					if (key.length > 1)
						commandArgs.put(key[0].substring(2),key[1]);
				}
			}
		}
		return commandArgs;
	}
}
