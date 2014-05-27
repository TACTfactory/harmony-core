/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator.androidxml.manifest.enums;

public enum SoftInputMode {
	STATE_UNSPECIFIED("stateUnspecified"),
	STATE_UNCHANGED("stateUnchanged"),
	STATE_HIDDEN("stateHidden"),
	STATE_ALWAYS_HIDDEN("stateAlwaysHidden"),
    STATE_VISIBLE("stateVisible"),
    STATE_ALWAYS_VISIBLE("stateAlwaysVisible"),
    ADJUST_UNSPECIFIED("adjustUnspecified"),
    ADJUST_RESIZE("adjustResize"),
    ADJUST_PAN("adjustPan");
	
	private String value;
	
	/**
	 * Private constructor.
	 * @param value The manifest value of this enum
	 */
	private SoftInputMode(String value) {
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
	public SoftInputMode fromValue(String value) {
		SoftInputMode result = null;
		
		for (SoftInputMode val : SoftInputMode.values()) {
			if (val.getValue().equals(value)) {
				result = val;
				break;
			}
		}
		
		return result;
	}
}
