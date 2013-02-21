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
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	public Map<String, FieldMetadata> fields = new LinkedHashMap<String, FieldMetadata>();

	/** List of ids of entity class*/
	public Map<String, FieldMetadata> ids = new LinkedHashMap<String, FieldMetadata>();
	
	/** List of relations of entity class*/
	public Map<String, FieldMetadata> relations = new LinkedHashMap<String, FieldMetadata>();
		
	/** Class inherited by the entity class or null if none*/
	public String extendType;
	
	/** Implemented class list of the entity class */
	public List<String> implementTypes = new ArrayList<String>();
	
	/** Implemented class list of the entity class */
	public List<MethodMetadata> methods = new ArrayList<MethodMetadata>();

	/** Imports of the class */
	public List<String> imports = new ArrayList<String>();
	
	/** Add Component String of field */
	public void makeString(final String componentName) {
		final String key = this.name.toLowerCase() + "_"+ componentName.toLowerCase(Locale.ENGLISH);
		TranslationMetadata.addDefaultTranslation(key, this.name, Group.MODEL);
	}

	
	/**
	 * Transform the class to a map given an adapter
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = new HashMap<String, Object>();
		
		model.put(TagConstant.SPACE,			this.space);
		//model.put(TagConstant.PROJECT_NAME,		Harmony.metas.name);
		model.put(TagConstant.NAME,				this.name);
		model.put(TagConstant.EXTENDS,			this.extendType);
		model.put(TagConstant.CONTROLLER_NAMESPACE, adapter.getNameSpaceEntity(this, adapter.getController()));
		model.put(TagConstant.DATA_NAMESPACE, 	adapter.getNameSpace(this, adapter.getData()));
		model.put(TagConstant.TEST_NAMESPACE, 	adapter.getNameSpace(this, adapter.getTest()));
		model.put(TagConstant.FIELDS,			this.toFieldArray(this.fields.values(), adapter));
		model.put(TagConstant.IDS,				this.toFieldArray(this.ids.values(), adapter));
		model.put(TagConstant.RELATIONS,		this.toFieldArray(this.relations.values(), adapter));
		model.put(TagConstant.INTERNAL,			"false");
		
		if (this.internal) {
			model.put(TagConstant.INTERNAL,		"true");
		}
		
		final Map<String, Object> optionsModel = new HashMap<String, Object>();
		for (final Metadata option : this.options.values()) {
			optionsModel.put(option.getName(), option.toMap(adapter));
		}
		model.put(TagConstant.OPTIONS, optionsModel);
		
		return model;
	}
	
	private List<Map<String,Object>> toFieldArray(final Collection<FieldMetadata> c, final BaseAdapter adapter) {
		final List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> subField = null;
		
		for (final FieldMetadata field : c) {
			//field.customize(adapter);
			
			subField = field.toMap(adapter);
			
			// Add field translate
			if (!field.internal && !field.hidden) {
				field.makeString("label");
			}
			
			result.add(subField);
		}
		
		return result;
	}	
}
