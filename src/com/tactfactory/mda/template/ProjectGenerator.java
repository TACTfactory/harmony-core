/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ProjectGenerator {	
	protected List<ClassMetadata> metas;
	protected ClassMetadata meta;
	protected BaseAdapter adapter;

	protected boolean isWritable = true;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	public ProjectGenerator(BaseAdapter adapter) throws Exception {
		if (adapter == null)
			throw new Exception("No adapter defined.");

		this.adapter	= adapter;

		String projectNameSpace = ""+Harmony.projectNameSpace;
		projectNameSpace = projectNameSpace.replaceAll("/","\\.");

		// Make class
		this.datamodel.put(TagConstant.PROJECT_NAME, Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE, projectNameSpace);
		this.datamodel.put(TagConstant.ANDROID_SDK_DIR, Harmony.androidSdkPath);

		this.datamodel.put(TagConstant.ANT_ANDROID_SDK_DIR, new TagConstant.AndroidSDK("${sdk.dir}"));
		this.datamodel.put(TagConstant.OUT_CLASSES_ABS_DIR, "CLASSPATHDIR/");
		this.datamodel.put(TagConstant.OUT_DEX_INPUT_ABS_DIR, "DEXINPUTDIR/");
	}

	public ProjectGenerator(BaseAdapter adapter, Boolean isWritable) throws Exception {
		this(adapter);

		this.isWritable = isWritable;
	}

	public ProjectGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		this(adapter);

		this.metas = metas;
		if(this.metas!=null&&this.metas.size()!=0){
			// Make entities
			ArrayList<Map<String, Object>> modelEntities = new ArrayList<Map<String,Object>>();
			for (ClassMetadata meta : this.metas) {
				Map<String, Object> modelClass = new HashMap<String, Object>();
				modelClass.put(TagConstant.SPACE,	meta.space );
				modelClass.put(TagConstant.NAME,	meta.name );

				// Make fields
				ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
				for (FieldMetadata field : meta.fields.values()) {
					Map<String, Object> modelField = new HashMap<String, Object>();
					field.customize(adapter);
					modelField.put(TagConstant.NAME, field.name);
					modelField.put(TagConstant.TYPE, field.type);

					modelFields.add(modelField);
				}
				modelClass.put(TagConstant.FIELDS, modelFields);

				modelEntities.add(modelClass);
			}
			this.datamodel.put(TagConstant.ENTITIES, modelEntities);
		}
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
			System.out.print("\tGenerate Project File : " + destFile.getPath() + "\n"); 


		// Create
		Template tpl;
		try {
			tpl = cfg.getTemplate(templateFile);	// Load template file in engine

			OutputStreamWriter output = new FileWriter(destFile);
			tpl.process(this.datamodel, output);				// Process datamodel (with previous template file), and output to output file
			output.flush();
			output.close();

			// Debug Log
			if (Harmony.DEBUG)
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

		// create empty package entity
		FileUtils.makeFolder(this.adapter.getSourcePath() + Harmony.projectNameSpace.replaceAll("\\.","/")+"/entity/" );

		// create HomeActivity.java
		this.updateProjectFile(this.adapter.getSourcePath()+Harmony.projectNameSpace.replaceAll("\\.","/")+"/HomeActivity.java",
				this.adapter.getTemplateSourcePath().substring(1)+"HomeActivity.java");

		// create configs.xml
		this.updateProjectFile(this.adapter.getRessourceValuesPath()+"configs.xml",
				this.adapter.getTemplateRessourceValuesPath().substring(1)+"configs.xml");

		// create strings.xml
		this.updateProjectFile(this.adapter.getRessourceValuesPath()+"strings.xml",
				this.adapter.getTemplateRessourceValuesPath().substring(1)+"strings.xml");

		// create main.xml
		this.updateProjectFile(this.adapter.getRessourceLayoutPath()+"main.xml",
				this.adapter.getTemplateRessourceLayoutPath().substring(1)+"main.xml");

		// copy libraries
		FileUtils.copyfile(new File(String.format("%s/%s",Harmony.pathHarmony,"Harmony.jar")),
				new File(String.format("%s/%s",this.adapter.getLibsPath(),"Harmony.jar")));
		FileUtils.copyfile(new File(String.format("%s/%s",Harmony.pathLibs,"android-support-v4.jar")),
				new File(String.format("%s/%s",this.adapter.getLibsPath(),"android-support-v4.jar")));

		// Update newly created files with datamodel
		if(dirProj.exists() && dirProj.listFiles().length!=0)
		{
			result = true;
			for(int i=0;i<dirProj.listFiles().length;i++)
			{
				if(dirProj.listFiles()[i].isFile()) {
					this.updateProjectFile(dirProj.listFiles()[i].getPath(),
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
			// Debug Log
			if (Harmony.DEBUG)
				System.out.println("Project "+this.adapter.getPlatform()+" removed!");
		}
		return result;
	}

	/**
	 * Generate HomeActivity File and merge it with datamodel
	 */
	public void generateHomeActivity() throws IOException,TemplateException {

		Configuration cfg = new Configuration();

		try {
			cfg.setDirectoryForTemplateLoading(new File("../"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = FileUtils.makeFile(
				String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						Harmony.projectNameSpace,
						"HomeActivity.java"));

		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Source : " + file.getPath() + "\n"); 

		// Create
		Template tpl = cfg.getTemplate(
				String.format("%s%s",
						this.adapter.getTemplateSourcePath().substring(1),
						"HomeActivity.java"));

		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
		
		this.updateStringsXml(cfg);
	}

	/**
	 * Update XML Strings
	 * 
	 * @param cfg Template engine
	 */
	public void updateStringsXml(Configuration cfg) {

		try {
			cfg.setDirectoryForTemplateLoading(new File("../"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File destFile = new File(String.format("%s%s",
				this.adapter.getRessourceValuesPath(),
				"strings.xml"));

		if(!destFile.exists())
			destFile = FileUtils.makeFile(destFile.getPath());

		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tUpdate Strings.xml File : " + destFile.getPath() + "\n"); 

		// Create
		Template tpl;
		try {
			tpl = cfg.getTemplate(String.format("%s%s",
					this.adapter.getTemplateRessourceValuesPath().substring(1),
					"strings.xml"));	// Load template file in engine

			OutputStreamWriter output = new FileWriter(destFile);
			tpl.process(this.datamodel, output);				// Process datamodel (with previous template file), and output to output file
			output.flush();
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}