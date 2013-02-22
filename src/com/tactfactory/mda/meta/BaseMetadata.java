/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseMetadata implements Metadata {
	/** Component name. */
	public String name;
	
	/** List of bundles Metadata. */
	public Map<String, Metadata> options =
			new LinkedHashMap<String, Metadata>();
	
	public String getName() {
		return this.name;
	}
	
}
