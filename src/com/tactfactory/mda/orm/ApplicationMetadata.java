package com.tactfactory.mda.orm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class ApplicationMetadata {
	/** Project name (demact) */
	public String projectName;
	
	/** Project NameSpace (com/tactfactory/mda/test/demact) */
	public String projectNameSpace;
	
	/** List of Entity of entity class*/
	public LinkedHashMap<String, ClassMetadata> entities = new LinkedHashMap<String, ClassMetadata>();
	
	public Map<String, Object> toMap(BaseAdapter adapt){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> entitiesMap = new HashMap<String, Object>();
		for(ClassMetadata cm : this.entities.values()){
			entitiesMap.put(cm.name, cm.toMap(adapt));
		}
		
		ret.put(TagConstant.PROJECT_NAME, this.projectName);
		ret.put(TagConstant.PROJECT_NAMESPACE, this.projectNameSpace);
		ret.put(TagConstant.ENTITIES, entitiesMap);
		return ret;
	}
}
