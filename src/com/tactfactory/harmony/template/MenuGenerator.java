/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * The generator class for Menu.
 */
public class MenuGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapt The adapter to use
	 * @throws Exception if adapter is null
	 */
	public MenuGenerator(final BaseAdapter adapt) throws Exception {
		super(adapt);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate the MenuBase.
	 */
	public final void generateMenu() {
		ConsoleUtils.display("Generating menu...");
		// create ProjectMenuBase
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "menu/TemplateMenuBase.java",
			this.getAdapter().getMenuPath()

			+ CaseFormat.LOWER_CAMEL.to(
					CaseFormat.UPPER_CAMEL,
					this.getAppMetas().getName())
			+ "MenuBase.java",
			true);

		// create ProjectMenu
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "menu/TemplateMenu.java",
			this.getAdapter().getMenuPath()
			+ CaseFormat.LOWER_CAMEL.to(
					CaseFormat.UPPER_CAMEL,
					this.getAppMetas().getName())
			+ "Menu.java",
			false);

		// create MenuWrapper
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "menu/MenuWrapperBase.java",
			this.getAdapter().getMenuPath() + "MenuWrapperBase.java",
			true);

	}

	/**
	 * Update the Menu managing class.
	 */
	public final void updateMenu() {
		ConsoleUtils.display("Updating menu...");

		this.getDatamodel().put("menus", this.getAvailableMenus());

		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "menu/TemplateMenuBase.java",
			this.getAdapter().getMenuPath()

			+ CaseFormat.LOWER_CAMEL.to(
					CaseFormat.UPPER_CAMEL,
					this.getAppMetas().getName())
			+ "MenuBase.java",
			true);
	}

	/**
	 * Get the list of all available menus.
	 * @return A list of the different menu names
	 */
	private ArrayList<String> getAvailableMenus() {
		ArrayList<String> ret = new ArrayList<String>();
		File menuFolder = new File(this.getAdapter().getMenuPath());
		if (menuFolder.isDirectory()) {
			File[] files = menuFolder.listFiles(new FileFilter() {

				@Override
				public boolean accept(final File arg0) {
					return arg0.getName().contains("MenuWrapper")
							&& !arg0.getName().contains("MenuWrapperBase");
				}
			});

			for (File file : files) {
				ret.add(file.getName().split("\\.")[0]);
			}
		}
		return ret;
	}

}
