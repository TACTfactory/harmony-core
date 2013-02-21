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

public class SocialMetadata extends BaseMetadata {	
	private static final String NAME = "social";
	public boolean isEnabled = false;
	
	public SocialMetadata() {
		super();
		this.name = NAME;
	}
	
	@Override
	public Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("isEnabled", this.isEnabled);
		
		return ret;
	}
}
