/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.List;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * The generator class for Menu.
 */
public class MenuGenerator extends BaseGenerator<IAdapter> {
	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public MenuGenerator(final IAdapter adapter) {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate the MenuBase.
	 */
	public final void generateMenu() {
		ConsoleUtils.display("Generating menu...");
		
		List<IUpdater> updaters = this.getAdapter().getAdapterProject()
		        .getMenuBaseFiles();
		this.processUpdater(updaters);
		
		updaters = this.getAdapter().getAdapterProject().getMenuFiles();
		this.processUpdater(updaters);
	}

	/**
	 * Update the Menu managing class.
	 */
	public final void updateMenu() {
		ConsoleUtils.display("Updating menu...");
		
		this.getDatamodel().put(
		        "menus",
		        this.getAdapter().getAdapterProject().getAvailableMenus());
		
		List<IUpdater> updaters = this.getAdapter().getAdapterProject()
		        .getMenuBaseFiles();
		this.processUpdater(updaters);
	}
	
	public void generateMenu(String menuName) {
	    List<IUpdater> updaters = this.getAdapter().getAdapterProject()
	            .getMenuFiles(menuName);
	    this.processUpdater(updaters);
	}
}
