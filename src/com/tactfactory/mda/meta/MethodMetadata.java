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
 * @author gregg
 *
 */
public class MethodMetadata extends BaseMetadata {	
	/** Return type. */
	public String type;
	
	/** Arguments types. */
	public List<String> argumentsTypes = new ArrayList<String>();
	
	/** Final. */
	public boolean isFinal;

	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put(TagConstant.TYPE, this.type);
		map.put("isFinal", this.isFinal);
		map.put("args", this.argumentsTypes);
		return map;
	}
}
