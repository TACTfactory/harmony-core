/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.AndroidAdapter;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.ActivityGenerator;
import com.tactfactory.harmony.template.ApplicationGenerator;
import com.tactfactory.harmony.template.EntityGenerator;
import com.tactfactory.harmony.template.EnumCompletor;
import com.tactfactory.harmony.template.ProjectGenerator;
import com.tactfactory.harmony.template.ProviderGenerator;
import com.tactfactory.harmony.template.SQLiteAdapterGenerator;
import com.tactfactory.harmony.template.SQLiteGenerator;
import com.tactfactory.harmony.template.TestDBGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Project Custom Files Code Generator.
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

	/** Bundle name. */	
	public static final String BUNDLE = "orm";
	/** Subject. */
	public static final String SUBJECT = "generate";

	/** Action entity. */
	public static final String ACTION_ENTITY = "entity";
	/** Action entities. */
	public static final String ACTION_ENTITIES = "entities";
	/** Action form. */
	public static final String ACTION_FORM = "form";
	/** Action crud. */
	public static final String ACTION_CRUD = "crud";

	//commands
	//public static String GENERATE_FORM 		= 
	//		BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_FORM;
	//public static String GENERATE_ENTITY 	=
	//		BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITY;
	
	/** Command : ORM:GENERATE:ENTITIES. */
	public static final String GENERATE_ENTITIES	= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITIES;

	/** Command : ORM:GENERATE:CRUD. */
	public static final String GENERATE_CRUD 		= 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_CRUD;

	/** Adapter. */
	private BaseAdapter adapter = new AndroidAdapter();


	/**
	 * Generate java code files from parsed Entities.
	 */
	protected final void generateEntities() {
		this.generateMetas();
		
		if (ApplicationMetadata.INSTANCE.getEntities() != null) {
			this.makeLayoutDatabase();
			this.makeLayoutTestDatabase();
		}

	}

	/**
	 * Generate Create,Read, Upload, Delete code functions.
	 */
	protected final void generateCrud() {
		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.getEntities() != null) {
			this.makeLayoutUi(true);
		}
	}
	
	/**
	 * Generate the Persistence part for the given classes.
	 */
	protected final void makeLayoutDatabase() {
		try {
			new EnumCompletor(this.adapter).generateAll();
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
	 * Generate Test DB for Entities.
	 */
	protected final void makeLayoutTestDatabase() {
		try {
			new TestDBGenerator(this.adapter).generateAll();
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate the GUI part for the given classes.
	 * @param generateHome True if you want the HomeActivity to be regenerated.
	 */
	protected final void makeLayoutUi(final boolean generateHome) {
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
	public final void summary() {
		ConsoleUtils.display("\n> ORM \n" 
				// + "\t" + GENERATE_ENTITY + "\t => Generate Entry\n" 
				+ "\t" + GENERATE_ENTITIES + "\t => Generate Entries\n" 
				// + "\t" + GENERATE_FORM + "\t => Generate Form\n" 
				+ "\t" + GENERATE_CRUD + "\t => Generate CRUD");
	}

	@Override
	public final void execute(final String action,
			final String[] args, 
			final String option) {
		ConsoleUtils.display("> ORM Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		
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
	public final boolean isAvailableCommand(final String command) {
		return  //|| command.equals(GENERATE_ENTITY)	
				command.equals(GENERATE_ENTITIES) 	
				//|| command.equals(GENERATE_FORM)		
				|| command.equals(GENERATE_CRUD);
	}
}
