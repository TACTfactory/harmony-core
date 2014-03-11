package com.tactfactory.tracscan.entity;

import java.io.Serializable;

import com.tactfactory.harmony.annotation.Id;

public enum ProductType implements Serializable {
	/**
	 * Aluminium
	 */
	DOOR("aluminium"),
	/**
	 * STEEL.
	 */
	WINDOWS("windows");

	/** Type type. */
	@Id
	private String type;

	/**
	 * Enum Type of the alert.
	 * 
	 * @param value
	 *            Integer Type value
	 */
	private ProductType(final String value) {
		this.type = value;
	}

	/**
	 * Return the type.
	 * 
	 * @return String Type
	 */
	public final String getValue() {
		return this.type;
	}

	/**
	 * Get the type by its name if it exists.
	 * 
	 * @param value The type name
	 * @return the corresponding type. null if nothing is found.
	 */
	public static final ProductType fromValue(final String value) {
		ProductType ret = null;
		if (value != null) {
			for (final ProductType type : ProductType.values()) {
				if (value.equals(type.type)) {
					ret = type;
				}
			}
		}

		return ret;
	}
}
