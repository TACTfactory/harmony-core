/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/**
 * Metadatas representing a Method.
 */
public class MethodMetadata extends BaseMetadata {	
	/** Return type. */
	private String type;
	
	/** Arguments types. */
	private List<String> argumentsTypes = new ArrayList<String>();
	
	/** Final. */
	private boolean isFinal;

	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put(TagConstant.TYPE, this.type);
		map.put("isFinal", this.isFinal);
		map.put("args", this.argumentsTypes);
		return map;
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
	 * @return the argumentsTypes
	 */
	public final List<String> getArgumentsTypes() {
		return argumentsTypes;
	}

	/**
	 * @param argumentsTypes the argumentsTypes to set
	 */
	public final void setArgumentsTypes(final List<String> argumentsTypes) {
		this.argumentsTypes = argumentsTypes;
	}

	/**
	 * @return the isFinal
	 */
	public final boolean isFinal() {
		return isFinal;
	}

	/**
	 * @param isFinal the isFinal to set
	 */
	public final void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}
}
