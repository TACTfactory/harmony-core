/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.social.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.bundles.social.parser.SocialParser;
import com.tactfactory.mda.bundles.social.template.SocialGenerator;

@PluginImplementation
public class SocialCommand extends BaseCommand{
	
	//bundle name
	public final static String BUNDLE = "social";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_SOCIAL = "social";

	//commands
	public static String GENERATE_SOCIAL	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_SOCIAL;

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> Social Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(GENERATE_SOCIAL)) {
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

		this.generateMetas();
		if(Harmony.metas.entities!=null){
			try {
				new SocialGenerator(new AndroidAdapter()).generateMenu();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void generateMetas(){
		this.registerParser(new SocialParser());
		super.generateMetas();
	}
	
	

	@Override
	public void summary() {
		ConsoleUtils.display("\n> Social \n" +
				"\t" + GENERATE_SOCIAL + "\t => Generate Social");
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_SOCIAL));
	}

}
