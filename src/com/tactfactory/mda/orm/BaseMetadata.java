package com.tactfactory.mda.orm;

import java.util.LinkedHashMap;

public abstract class BaseMetadata implements Metadata {
	/** Component name */
	public String name;
	
	/** List of bundles Metadata */
	public LinkedHashMap<String, Metadata> options = new LinkedHashMap<String, Metadata>();
	
	@Override
	public String getName(){
		return this.name;
	}
	
}
