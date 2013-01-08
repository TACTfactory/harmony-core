package com.tactfactory.mda.rest.meta;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.orm.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.rest.annotation.Rest.Security;

public class RestMetadata extends BaseMetadata{
	private String name = "rest";
	
	public boolean isEnabled = false;
	public Security security = Security.NONE;
	public String uri;
	
	@Override
	public Map<String, Object> toMap(BaseAdapter adapter) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("isEnabled", this.isEnabled);
		ret.put("uri", this.uri);
		ret.put("security", this.security.getValue());
		
		return ret;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
