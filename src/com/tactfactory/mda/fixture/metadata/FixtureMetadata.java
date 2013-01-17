/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.fixture.metadata;

import java.util.HashMap;

import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public class FixtureMetadata extends BaseMetadata{
	private final static String NAME = "fixture"; 
	public boolean enabled = true;
	public String type;
	
	public FixtureMetadata(){
		this.name = NAME;
	}
	
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("enabled", enabled);
			model.put("type", type);
		return model;
	}

}
