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

public class FixtureMetadata extends BaseMetadata{
	private final static String NAME = "fixture"; 
	public boolean enabled = true;
	public String type;
	
	public FixtureMetadata() {
		super();
		this.name = NAME;
	}
	
	@Override
	public HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("enabled", this.enabled);
			model.put("type", this.type);
		return model;
	}

	public boolean getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(final String type) {
		this.type = type;
	}
}
