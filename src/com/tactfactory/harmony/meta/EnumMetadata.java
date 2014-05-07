/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.TagConstant;

/**
 * Enum Metadata.
 */
public class EnumMetadata extends ClassMetadata {
	/** ID field name. */
	private String idName;

	/** Enum type.*/
	private String type;

	/** List of the enum names.*/
	private ArrayList<String> entries = new ArrayList<String>();
	
	private Map<String, String> values =
			new HashMap<String, String>();

	/**
	 * Transform the class to a map given an adapter.
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = super.toMap(adapter);
		model.put(TagConstant.ID, this.idName);
		model.put(TagConstant.TYPE, this.type);
		model.put(TagConstant.NAMES, this.entries);
		model.put(TagConstant.VALUES, this.values);
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
	 * @param entries the entries to set
	 */
	public final void setEntries(final ArrayList<String> entries) {
		this.entries = entries;
	}

	/**
	 * @return the idName
	 */
	public final String getIdName() {
		return idName;
	}

	/**
	 * @param idName the idName to set
	 */
	public final void setIdName(final String idName) {
		this.idName = idName;
	}

	/**
	 * @return the values
	 */
	public final Map<String, String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public final void setValues(Map<String, String> values) {
		this.values = values;
	}
}
