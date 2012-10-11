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
import com.tactfactory.mda.orm.SqliteAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AdapterGenerator {
	protected List<ClassMetadata> metas;
	protected Map<String, Object> entities;
	protected ClassMetadata meta;
	protected BaseAdapter adapter;
	protected String localNameSpace;
	protected boolean isWritable = true;
	protected HashMap<String, Object> datamodel;

	public AdapterGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		if (meta == null && adapter == null)
			throw new Exception("No meta or adapter define.");
		
		this.metas		= metas;
		this.adapter	= adapter;
		
		// Make entities
		this.entities = new HashMap<String, Object>();
		for (ClassMetadata meta : this.metas) {
			this.meta = meta;
			Map<String, Object> modelClass = new HashMap<String, Object>();
			modelClass.put(TagConstant.SPACE,	meta.space );
			modelClass.put(TagConstant.NAME,	meta.name );
			modelClass.put(TagConstant.LOCAL_NAMESPACE,	this.adapter.getNameSpace(this.meta, this.adapter.getData()) );
			
			// Make ids
			ArrayList<Map<String, Object>> modelIds = new ArrayList<Map<String,Object>>();
			for (FieldMetadata field : this.meta.ids.values()) {
				Map<String, Object> modelId = new HashMap<String, Object>();
				modelId.put(TagConstant.NAME, field.name);
				modelId.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(field));
				modelId.put(TagConstant.TYPE, field.type);
				
				modelIds.add(modelId);
			}
			
			// Make fields
			ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
			for (FieldMetadata field : this.meta.fields.values()) {
				Map<String, Object> modelField = new HashMap<String, Object>();
				field.customize(adapter);
				modelField.put(TagConstant.NAME, field.name);
				modelField.put(TagConstant.TYPE, field.type);
				modelField.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(field));
				modelField.put(TagConstant.SCHEMA, SqliteAdapter.generateStructure(field));
				
				modelFields.add(modelField);
			}

			// Make relations
			ArrayList<Map<String, Object>> modelRelations = new ArrayList<Map<String,Object>>();
			for (FieldMetadata relation : this.meta.relations.values()) {
				Map<String, Object> modelRelation = new HashMap<String, Object>();
				modelRelation.put(TagConstant.NAME, relation.name);
				modelRelation.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(relation));
				modelRelation.put(TagConstant.TYPE, relation.type);
				modelRelation.put(TagConstant.RELATION_TYPE, relation.relation_type);
				
				modelRelations.add(modelRelation);
			}
			
			modelClass.put(TagConstant.IDS, modelIds);
			modelClass.put(TagConstant.FIELDS, modelFields);
			modelClass.put(TagConstant.RELATIONS, modelRelations);
			
			this.entities.put((String) modelClass.get(TagConstant.NAME), modelClass);
		}

		//Message info
		System.out.println(">> Analyse Databases relations in models");
		
		//List each entity hashmap
		for(Object metaMap : this.entities.values()) {
			Map<String, Object> modelClass = (Map<String, Object>)metaMap;
			
			//List each entity relation field
			for (Object metaRelations : (ArrayList<Map<String, Object>>)modelClass.get(TagConstant.RELATIONS) ) {
				Map<String, Object> relation = (Map<String, Object>) metaRelations;
				
				if( ((String)relation.get(TagConstant.RELATION_TYPE)).equals("@OneToOne") ) {

				} else
					
				if( ((String)relation.get(TagConstant.RELATION_TYPE)).equals("@OneToMany")) {
					System.out.println("\tFound a @OneToMany relation in "+modelClass.get(TagConstant.NAME) );
					
					// Target Entity type
					String targetEntity = ((String)relation.get(TagConstant.TYPE)).replace("ArrayList<","").replace(">","");
					System.out.println("\tCheck if @ManyToOne exists in "+targetEntity);
					
					// Get Target Entity Relations
					Map<String, Object> targetModelClass = (Map<String, Object>)this.entities.get(targetEntity);
					ArrayList<Map<String, Object>> targetRelations = (ArrayList<Map<String, Object>>)targetModelClass.get(TagConstant.RELATIONS);
					
					// list and check for a reversed @OneToMany (@ManyToOne)
					for(Map<String,Object> targetRelation : targetRelations) {
						
						if(	targetRelation.get(TagConstant.TYPE).equals(modelClass.get(TagConstant.NAME).toString())
									&& targetRelation.get(TagConstant.RELATION_TYPE).toString().equals("@ManyToOne")) {
							System.out.println("\t@ManyToOne Relation exists, no need to add column... ");
							break;
						} else {
							System.out.println("\tNo @ManyToOne relation found, creating column...");

							Map<String, Object> modelRelation = new HashMap<String, Object>();
							modelRelation.put(TagConstant.NAME,				modelClass.get(TagConstant.NAME).toString().toLowerCase());
							modelRelation.put(TagConstant.ALIAS,			SqliteAdapter.generateRelationColumnName(
																				modelClass.get(TagConstant.NAME).toString()));
							modelRelation.put(TagConstant.TYPE,				modelClass.get(TagConstant.NAME).toString());
							modelRelation.put(TagConstant.RELATION_TYPE,	"@ManyToOne");							
						}
					}
					
					System.out.print("\n");
				} else
				
				if( ((String)relation.get(TagConstant.RELATION_TYPE)).equals("@ManyToOne")) {

				} else
				
				if( ((String)relation.get(TagConstant.RELATION_TYPE)).equals("@ManyToMany")) {

				} else {
					
				}
			}
		}
	}
	
	public void generateAll() {
		
		for(Object modelEntity : this.entities.values()) {
			
			Map<String, Object> entity = (Map<String, Object>) modelEntity;
			this.localNameSpace = (String) entity.get(TagConstant.LOCAL_NAMESPACE);
			
			// Make class
			this.datamodel = new HashMap<String, Object>();
			this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
			this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	entity.get(TagConstant.SPACE));
			this.datamodel.put(TagConstant.NAME, 				entity.get(TagConstant.NAME));
			this.datamodel.put(TagConstant.LOCAL_NAMESPACE, 	this.localNameSpace);
			this.datamodel.put(TagConstant.IDS,					entity.get(TagConstant.IDS));
			this.datamodel.put(TagConstant.FIELDS,				entity.get(TagConstant.FIELDS));
			this.datamodel.put(TagConstant.RELATIONS,			entity.get(TagConstant.RELATIONS));
			
			this.generate();
		}
	}
	
	private void generate() {
		// Info
		System.out.print(">> Generate Adapter for " +  this.datamodel.get(TagConstant.NAME) + "\n");
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File("../"));
			
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
				String.format(filename, this.datamodel.get(TagConstant.NAME)));
		File testFile = new File(filePath);

		if (!(!override && testFile.exists())) {
			File file = FileUtils.makeFile(filePath);
			
			// Debug Log
			if (Harmony.DEBUG)
				System.out.print("\tGenerate Source : " + file.getPath() + "\n"); 
			
			// Create
			Template tpl = cfg.getTemplate(
					this.adapter.getTemplateSourceProviderPath().substring(1) + template);
			
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(datamodel, output);
			output.flush();
			output.close();
		}
	}
}
