/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator.androidxml.manifest.enums;

public enum ConfigChange {
	MCC("mcc"),
	MNC("mnc"),
	LOCALE("locale"),
    TOUCHSCREEN("touchscreen"),
    KEYBOARD("keyboard"),
    KEYBOARD_HIDDEN("keyboardHidden"),
    NAVIGATION("navigation"),
    SCREEN_LAYOUT("screenLayout"),
    FONT_SCALE("fontScale"),
    UI_MODE("uiMode"),
    ORIENTATION("orientation"),
    SCREEN_SIZE("screenSize"),
    SMALLEST_SCREEN_SIZE("smallestScreenSize");
	
	private String value;
	
	/**
	 * Private constructor.
	 * @param value The manifest value of this enum
	 */
	private ConfigChange(String value) {
		this.value = value;
	}
	
	/**
	 * Gets this enum's value.
	 * @return The value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Returns the enum corresponding to the given value.
	 * @param value The value
	 * @return The enum found. Null if none
	 */
	public ConfigChange fromValue(String value) {
		ConfigChange result = null;
		
		for (ConfigChange val : ConfigChange.values()) {
			if (val.getValue().equals(value)) {
				result = val;
				break;
			}
		}
		
		return result;
	}
}