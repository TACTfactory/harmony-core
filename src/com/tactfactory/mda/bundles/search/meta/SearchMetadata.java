/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.search.meta;

import java.util.HashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

/**
 * Search Bundle Metadata.
 */
public class SearchMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "search";
	
	/**
	 * Constructor.
	 */
	public SearchMetadata() {
		super();
		this.name = NAME;
	}
	
	
	@Override
	public final HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> ret = new HashMap<String, Object>();
		
		return ret;
	}
}
