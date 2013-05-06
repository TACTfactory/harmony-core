package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

public class MenuGenerator extends BaseGenerator {

	public MenuGenerator(BaseAdapter adapt) throws Exception {
		super(adapt);
		
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}
	
	public void generateMenu() {
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
	
	public void updateMenu() {
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
	
	private ArrayList<String> getAvailableMenus() {
		ArrayList<String> ret = new ArrayList<String>();
		File menuFolder = new File(this.getAdapter().getMenuPath());
		if (menuFolder.isDirectory()) {
			File[] files = menuFolder.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File arg0) {
					return arg0.getName().contains("MenuWrapper") && !arg0.getName().contains("MenuWrapperBase");
				}
			});
			
			for( File file : files) {
				ret.add(file.getName().split("\\.")[0]);
			}
		}
		return ret;
	}

}
