/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.rest.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class ApplicationRestMetadata extends BaseMetadata{
	private final static String NAME = "rest";
	public LinkedHashMap<String, ClassMetadata> entities = new LinkedHashMap<String, ClassMetadata>();
	
	public ApplicationRestMetadata() {
		super();
		this.name = NAME;
	}
	
	@Override
	public HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> ret = new HashMap<String, Object>();
		final HashMap<String, Object> entitiesMap = new HashMap<String, Object>();
		for (final ClassMetadata cm : this.entities.values()){
			entitiesMap.put(cm.getName(), cm.toMap(adapter));
		}
		ret.put(TagConstant.ENTITIES, entitiesMap);
		
		return ret;
	}
}
