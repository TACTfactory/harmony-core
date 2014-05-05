/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme;

import com.google.common.base.Strings;

/**
 * Target platform.
 *
 */
public enum TargetPlatform {
	/** All platforms. */
	ALL		(0, 	"all"),
	/** Web. */
	WEB 	(1005, 	"web"),
	/** Android. */
	ANDROID (2004, 	"android"),
	/** iPhone. */
	IPHONE 	(3005, 	"iphone"),
	/** iPad. */
	IPAD	(3105, 	"ipad"),
	/** RIM. */
	RIM		(4006, 	"rim"),
	/** WinPhone. */
	WINPHONE(5007, 	"winphone");

	/** Value. */
	private final int value;

	/** Platform name. */
	private final String str;

	/**
	 * Constructor.
	 * @param v The value
	 * @param s The platform name
	 */
	TargetPlatform(final int v, final String s) {
		this.value = v;
		this.str = s;
	}

	/**
	 * Get the platform value.
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Get the platform name.
	 * @return the platform name
	 */
	public String toLowerString() {
		return this.str;
	}

	/**
	 * Parses a target to get the correct platform.
	 * @param target The target to parse
	 * @return The found platform. (All if nothing else found)
	 */
	public static TargetPlatform parse(final String target) {
		TargetPlatform result = TargetPlatform.ALL;

		if (!Strings.isNullOrEmpty(target)) {
			if (target.equalsIgnoreCase(
					TargetPlatform.ANDROID.toLowerString())) {
				result = TargetPlatform.ANDROID;
			} else

			if (target.equalsIgnoreCase(
					TargetPlatform.IPHONE.toLowerString())) {
				result = TargetPlatform.IPHONE;
			} else

			if (target.equalsIgnoreCase(
					TargetPlatform.WEB.toLowerString())) {
				result = TargetPlatform.WEB;
			}
		}

		return result;
	}
}
