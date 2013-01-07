package com.tactfactory.mda.template;

import java.util.HashMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ApplicationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public abstract class BaseGenerator {
	protected ApplicationMetadata metas;	// Meta-models
	protected BaseAdapter adapter;			// Platform adapter
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	public BaseGenerator(BaseAdapter adapter) throws Exception {
		if (adapter == null)
			throw new Exception("No adapter define.");
		
		this.metas		= Harmony.metas;	// FIXME Clone object tree
		this.adapter	= adapter;
	}
}
