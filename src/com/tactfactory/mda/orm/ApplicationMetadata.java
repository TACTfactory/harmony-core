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
import java.util.TreeMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public class ApplicationMetadata extends BaseMetadata {	
	/** Project NameSpace (com/tactfactory/mda/test/demact) */
	public String projectNameSpace;
	
	/** List of Entity of entity class */
	public LinkedHashMap<String, ClassMetadata> entities = new LinkedHashMap<String, ClassMetadata>();
	
	/** List of string use in application */
	public TreeMap<String, TranslationMetadata> translates = new TreeMap<String, TranslationMetadata>();

	/** List of config use in application */
	public TreeMap<String, ConfigMetadata> configs = new TreeMap<String, ConfigMetadata>();
	
	/**
	 * Transform the application to a map of strings and maps (for each field) given an adapter
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapt){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		HashMap<String, Object> entitiesMap = new HashMap<String, Object>();
		
		// Make Map for entities
		for(ClassMetadata cm : this.entities.values()){
			entitiesMap.put(cm.name, cm.toMap(adapt));
			cm.makeString("label");
		}
		
		// Add root
		ret.put(TagConstant.PROJECT_NAME, 		this.name);
		ret.put(TagConstant.PROJECT_NAMESPACE, 	this.projectNameSpace.replaceAll("/", "\\."));
		ret.put(TagConstant.DATA_NAMESPACE, 	this.projectNameSpace.replaceAll("/", "\\.")+"."+adapt.getData());
		ret.put(TagConstant.SERVICE_NAMESPACE, 	this.projectNameSpace.replaceAll("/", "\\.")+"."+adapt.getService());

		ret.put(TagConstant.ENTITIES, 			entitiesMap);
		
		ret.put(TagConstant.ANDROID_SDK_DIR, Harmony.androidSdkPath);
		ret.put(TagConstant.ANT_ANDROID_SDK_DIR, new TagConstant.AndroidSDK("${sdk.dir}"));
		ret.put(TagConstant.OUT_CLASSES_ABS_DIR, "CLASSPATHDIR/");
		ret.put(TagConstant.OUT_DEX_INPUT_ABS_DIR, "DEXINPUTDIR/");
		
		// Add Extra bundle
		HashMap<String, Object> optionsMap = new HashMap<String, Object>();
		for(Metadata bm : options.values()){
			optionsMap.put(bm.getName(), bm.toMap(adapt));
		}
		ret.put(TagConstant.OPTIONS, optionsMap);
		
		return ret;
	}
}
