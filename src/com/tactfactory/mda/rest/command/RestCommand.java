package com.tactfactory.mda.rest.command;

import japa.parser.ast.CompilationUnit;

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

	//commands
	public static String GENERATE_ADAPTERS	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ADAPTERS;

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
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> REST \n" +
				"\t" + GENERATE_ADAPTERS + "\t => Generate Adapters");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ADAPTERS));
	}

}
