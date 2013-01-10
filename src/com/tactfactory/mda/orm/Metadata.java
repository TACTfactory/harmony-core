package com.tactfactory.mda.orm;

import java.util.HashMap;

import com.tactfactory.mda.plateforme.BaseAdapter;

public interface Metadata {

	public abstract HashMap<String, Object> toMap(BaseAdapter adapter);
	public abstract String getName();
}
