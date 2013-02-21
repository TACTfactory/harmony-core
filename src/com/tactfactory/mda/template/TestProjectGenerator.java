/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

public class TestProjectGenerator extends BaseGenerator {

	public TestProjectGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.datamodel = this.appMetas.toMap(this.adapter);
	}

	/**
	 * Make Platform specific Project Structure
	 * @return success to make the platform test project folder
	 */
	public boolean makeProject() {
		boolean result = false;
		if (this.adapter.getPlatform().equals("android")) {
			result = this.makeTestProjectAndroid();
		}
		else if (this.adapter.getPlatform().equals("ios")) {
			result = this.makeTestProjectIOS();
		}
		else if (this.adapter.getPlatform().equals("rim")) {
			result = this.makeTestProjectRIM();
		}
		else if (this.adapter.getPlatform().equals("winphone")) {
			result = this.makeTestProjectWinPhone();
		}

		return result;
	}

	/**
	 * Make Android Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectAndroid() {
		boolean result = false;

		// create project name space folders
		//FileUtils.makeFolder(this.adapter.getSourcePath() + Harmony.projectNameSpace.replaceAll("\\.","/"));

		// create libs folder
		FileUtils.makeFolder(this.adapter.getTestLibsPath());
				
		// create strings.xml
		super.makeSource(
				this.adapter.getTemplateStringsTestPathFile(), 
				this.adapter.getStringsTestPathFile(), false);
		
		this.updateLibrary("android-junit-report-1.5.8.jar");
		
		final File dirTpl = new File(this.adapter.getTemplateTestProjectPath());

		// Update newly created files with datamodel
		if (dirTpl.exists() && dirTpl.listFiles().length!=0) {
			result = true;
			for (int i=0;i<dirTpl.listFiles().length;i++) {
				if (dirTpl.listFiles()[i].isFile()) {
					final String fullFilePath = String.format("%s/%s/%s/%s", 
							Harmony.PATH_PROJECT, 
							this.adapter.getPlatform(), 
							this.adapter.getTest(),
							dirTpl.listFiles()[i].getName());
					
					final String fullTemplatePath = this.adapter.getTemplateTestProjectPath() + dirTpl.listFiles()[i].getName();
					
					super.makeSource(fullTemplatePath, fullFilePath, false);
				}
			}
		}
		return result;
	}

	/**
	 * Make IOS Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectIOS() {
		boolean result = false;
		
		//Generate base folders & files
		final File dirProj = FileUtils.makeFolderRecursive(
				String.format("%s/%s/%s/", Harmony.PATH_TEMPLATE , this.adapter.getPlatform(), this.adapter.getProject()),
				String.format("%s/%s/", Harmony.PATH_PROJECT, this.adapter.getPlatform()),
				true);
		
		if (dirProj.exists() && dirProj.listFiles().length!=0) {
			result = true;
		}

		return result;
	}

	/**
	 * Make RIM Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectRIM() {
		final boolean result = false;

		return result;
	}

	/**
	 * Make Windows Phone Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectWinPhone() {
		final boolean result = false;

		return result;
	}
	
	/**
	 * Update TestLibs
	 */
	@Override
	protected void updateLibrary(final String libName) {
		final File dest = new File(String.format("%s/%s", this.adapter.getTestLibsPath(), libName));
		
		if (!dest.exists()) {
			FileUtils.copyfile(
					new File(String.format("%s/%s", Harmony.PATH_LIBS, libName)),
					dest);
		}
	}
}
