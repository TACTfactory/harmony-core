package com.tactfactory.mda.rest.command;

import japa.parser.ast.CompilationUnit;

import java.util.LinkedHashMap;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.orm.ClassCompletor;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.parser.JavaModelParser;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.rest.parser.RestCompletor;
import com.tactfactory.mda.rest.parser.RestParser;
import com.tactfactory.mda.rest.template.RestGenerator;

@PluginImplementation
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
		ConsoleUtils.display("> Adapters Generator");

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
		//Harmony.metas.entities = getMetasFromAll();
		generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new RestGenerator(new AndroidAdapter()).generateAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void generateMetas(){
		// Info Log
		ConsoleUtils.display(">> Analyse Models...");
		
		// Parse models and load entities into CompilationUnits
		try {
			JavaModelParser javaModelParser = new JavaModelParser();
			javaModelParser.registerParser(new RestParser());
			javaModelParser.loadEntities();

			// Convert CompilationUnits entities to ClassMetaData
			if (javaModelParser.getEntities().size() > 0) {
				for (CompilationUnit mclass : javaModelParser.getEntities()) {
					javaModelParser.parse(mclass);
				}
				
				// Generate views from MetaData
				if (javaModelParser.getMetas().size() > 0) {
					for (ClassMetadata meta : javaModelParser.getMetas()) {
						Harmony.metas.entities.put(meta.name, meta);
					}
					new ClassCompletor(Harmony.metas.entities).execute();
					new RestCompletor().generateApplicationRestMetadata(Harmony.metas);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the Metadatas of all the entities actually in the package entity
	 * @return The metadatas of the application
	 */
	/*public LinkedHashMap<String, ClassMetadata> getMetasFromAll(){
		LinkedHashMap<String, ClassMetadata> ret = null;
		// Info Log
		ConsoleUtils.display(">> Analyse Models...");

		// Parse models and load entities into CompilationUnits
		try {
			JavaModelParser javaModelParser = new JavaModelParser();
			javaModelParser.registerParser(new RestParser());
			javaModelParser.loadEntities();

			// Convert CompilationUnits entities to ClassMetaData
			if (javaModelParser.getEntities().size() > 0) {
				for (CompilationUnit mclass : javaModelParser.getEntities()) {
					javaModelParser.parse(mclass);
				}
		
				// Generate views from MetaData
				if (javaModelParser.getMetas().size() > 0) {
					ret = new LinkedHashMap<String, ClassMetadata>();
					
					for (ClassMetadata meta : javaModelParser.getMetas()) {
						ret.put(meta.name, meta);
					}
				}
			} else {
				ConsoleUtils.displayWarning("No entities found in entity package!");
			}
			
			new ClassCompletor(ret).execute();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}*/


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
