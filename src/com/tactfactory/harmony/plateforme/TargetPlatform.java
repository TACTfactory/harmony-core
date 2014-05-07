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
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.plateforme.ios.IosAdapter;
import com.tactfactory.harmony.plateforme.rim.RimAdapter;
import com.tactfactory.harmony.plateforme.winphone.WinphoneAdapter;

/**
 * Target platform.
 *
 */
public enum TargetPlatform {
	/** All platforms. */
	ALL		(0, 	"all", null),
	/** Web. */
	WEB 	(1005, 	"web", null), //TODO WebAdapter.class),
	/** Android. */
	ANDROID (2004, 	"android", AndroidAdapter.class),
	/** iPhone. */
	IPHONE 	(3005, 	"iphone", IosAdapter.class),
	/** iPad. */
	IPAD	(3105, 	"ipad", IosAdapter.class),
	/** RIM. */
	RIM		(4006, 	"rim", RimAdapter.class),
	/** WinPhone. */
	WINPHONE(5007, 	"winphone", WinphoneAdapter.class);

	/** Value. */
	private final int value;

	/** Platform name. */
	private final String str;
	
	private final Class<?> adapterType;

	/**
	 * Constructor.
	 * @param v The value
	 * @param s The platform name
	 */
	TargetPlatform(final int v, final String s, Class<?> adapterType) {
		this.value = v;
		this.str = s;
		this.adapterType = adapterType;
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
	
	public Class<?> getAdapterType() {
	    return this.adapterType;
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
			}  else

			if (target.equalsIgnoreCase(
					TargetPlatform.WINPHONE.toLowerString())) {
				result = TargetPlatform.WINPHONE;
			} else

			if (target.equalsIgnoreCase(
					TargetPlatform.IPHONE.toLowerString())) {
				result = TargetPlatform.IPHONE;
			} else

		    if (target.equalsIgnoreCase(
		            TargetPlatform.RIM.toLowerString())) {
                result = TargetPlatform.RIM;
            } else

//		    if (target.equalsIgnoreCase(
//                    TargetPlatform.WINPHONE.toLowerString())) {
//                result = TargetPlatform.WINPHONE;
//            } else

			if (target.equalsIgnoreCase(
					TargetPlatform.WEB.toLowerString())) {
				result = TargetPlatform.WEB;
			}
		}

		return result;
	}
	
	/**
     * Parses a target to get the correct platform.
     * @param target The target to parse
     * @return The found platform. (All if nothing else found)
     */
    public static TargetPlatform parse(final BaseAdapter adapter) {
        TargetPlatform result = TargetPlatform.ALL;

        if (adapter != null) {
            if (TargetPlatform.ANDROID
                    .getAdapterType().equals(adapter.getClass())) {
                result = TargetPlatform.ANDROID;
            } else

            if (TargetPlatform.IPHONE
                    .getAdapterType().equals(adapter.getClass())) {
                result = TargetPlatform.IPHONE;
            } else

            if (TargetPlatform.RIM
                    .getAdapterType().equals(adapter.getClass())) {
                result = TargetPlatform.RIM;
            } else

            if (TargetPlatform.WINPHONE
                    .getAdapterType().equals(adapter.getClass())) {
                result = TargetPlatform.WINPHONE;
            } //else

//            if (TargetPlatform.WEB
//                    .getAdapterType().equals(adapter.getClass())) {
//                result = TargetPlatform.WEB;
//            }
        }

        return result;
    }
}
