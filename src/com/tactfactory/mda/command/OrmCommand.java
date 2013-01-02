/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.orm.*;
import com.tactfactory.mda.parser.*;
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
	protected JavaModelParser javaModelParser;

	protected void generateForm() {
		/*ArrayList<ClassMetadata> metas = this.getMetasFromAll();
		if(metas!=null){
			this.generateActivitiesForEntities(metas, true);
		}*/
	}

	/**
	 * Generate java code files from first parsed Entity
	 */
	protected void generateEntity() {
		/*ArrayList<ClassMetadata> metas = this.getMetasFromArg();
		
		if(metas!=null){
			//this.generateActivitiesForEntities(metas, false);
			this.makeLayoutDatabase(metas);
			this.makeLayoutTestDatabase(metas);
		}*/
	
	}

	/**
	 * Generate java code files from parsed Entities
	 */
	protected void generateEntities() {

		ArrayList<ClassMetadata> metas = this.getMetasFromAll();
		if(metas!=null){
			this.makeLayoutDatabase(metas);
			this.makeLayoutTestDatabase(metas);
		}

	}

	/**
	 * Generate Create,Read,Upload,Delete code functions
	 */
	protected void generateCrud() {

		ArrayList<ClassMetadata> metas = this.getMetasFromAll();
		if(metas!=null){
			this.makeLayoutUi(metas, true);
		}
	}
	
	/**
	 * Generate the Persistence part for the given classes
	 * @param metas The classes Metadata
	 */
	protected void makeLayoutDatabase(ArrayList<ClassMetadata> metas){
		try {
			new EntityGenerator(metas, this.adapter).generateAll();
			new SQLiteAdapterGenerator(metas, this.adapter).generateAll();
			new SQLiteGenerator(metas, this.adapter).generateDatabase();
			new ProviderGenerator(metas, this.adapter).generateProvider();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate Test DB for Entities
	 * @param metas The classes Metadata
	 */
	protected void makeLayoutTestDatabase(ArrayList<ClassMetadata> metas){
		try {
			// Make
			new TestGenerator(metas, this.adapter).generateAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate the GUI part for the given classes
	 * @param metas The classes Metadata
	 */
	protected void makeLayoutUi(ArrayList<ClassMetadata> metas, boolean generateHome){
		try {
			// Make
			if(generateHome)
				new ProjectGenerator(metas, this.adapter).generateHomeActivity();
			new ActivityGenerator(metas, this.adapter).generateAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * Gets the Metadatas of the class which name is given in argument
//	 * @return The metadata of the class given with the argument --filename
//	 */
//	private ArrayList<ClassMetadata> getMetasFromArg(){
//		ArrayList<ClassMetadata> ret = null;
//		if(this.commandArgs.size()!=0 && this.commandArgs.containsKey("filename")) {
//			String entityPath = this.adapter.getSourcePath()
//					+Harmony.projectNameSpace.replaceAll("\\.","/")
//					+"/entity/" + this.commandArgs.get("filename"); //TODO factoring !
//			File f = new File(entityPath);
//			if(f.exists() && !f.isDirectory()) {
//				this.javaModelParser.loadEntity(entityPath);
//				if(this.javaModelParser.getEntities().size()!=0) {
//					this.javaModelParser.parse(this.javaModelParser.getEntities().get(0));
//					ret = this.javaModelParser.getMetas();
//				}else{
//					ConsoleUtils.displayWarning("Given entity filename is not an entity!");
//				}
//			}else{
//				ConsoleUtils.displayWarning("Given entity file does not exist!");
//			}
//		}else{
//			ConsoleUtils.displayWarning("No given filename! Specify a filename with --filename=\"Entity.java\"");
//		}
//		return ret;
//	}
		
	/**
	 * Gets the Metadatas of all the entities actually in the package entity
	 * @return The metadatas of the different classes
	 */
	public ArrayList<ClassMetadata> getMetasFromAll(){
		ArrayList<ClassMetadata> ret = null;
		// Info Log
		ConsoleUtils.display(">> Analyse Models...");

		// Parse models and load entities into CompilationUnits
		try {
			this.javaModelParser = new JavaModelParser();
			this.javaModelParser.loadEntities();
		} catch(Exception e) {
			e.printStackTrace();
		}

		if(this.javaModelParser.getEntities().size()!=0)
		{
			// Convert CompilationUnits entities to ClassMetaData
			for (CompilationUnit mclass : this.javaModelParser.getEntities()) {
				this.javaModelParser.parse(mclass);
			}
	
			// Generate views from MetaData
			ret = this.javaModelParser.getMetas();			
		} else {
			ConsoleUtils.displayWarning("No entities found in entity package!");
		}
		new ClassCompletor(ret).execute();
		return ret;
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
		if (action.equals(GENERATE_ENTITY)) {
			try {
				this.generateEntity();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ENTITY) ||
				command.equals(GENERATE_ENTITIES) ||
				command.equals(GENERATE_FORM) ||
				command.equals(GENERATE_CRUD) );
	}
}