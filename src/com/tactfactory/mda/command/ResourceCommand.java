/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.parser.*;
import com.tactfactory.mda.plateforme.*;

/**
 * 
 *
 */
@PluginImplementation
public class ResourceCommand extends BaseCommand {

	//bundle name
	public final static String BUNDLE = "resource";
	public final static String SUBJECT = "generate";

	//actions
	public final static String ACTION_IMAGE = "image";
	public final static String ACTION_TRANSLATE = "translate";

	//commands
	public static String GENERATE_IMAGE 	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_IMAGE;
	public static String GENERATE_TRANSLATE	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_TRANSLATE;

	//internal
	protected BaseAdapter adapter = new AndroidAdapter();
	protected JavaModelParser javaModelParser;
	
	@Override
	public void summary() {
		ConsoleUtils.display("\n> " + BUNDLE.toUpperCase() + " \n" +
				"\t" + GENERATE_IMAGE + "\t => Generate all resize of the HD images\n" +
				"\t" + GENERATE_TRANSLATE + "\t => Generate translate\n");
	}

	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> Resource Generator");

		this.commandArgs = Console.parseCommandArgs(args);
		
		try {
			if (action.equals(GENERATE_IMAGE)) {
				this.adapter.resizeImage();
			} else
	
			if (action.equals(GENERATE_TRANSLATE)) {
				//this.generateTranslate();
			} else {
	
			}
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_IMAGE) ||
				command.equals(GENERATE_TRANSLATE)  );
	}
}