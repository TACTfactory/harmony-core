package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.template.TagConstant;

/** Entity relation Metadata*/
public class RelationMetadata {

	/** The type of relation */
	public String type;
	
	/** The entity's field which will be used for the relation*/
	public String field;
	
	/** The related entity */
	public String entity_ref;
	
	/** The related entity's field which will be used for the relation*/
	public ArrayList<String> field_ref = new ArrayList<String>();

	/** The Foreign Key name*/
	public String name;
	
	public Map<String, Object> toMap(){
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.NAME, this.name);
		model.put(TagConstant.TYPE, this.type);
		model.put(TagConstant.FIELD_REF, this.field_ref);
		model.put(TagConstant.ENTITY_REF, this.entity_ref);
		
		return model;
	}
}
