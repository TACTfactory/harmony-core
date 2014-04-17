/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.Map;

import com.tactfactory.harmony.plateforme.IAdapter;

/**
 * Defines the Metadatas class which will be used by Freemarker.
 */
public interface Metadata {

	/** Converts the Metadata to a map usable for Freemaker.
	 *
	 * @param adapter The adapter to use for the conversion
	 * @return The generated map
	 **/
	Map<String, Object> toMap(IAdapter adapter);

	/**
	 * The key defining this Metadata in the global map.
	 * This will be used to call the metadatas in Freemarker templates.
	 * @return The key of the Metadata
	 */
	String getName();
}
