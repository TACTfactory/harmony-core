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
	ALL		(0),
	WEB 	(1005),
	ANDROID (2004),
	IPHONE 	(3005),
	IPAD	(3105),
	RIM		(4006),
	WINPHONE(5007);
	
	private static final String STRING_ALL 		= "all";
	private static final String STRING_WEB 		= "web";
	private static final String STRING_ANDROID	= "android";
	private static final String STRING_IPHONE 	= "iphone";
	private static final String STRING_IPAD 	= "ipad";
	private static final String STRING_RIM 		= "rim";
	private static final String STRING_WINPHONE = "winphone";

	private final int value;
	TargetPlatform (final int value) {
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
	
	public String toLowerString() {
		String result = STRING_ALL;
		
		switch (this.value) {
		case 1005:
			result = STRING_WEB;
			break;
		case 2004:
			result = STRING_ANDROID;
			break;
		case 3005:
			result = STRING_IPHONE;
			break;
		case 3105:
			result = STRING_IPAD;
			break;
		case 4006:
			result = STRING_RIM;
			break;
		case 5007:
			result = STRING_WINPHONE;
			break;
		default:
			break;
		}
		
		
		return result;
	}
	
	public static TargetPlatform parse(final String target) {
		TargetPlatform result = TargetPlatform.ALL;
		
		if (!Strings.isNullOrEmpty(target)) {
			if (target.equalsIgnoreCase(STRING_ANDROID)){
				result = TargetPlatform.ANDROID;
			} else
				
			if (target.equalsIgnoreCase(STRING_IPHONE)) {
				result = TargetPlatform.IPHONE;
			} else 
				
			if (target.equalsIgnoreCase(STRING_WEB)) {
				result = TargetPlatform.WEB;
			}
		}
		
		return result;
	}
}
