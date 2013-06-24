/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.fixture.metadata;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.meta.BaseMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;

/**
 * Metadata for Fixture bundle.
 */
public class FixtureMetadata extends BaseMetadata {
	/** Bundle name. */
	public static final String NAME = "fixture";
	/** Fixture enabled state. */
	private boolean enabled = true;
	/** Fixture type (xml, yml). */
	private String type;
	
	/**
	 * Constructor.
	 */
	public FixtureMetadata() {
		super();
		this.setName(NAME);
	}
	
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = new HashMap<String, Object>();
			model.put("enabled", this.enabled);
			model.put("type", this.type);
		return model;
	}

	/**
	 * @return the enabled
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public final void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(final String type) {
		this.type = type;
	}
}
