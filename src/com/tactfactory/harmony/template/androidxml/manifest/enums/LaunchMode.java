package com.tactfactory.harmony.template.androidxml.manifest.enums;

public enum LaunchMode {
	MULTIPLE("multiple"),
	SINGLE_TOP("singleTop"),
	SINGLE_TASK("singleTask"),
	SINGLE_INSTANCE("singleInstance");
	
	private String value;
	
	/**
	 * Private constructor.
	 * @param value The manifest value of this enum
	 */
	private LaunchMode(String value) {
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
	public LaunchMode fromValue(String value) {
		LaunchMode result = null;
		
		for (LaunchMode val : LaunchMode.values()) {
			if (val.getValue().equals(value)) {
				result = val;
				break;
			}
		}
		
		return result;
	}
}
