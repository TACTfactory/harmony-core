package com.tactfactory.mda.rest.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tactfactory.mda.orm.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class ApplicationRestMetadata extends BaseMetadata{
	private static final String NAME = "rest";
	public LinkedHashMap<String, RestMetadata> entities = new LinkedHashMap<String, RestMetadata>();
	
	public ApplicationRestMetadata() {
		this.name = NAME;
	}
	
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		
		ret.put(TagConstant.ENTITIES, entities);
		
		return ret;
	}
}
