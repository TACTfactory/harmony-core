/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class TranslationMetadata {

	/** Identify string resource */
	private String key;
	
	/** Groups of string resource */
	private Group group = Group.NONE;
	
	/** Translate resources (by Locale) */
	private HashMap<Locale, String> i18n = new LinkedHashMap<Locale, String>();
	
	
	public static enum Group {
		NONE(0),
		COMMON(1),
		MODEL(2),
		PROVIDER(3),
		SERVICE(4);
		
		private final int value;
		private Group(final int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
		
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
	 * Insert to meta a new resource string in the default group (Group.NONE)
	 * 
	 * @param key
	 * @param defaultValue
	 * @return the TranslationMetadata generated
	 */
	public static TranslationMetadata addDefaultTranslation(final String key, final String defaultValue) {		
		return addDefaultTranslation(key, defaultValue, Group.NONE);
	}
	
	/**
	 * Insert to meta a new resource string
	 * 
	 * @param key
	 * @param defaultValue
	 * @param group 
	 * @return the TranslationMetadata generated
	 */
	
	public static TranslationMetadata addDefaultTranslation(final String key, final String defaultValue, final Group group) {		
		final TranslationMetadata translateMeta = new TranslationMetadata();
		translateMeta.key = key;
		translateMeta.group = group;
		translateMeta.i18n.put(Locale.getDefault(), defaultValue);
		
		ApplicationMetadata.INSTANCE.translates.put(translateMeta.key, translateMeta);
		
		return translateMeta;
	}
	
	public final String getKey() {
		return key;
	}

	public final void setKey(final String key) {
		this.key = key;
	}

	public final Group getGroup() {
		return group;
	}

	public final void setGroup(final Group group) {
		this.group = group;
	}

	public final HashMap<Locale, String> getI18n() {
		return i18n;
	}

	public final void setI18n(final HashMap<Locale, String> i18n) {
		this.i18n = i18n;
	}
}
