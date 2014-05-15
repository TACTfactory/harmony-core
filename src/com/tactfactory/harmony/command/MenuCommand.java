/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.util.LinkedHashMap;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.command.base.CommandBase;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.template.MenuGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Command class for Menu generation.
 */
@PluginImplementation
public class MenuCommand extends CommandBase {

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
		//this.generateMetas();

		for(IAdapter adapter : this.getAdapters()) {
    		try {
    			if (action.equals(UPDATE_MENU)) {
    				this.generateMetas();           //TODO MG : why ?
    				new MenuGenerator(adapter).updateMenu();
    			}
    		} catch (final Exception e) {
    			ConsoleUtils.displayError(e);
    		}
		}
	}

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands =
				new LinkedHashMap<String, String>();
		commands.put(UPDATE_MENU, "Update the menu");
		
		ConsoleUtils.displaySummary(
				"Menu",
				commands);
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
