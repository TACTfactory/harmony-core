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
		
		this.localNameSpace = this.adapter.getNameSpaceEntity(this.meta, this.adapter.getData());
		
		// Make fields
		ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
		for (FieldMetadata field : this.meta.fields.values()) {
			Map<String, Object> modelField = new HashMap<String, Object>();
			field.customize(adapter);
			modelField.put("name", field.name);
			modelField.put("type", field.type);
			modelField.put("customEditType", field.customEditType);
			modelField.put("customShowType", field.customShowType);
			
			modelFields.add(modelField);
		}
		
		// Make class
		this.datamodel.put("namespace", meta.space);
		this.datamodel.put("space", 		meta.name);
		this.datamodel.put("localnamespace", this.localNameSpace);
		this.datamodel.put("fields", 	modelFields);
	}
	
	public void generate() {
		
	}
	
	/** Make Java Source Code
	 * @param cfg Template engine
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeSourceControler(Configuration cfg, String template, String filename) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(
				String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						String.format(filename, this.meta.name)));
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Source : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(
				this.adapter.getTemplateSourceControlerPath() + template);
		
		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
	}
}
