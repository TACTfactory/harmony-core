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

import com.tactfactory.mda.bundles.rest.annotation.Rest.Security;
import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public class RestMetadata extends BaseMetadata {	
	private static final String NAME = "rest";
	public boolean isEnabled = false;
	public Security security = Security.NONE;
	public String uri;
	
	
	public RestMetadata() {
		super();
		this.name = NAME;
	}
	
	
	@Override
	public HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("isEnabled", this.isEnabled);
		ret.put("uri", this.uri);
		ret.put("security", this.security.getValue());
		
		return ret;
	}
}
