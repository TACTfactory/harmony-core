package com.tactfactory.mda.symfony.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.rest.parser.RestCompletor;
import com.tactfactory.mda.rest.parser.RestParser;
import com.tactfactory.mda.symfony.template.WebGenerator;

@PluginImplementation
public class SymfonyCommand extends BaseCommand{
	
	//bundle name
	public final static String BUNDLE = "web";
	public final static String SUBJECT_SYMFONY = "symfony";
	public final static String SUBJECT_BUNDLES = "bundles";
	public final static String SUBJECT_PROJECT = "project";
	public final static String SUBJECT_GENERATE = "generate";

	//actions
	public final static String ACTION_INSTALL = "install";
	public final static String ACTION_INIT = "init";
	public final static String ACTION_ENTITIES = "entities";
	public final static String ACTION_REPOSITORIES = "repositories";

	//commands
	public static String PROJECT_INIT = BUNDLE + SEPARATOR + SUBJECT_PROJECT + SEPARATOR + ACTION_INIT;
	public static String GENERATE_ENTITIES = BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_ENTITIES;
	public static String GENERATE_REPOSITORIES = BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_REPOSITORIES;
	public static String INSTALL_SYMFONY = BUNDLE + SEPARATOR + SUBJECT_SYMFONY + SEPARATOR + ACTION_INSTALL;
	public static String INSTALL_BUNDLES = BUNDLE + SEPARATOR + SUBJECT_BUNDLES + SEPARATOR + ACTION_INSTALL;

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> Adapters Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(GENERATE_ENTITIES)){
			this.generateEntities();
		}else if (action.equals(GENERATE_REPOSITORIES)){
			this.generateWebRepositories();
		}else if (action.equals(PROJECT_INIT)){
			this.initProject();
		}else if (action.equals(INSTALL_BUNDLES)){
			this.installBundles();
		}else if (action.equals(INSTALL_SYMFONY)){
			this.installSymfony();
		}
	}
	
	/**
	 * Generate java code files from parsed Entities
	 */
	protected void initProject() {

		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new WebGenerator(new AndroidAdapter()).initProject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Generate yaml entities for symfony
	 */
	protected void generateEntities() {

		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new WebGenerator(new AndroidAdapter()).generateEntities();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Generate yaml entities for symfony
	 */
	protected void generateWebRepositories() {

		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new WebGenerator(new AndroidAdapter()).generateWebControllers();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Generate yaml entities for symfony
	 */
	protected void installSymfony() {
		try {
			new WebGenerator(new AndroidAdapter()).installSymfony();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate yaml entities for symfony
	 */
	protected void installBundles() {
		try {
			new WebGenerator(new AndroidAdapter()).installBundles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void generateMetas(){
		this.registerParser(new RestParser());
		super.generateMetas();
		new RestCompletor().generateApplicationRestMetadata(Harmony.metas);
	}
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> REST \n" +
				"\t" + PROJECT_INIT + "\t => Init symfony project\n"+
				"\t" + GENERATE_ENTITIES + "\t => Generate Web Entities\n"+
				"\t" + GENERATE_REPOSITORIES + "\t => Generate Web Repositories");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(PROJECT_INIT)||
				command.equals(INSTALL_BUNDLES)||
				command.equals(INSTALL_SYMFONY)||
				command.equals(GENERATE_ENTITIES)||
				command.equals(GENERATE_REPOSITORIES));
	}

}
