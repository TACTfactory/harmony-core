package com.tactfactory.mda.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.template.MenuGenerator;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * Command class for Menu generation.
 */
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
	public final void execute(
			final String action, 
			final String[] args, 
			final String option) {
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
	public final void summary() {
		ConsoleUtils.display("\n> MENU \n" 
				+ "\t" + UPDATE_MENU + "\t => Update the menu\n"); 		
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		boolean result = false;
		if (UPDATE_MENU.equals(command)) {
			result = true;
		}
		return result;
	}

}
