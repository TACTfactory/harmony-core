package com.tactfactory.mda.meta;

import java.util.ArrayList;
import java.util.Map;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class EnumMetadata extends ClassMetadata {
	/** Enum type.*/
	private String type;
	
	/** List of the enum names.*/
	private ArrayList<String> entries = new ArrayList<String>();
	
	/**
	 * Transform the class to a map given an adapter.
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = super.toMap(adapter);
		model.put(TagConstant.TYPE, type);
		model.put(TagConstant.NAMES, entries);
		return model;
	}
	
	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the names
	 */
	public final ArrayList<String> getEntries() {
		return entries;
	}

	/**
	 * @param names the names to set
	 */
	public final void setEntries(final ArrayList<String> entries) {
		this.entries = entries;
	}
}
