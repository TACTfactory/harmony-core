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
	private boolean isEnabled = false;
	/** Security level. */
	private Security security = Security.NONE;
	/** URI. */
	private String uri;
	
	/**
	 * Constructor.
	 */
	public RestMetadata() {
		super();
		this.setName(NAME);
	}
	
	
	@Override
	public final HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("isEnabled", this.isEnabled);
		ret.put("uri", this.uri);
		ret.put("security", this.security.getValue());
		
		return ret;
	}


	/**
	 * @return the isEnabled
	 */
	public final boolean isEnabled() {
		return isEnabled;
	}


	/**
	 * @param isEnabled the isEnabled to set
	 */
	public final void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	/**
	 * @return the security
	 */
	public final Security getSecurity() {
		return security;
	}


	/**
	 * @param security the security to set
	 */
	public final void setSecurity(final Security security) {
		this.security = security;
	}


	/**
	 * @return the uri
	 */
	public final String getUri() {
		return uri;
	}


	/**
	 * @param uri the uri to set
	 */
	public final void setUri(final String uri) {
		this.uri = uri;
	}
}
