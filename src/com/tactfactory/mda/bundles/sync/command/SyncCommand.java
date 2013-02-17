/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.sync.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.bundles.rest.parser.RestCompletor;
import com.tactfactory.mda.bundles.rest.parser.RestParser;
import com.tactfactory.mda.bundles.sync.parser.SyncCompletor;
import com.tactfactory.mda.bundles.sync.parser.SyncParser;
import com.tactfactory.mda.bundles.sync.template.SyncGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.plateforme.AndroidAdapter;

@PluginImplementation
public class SyncCommand extends BaseCommand{
	
	//bundle name
	public final static String BUNDLE = "sync";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_SERVICE = "service";

	//commands
	public static String GENERATE_SERVICE	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_SERVICE;

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> Sync Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(GENERATE_SERVICE)) {
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
		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new SyncGenerator(new AndroidAdapter()).generateAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void generateMetas(){
		this.registerParser(new RestParser());
		this.registerParser(new SyncParser());
		super.generateMetas();
		new SyncCompletor();
		new RestCompletor().generateApplicationRestMetadata(Harmony.metas);
	}
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> SYNC \n" +
				"\t" + GENERATE_SERVICE + "\t => Generate Adapters");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_SERVICE));
	}
}
