/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.orm.RelationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.SystemCommand;

public class WebServiceGenerator {
	protected List<ClassMetadata> metas;
	protected BaseAdapter adapter;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();
	protected String localNameSpace;
	private SystemCommand console;

	public WebServiceGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
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
			ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
			for (FieldMetadata field : meta.fields.values()) {
				Map<String, Object> modelField = new HashMap<String, Object>();
				field.customize(adapter);
				modelField.put(TagConstant.NAME, field.name);
				modelField.put(TagConstant.TYPE, field.type);
				
				modelFields.add(modelField);
			}
			modelClass.put(TagConstant.FIELDS, modelFields);

			// Make relations
			ArrayList<Map<String, Object>> modelRelations = new ArrayList<Map<String,Object>>();

			for (FieldMetadata relation : meta.relations.values()) {
				Map<String, Object> modelRelation = new HashMap<String, Object>();
				relation.customize(adapter);
				modelRelation.put(TagConstant.NAME, relation.name);
				modelRelation.put(TagConstant.TYPE, relation.type);
				modelRelation.put(TagConstant.RELATION_TYPE, relation.columnDefinition);
				
				modelRelations.add(modelRelation);
			}
			modelClass.put(TagConstant.RELATIONS, modelRelations);
			
			modelEntities.add(modelClass);
		}
		this.datamodel.put(TagConstant.PROJECT_NAME, 		Harmony.projectName);
		this.datamodel.put(TagConstant.PROJECT_NAMESPACE,	this.metas.get(0).space);
		this.datamodel.put(TagConstant.ENTITIES,			modelEntities);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE,		this.localNameSpace);
	}
	
	public void generateAll() {
		// Info
		ConsoleUtils.display(">> Generate WebService");
		
		List<String> command = new ArrayList<String>();
		command.add("php");
		command.add(String.format("%s/%s/%s", Harmony.symfonyPath, "app", "console") );

		this.console = new SystemCommand();
		this.console.executeCommand();
		
		ConsoleUtils.displayDebug(console.getStandardOutputFromCommand().toString());
	}
	
	private void generateSymfonyEntities() {

		//php app/console generate:doctrine:entity --non-interaction --entity=AcmeBlogBundle:Post --fields="title:string(100) body:text" --format=xml
		List<String> command = new ArrayList<String>();
		command.add("php");
		command.add(String.format("%s/%s/%s", Harmony.symfonyPath, "app", "console") );
		command.add("generate:doctrine:entity");
		command.add("--non-interaction");
		command.add("--entity=AcmeBlogBundle:Post");
		command.add("--fields=\"title:string(100) body:text\"");
		command.add("--format=xml");

		this.console.setCommandArgs(command);
		this.console.executeCommand();

	}
}
