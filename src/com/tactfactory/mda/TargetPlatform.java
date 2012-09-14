package com.tactfactory.mda;

import com.google.common.base.Strings;

public enum TargetPlateforme {
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
	TargetPlateforme (int value) {
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
	
	public static TargetPlateforme parse(String target) {
		TargetPlateforme result = TargetPlateforme.ALL;;
		
		if (!Strings.isNullOrEmpty(target)) {
			if (target.toLowerCase().equals(STRING_ANDROID))
				result = TargetPlateforme.ANDROID;
			else
				
			if (target.toLowerCase().equals(STRING_IPHONE))
				result = TargetPlateforme.IPHONE;
			else
				
			if (target.toLowerCase().equals(STRING_WEB))
				result = TargetPlateforme.WEB;
		}
		
		return result;
	}
}
