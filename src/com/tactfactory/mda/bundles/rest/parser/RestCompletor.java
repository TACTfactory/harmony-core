/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.rest.parser;

import com.tactfactory.mda.bundles.rest.meta.RestMetadata;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;

/**
 * Completor for rest bundle.
 */
public class RestCompletor {
	
	/**
	 * Store all rest entities in an ApplicationRestMetadata class.
	 * @param am The ApplicationMetadata.
	 */
	public final void generateApplicationRestMetadata(
			final ApplicationMetadata am) {
		for (final ClassMetadata cm : am.getEntities().values()) {
			if (cm.getOptions().containsKey("rest")) {
				final RestMetadata rm = 
						(RestMetadata) cm.getOptions().get("rest");
				if (rm.getUri() == null || rm.getUri().equals("")) {
					rm.setUri(cm.getName());
				}
			}
		}
	}
}
