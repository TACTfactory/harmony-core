/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.fixture.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.fixture.metadata.FixtureMetadata;
import com.tactfactory.mda.fixture.template.FixtureGenerator;
import com.tactfactory.mda.plateforme.AndroidAdapter;

@PluginImplementation
public class FixtureCommand extends BaseCommand{
	//bundle name
	public final static String BUNDLE = "orm";
	public final static String SUBJECT = "fixture";

	//actions
	public final static String ACTION_INIT = "init";
	public final static String ACTION_LOAD = "load";
	public final static String ACTION_PURGE = "purge";
	public final static String ACTION_UPDATE = "update";

	//commands
	public static String FIXTURE_INIT	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_INIT;
	public static String FIXTURE_LOAD	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_LOAD;
	public static String FIXTURE_PURGE	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_PURGE;
	public static String FIXTURE_UPDATE = BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_UPDATE;
	
	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> Fixture Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(FIXTURE_INIT)) {
			this.init();
		} else if(action.equals(FIXTURE_LOAD)){
			this.load();
		} else if(action.equals(FIXTURE_PURGE)){
			this.purge();
		} else if(action.equals(FIXTURE_UPDATE)){
			this.update();
		}
		
	}
	
	public void init(){
		try {
			this.generateMetas();
			FixtureMetadata fixtureMeta = new FixtureMetadata();
			
			//TODO : get type by user input
			fixtureMeta.type = "yml";
			if(this.commandArgs.containsKey("format")){
				String format = this.commandArgs.get("format");
				if(format.equals("xml") || format.equals("yml"))
					fixtureMeta.type = format; 
			}
			Harmony.metas.options.put(fixtureMeta.getName(), fixtureMeta);
			new FixtureGenerator(new AndroidAdapter()).init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(){
		try {
			new FixtureGenerator(new AndroidAdapter()).load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void purge(){
		try{
			this.generateMetas();
			new FixtureGenerator(new AndroidAdapter()).purge();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(){
		
	}

	@Override
	public void summary() {
		ConsoleUtils.display("\n> FIXTURE \n" +
				"\t" + FIXTURE_INIT + "\t => Initialize fixtures, create loaders\n"+
				"\t" + FIXTURE_LOAD + "\t => Load fixtures into the projects (overwrite)\n"+
				"\t" + FIXTURE_PURGE + "\t => Clear fixtures on the projects\n"+
				"\t" + FIXTURE_UPDATE + "\t => Update the fixtures in the project");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(FIXTURE_INIT) || 
				command.equals(FIXTURE_LOAD) ||
				command.equals(FIXTURE_PURGE) ||
				command.equals(FIXTURE_UPDATE));
		
	}

}
