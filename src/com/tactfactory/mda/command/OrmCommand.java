/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.*;
import com.tactfactory.mda.template.*;

/**
 * Project Custom Files Code Generator
 * 
 * use Entity files in name space folder
 * it can :
 * <ul>
 * <li>Generate entities</li>
 * <li>Generate form</li>
 * <li>Generate CRUD functions</li>
 * </ul>
 *
 */
@PluginImplementation
public class OrmCommand extends BaseCommand {

	//bundle name
	public final static String BUNDLE = "orm";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_ENTITY = "entity";
	public final static String ACTION_ENTITIES = "entities";
	public final static String ACTION_FORM = "form";
	public final static String ACTION_CRUD = "crud";

	//commands
	public static String GENERATE_ENTITY 	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITY;
	public static String GENERATE_ENTITIES	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITIES;
	public static String GENERATE_FORM 		= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_FORM;
	public static String GENERATE_CRUD 		= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_CRUD;

	//internal
	protected BaseAdapter adapter = new AndroidAdapter();

	protected void generateForm() {
		/*ApplicationMetadata appMetas = this.getMetasFromAll();
		if(appMetas!=null){
			this.generateActivitiesForEntities(appMetas, true);
		}*/
	}

	/**
	 * Generate java code files from first parsed Entity
	 */
	protected void generateEntity() {
		/*ApplicationMetadata appMetas = this.getMetasFromArg();
		
		if(appMetas!=null){
			//this.generateActivitiesForEntities(appMetas, false);
			this.makeLayoutDatabase(appMetas);
			this.makeLayoutTestDatabase(appMetas);
		}*/
	
	}

	/**
	 * Generate java code files from parsed Entities
	 */
	protected void generateEntities() {
		this.generateMetas();
		
		if(Harmony.metas.entities!=null){
			this.makeLayoutDatabase();
			this.makeLayoutTestDatabase();
		}

	}

	/**
	 * Generate Create,Read,Upload,Delete code functions
	 */
	protected void generateCrud() {
		this.generateMetas();
		if(Harmony.metas.entities != null){
			this.makeLayoutUi(true);
		}
	}
	
	/**
	 * Generate the Persistence part for the given classes
	 */
	protected void makeLayoutDatabase(){
		try {
			new EntityGenerator(this.adapter).generateAll();
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteAdapterGenerator(this.adapter).generateAll();
			new SQLiteGenerator(this.adapter).generateDatabase();
			new ProviderGenerator(this.adapter).generateProvider();
			
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	/**
	 * Generate Test DB for Entities
	 */
	protected void makeLayoutTestDatabase(){
		try {
			new TestDBGenerator(this.adapter).generateAll();
			
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	/**
	 * Generate the GUI part for the given classes
	 */
	protected void makeLayoutUi(boolean generateHome){
		try {
			if(generateHome) {
				new ProjectGenerator(this.adapter).generateHomeActivity();
			}
				
			new ActivityGenerator(this.adapter).generateAll();
			
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	@Override
	public void summary() {
		ConsoleUtils.display("\n> ORM \n" +
				"\t" + GENERATE_ENTITY + "\t => Generate Entry\n" +
				"\t" + GENERATE_ENTITIES + "\t => Generate Entries\n" +
				"\t" + GENERATE_FORM + "\t => Generate Form\n" +
				"\t" + GENERATE_CRUD + "\t => Generate CRUD");
	}

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> ORM Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		
		try {
			if (action.equals(GENERATE_ENTITY)) {
				this.generateEntity();
			} else
	
			if (action.equals(GENERATE_ENTITIES)) {
				this.generateEntities();
			} else
	
			if (action.equals(GENERATE_FORM)) {
				this.generateForm();
			} else
	
			if (action.equals(GENERATE_CRUD)) {
				this.generateCrud();
			} else {
	
			}
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ENTITY) ||
				command.equals(GENERATE_ENTITIES) ||
				command.equals(GENERATE_FORM) ||
				command.equals(GENERATE_CRUD) );
	}
}