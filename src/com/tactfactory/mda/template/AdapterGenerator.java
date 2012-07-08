package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.orm.SqliteAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AdapterGenerator {
	protected ClassMetadata meta;
	protected BaseAdapter adapter;
	protected String localNameSpace;
	protected boolean isWritable = true;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	public AdapterGenerator(ClassMetadata meta, BaseAdapter adapter) throws Exception {
		if (meta == null && adapter == null)
			throw new Exception("No meta or adapter define.");
		
		this.meta 		= meta;
		this.adapter	= adapter;
		
		this.localNameSpace = this.adapter.getNameSpace(this.meta, this.adapter.getData());
		
		// Make fields
		ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
		for (FieldMetadata field : this.meta.fields.values()) {
			Map<String, Object> modelField = new HashMap<String, Object>();
			field.customize(adapter);
			modelField.put(TagConstant.NAME, field.name);
			modelField.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(field));
			modelField.put(TagConstant.TYPE, SqliteAdapter.generateStructure(field));
			
			modelFields.add(modelField);
		}
		
		// Make ids
		ArrayList<Map<String, Object>> modelIds = new ArrayList<Map<String,Object>>();
		for (FieldMetadata field : this.meta.ids.values()) {
			Map<String, Object> modelId = new HashMap<String, Object>();
			modelId.put(TagConstant.NAME, field.name);
			modelId.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(field));
			modelId.put(TagConstant.TYPE, field.type);
			
			modelIds.add(modelId);
		}
		
		// Make class
		this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	meta.space);
		this.datamodel.put(TagConstant.NAME, 				meta.name);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE, 	this.localNameSpace);
		this.datamodel.put(TagConstant.FIELDS,				modelFields);
		this.datamodel.put(TagConstant.IDS,					modelIds);
	}
	
	public void generate() {
		// Info
		System.out.print(">> Generate Adapter for " +  meta.name + "\n");
		
		try {
			Configuration cfg = new Configuration();
			
			this.makeSourceControler(cfg, 
					"TemplateAdapterBase.java", 
					"%sAdapterBase.java", true);
			
			this.makeSourceControler(cfg, 
					"TemplateAdapter.java", 
					"%sAdapter.java", false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("\n");
	}
	
	/** Make Java Source Code
	 * @param cfg Template engine
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeSourceControler(Configuration cfg, String template, String filename, boolean override) throws IOException,
			TemplateException {
		String filePath = String.format("%s%s/%s",
				this.adapter.getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				String.format(filename, this.meta.name));
		File testFile = new File(filePath);

		if (!(!override && testFile.exists())) {
			File file = FileUtils.makeFile(filePath);
			
			// Debug Log
			if (Harmony.DEBUG)
				System.out.print("\tGenerate Source : " + file.getAbsoluteFile() + "\n"); 
			
			// Create
			Template tpl = cfg.getTemplate(
					this.adapter.getTemplateSourceProviderPath() + template);
			
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(datamodel, output);
			output.flush();
			output.close();
		}
	}
}
