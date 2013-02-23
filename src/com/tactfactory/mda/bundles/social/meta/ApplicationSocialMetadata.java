/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.social.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/**
 * Social metadata at application level.
 */
public class ApplicationSocialMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "social";
	/** Social entities. */
	public Map<String, ClassMetadata> entities =
			new LinkedHashMap<String, ClassMetadata>();
	
	/**
	 * Constructor.
	 */
	public ApplicationSocialMetadata() {
		super();
		this.name = NAME;
	}
	
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> ret = new HashMap<String, Object>();
		final Map<String, Object> entitiesMap = new HashMap<String, Object>();
		for (final ClassMetadata cm : this.entities.values()) {
			entitiesMap.put(cm.getName(), cm.toMap(adapter));
		}
		ret.put(TagConstant.ENTITIES, entitiesMap);
		
		return ret;
	}
}
