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
import java.util.Map;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

/**
 * Social metadata.
 *
 */
public class SocialMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "social";
	/** Is Social enabled ? */
	public boolean isEnabled = false;
	
	/** 
	 * Constructor.
	 */
	public SocialMetadata() {
		super();
		this.name = NAME;
	}
	
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("isEnabled", this.isEnabled);
		
		return ret;
	}
}
