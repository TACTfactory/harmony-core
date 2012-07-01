/**
 * This file is part of the Symfodroid package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.command.Command;

public class Console {
	public static final boolean DEBUG = true;
	public static Console instance;
	public static String pathProject;
	public static String projectFolder;
	
	protected HashMap<Class<?>, Command> bootstrap = new HashMap<Class<?>, Command>();
	protected ArrayList<CompilationUnit> entities = new ArrayList<CompilationUnit>();

	public Console() throws Exception {
		this.initialize();
		
		// Default Commands
		this.bootstrap.put(GeneralCommand.class, 	new GeneralCommand() );
		this.bootstrap.put(OrmCommand.class, 		new OrmCommand() );
		this.bootstrap.put(RouterCommand.class, 	new RouterCommand() );
		this.bootstrap.put(FosCommand.class, 		new FosCommand() );
	}

	/** Initialize Console 
	 * @throws Exception */
	private void initialize() throws Exception {
		instance = this;
		
		if (projectFolder == null || projectFolder.equals("")) {
			System.out.print("Not project folder define"); 
			throw new Exception("Not project folder define");
		}
		
		try {	
			pathProject = new java.io.File("..").getCanonicalPath().concat("/" + projectFolder + "/") ;
			
			// Debug Log
			if (com.tactfactory.mda.command.Console.DEBUG)
				System.out.print("Current Path : " + pathProject + "\n\n"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Command getCommand(Class<?> commandName) {
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
		for (Command command : this.bootstrap.values()) {
			if (command.isAvailableCommand(action)) {
				command.execute(action, entities);
				isfindAction = true;
			}
		}
		
		// No found action
		if (!isfindAction) {
			this.getCommand(GeneralCommand.class).execute(GeneralCommand.LIST, null);
		}
	}

	
}