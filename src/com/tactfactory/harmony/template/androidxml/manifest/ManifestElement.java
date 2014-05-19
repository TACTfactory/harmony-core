/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template.androidxml.manifest;

import org.jdom2.Element;
import org.jdom2.Namespace;

public abstract class ManifestElement implements ManifestConstants {
	
	protected void addAttribute(
			final Element element,
			final Namespace ns,
			final String attributeName,
			final String attribute) {
		if (attribute != null) {
			element.setAttribute(
					attributeName,
					attribute,
					ns);
		}
	}

	protected void addAttribute(
			final Element element,
			final Namespace ns,
			final String attributeName,
			final Boolean attribute) {
		if (attribute != null) {
			element.setAttribute(
					attributeName,
					String.valueOf(attribute),
					ns);
		}
	}

	/**
	 * Converts this activity to a XML element.
	 * @return The XML element
	 */
	public abstract Element toElement(Namespace ns);
}
