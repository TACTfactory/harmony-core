package com.tactfactory.mda.rest.meta;

import java.util.HashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.rest.annotation.Rest.Security;

public class RestMetadata extends BaseMetadata{	
	private final String NAME = "rest";
	public boolean isEnabled = false;
	public Security security = Security.NONE;
	public String uri;
	
	
	public RestMetadata() {
		this.name = NAME;
	}
	
	
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("isEnabled", this.isEnabled);
		ret.put("uri", this.uri);
		ret.put("security", this.security.getValue());
		
		return ret;
	}
}
