/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.fixture.metadata;

import java.util.HashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

/**
 * Metadata for Fixture bundle.
 * @author gregg
 *
 */
public class FixtureMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "fixture";
	/** Fixture enabled state. */
	public boolean enabled = true;
	/** Fixture type (xml, yml). */
	public String type;
	
	/**
	 * Constructor.
	 */
	public FixtureMetadata() {
		super();
		this.name = NAME;
	}
	
	@Override
	public final HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("enabled", this.enabled);
			model.put("type", this.type);
		return model;
	}
}
