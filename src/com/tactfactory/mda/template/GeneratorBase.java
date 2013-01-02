package com.tactfactory.mda.template;

import java.util.HashMap;
import java.util.List;

import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public abstract class GeneratorBase {
	protected List<ClassMetadata> metas;	// Meta-models
	protected BaseAdapter adapter;			// Platform adapter
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	public GeneratorBase(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		if (metas == null && adapter == null)
			throw new Exception("No meta or adapter define.");
		
		this.metas		= metas;
		this.adapter	= adapter;
	}
}
