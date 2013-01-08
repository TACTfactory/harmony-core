package com.tactfactory.mda.rest.command;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.BaseCommand;

public class RestCommand extends BaseCommand{
	
	//bundle name
	public final static String BUNDLE = "rest";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_ADAPTERS = "adapters";
	/*public final static String ACTION_ENTITIES = "entities";
	public final static String ACTION_FORM = "form";
	public final static String ACTION_CRUD = "crud";*/

	//commands
	//public static String GENERATE_ENTITY 	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITY;
	public static String GENERATE_ADAPTERS	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ADAPTERS;
	//public static String GENERATE_FORM 		= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_FORM;
	//public static String GENERATE_CRUD 		= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_CRUD;

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> ORM Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(GENERATE_ADAPTERS)) {
			try {
				this.generateAdapters();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Generate java code files from parsed Entities
	 */
	protected void generateAdapters() {
		if(Harmony.metas.entities!=null){
			
		}

	}


	@Override
	public void summary() {
		ConsoleUtils.display("\n> ORM \n" +
				"\t" + GENERATE_ADAPTERS + "\t => Generate Adapters");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ADAPTERS));
	}

}
