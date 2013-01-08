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
	public String key;
	
	public HashMap<Locale, String> i18n = new LinkedHashMap<Locale, String>();
	
	public static TranslationMetadata addDefaultTranslation(String key, String defaultValue) {		
		TranslationMetadata translateMeta = new TranslationMetadata();
		translateMeta.key = key;
		translateMeta.i18n.put(Locale.getDefault(), defaultValue);
		
		Harmony.metas.translates.put(translateMeta.key, translateMeta);
		
		return translateMeta;
	}
}
