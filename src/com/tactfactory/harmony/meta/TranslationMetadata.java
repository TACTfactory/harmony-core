/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * Translations metadata.
 *
 */
public final class TranslationMetadata {

	/** Identify string resource. */
	private String key;

	/** Groups of string resource. */
	private Group group = Group.NONE;

	/** Translate resources (by Locale). */
	private HashMap<Locale, String> i18n = new LinkedHashMap<Locale, String>();

	/** Translation group. */
	public static enum Group {
		/** No Group. */
		NONE(0),
		/** Common Group. */
		COMMON(1),
		/** Model Group. */
		MODEL(2),
		/** Provider Group. */
		PROVIDER(3),
		/** Service Group. */
		SERVICE(4);

		/** Group id. */
		private final int value;

		/**
		 * Constructor.
		 * @param v The group id.
		 */
		private Group(final int v) {
			this.value = v;
		}

		/**
		 * Get the Group id.
		 * @return The group id.
		 */
		public int getValue() {
			return this.value;
		}

		/**
		 * Get the Group by its id.
		 * @param value The id.
		 * @return The group corresponding to the ID.
		 * Null if none is corresponding.
		 */
		public static Group fromValue(final int value) {
			Group ret = null;
			for (final Group group : Group.values()) {
				if (value == group.value) {
					ret = group;
				}
			}

			return ret;
		}
	}

	/**
	 * Insert to meta a new resource string in the default group (Group.NONE).
	 *
	 * @param key The translation key
	 * @param defaultValue The translation value
	 * @return the TranslationMetadata generated
	 */
	public static TranslationMetadata addDefaultTranslation(final String key,
			final String defaultValue) {
		return addDefaultTranslation(key, defaultValue, Group.NONE);
	}

	/**
	 * Insert to meta a new resource string.
	 *
	 * @param key The translation key
	 * @param defaultValue The translation value
	 * @param group  The translation group
	 * @return the TranslationMetadata generated
	 */

	public static TranslationMetadata addDefaultTranslation(final String key,
			final String defaultValue,
			final Group group) {
		final TranslationMetadata translateMeta = new TranslationMetadata();
		translateMeta.key = key.toLowerCase(Locale.US);
		translateMeta.group = group;
		translateMeta.i18n.put(Locale.getDefault(), defaultValue);

		ApplicationMetadata.INSTANCE.getTranslates().put(translateMeta.key,
				translateMeta);

		return translateMeta;
	}

	/**
	 * Get the translation key.
	 * @return the Translation key
	 */
	public final String getKey() {
		return key;
	}

	/**
	 * Set the translation key.
	 * @param k the new key
	 */
	public final void setKey(final String k) {
		this.key = k;
	}

	/**
	 * Get the translation Group.
	 * @return the Translation Group
	 */
	public final Group getGroup() {
		return group;
	}

	/**
	 * Set the translation group.
	 * @param g the new Group
	 */
	public final void setGroup(final Group g) {
		this.group = g;
	}

	/**
	 * Get the translation I18n.
	 * @return the Translation I18n
	 */
	public final HashMap<Locale, String> getI18n() {
		return i18n;
	}

	/**
	 * Set the translation I18n.
	 * @param i the new I18b
	 */
	public final void setI18n(final HashMap<Locale, String> i) {
		this.i18n = i;
	}
}
