/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity class metadata */
public class ClassMetadata extends BaseMetadata {

	/** Used for join tables (ManyToMany relations)*/
	public boolean internal = false;
	
	/** Namespace of entity class */
	public String space = "";

	/** List of fields of entity class*/
	public LinkedHashMap<String, FieldMetadata> fields = new LinkedHashMap<String, FieldMetadata>();

	/** List of ids of entity class*/
	public LinkedHashMap<String, FieldMetadata> ids = new LinkedHashMap<String, FieldMetadata>();
	
	/** List of relations of entity class*/
	public LinkedHashMap<String, FieldMetadata> relations = new LinkedHashMap<String, FieldMetadata>();
		
	/** Class inherited by the entity class or null if none*/
	public String extendType = null;
	
	/** Implemented class list of the entity class */
	public ArrayList<String> implementTypes = new ArrayList<String>();
	
	/** Implemented class list of the entity class */
	public ArrayList<MethodMetadata> methods = new ArrayList<MethodMetadata>();

	/** Imports of the class */
	public ArrayList<String> imports = new ArrayList<String>();
	
	/** Add Component String of field */
	public void makeString(String componentName) {
		String key = name.toLowerCase() + "_"+ componentName.toLowerCase();
		TranslationMetadata.addDefaultTranslation(key, name, Group.MODEL);
	}

	
	/**
	 * Transform the class to a map of strings and maps (for each field) given an adapter
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter){
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put(TagConstant.SPACE,			this.space);
		model.put(TagConstant.PROJECT_NAME,		Harmony.metas.name);
		model.put(TagConstant.NAME,				this.name);
		model.put(TagConstant.CONTROLLER_NAMESPACE, adapter.getNameSpaceEntity(this, adapter.getController()));
		model.put(TagConstant.DATA_NAMESPACE, 	adapter.getNameSpace(this, adapter.getData()));
		model.put(TagConstant.TEST_NAMESPACE, 	adapter.getNameSpace(this, adapter.getTest()));
		model.put(TagConstant.FIELDS,			this.toFieldArray(this.fields.values(), adapter));
		model.put(TagConstant.IDS,				this.toFieldArray(this.ids.values(), adapter));
		model.put(TagConstant.RELATIONS,		this.toFieldArray(this.relations.values(), adapter));
		model.put(TagConstant.INTERNAL,			"false");
		
		if(this.internal)
			model.put(TagConstant.INTERNAL,		"true");
		
		HashMap<String, Object> optionsModel = new HashMap<String, Object>();
		for(Metadata option : options.values()){
			optionsModel.put(option.getName(), option.toMap(adapter));
		}
		model.put(TagConstant.OPTIONS, optionsModel);
		
		return model;
	}
	
	private ArrayList<Map<String,Object>> toFieldArray(Collection<FieldMetadata> c, BaseAdapter adapter){
		ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> subField = null;
		
		for (FieldMetadata field : c) {
			field.customize(adapter);
			
			subField = new HashMap<String, Object>();
			subField = field.toMap(adapter);
			
			// Add field translate
			if (!field.internal && !field.hidden)
				field.makeString("label");
			
			result.add(subField);
		}
		
		return result;
	}	
}
