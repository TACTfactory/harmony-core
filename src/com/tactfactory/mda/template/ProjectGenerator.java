/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ProjectGenerator {	
	protected ClassMetadata meta;
	protected BaseAdapter adapter;

	protected boolean isWritable = true;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	public ProjectGenerator(BaseAdapter adapter) throws Exception {
		if (meta == null && adapter == null)
			throw new Exception("No adapter defined.");

		this.adapter	= adapter;

		// Make class
		this.datamodel.put(TagConstant.PROJECT_NAME, Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE, Harmony.projectNameSpace.replaceAll("/","\\."));
		this.datamodel.put(TagConstant.ANDROID_SDK_DIR, Harmony.androidSdkPath);
		
		this.datamodel.put(TagConstant.ANT_ANDROID_SDK_DIR, new TagConstant.AndroidSDK("${sdk.dir}"));
		this.datamodel.put(TagConstant.OUT_CLASSES_ABS_DIR, "CLASSPATHDIR/");
        this.datamodel.put(TagConstant.OUT_DEX_INPUT_ABS_DIR, "DEXINPUTDIR/");
	}

	public ProjectGenerator(BaseAdapter adapter, Boolean isWritable) throws Exception {
		this(adapter);

		this.isWritable = isWritable;
	}

	/**
	 * Update Project File merge template & datamodel
	 * 
	 * @param destFile File to update
	 * @param templateFile Template File to use
	 */ 
	public void updateProjectFile(String destPath, String templateFile) {

		Configuration cfg = new Configuration();
		
		try {
			cfg.setDirectoryForTemplateLoading(new File("../"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File destFile = new File(destPath);
		if(!destFile.exists())
			destFile = FileUtils.makeFile(destPath);
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Project File : " + destFile.getAbsolutePath() + "\n"); 
		

		// Create
		Template tpl;
		try {
			tpl = cfg.getTemplate(templateFile);	// Load template file in engine

			OutputStreamWriter output = new FileWriter(destFile);
			tpl.process(this.datamodel, output);				// Process datamodel (with previous template file), and output to output file
			output.flush();
			output.close();
			System.out.println("File "+destFile.getName()+" processed...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Make Platform specific Project Structure
	 * @return success to make the platform project folder
	 */
	public boolean makeProject(){
		boolean result = false;
		if(this.adapter.getPlatform().equals("android")) {
			result = this.makeProjectAndroid();
		}
		else if(this.adapter.getPlatform().equals("ios")) {
			result = this.makeProjectIOS();
		}
		else if(this.adapter.getPlatform().equals("rim")) {
			result = this.makeProjectRIM();
		}
		else if(this.adapter.getPlatform().equals("winphone")) {
			result = this.makeProjectWinPhone();
		}
		
		return result;
	}

	/**
	 * Make Android Project Structure
	 * @return success to make the platform project folder
	 */
	private boolean makeProjectAndroid(){
		boolean result = false;
		
		//create project template structure
		File dirProj = FileUtils.makeFolderRecursive(
				String.format("%s/%s/%s/", Harmony.pathTemplate, this.adapter.getPlatform(), this.adapter.getProject()),
				String.format("%s/%s/", Harmony.pathProject, this.adapter.getPlatform()),
				true);
		
		// create project name space folders
		FileUtils.makeFolder(this.adapter.getSourcePath() + Harmony.projectNameSpace.replaceAll("\\.","/"));
		
		// create configs.xml
		this.updateProjectFile(this.adapter.getRessourceValuesPath()+"configs.xml",
				this.adapter.getTemplateRessourceValuesPath().substring(1)+"configs.xml");
		
		// Update newly created files with datamodel
		if(dirProj.exists() && dirProj.listFiles().length!=0)
		{
			result = true;
			for(int i=0;i<dirProj.listFiles().length;i++)
			{
				if(dirProj.listFiles()[i].isFile()) {
					this.updateProjectFile(dirProj.listFiles()[i].getAbsolutePath(),
							this.adapter.getTemplateProjectPath().substring(1) + dirProj.listFiles()[i].getName());
				}
			}
		}
		return result;
	}

	/**
	 * Make IOS Project Structure
	 * @return success to make the platform project folder
	 */
	private boolean makeProjectIOS(){
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
	 * Make RIM Project Structure
	 * @return success to make the platform project folder
	 */
	private boolean makeProjectRIM(){
		boolean result = false;
		
		return result;
	}

	/**
	 * Make Windows Phone Project Structure
	 * @return success to make the platform project folder
	 */
	private boolean makeProjectWinPhone(){
		boolean result = false;
		
		return result;
	}

	/**
	 * Remove Platform specific Project Structure
	 * @return success to make the platform project folder
	 */
	public boolean removeProject(){
		boolean result = false;
		File dirproj = new File(String.format("%s/%s/", Harmony.pathProject, this.adapter.getPlatform()));
		if(FileUtils.deleteRecursive(dirproj)) {
			System.out.println("Project "+this.adapter.getPlatform()+" removed!");
		}
		return result;
	}
}