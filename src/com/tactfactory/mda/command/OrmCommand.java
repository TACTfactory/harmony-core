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
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ActivityGenerator;
import com.tactfactory.mda.template.ApplicationGenerator;
import com.tactfactory.mda.template.EntityGenerator;
import com.tactfactory.mda.template.ProjectGenerator;
import com.tactfactory.mda.template.ProviderGenerator;
import com.tactfactory.mda.template.SQLiteAdapterGenerator;
import com.tactfactory.mda.template.SQLiteGenerator;
import com.tactfactory.mda.template.TestDBGenerator;
import com.tactfactory.mda.utils.ConsoleUtils;

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
	public static final String BUNDLE = "orm";
	public static final String SUBJECT = "generate";

	//actions
	public static final String ACTION_ENTITY = "entity";
	public static final String ACTION_ENTITIES = "entities";
	public static final String ACTION_FORM = "form";
	public static final String ACTION_CRUD = "crud";

	//commands
	//public static String GENERATE_ENTITY 	=
	//		BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITY;
	public static final String GENERATE_ENTITIES	= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITIES;
	//public static String GENERATE_FORM 		= 
	//		BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_FORM;
	public static final String GENERATE_CRUD 		= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_CRUD;

	//internal
	private BaseAdapter adapter = new AndroidAdapter();

	protected void generateForm() {
		/*ApplicationMetadata appMetas = this.getMetasFromAll();
		if (appMetas != null) {
			this.generateActivitiesForEntities(appMetas, true);
		}*/
	}

	/**
	 * Generate java code files from first parsed Entity
	 */
	protected void generateEntity() {
		/*ApplicationMetadata appMetas = this.getMetasFromArg();
		
		if (appMetas != null) {
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
		
		if (ApplicationMetadata.INSTANCE.entities != null) {
			this.makeLayoutDatabase();
			this.makeLayoutTestDatabase();
		}

	}

	/**
	 * Generate Create,Read, Upload, Delete code functions
	 */
	protected void generateCrud() {
		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.entities != null) {
			this.makeLayoutUi(true);
		}
	}
	
	/**
	 * Generate the Persistence part for the given classes
	 */
	protected void makeLayoutDatabase() {
		try {
			new EntityGenerator(this.adapter).generateAll();
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteAdapterGenerator(this.adapter).generateAll();
			new SQLiteGenerator(this.adapter).generateDatabase();
			new ProviderGenerator(this.adapter).generateProvider();
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate Test DB for Entities
	 */
	protected void makeLayoutTestDatabase() {
		try {
			new TestDBGenerator(this.adapter).generateAll();
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate the GUI part for the given classes
	 */
	protected void makeLayoutUi(final boolean generateHome) {
		try {
			if (generateHome) {
				new ProjectGenerator(this.adapter).generateHomeActivity();
			}
				
			new ActivityGenerator(this.adapter).generateAll();
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	@Override
	public void summary() {
		ConsoleUtils.display("\n> ORM \n" 
				// + "\t" + GENERATE_ENTITY + "\t => Generate Entry\n" 
				+ "\t" + GENERATE_ENTITIES + "\t => Generate Entries\n" 
				// + "\t" + GENERATE_FORM + "\t => Generate Form\n" 
				+ "\t" + GENERATE_CRUD + "\t => Generate CRUD");
	}

	@Override
	public void execute(final String action,
			final String[] args, 
			final String option) {
		ConsoleUtils.display("> ORM Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		
		try {
			/*if (action.equals(GENERATE_ENTITY)) {
				this.generateEntity();
			} else*/
	
			if (action.equals(GENERATE_ENTITIES)) {
				this.generateEntities();
			} else
	
			/*if (action.equals(GENERATE_FORM)) {
				this.generateForm();
			} else*/
	
			if (action.equals(GENERATE_CRUD)) {
				this.generateCrud();
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	@Override
	public boolean isAvailableCommand(final String command) {
		return  //|| command.equals(GENERATE_ENTITY)	
				command.equals(GENERATE_ENTITIES) 	
				//|| command.equals(GENERATE_FORM)		
				|| command.equals(GENERATE_CRUD);
	}
}
