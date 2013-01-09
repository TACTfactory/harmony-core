package com.tactfactory.mda.orm;

import java.util.Map;

import com.tactfactory.mda.plateforme.BaseAdapter;

public interface BaseMetadata {

	public abstract Map<String, Object> toMap(BaseAdapter adapter);
	public abstract String getName();
}
