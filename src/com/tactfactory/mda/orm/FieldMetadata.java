/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.HashMap;

import com.tactfactory.mda.orm.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity field metadata */
public class FieldMetadata extends BaseMetadata {
	/** Owner */
	public ClassMetadata owner;
	
	/** Field type */
	public String type;
	
	/** Column name */
	public String columnName;
	
	/** Field database type */
	public String columnDefinition;
	
	/** Field optional */
	public HashMap<String, String> options; // (Not use...) for extra option of all bundle!
	
	/** Relation mapped to this field*/
	public RelationMetadata relation;
	
	public boolean nullable = false;
	public boolean unique = false;
	public boolean id = false;
	public int length = 255;
	public int precision = 0;
	public int scale = 0;
	public boolean hidden = false;
	
	/** GUI show field type */
	public String customShowType;
	
	/** GUI edit field type */
	public String customEditType;
	
	/** Is field hidden ? */
	public boolean internal = false;
	
	public FieldMetadata(ClassMetadata owner) {
		this.owner = owner;
	}
	
	/** Customize edit and show GUI field */
	public void customize(BaseAdapter adapter) {
		this.customShowType = adapter.getViewComponentShow(this);
		this.customEditType = adapter.getViewComponentEdit(this);
	}
	
	/** Add Component String of field */
	public void makeString(String componentName) {
		String key = owner.name.toLowerCase() + "_" + name.toLowerCase() + "_"+ componentName.toLowerCase();;
		TranslationMetadata.addDefaultTranslation(key, name, Group.MODEL);
	}
	
	/**
	 * Transform the field to a map of strings and a relation map
	 * @return the map
	 */
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter){
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put(TagConstant.NAME, 		this.name);
		model.put(TagConstant.TYPE, 		this.type);
		model.put(TagConstant.FIELD_NAME, 	this.columnName);
		model.put(TagConstant.FIELD_DEF, 	this.columnDefinition);
		model.put(TagConstant.HIDDEN, 		this.hidden);

		model.put(TagConstant.FIELD_CUSTOM_EDIT, 	this.customEditType);
		model.put(TagConstant.FIELD_CUSTOM_SHOW, 	this.customShowType);
		
		model.put(TagConstant.SCHEMA, 		SqliteAdapter.generateStructure(this));
		model.put(TagConstant.INTERNAL, 	this.internal);
		
		if(relation!=null){
			model.put(TagConstant.RELATION, this.relation.toMap(adapter));
		}
		
		return model;
	}
}
