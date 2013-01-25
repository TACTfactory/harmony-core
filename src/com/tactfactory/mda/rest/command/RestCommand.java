/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.rest.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.rest.parser.RestCompletor;
import com.tactfactory.mda.rest.parser.RestParser;
import com.tactfactory.mda.rest.template.RestGenerator;
import com.tactfactory.mda.rest.template.WebGenerator;

@PluginImplementation
public class RestCommand extends BaseCommand{
	
	//bundle name
	public final static String BUNDLE = "rest";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_ADAPTERS = "adapters";
	public final static String ACTION_ENTITIES = "entities";
	public final static String ACTION_REPOSITORIES = "repositories";

	//commands
	public static String GENERATE_ADAPTERS	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ADAPTERS;
	public static String GENERATE_ENTITIES = BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITIES;
	public static String GENERATE_REPOSITORIES = BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_REPOSITORIES;

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
		}else if (action.equals(GENERATE_ENTITIES)){
			try{
				this.generateEntities();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals(GENERATE_REPOSITORIES)){
			try{
				this.generateWebRepositories();
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

		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new RestGenerator(new AndroidAdapter()).generateAll();
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
				new WebGenerator(new AndroidAdapter()).generateWebRepositories();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				"\t" + GENERATE_ADAPTERS + "\t => Generate Adapters\n"+
				"\t" + GENERATE_ENTITIES + "\t => Generate Web Entities\n"+
				"\t" + GENERATE_REPOSITORIES + "\t => Generate Web Repositories");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ADAPTERS)||
				command.equals(GENERATE_ENTITIES)||
				command.equals(GENERATE_REPOSITORIES));
	}

}
