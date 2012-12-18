/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity class metadata */
public class ClassMetadata {
	/** Used for join tables (ManyToMany relations)*/
	public boolean isAssociationClass = false;
	
	/** Namespace of entity class */
	public String space = "";
	
	/** Name of entity class */
	public String name = "";
	
	/** List of fields of entity class*/
	public HashMap<String, FieldMetadata> fields = new HashMap<String, FieldMetadata>();

	/** List of ids of entity class*/
	public HashMap<String, FieldMetadata> ids = new HashMap<String, FieldMetadata>();
	
	/** List of relations of entity class*/
	public HashMap<String, FieldMetadata> relations = new HashMap<String, FieldMetadata>();
		
	/** Class inherited by the entity class or null if none*/
	public String extendType = null;
	
	/** Implemented class list of the entity class */
	public ArrayList<String> implementTypes = new ArrayList<String>();
	
	/** Implemented class list of the entity class */
	public ArrayList<MethodMetadata> methods = new ArrayList<MethodMetadata>();

	/** Imports of the class */
	public ArrayList<String> imports = new ArrayList<String>();
	
	
	/**
	 * Transform the class to a map of strings and maps (for each field) given an adapter
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	public Map<String, Object> toMap(BaseAdapter adapter){
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.SPACE, this.space);
		model.put(TagConstant.NAME, this.name);
		model.put(TagConstant.LOCAL_NAMESPACE, adapter.getNameSpaceEntity(this, adapter.getController()));
		model.put("isAssociationClass","false");
		if(isAssociationClass)
			model.put("isAssociationClass","true");
		//model.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(this));
		model.put(TagConstant.FIELDS, toFieldArray(this.fields.values(), adapter));
		model.put(TagConstant.IDS, toFieldArray(this.ids.values(), adapter));
		model.put(TagConstant.RELATIONS, toFieldArray(this.relations.values(), adapter));
		
		return model;
	}
	
	private ArrayList<Map<String,Object>> toFieldArray(Collection<FieldMetadata> c, BaseAdapter adapter){
		ArrayList<Map<String,Object>> SubFields = new ArrayList<Map<String,Object>>();
		for (FieldMetadata field : c) {
			Map<String, Object> subField = new HashMap<String, Object>();
			field.customize(adapter);
			subField = field.toMap();
			SubFields.add(subField);
		}
		return SubFields;
	}
}
