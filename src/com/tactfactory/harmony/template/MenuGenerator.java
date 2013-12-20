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
	/* File constants. */
	/** Constant for MenuWrapper. */
	private static final String MENU_WRAPPER = "MenuWrapper";

	/** Constant for MenuWrapper. */
	private static final String FILE_MENU_WRAPPER = MENU_WRAPPER + ".java";

	/** Constant for MenuWrapperBase. */
	private static final String MENU_WRAPPER_BASE = MENU_WRAPPER + "Base";

	/** Constant for file MenuWrapperBase.java. */
	private static final String FILE_MENU_WRAPPER_BASE = 
			MENU_WRAPPER_BASE + ".java";

	/** Constant for file TemplateMenuBase.java. */
	private static final String FILE_TEMPLATE_MENU_BASE = "TemplateMenuBase.java";
	
	/** Constant for file %sMenuBase.java. */
	private static final String FILE_TEMPLATE_MENU_BASE_TPL = "%sMenuBase.java";

	/** Constant for file TemplateMenu.java. */
	private static final String FILE_TEMPLATE_MENU = "TemplateMenu.java";
	
	/** Constant for file %sMenu.java. */
	private static final String FILE_TEMPLATE_MENU_TPL = "%sMenu.java";
	

	/**
	 * Constructor.
	 * @param adapt The adapter to use
	 * @throws Exception if adapter is null
	 */
	public MenuGenerator(final BaseAdapter adapt) {
		super(adapt);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate the MenuBase.
	 */
	public final void generateMenu() {
		ConsoleUtils.display("Generating menu...");
		
		String cappedProjectName = CaseFormat.LOWER_CAMEL.to(
				CaseFormat.UPPER_CAMEL,
				this.getAppMetas().getName());
		
		// create ProjectMenuBase
		super.makeSource(
			this.getAdapter().getTemplateMenuBasePath()	
					+ FILE_TEMPLATE_MENU_BASE,
			this.getAdapter().getMenuBasePath()
					+ String.format(
							FILE_TEMPLATE_MENU_BASE_TPL, cappedProjectName),
			true);

		// create ProjectMenu
		super.makeSource(
			this.getAdapter().getTemplateMenuPath()	+ FILE_TEMPLATE_MENU,
			this.getAdapter().getMenuPath()
					+ String.format(FILE_TEMPLATE_MENU_TPL, cappedProjectName),
			false);

		// create MenuWrapper
		super.makeSource(
			this.getAdapter().getTemplateMenuBasePath()	
					+ FILE_MENU_WRAPPER_BASE,
			this.getAdapter().getMenuBasePath() + FILE_MENU_WRAPPER_BASE,
			true);

	}

	/**
	 * Update the Menu managing class.
	 */
	public final void updateMenu() {
		ConsoleUtils.display("Updating menu...");
		
		String cappedProjectName = CaseFormat.LOWER_CAMEL.to(
				CaseFormat.UPPER_CAMEL,
				this.getAppMetas().getName());

		this.getDatamodel().put("menus", this.getAvailableMenus());

		super.makeSource(
			this.getAdapter().getTemplateMenuBasePath()
				+ FILE_TEMPLATE_MENU_BASE,
			this.getAdapter().getMenuBasePath()
				+ String.format(FILE_TEMPLATE_MENU_BASE_TPL, cappedProjectName),
			true);
	}

	/**
	 * Get the list of all available menus.
	 * @return A list of the different menu names
	 */
	private ArrayList<String> getAvailableMenus() {
		final ArrayList<String> ret = new ArrayList<String>();
		final File menuFolder = new File(this.getAdapter().getMenuPath());
		if (menuFolder.isDirectory()) {
			File[] files = menuFolder.listFiles(new FileFilter() {

				@Override
				public boolean accept(final File arg0) {
					return arg0.getName().endsWith(FILE_MENU_WRAPPER);
				}
			});

			for (File file : files) {
				ret.add(file.getName().split("\\.")[0]);
			}
		}
		return ret;
	}
	
	public void generateMenu(String menuName) {
		// create MenuWrapper
		super.makeSource(
			this.getAdapter().getTemplateMenuPath()
					+ menuName + MENU_WRAPPER + ".java",
			this.getAdapter().getMenuPath() 
					+ menuName + MENU_WRAPPER + ".java",
			false);
	}

}
