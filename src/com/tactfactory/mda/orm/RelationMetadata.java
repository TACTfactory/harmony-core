package com.tactfactory.mda.orm;

import java.util.ArrayList;

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
}
