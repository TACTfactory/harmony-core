package com.tactfactory.mda.search.meta;

import java.util.HashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public class SearchMetadata extends BaseMetadata{	
	private final String NAME = "search";
	
	
	public SearchMetadata() {
		this.name = NAME;
	}
	
	
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		
		return ret;
	}
}
