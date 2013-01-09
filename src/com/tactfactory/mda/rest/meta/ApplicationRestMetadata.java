package com.tactfactory.mda.rest.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tactfactory.mda.orm.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class ApplicationRestMetadata implements BaseMetadata{
	private final String name = "rest";
	public LinkedHashMap<String, RestMetadata> entities = new LinkedHashMap<String, RestMetadata>();
	
	@Override
	public Map<String, Object> toMap(BaseAdapter adapter) {
		Map<String, Object> ret = new HashMap<String, Object>();
			ret.put(TagConstant.ENTITIES, entities);
		return ret;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
