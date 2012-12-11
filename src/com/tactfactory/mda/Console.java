/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Harmony console class*/
public class Console extends Harmony {
	
	/** Extract command line parameters for java core
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Harmony.isConsole = true;
		System.out.println(Harmony.VERSION);

		// Check if has a parameter
		if (args.length == 0) {
			// If no valid parameters
			ConsoleUtils.display("Usage:\n\t[options] command [arguments]\n");
			ConsoleUtils.display("\nOptions:\n" + 
					"\t--help\t\t -h Display this help message.\n" + 
					"\t--quiet\t\t -q Do not output any message.\n" + 
					"\t--verbose\t -v Increase verbosity of messages.\n" + 
					"\t--version\t -V Display this application version.\n" + 
					// "\t--ansi\t\t    Force ANSI output.\n" + 
					// "\t--no-ansi\t    Disable ANSI output.\n" + 
					// "\t--no-interaction -n Do not ask any interactive question.\n" + 
					// "\t--shell\t\t -s Launch the shell.\n" + 
					// "\t--env\t\t -e The Environment name.\n" + 
					"\t--no-debug \t    Switches off debug mode.\n\n");

			//System.out.print("Usage : console testBundle:com/tactfactory/mdatest/android:User");
			ConsoleUtils.display("Tips : please use 'list' command to display available commands!\n");
			throw new Exception("Usage Exception, please launch help !");
			
		} else {
			String command = null;
			String bundle = null;
			String subject = null;
			String action = null;
			String[] commandArgs = null;
			String commandOption = null;
			String platformTmp = "";
			//TargetPlatform platform = null;
			
			// Extract command
			String[] cmd = args[0].split(":");

			// Extract required command
			if(cmd.length==3) {
				bundle  = cmd[0];
				subject = cmd[1];
				action  = cmd[2];
				command = String.format("%s:%s:%s", bundle, subject, action);
			}
			// Extract optional command
			else if(cmd.length>3){
				platformTmp = cmd[3];
				//platform = TargetPlatform.parse(platformTmp);
			}
			else {
				command = args[0];
			}
			
			// Extract optional command arguments
			if(args.length>1){
				List<String> list = new ArrayList<String>(Arrays.asList(args));
				list.remove(0);
				commandArgs = list.toArray(new String[args.length-1]);	
			}
			
			// Harmony command launch
			Harmony harmony = new Harmony();
			harmony.initialize();
			harmony.findAndExecute(command,commandArgs,commandOption);
		}
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
				if(arg.startsWith("--")){
					String[] key = arg.split("=");
					if(key.length>1)
						commandArgs.put(key[0].substring(2),key[1]);
				}
			}
		}
		return commandArgs;
	}
}
