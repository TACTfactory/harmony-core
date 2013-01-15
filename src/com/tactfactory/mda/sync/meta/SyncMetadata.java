package com.tactfactory.mda.sync.meta;

import java.util.HashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.sync.annotation.Sync.Level;
import com.tactfactory.mda.sync.annotation.Sync.Mode;
import com.tactfactory.mda.template.TagConstant;

public class SyncMetadata extends BaseMetadata{
	private final static String NAME = "sync";
	
	public Level level = Level.GLOBAL;
	public Mode mode = Mode.POOLING;
	public int priority = 1;
	
	public SyncMetadata(){
		this.name = NAME;
	}
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.LEVEL, level.getValue());
		model.put(TagConstant.MODE, mode.getValue());
		model.put(TagConstant.PRIORITY, priority);
		return model;
	}

}
