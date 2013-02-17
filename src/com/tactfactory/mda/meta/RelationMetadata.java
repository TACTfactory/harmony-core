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
import java.util.HashMap;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity relation Metadata*/
public class RelationMetadata extends BaseMetadata {

	/** The type of relation */
	public String type;
	
	/** The entity's field which will be used for the relation*/
	public String field;
	
	/** The related entity */
	public String entity_ref;
	
	/** The related entity's field which will be used for the relation*/
	public ArrayList<String> field_ref = new ArrayList<String>();

	/** Inversed by (in case of OneToMany)*/
	public String mappedBy;
	
	/** Inversed by (in case of ManyToOne)*/
	public String inversedBy;

	/** Name of the join table used to join ManyToMany relations*/
	public String joinTable;
	
	/**
	 * Transform the relation to a field of map of strings
	 * @return the generated HashMap
	 */
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter){
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.NAME, this.name);
		model.put(TagConstant.TYPE, this.type);
		model.put(TagConstant.FIELD_REF, this.field_ref);
		model.put(TagConstant.ENTITY_REF, this.entity_ref);
		if(inversedBy!=null)
			model.put("inversedBy", this.inversedBy);
		if(mappedBy!=null)
			model.put("mappedBy", this.mappedBy);
		model.put("joinTable", this.joinTable);
		
		return model;
	}
}
