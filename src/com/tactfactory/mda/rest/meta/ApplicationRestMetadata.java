package com.tactfactory.mda.rest.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class ApplicationRestMetadata extends BaseMetadata{
	private final String NAME = "rest";
	public LinkedHashMap<String, ClassMetadata> entities = new LinkedHashMap<String, ClassMetadata>();
	
	public ApplicationRestMetadata() {
		this.name = NAME;
	}
	
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		HashMap<String, Object> entitiesMap = new HashMap<String, Object>();
		for(ClassMetadata cm : this.entities.values()){
			entitiesMap.put(cm.getName(), cm.toMap(adapter));
		}
		ret.put(TagConstant.ENTITIES, entitiesMap);
		
		return ret;
	}
}
