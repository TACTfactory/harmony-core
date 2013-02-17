package com.tactfactory.mda.bundles.search.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.bundles.search.parser.SearchParser;
import com.tactfactory.mda.bundles.search.template.SearchGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.plateforme.AndroidAdapter;

@PluginImplementation
public class SearchCommand  extends BaseCommand{
	
	//bundle name
	public final static String BUNDLE = "search";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_ACTIVITIES = "activities";

	//commands
	public static String GENERATE_ACTIVITIES = BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ACTIVITIES;

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> Adapters Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(GENERATE_ACTIVITIES)) {
			try {
				this.generateLoaders();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Generate java code files from parsed Entities
	 */
	protected void generateLoaders() {

		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new SearchGenerator(new AndroidAdapter()).generateAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void generateMetas(){
		this.registerParser(new SearchParser());
		super.generateMetas();
		//new SearchCompletor().generateApplicationRestMetadata(Harmony.metas);
	}
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> Search \n" +
				"\t" + GENERATE_ACTIVITIES + "\t => Generate Activities");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ACTIVITIES));
	}

}
