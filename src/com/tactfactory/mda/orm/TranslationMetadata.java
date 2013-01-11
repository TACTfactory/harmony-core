/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.tactfactory.mda.Harmony;

public class TranslationMetadata {
	
	public static enum Group {
		NONE(0),
		COMMON(1),
		MODEL(2),
		PROVIDER(3),
		SERVICE(4);
		
		private int value;
		private Group(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
		
		public static Group fromValue(int value){
			for (Group group : Group.values()) {
				if (value == group.value) {
					return group;
				}    
			}
			
			return null;
		}
	}
	
	/** Identify string resource */
	public String key;
	
	/** Groups of string resource */
	public Group group = Group.NONE;
	
	/** Translate resources (by Locale) */
	public HashMap<Locale, String> i18n = new LinkedHashMap<Locale, String>();
	
	/**
	 * Insert to meta a new resource string
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static TranslationMetadata addDefaultTranslation(String key, String defaultValue) {		
		TranslationMetadata translateMeta = new TranslationMetadata();
		translateMeta.key = key;
		translateMeta.i18n.put(Locale.getDefault(), defaultValue);
		
		Harmony.metas.translates.put(translateMeta.key, translateMeta);
		
		return translateMeta;
	}
	
	public static TranslationMetadata addDefaultTranslation(String key, String defaultValue, Group group) {		
		TranslationMetadata translateMeta = new TranslationMetadata();
		translateMeta.key = key;
		translateMeta.group = group;
		translateMeta.i18n.put(Locale.getDefault(), defaultValue);
		
		Harmony.metas.translates.put(translateMeta.key, translateMeta);
		
		return translateMeta;
	}
}
