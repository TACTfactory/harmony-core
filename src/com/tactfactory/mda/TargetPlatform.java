/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import com.google.common.base.Strings;

public enum TargetPlatform {
	ALL		(0, 	"all"),
	WEB 	(1005, 	"web"),
	ANDROID (2004, 	"android"),
	IPHONE 	(3005, 	"iphone"),
	IPAD	(3105, 	"ipad"),
	RIM		(4006, 	"rim"),
	WINPHONE(5007, 	"winphone");

	private final int value;
	private final String str;
	TargetPlatform(final int value, final String str) {
		this.value = value;
		this.str = str;
	}
	public int getValue() {
		return this.value;
	}
	
	public String toLowerString() {		
		return this.str;
	}
	
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
