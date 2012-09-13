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

	private final int value;
	TargetPlateforme (int value) {
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
	
	public static TargetPlateforme parse(String target) {
		TargetPlateforme result = TargetPlateforme.ALL;;
		
		if (!Strings.isNullOrEmpty(target)) {
			if (target.equals("android"))
				result = TargetPlateforme.ANDROID;
			else
			if (target.equals("iphone"))
				result = TargetPlateforme.IPHONE;
			else
			if (target.equals("web"))
				result = TargetPlateforme.WEB;
		}
		
		return result;
	}
}
