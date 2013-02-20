/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.fixture.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.bundles.fixture.metadata.FixtureMetadata;
import com.tactfactory.mda.bundles.fixture.template.FixtureGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

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
	public final static String FIXTURE_INIT	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_INIT;
	public final static String FIXTURE_LOAD	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_LOAD;
	public final static String FIXTURE_PURGE	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_PURGE;
	public final static String FIXTURE_UPDATE = BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_UPDATE;
	
	@Override
	public void execute(final String action, final String[] args, final String option) {
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
			final FixtureMetadata fixtureMeta = new FixtureMetadata();
			//TODO : get type by user input
			fixtureMeta.type = "yml";
			if(this.commandArgs.containsKey("format")){
				final String format = this.commandArgs.get("format");
				if(format.equals("xml") || format.equals("yml")){
					fixtureMeta.type = format; 
				}
			}
			ApplicationMetadata.INSTANCE.options.put(fixtureMeta.getName(), fixtureMeta);
			new FixtureGenerator(new AndroidAdapter()).init();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	public void load(){
		try {
			new FixtureGenerator(new AndroidAdapter()).load();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	public void purge(){
		try{
			this.generateMetas();
			new FixtureGenerator(new AndroidAdapter()).purge();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
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
	public boolean isAvailableCommand(final String command) {
		return 	command.equals(FIXTURE_INIT) || 
				command.equals(FIXTURE_LOAD) ||
				command.equals(FIXTURE_PURGE)||
				command.equals(FIXTURE_UPDATE);
		
	}

}
