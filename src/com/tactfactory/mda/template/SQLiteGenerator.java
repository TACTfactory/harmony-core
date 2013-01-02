/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
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

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SQLiteGenerator extends GeneratorBase {
	protected String localNameSpace;

	public SQLiteGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		super(metas, adapter);
		
		this.localNameSpace = this.adapter.getNameSpace(this.metas.get(0), this.adapter.getData());

		// Make entities
		ArrayList<Map<String, Object>> modelEntities = new ArrayList<Map<String,Object>>();
		for (ClassMetadata meta : this.metas) {
			if(!meta.fields.isEmpty()){
				Map<String, Object> modelClass = new HashMap<String, Object>();
				modelClass.put(TagConstant.SPACE,	meta.space );
				modelClass.put(TagConstant.NAME,	meta.name );
				
				// Make fields
				ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
				for (FieldMetadata field : meta.fields.values()) {
					Map<String, Object> modelField = new HashMap<String, Object>();
					field.customize(adapter);
					modelField.put(TagConstant.NAME, field.fieldName);
					modelField.put(TagConstant.TYPE, field.type);
					
					modelFields.add(modelField);
				}
				modelClass.put(TagConstant.FIELDS, modelFields);
	
				// Make relations
				ArrayList<Map<String, Object>> modelRelations = new ArrayList<Map<String,Object>>();
	
				for (FieldMetadata relation : meta.relations.values()) {
					Map<String, Object> modelRelation = new HashMap<String, Object>();
					relation.customize(adapter);
					modelRelation.put(TagConstant.NAME, relation.fieldName);
					modelRelation.put(TagConstant.TYPE, relation.type);
					modelRelation.put(TagConstant.RELATION_TYPE, relation.columnDefinition);
					
					modelRelations.add(modelRelation);
				}
				modelClass.put(TagConstant.RELATIONS, modelRelations);
				
				modelEntities.add(modelClass);
			}
		}
		this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	this.metas.get(0).space);
		this.datamodel.put(TagConstant.ENTITIES,			modelEntities);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE,		this.localNameSpace);
	}

	/**
	 * Generate Database Interface Source Code
	 */
	public void generateDatabase() {
		// Info
		ConsoleUtils.display(">> Generate Database");
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			this.makeSourceData(cfg, 
					"TemplateSQLiteOpenHelper.java", 
					"%sSQLiteOpenHelper.java");
			
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
						String.format(filename, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, Harmony.projectName))));
		
		// Debug Log
		ConsoleUtils.displayDebug("Generate Source : " + file.getPath()); 
		
		// Create
		Template tpl = cfg.getTemplate(
				this.adapter.getTemplateSourceProviderPath().substring(1) + template);
		
		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
	}
}
