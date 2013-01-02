/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestProjectGenerator extends BaseGenerator {
	protected boolean isWritable = true;

	public TestProjectGenerator(BaseAdapter adapter) throws Exception {
		super(new ArrayList<ClassMetadata>(), adapter);

		String projectNameSpace = "" + Harmony.projectNameSpace;
		projectNameSpace = projectNameSpace.replaceAll("/","\\.");

		// Make class
		this.datamodel.put(TagConstant.PROJECT_NAME, Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE, projectNameSpace);
		this.datamodel.put(TagConstant.ANDROID_SDK_DIR, Harmony.androidSdkPath);

		this.datamodel.put(TagConstant.ANT_ANDROID_SDK_DIR, new TagConstant.AndroidSDK("${sdk.dir}"));
		this.datamodel.put(TagConstant.OUT_CLASSES_ABS_DIR, "CLASSPATHDIR/");
		this.datamodel.put(TagConstant.OUT_DEX_INPUT_ABS_DIR, "DEXINPUTDIR/");
	}

	public TestProjectGenerator(BaseAdapter adapter, Boolean isWritable) throws Exception {
		this(adapter);

		this.isWritable = isWritable;
	}

	/**
	 * Update Test Project File merge template & datamodel
	 * 
	 * @param destPath File to update
	 * @param templateFile Template File to use
	 */ 
	public void updateProjectFile(String destPath, String templateFile) {
		if(!FileUtils.exists(destPath)){
			Configuration cfg = new Configuration();
	
			try {
				cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			File destFile = new File(destPath);
			if(!destFile.exists())
				destFile = FileUtils.makeFile(destPath);
	
			// Debug Log
		ConsoleUtils.displayDebug("\tGenerate Test Project File : " + destFile.getPath()); 
	
	
			// Create
			Template tpl;
			try {
				
				tpl = cfg.getTemplate(templateFile);	// Load template file in engine
	
				OutputStreamWriter output = new FileWriter(destFile);
				tpl.process(this.datamodel, output);				// Process datamodel (with previous template file), and output to output file
				output.flush();
				output.close();
	
				// Debug Log
				ConsoleUtils.displayDebug("File "+destFile.getName()+" processed...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
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
		this.updateProjectFile(this.adapter.getStringsTestPathFile(),
				this.adapter.getTemplateStringsTestPathFile()); //.substring(1));
		
		File dirTpl = new File(this.adapter.getTemplateTestProjectPath());

		// Update newly created files with datamodel
		if(dirTpl.exists() && dirTpl.listFiles().length!=0)
		{
			result = true;
			for(int i=0;i<dirTpl.listFiles().length;i++)
			{
				if(dirTpl.listFiles()[i].isFile()) {
					this.updateProjectFile(String.format("%s/%s/%s/", Harmony.pathProject, this.adapter.getPlatform(), 
							this.adapter.getTest())+dirTpl.listFiles()[i].getName(),
							this.adapter.getTemplateTestProjectPath() + dirTpl.listFiles()[i].getName());
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