/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import java.io.File;
import java.io.FilenameFilter;

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
			System.out.print("\nUsage:\n\t[options] command [arguments]\n");
			System.out.print("\nOptions:\n" + 
					"\t--help\t\t -h Display this help message.\n" + 
					"\t--quiet\t\t -q Do not output any message.\n" + 
					"\t--verbose\t -v Increase verbosity of messages.\n" + 
					"\t--version\t -V Display this application version.\n" + 
					"\t--ansi\t\t    Force ANSI output.\n" + 
					"\t--no-ansi\t    Disable ANSI output.\n" + 
					"\t--no-interaction -n Do not ask any interactive question.\n" + 
					"\t--shell\t\t -s Launch the shell.\n" + 
					"\t--env\t\t -e The Environment name.\n" + 
					"\t--no-debug \t    Switches off debug mode.\n\n");

			//System.out.print("Usage : console testBundle:com/tactfactory/mdatest/android:User");
			System.out.println("Tips : please use 'list' command to display available commands!");
			throw new Exception("Usage Exception, please launch help !");
			
		} else {
			// Extract command
			String[] cmd = args[0].split(":");
			System.out.println(cmd);
			
			// Extract require command
			String bundle  = cmd[0];
			String subject = cmd[1];
			String action  = cmd[2];
			String platformTmp = "";
			
			// Extract optional command
			if (cmd.length > 3)
				platformTmp = cmd[3];
			TargetPlatform platform = TargetPlatform.parse(platformTmp);
			
			// Extract parameters
			String[] arg = args[1].split("\\");
			
			//projectFolder = argProject[0];
			String patchNameSpace = "app/android/" + args[1] + "/entity/";

			Harmony harmony = new Harmony();

			if (args.length == 3) {
				//harmony.parseJavaFile(patchNameSpace + args[2] + ".java");
			} else {
				FilenameFilter filter = new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				        return name.endsWith(".java");
				    }
				};
				
				File dir = new File(Console.pathProject + patchNameSpace);
				String[] files = dir.list(filter);
				
				for (String filename : files) {
					//harmony.parseJavaFile(patchNameSpace + filename);
				}
			}

			harmony.findAndExecute(String.format("%s:%s:%s", bundle, subject, action),null,null);
		}
	}
	
	/** Constructor */
	public Console() throws Exception {
		
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}
}
