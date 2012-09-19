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
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SQLiteGenerator {
	protected List<ClassMetadata> metas;
	protected BaseAdapter adapter;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();
	protected String localNameSpace;
	
	public SQLiteGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		if (metas == null && adapter == null)
			throw new Exception("No meta or adapter define.");
		
		this.metas 		= metas;
		this.adapter	= adapter;
		this.localNameSpace = this.adapter.getNameSpace(this.metas.get(0), this.adapter.getData());
		
		// Make entities
		ArrayList<Map<String, Object>> modelEntities = new ArrayList<Map<String,Object>>();
		for (ClassMetadata meta : this.metas) {
			Map<String, Object> modelClass = new HashMap<String, Object>();
			modelClass.put(TagConstant.SPACE,	meta.space );
			modelClass.put(TagConstant.NAME,	meta.name );
			
			// Make fields
			/*ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
			for (FieldMetadata field : meta.fields.values()) {
				Map<String, Object> modelField = new HashMap<String, Object>();
				field.customize(adapter);
				modelField.put(TagConstant.NAME, field.name);
				modelField.put(TagConstant.TYPE, field.type);
				
				modelFields.add(modelField);
			}
			modelClass.put(TagConstant.FIELDS, modelFields);*/
			
			modelEntities.add(modelClass);
		}
		
		this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	this.metas.get(0).space);
		this.datamodel.put(TagConstant.ENTITIES,			modelEntities);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE,		this.localNameSpace);
		
	}

	/**  */
	public void generateDatabase() {
		// Info
		System.out.print(">> Generate Database\n");
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File("../"));
			
			this.makeSourceData(cfg, 
					"TemplateSqliteOpenHelper.java", 
					"%sSqliteOpenHelper.java");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Make Java Source Code
	 * @param cfg Template engine
	 * @param template Template path file.
	 * @param filename
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeSourceData(Configuration cfg, String template, String filename) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(
				String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						String.format(filename, Harmony.projectName)));
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Source : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(
				this.adapter.getTemplateSourceProviderPath().substring(1) + template);
		
		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
	}
}
