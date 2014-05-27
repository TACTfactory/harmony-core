/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator.androidxml.manifest.enums;

public enum ScreenOrientation {
	UNSPECIFIED("unspecified"),
	BEHIND("behind"),
    LANDSCAPE("landscape"),
    PORTRAIT("portrait"),
    REVERSE_LANDSCAPE("reverseLandscape"),
    REVERSE_PORTRAIT("reversePortrait"),
    SENSOR_LANDSCAPE("sensorLandscape"),
    SENSOR_PORTRAIT("sensorPortrait"),
    USER_LANDSCAPE("userLandscape"),
    USER_PORTRAIT("userPortrait"),
    SENSOR("sensor"),
    FULL_SENSOR("fullSensor"),
    NO_SENSOR("nosensor"),        
    USER("user"),
    FULL_USER("fullUser"),
    LOCKED("locked");
	
	private String value;
	
	/**
	 * Private constructor.
	 * @param value The manifest value of this enum
	 */
	private ScreenOrientation(String value) {
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
	public ScreenOrientation fromValue(String value) {
		ScreenOrientation result = null;
		
		for (ScreenOrientation val : ScreenOrientation.values()) {
			if (val.getValue().equals(value)) {
				result = val;
				break;
			}
		}
		
		return result;
	}
}