package com.tactfactory.harmony.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.plateforme.AndroidAdapter;
import com.tactfactory.harmony.template.MenuGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;

@PluginImplementation
public class MenuCommand extends BaseCommand {

	/** Bundle name. */	
	public static final String BUNDLE = "orm";
	/** Subject. */
	public static final String SUBJECT = "menu";
	/** Action crud. */
	public static final String ACTION_UPDATE = "update";
	
	/** Command : ORM:GENERATE:ENTITIES. */
	public static final String UPDATE_MENU = 
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_UPDATE;
	
	@Override
	public void execute(String action, String[] args, String option) {
		ConsoleUtils.display("> ORM Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		
		try {
			if (action.equals(UPDATE_MENU)) {
				this.generateMetas();
				new MenuGenerator(new AndroidAdapter()).updateMenu();
			} 
	
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	@Override
	public void summary() {
		ConsoleUtils.display("\n> MENU \n" 
				+ "\t" + UPDATE_MENU+ "\t => Update the menu\n"); 		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		boolean result = false;
		if (UPDATE_MENU.equals(command)) {
			result = true;
		}
		return result;
	}

}
