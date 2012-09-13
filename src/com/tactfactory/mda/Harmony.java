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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Strings;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.command.FosCommand;
import com.tactfactory.mda.command.GeneralCommand;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.RouterCommand;

/** Harmony main class */
public class Harmony {
	/** Debug state*/
	public static final boolean DEBUG = true;
	/** Singleton of console */
	public static Harmony instance;
	/** Full path of project ex : /root/project/ */
	public static String pathProject;
	/** Project space */
	public static String projectFolder;
	
	public static String projectName;
	public static String projectNameSpace;
	
	
	public HashMap<Class<?>, BaseCommand> bootstrap = new HashMap<Class<?>, BaseCommand>();
	protected ArrayList<CompilationUnit> entities = new ArrayList<CompilationUnit>();

	public Harmony() throws Exception {
		this.initialize();
		
		// Default Commands
		this.bootstrap.put(GeneralCommand.class, 	new GeneralCommand() );
		this.bootstrap.put(OrmCommand.class, 		new OrmCommand() );
		this.bootstrap.put(RouterCommand.class, 	new RouterCommand() );
		this.bootstrap.put(FosCommand.class, 		new FosCommand() );
	}

	/** Initialize Harmony 
	 * @throws Exception */
	private void initialize() throws Exception {
		instance = this;
		
		if (Strings.isNullOrEmpty(projectFolder)) {
			System.out.print("Not project folder define"); 
			throw new Exception("Not project folder define");
		}
		
		try {	
			pathProject = new java.io.File("..").getCanonicalPath().concat("/" + projectFolder + "/") ;
			projectName = projectFolder.replaceAll("-", "").replaceAll("_", "");
			
			// Debug Log
			if (Harmony.DEBUG)
				System.out.print("Current Path : " + pathProject + "\n\n"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BaseCommand getCommand(Class<?> commandName) {
		return this.bootstrap.get(commandName);
	}

	/**
	 * @return the entities
	 */
	public ArrayList<CompilationUnit> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(ArrayList<CompilationUnit> entities) {
		this.entities = entities;
	}

	public void findAndExecute(String action) {
		
		// Select Action and launch
		boolean isfindAction = false;
		for (BaseCommand baseCommand : this.bootstrap.values()) {
			if (baseCommand.isAvailableCommand(action)) {
				baseCommand.execute(action, entities);
				isfindAction = true;
			}
		}
		
		// No found action
		if (!isfindAction) {
			this.getCommand(GeneralCommand.class).execute(GeneralCommand.LIST, null);
		}
	}

	/** Load Entity */
	public void parseJavaFile(String filename) {
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
				if (Harmony.DEBUG)
					e.printStackTrace();
			}
        }
		
		//if (cu != null)
		//	this.entities.add(cu);
	}
}