package com.tactfactory.tracscan.entity;

import java.io.Serializable;

import com.tactfactory.harmony.annotation.Id;

public enum ItemState implements Serializable {
	/** Started item. */
	STARTED("start"),
	/** Stoped item. */
	STOPED("stop");

	/** Type type. */
	@Id
	private String type;

	/**
	 * Enum Type of the alert.
	 * 
	 * @param value Integer Type value
	 */
	private ItemState(final String value) {
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
	public static final ItemState fromValue(final String value) {
		ItemState ret = null;
		if (value != null) {
			for (final ItemState type : ItemState.values()) {
				if (value.equals(type.type)) {
					ret = type;
				}
			}
		}

		return ret;
	}
}
