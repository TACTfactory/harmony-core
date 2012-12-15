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
import java.util.Map;

import com.tactfactory.mda.orm.annotation.Column.Type;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity field metadata */
public class FieldMetadata {
	
	/** Field name */
	public String name;
	
	/** Field type */
	public String type;
	
	/** Field name */
	public String name_in_db;
	
	/** Field type as given in attribute */
	public Type type_attribute;
	
	
	/** Field database type */
	public String columnDefinition;
	
	/** Field optional */
	public HashMap<String, String> options; // (Not use...) for extra option of all bundle!
	
	public boolean nullable = false;
	public boolean unique = false;
	public int length = 255;
	public int precision = 0;
	public int scale = 0;
	
	/** GUI show field type */
	public String customShowType;
	
	/** GUI edit field type */
	public String customEditType;
	
	/** Customize edit and show GUI field */
	public void customize(BaseAdapter adapter) {
		this.customShowType = adapter.getViewComponentShow(this);
		this.customEditType = adapter.getViewComponentEdit(this);
	}
	
	/**
	 * Transform the field to a map of strings and a relation map
	 * @return the map
	 */
	public Map<String, Object> toMap(){
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.NAME, this.name);
		model.put(TagConstant.TYPE, this.type);
		model.put("columnDefinition", this.columnDefinition);
		//model.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(this));

		model.put("customEditType", this.customEditType);
		model.put("customShowType", this.customShowType);
		
		model.put(TagConstant.SCHEMA, SqliteAdapter.generateStructure(this));
		
		if(relation!=null){
			model.put(TagConstant.RELATION, this.relation.toMap());
		}
		
		return model;
	}
	
	/** Relation mapped to this field*/
	public RelationMetadata relation;
}
