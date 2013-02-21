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
import java.util.Map;
import java.util.TreeMap;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

public final class ApplicationMetadata extends BaseMetadata {
	private final static String PATH_DELIMITER = "/";
	private final static String PACKAGE_DELIMITER = "\\.";
	
	/** Singleton*/
	public final static ApplicationMetadata INSTANCE = new ApplicationMetadata();
	
	/** Android SDK Path*/
	public static String androidSdkPath;
	
	/** Project NameSpace (com/tactfactory/mda/test/demact) */
	public String projectNameSpace;
	
	/** List of Entity of entity class */
	public Map<String, ClassMetadata> entities = new LinkedHashMap<String, ClassMetadata>();
	
	/** List of string use in application */
	public Map<String, TranslationMetadata> translates = new TreeMap<String, TranslationMetadata>();

	/** List of config use in application */
	public Map<String, ConfigMetadata> configs = new TreeMap<String, ConfigMetadata>();
	
	private ApplicationMetadata() {}
	
	/**
	 * Transform the application to a map given an adapter
	 * @param adapt The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public Map<String, Object> toMap(final BaseAdapter adapt) {
		final Map<String, Object> ret = new HashMap<String, Object>();
		final Map<String, Object> entitiesMap = new HashMap<String, Object>();
		
		// Make Map for entities
		for (final ClassMetadata cm : this.entities.values()) {
			entitiesMap.put(cm.name, cm.toMap(adapt));
			cm.makeString("label");
		}
		
		// Add root
		ret.put(TagConstant.PROJECT_NAME, 		this.name);
		ret.put(TagConstant.PROJECT_PATH, 		this.projectNameSpace);
		ret.put(TagConstant.PROJECT_NAMESPACE, 	this.projectNameSpace.replaceAll(PATH_DELIMITER, PACKAGE_DELIMITER));
		ret.put(TagConstant.ENTITY_NAMESPACE, 	this.projectNameSpace.replaceAll(PATH_DELIMITER, PACKAGE_DELIMITER)+"."+adapt.getModel());
		ret.put(TagConstant.TEST_NAMESPACE, 	this.projectNameSpace.replaceAll(PATH_DELIMITER, PACKAGE_DELIMITER)+"."+adapt.getTest());
		ret.put(TagConstant.DATA_NAMESPACE, 	this.projectNameSpace.replaceAll(PATH_DELIMITER, PACKAGE_DELIMITER)+"."+adapt.getData());
		ret.put(TagConstant.SERVICE_NAMESPACE, 	this.projectNameSpace.replaceAll(PATH_DELIMITER, PACKAGE_DELIMITER)+"."+adapt.getService());
		ret.put(TagConstant.FIXTURE_NAMESPACE, 	this.projectNameSpace.replaceAll(PATH_DELIMITER, PACKAGE_DELIMITER)+"."+adapt.getFixture());

		ret.put(TagConstant.ENTITIES, 			entitiesMap);
		
		ret.put(TagConstant.ANDROID_SDK_DIR, ApplicationMetadata.androidSdkPath);
		// SDKDIR Hack
		final HashMap<String, String> sdkDir = new HashMap<String, String>();
		sdkDir.put("dir", "${sdk.dir}");
		ret.put(TagConstant.ANT_ANDROID_SDK_DIR, sdkDir);
		ret.put(TagConstant.OUT_CLASSES_ABS_DIR, "CLASSPATHDIR/");
		ret.put(TagConstant.OUT_DEX_INPUT_ABS_DIR, "DEXINPUTDIR/");
		
		// Add Extra bundle
		final HashMap<String, Object> optionsMap = new HashMap<String, Object>();
		for (final Metadata bm : this.options.values()) {
			optionsMap.put(bm.getName(), bm.toMap(adapt));
		}
		ret.put(TagConstant.OPTIONS, optionsMap);
		
		return ret;
	}
}
