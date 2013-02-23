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

/**
 * Metadata for Bundle Rest.
 */
public class RestMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "rest";
	/** Bundle enable state. */
	public boolean isEnabled = false;
	/** Security level. */
	public Security security = Security.NONE;
	/** URI. */
	public String uri;
	
	/**
	 * Constructor.
	 */
	public RestMetadata() {
		super();
		this.name = NAME;
	}
	
	
	@Override
	public final HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("isEnabled", this.isEnabled);
		ret.put("uri", this.uri);
		ret.put("security", this.security.getValue());
		
		return ret;
	}
}
