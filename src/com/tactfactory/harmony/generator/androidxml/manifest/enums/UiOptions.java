package com.tactfactory.harmony.generator.androidxml.manifest.enums;

public enum UiOptions {
	NONE("None"),
	SPLIT_ACTION_BAR_WHEN_NARROW("splitActionBarWhenNarrow");
	
	private String value;
	
	/**
	 * Private constructor.
	 * @param value The manifest value of this enum
	 */
	private UiOptions(String value) {
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
	public UiOptions fromValue(String value) {
		UiOptions result = null;
		
		for (UiOptions val : UiOptions.values()) {
			if (val.getValue().equals(value)) {
				result = val;
				break;
			}
		}
		
		return result;
	}
}
