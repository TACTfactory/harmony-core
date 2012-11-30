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

import java.io.File;
import java.util.ArrayList;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.JavaAdapter;
import com.tactfactory.mda.parser.JavaModelParser;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ActivityGenerator;
import com.tactfactory.mda.template.SQLiteAdapterGenerator;
import com.tactfactory.mda.template.ProjectGenerator;
import com.tactfactory.mda.template.ProviderGenerator;
import com.tactfactory.mda.template.SQLiteGenerator;
import com.tactfactory.mda.template.WebServiceGenerator;
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
		ArrayList<ClassMetadata> metas = getMetasFromAll();
		if(metas!=null){
			generateActivitiesForEntities(metas, true);
		}
	}

	/**
	 * Generate java code files from first parsed Entity
	 */
	protected void generateEntity() {
					
		ArrayList<ClassMetadata> metas = getMetasFromArg();
		
		if(metas!=null){
			generateActivitiesForEntities(metas, false);
			generateDBForEntities(metas);
		}
	
	}

	/**
	 * Generate java code files from parsed Entities
	 */
	protected void generateEntities() {

		ArrayList<ClassMetadata> metas = getMetasFromAll();
		if(metas!=null){
			generateActivitiesForEntities(metas, true);
			generateDBForEntities(metas);
		}

	}

	/**
	 * Generate Create,Read,Upload,Delete code functions
	 */
	protected void generateCrud() {

		ArrayList<ClassMetadata> metas = getMetasFromAll();
		if(metas!=null){
			generateDBForEntities(metas);
		}
	}
	
	protected void generateDBForEntities(ArrayList<ClassMetadata> metas){
		try {
			// Make Database from MetaData
			new SQLiteAdapterGenerator(metas, this.adapter).generateAll();
			new SQLiteGenerator(metas, this.adapter).generateDatabase();
			new ProviderGenerator();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected ArrayList<ClassMetadata> getMetasFromArg(){
		ArrayList<ClassMetadata> ret = null;
		if(this.commandArgs.size()!=0 && this.commandArgs.containsKey("filename")) {
			String entityPath = this.adapter.getSourcePath()
					+Harmony.projectNameSpace.replaceAll("\\.","/")
					+"/entity/" + this.commandArgs.get("filename");
			File f = new File(entityPath);
			if(f.exists() && !f.isDirectory()) {
				this.javaModelParser.loadEntity(entityPath);
				if(this.javaModelParser.getEntities().size()!=0) {
					JavaAdapter javaAdapter = new JavaAdapter();
					javaAdapter.parse(this.javaModelParser.getEntities().get(0));
					ret = javaAdapter.getMetas();
				}else{
					System.out.println("Given entity filename is not an entity!");
				}
			}else{
				System.out.println("Given entity file does not exist!");
			}
		}else{
			System.out.println("No given filename!");
		}
		return ret;
	}
	
	
	protected ArrayList<ClassMetadata> getMetasFromAll(){
		ArrayList<ClassMetadata> ret = null;
		// Info Log
		System.out.print(">> Analyse Models...\n");

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
			JavaAdapter javaAdapter = new JavaAdapter();
			for (CompilationUnit mclass : this.javaModelParser.getEntities()) {
				javaAdapter.parse(mclass);
			}
	
			System.out.print("\n");
	
			// Generate views from MetaData
			ret = javaAdapter.getMetas();			
		} else {
			System.out.println("No entities found in entity package!");
		}
		return ret;
	}
	
	protected void generateActivitiesForEntities(ArrayList<ClassMetadata> metas, boolean generateHome){
		try {
			// Make
			if(generateHome)
				new ProjectGenerator(metas, this.adapter).generateHomeActivity();
			new ActivityGenerator(metas, this.adapter).generateAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void summary() {
		System.out.print("\n> ORM Bundle\n");
		System.out.print("\t" + GENERATE_ENTITY + "\t => Generate Entry\n");
		System.out.print("\t" + GENERATE_ENTITIES + "\t => Generate Entries\n");
		System.out.print("\t" + GENERATE_FORM + "\t => Generate Form\n");
		System.out.print("\t" + GENERATE_CRUD + "\t => Generate CRUD\n");
	}

	@Override
	public void execute(String action, String[] args, String option) {
		System.out.print("> ORM Generator\n");

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
					} else

					{

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