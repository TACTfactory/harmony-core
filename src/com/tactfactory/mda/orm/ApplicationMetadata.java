package com.tactfactory.mda.orm;

import java.util.LinkedHashMap;

public class ApplicationMetadata {
	/** Project name (demact) */
	public String projectName;
	
	/** Project NameSpace (com/tactfactory/mda/test/demact) */
	public String projectNameSpace;
	
	/** List of Entity of entity class*/
	public LinkedHashMap<String, ClassMetadata> entities = new LinkedHashMap<String, ClassMetadata>();
	
	
}
