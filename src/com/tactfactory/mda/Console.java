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

/** Harmony console class*/
public class Console extends Harmony {
	protected final static String ARGUMENT_PREFIX = "--";
	protected final static String ARGUMENT_PREFIX_SHORT = "-";
	protected final static String ARGUMENT_AFFECT = "=";
	
	
	/** Extract command line parameters for java core
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Harmony.isConsole = true;

		// Check if has a parameter
		if (args.length == 0) {
			ConsoleUtils.displayLicence("Harmony version " + Harmony.VERSION);
			
			// If no valid parameters
			ConsoleUtils.display("Usage:\n\t[options] command [arguments]\n");
			ConsoleUtils.display("\nOptions:\n" + 
					"\t--help\t\t -h Display this help message.\n" + 
					"\t--quiet\t\t -q Do not output any message.\n" + 
					"\t--verbose\t -v Increase verbosity of messages.\n" + 
					"\t--version\t -V Display this application version.\n" + 
					"\t--ansi\t\t    Force ANSI output.\n" + 
					"\t--no-ansi\t    Disable ANSI output.\n"  
					// "\t--no-interaction -n Do not ask any interactive question.\n" + 
					// "\t--shell\t\t -s Launch the shell.\n" + 
					// "\t--env\t\t -e The Environment name.\n" + 
					// "\t--no-debug \t    Switches off debug mode.\n\n"
					);

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
			/*
			String bundle = null;
			String subject = null;
			String action = null; 
			bundle  = cmd[0];
			subject = cmd[1];
			action  = cmd[2];*/
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
				
				if(arg.startsWith(ARGUMENT_PREFIX_SHORT)){
					String key = arg.substring(1);
					if (arg.startsWith(ARGUMENT_PREFIX))
						key = arg.substring(2);
					
					// Help call
					if (key.equals("h") || key.equals("help")) {
						
					} else
					
					// Quiet mode
					if (key.equals("q") || key.equals("quiet")) {								
						ConsoleUtils.quiet = true;
					} else
					
					// Verbose mode
					if (key.equals("v") || key.equals("verbose")) {
						Harmony.debug = true;
					} else
					
					// Version mode
					if (key.equals("V") || key.equals("version")) {
						ConsoleUtils.displayLicence("Harmony version " + Harmony.VERSION);
					} else
						
					// ANSI mode (default)
					if (key.equals("ansi")) {
						ConsoleUtils.ansi = true;
					} else
						
					// NO_ANSI mode
					if (key.equals("no-ansi")) {
						ConsoleUtils.ansi = false;
					}
				} else {
					currentPosition = i;
					break;
				}		
			}
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
		if(args!=null && args.length!=0){
			for(String arg : args){
				if(arg.startsWith(ARGUMENT_PREFIX)){
					String[] key = arg.split(ARGUMENT_AFFECT);
					
					if (key.length > 1)
						commandArgs.put(key[0].substring(2),key[1]);
				}
			}
		}
		return commandArgs;
	}
}
