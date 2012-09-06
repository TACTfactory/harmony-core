/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

/** Harmony console class*/
public class Console extends Harmony {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		//argProject = args[1].split(":");

		Console csl = new Console();
		
		if(args.length==0) {
			System.out.println("You need to pass commands to the tool!");
			System.out.println("Usage : java -jar harmony.jar [command]");
			System.out.println("ex : harmony.jar project:init:android\n");
			csl.findAndExecute("help");
		}
		else {
			//Execute command given in arguments
			csl.findAndExecute(args[0]);
		}
		// Need Project Name Space !!!
		//projectFolder = argProject[0];
		
//		String patchNameSpace = "src/" + argProject[1] + "/entity/";
//
//		if (argProject.length == 3) {
//			csl.parseJavaFile(patchNameSpace + argProject[2] + ".java");
//		}
//		else {
//			FilenameFilter filter = new FilenameFilter() {
//			    public boolean accept(File dir, String name) {
//			        return name.endsWith(".java");
//			    }
//			};
//			
//			File dir = new File(Console.pathProject + patchNameSpace);
//			String[] files = dir.list(filter);
//			
//			for (String filename : files) {
//				csl.parseJavaFile(patchNameSpace + filename);
//			}
//		}

	}

	/** Load Entity */
	private void parseJavaFile(String filename) {
        FileInputStream in = null;
        CompilationUnit cu = null;
        
		try {
			// creates an input stream for the file to be parsed
			in = new FileInputStream(Console.pathProject + filename);

            // parse the file
			cu = JavaParser.parse(in);
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				in.close();
			} catch (IOException e) {
				if (DEBUG)
					e.printStackTrace();
			}
        }
		
		if (cu != null)
			this.entities.add(cu);
	}
	
	/** Constructor */
	public Console() throws Exception {
		
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}
}
