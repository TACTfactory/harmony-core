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

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SQLiteAdapterGenerator extends GeneratorBase {	
	protected Map<String, Object> entities;
	protected String localNameSpace;
	protected boolean isWritable = true;

	public SQLiteAdapterGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		super(metas, adapter);
		
		this.entities = new HashMap<String, Object>();
		
		for(ClassMetadata meta : metas){
			if(!meta.fields.isEmpty()){
				Map<String, Object> modelClass = meta.toMap(this.adapter);
				modelClass.put(TagConstant.LOCAL_NAMESPACE, this.adapter.getNameSpace(meta, this.adapter.getData()));
				
				this.entities.put((String) modelClass.get(TagConstant.NAME), modelClass);
				this.makeRelationModel();
			}
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void makeRelationModel() {
		String relationType = null;
		String modelName = null;
		//Message info
		ConsoleUtils.display(">> Analyse Databases relations in models");
		
		//List each entity hashmap
		for(Object metaMap : this.entities.values()) {
			if (metaMap instanceof Map<?, ?>) {
				Map<String, Object> modelClass = (Map<String, Object>)metaMap;
				
				//List each entity relation field
				for (Object metaRelations : (ArrayList<Map<String, Object>>)modelClass.get(TagConstant.RELATIONS) ) {
					Map<String, Object> relation = (Map<String, Object>) ((Map<String, Object>) metaRelations).get(TagConstant.RELATION);
					relationType = ((String)relation.get(TagConstant.TYPE));
					modelName = ((String)modelClass.get(TagConstant.NAME));
					
					// One to One relation
					if (relationType.equals("@OneToOne") ) {
						ConsoleUtils.display("\tFound a @OneToOne relation in " + modelName );
	
					} else
						
					// One to Many relation
					if (relationType.equals("@OneToMany")) {
						ConsoleUtils.display("\tFound a @OneToMany relation in " + modelName );
						
						// Target Entity type
						String targetEntity = relationType.replace("ArrayList<","").replace(">","");
						ConsoleUtils.display("\tCheck if @ManyToOne exists in " + targetEntity);
						
						// Get Target Entity Relations
						if(this.entities.containsKey(targetEntity)) {
							Map<String, Object> targetModelClass = (Map<String, Object>)this.entities.get(targetEntity);
							ArrayList<Map<String, Object>> targetRelations = (ArrayList<Map<String, Object>>)targetModelClass.get(TagConstant.RELATIONS);
							
							// list and check for a reversed @OneToMany (@ManyToOne)
							for(Map<String,Object> targetRelation : targetRelations) {
								
								if(	targetRelation.get(TagConstant.TYPE).equals(modelName)
											&& targetRelation.get(TagConstant.TYPE).toString().equals("@ManyToOne")) {
									ConsoleUtils.display("\t@ManyToOne Relation exists, no need to add column... ");
									break;
								} else {
									ConsoleUtils.display("\tNo @ManyToOne relation found, creating column...");
		
									Map<String, Object> modelRelation = new HashMap<String, Object>();
									modelRelation.put(TagConstant.NAME,				modelName.toLowerCase());
									modelRelation.put(TagConstant.ALIAS,			SqliteAdapter.generateRelationColumnName(modelName));
									modelRelation.put(TagConstant.TYPE,				modelName);
									modelRelation.put(TagConstant.RELATION_TYPE,	"@ManyToOne");							
								}
							}
	
							ConsoleUtils.display("..$..");
						} else {
							ConsoleUtils.displayWarning("\tTarget Entity not found please add it or use orm:generate:entities...");
						}
					} else
					
					// Many to One relation
					if (relationType.equals("@ManyToOne")) {
						ConsoleUtils.display("\tFound a @ManyToOne relation in " + modelName );
					} else
					
					// Many to Many relation
					if (relationType.equals("@ManyToMany")) {
						ConsoleUtils.display("\tFound a @ManyToMany relation in " + modelName );
					} else {
						
					}
				}
			}
		}
	}


	
	@SuppressWarnings("unchecked")
	public void generateAll() {
		ConsoleUtils.display(">> Generate Adapter...");
		
		for(Object modelEntity : this.entities.values()) {
			
			Map<String, Object> entity = (Map<String, Object>) modelEntity;
			this.localNameSpace = (String) entity.get(TagConstant.LOCAL_NAMESPACE);
			
			// Make class
			/*this.datamodel = new HashMap<String, Object>();
			this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
			this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	entity.get(TagConstant.SPACE));
			this.datamodel.put(TagConstant.NAME, 				entity.get(TagConstant.NAME));
			this.datamodel.put(TagConstant.LOCAL_NAMESPACE, 	this.localNameSpace);
			this.datamodel.put(TagConstant.IDS,					entity.get(TagConstant.IDS));
			this.datamodel.put(TagConstant.FIELDS,				entity.get(TagConstant.FIELDS));
			this.datamodel.put(TagConstant.RELATIONS,			entity.get(TagConstant.RELATIONS));*/
			this.datamodel = (HashMap<String, Object>) modelEntity;
			this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	entity.get(TagConstant.SPACE));
			this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
			
			this.generate();
		}
	}
	
	private void generate() {
		// Info
		ConsoleUtils.display(">>> Generate Adapter for " +  this.datamodel.get(TagConstant.NAME));
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			this.makeSourceControler(cfg, 
					"TemplateSQLiteAdapterBase.java", 
					"%sSQLiteAdapterBase.java", true);
			
			this.makeSourceControler(cfg, 
					"TemplateSQLiteAdapter.java", 
					"%sSQLiteAdapter.java", false);
			
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
}
