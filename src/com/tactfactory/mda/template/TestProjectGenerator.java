/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.util.HashMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

public class TestProjectGenerator extends BaseGenerator {
	protected boolean isWritable = true;

	public TestProjectGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);

		String projectNameSpace = "" + this.appMetas.projectNameSpace;
		projectNameSpace = projectNameSpace.replaceAll("/","\\.");

		this.datamodel = (HashMap<String, Object>) this.appMetas.toMap(this.adapter);
	}

	public TestProjectGenerator(BaseAdapter adapter, Boolean isWritable) throws Exception {
		this(adapter);

		this.isWritable = isWritable;
	}

	/**
	 * Make Platform specific Project Structure
	 * @return success to make the platform test project folder
	 */
	public boolean makeProject(){
		boolean result = false;
		if(this.adapter.getPlatform().equals("android")) {
			result = this.makeTestProjectAndroid();
		}
		else if(this.adapter.getPlatform().equals("ios")) {
			result = this.makeTestProjectIOS();
		}
		else if(this.adapter.getPlatform().equals("rim")) {
			result = this.makeTestProjectRIM();
		}
		else if(this.adapter.getPlatform().equals("winphone")) {
			result = this.makeTestProjectWinPhone();
		}

		return result;
	}

	/**
	 * Make Android Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectAndroid(){
		boolean result = false;

		// create project name space folders
		//FileUtils.makeFolder(this.adapter.getSourcePath() + Harmony.projectNameSpace.replaceAll("\\.","/"));

		// create strings.xml
		super.makeSource(
				this.adapter.getTemplateStringsTestPathFile(), 
				this.adapter.getStringsTestPathFile(), false);
		
		File dirTpl = new File(this.adapter.getTemplateTestProjectPath());

		// Update newly created files with datamodel
		if(dirTpl.exists() && dirTpl.listFiles().length!=0) {
			result = true;
			for(int i=0;i<dirTpl.listFiles().length;i++)
			{
				if(dirTpl.listFiles()[i].isFile()) {
					String fullFilePath = String.format("%s/%s/%s/%s", 
							Harmony.pathProject, 
							this.adapter.getPlatform(), 
							this.adapter.getTest(),
							dirTpl.listFiles()[i].getName());
					
					String fullTemplatePath = this.adapter.getTemplateTestProjectPath() + dirTpl.listFiles()[i].getName();
					
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
	private boolean makeTestProjectIOS(){
		boolean result = false;
		
		//Generate base folders & files
		File dirProj = FileUtils.makeFolderRecursive(
				String.format("%s/%s/%s/", Harmony.pathTemplate , this.adapter.getPlatform(), this.adapter.getProject()),
				String.format("%s/%s/", Harmony.pathProject, this.adapter.getPlatform()),
				true);
		
		if(dirProj.exists() && dirProj.listFiles().length!=0)
			result = true;

		return result;
	}

	/**
	 * Make RIM Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectRIM(){
		boolean result = false;

		return result;
	}

	/**
	 * Make Windows Phone Test Project Structure
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectWinPhone(){
		boolean result = false;

		return result;
	}
}
