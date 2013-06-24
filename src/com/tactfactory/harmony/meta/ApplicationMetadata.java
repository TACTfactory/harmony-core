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
import java.util.Map;
import java.util.TreeMap;

import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.TagConstant;

/**
 * Application Metadata.
 */
public final class ApplicationMetadata extends BaseMetadata {
	/** Path delimiter. */
	private static final String PATH_DELIMITER = "/";
	/** Package delimiter. */
	private static final String PACKAGE_DELIMITER = "\\.";
	
	/** Singleton. */
	public static final ApplicationMetadata INSTANCE =
			new ApplicationMetadata();
	
	/** Android SDK Path. */
	private static String androidSdkPath;
	
	/** Project NameSpace (com/tactfactory/harmony/test/demact). */
	private String projectNameSpace;
	
	/** List of Entity of entity class. */
	private Map<String, ClassMetadata> classes =
			new LinkedHashMap<String, ClassMetadata>();
	
	/** List of Entity of entity class. */
	private Map<String, EnumMetadata> enums =
			new LinkedHashMap<String, EnumMetadata>();
	
	/** List of Entity of entity class. */
	private Map<String, InterfaceMetadata> interfaces =
			new LinkedHashMap<String, InterfaceMetadata>();
	
	/** List of Entity of entity class. */
	private Map<String, EntityMetadata> entities =
			new LinkedHashMap<String, EntityMetadata>();
	
	/** List of string use in application. */
	private Map<String, TranslationMetadata> translates =
			new TreeMap<String, TranslationMetadata>();

	/** List of config use in application. */
	private Map<String, ConfigMetadata> configs =
			new TreeMap<String, ConfigMetadata>();
	

	/**
	 * Constructor.
	 */
	private ApplicationMetadata() { 
		super();
	}
	
	/**
	 * @return the androidSdkPath
	 */
	public static String getAndroidSdkPath() {
		return androidSdkPath;
	}

	/**
	 * @param androidSdkPath the androidSdkPath to set
	 */
	public static void setAndroidSdkPath(final String androidSdkPath) {
		ApplicationMetadata.androidSdkPath = androidSdkPath;
	}

	/**
	 * @return the projectNameSpace
	 */
	public String getProjectNameSpace() {
		return projectNameSpace;
	}

	/**
	 * @param projectNameSpace the projectNameSpace to set
	 */
	public void setProjectNameSpace(final String projectNameSpace) {
		this.projectNameSpace = projectNameSpace;
	}

	/**
	 * @return the entities
	 */
	public Map<String, EntityMetadata> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(final Map<String, EntityMetadata> entities) {
		this.entities = entities;
	}

	/**
	 * @return the translates
	 */
	public Map<String, TranslationMetadata> getTranslates() {
		return translates;
	}

	/**
	 * @param translates the translates to set
	 */
	public void setTranslates(
			final Map<String, TranslationMetadata> translates) {
		this.translates = translates;
	}

	/**
	 * @return the configs
	 */
	public Map<String, ConfigMetadata> getConfigs() {
		return configs;
	}

	/**
	 * @param configs the configs to set
	 */
	public void setConfigs(final Map<String, ConfigMetadata> configs) {
		this.configs = configs;
	}

	/**
	 * @return the classes
	 */
	public Map<String, ClassMetadata> getClasses() {
		return classes;
	}

	/**
	 * @param classes the classes to set
	 */
	public void setClasses(final Map<String, ClassMetadata> classes) {
		this.classes = classes;
	}

	/**
	 * @return the enums
	 */
	public Map<String, EnumMetadata> getEnums() {
		return enums;
	}

	/**
	 * @param enums the enums to set
	 */
	public void setEnums(final Map<String, EnumMetadata> enums) {
		this.enums = enums;
	}

	/**
	 * @return the interfaces
	 */
	public Map<String, InterfaceMetadata> getInterfaces() {
		return interfaces;
	}

	/**
	 * @param interfaces the interfaces to set
	 */
	public void setInterfaces(
			final Map<String, InterfaceMetadata> interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * Transform the application to a map given an adapter.
	 * @param adapt The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public Map<String, Object> toMap(final BaseAdapter adapt) {
		final Map<String, Object> ret = new HashMap<String, Object>();
		final Map<String, Object> classesMap = new HashMap<String, Object>();
		final Map<String, Object> enumsMap = new HashMap<String, Object>();
		final Map<String, Object> interfacesMap = new HashMap<String, Object>();
		final Map<String, Object> entitiesMap = new HashMap<String, Object>();
		
		// Make Map for entities
		for (final ClassMetadata cm : this.classes.values()) {
			classesMap.put(cm.getName(), cm.toMap(adapt));
			
			if (cm instanceof EntityMetadata) {
				entitiesMap.put(cm.getName(), cm.toMap(adapt));
				if (!((EntityMetadata) cm).isInternal()) {
					((EntityMetadata) cm).makeString("label");
				}
			} else
				
			if (cm instanceof EnumMetadata) {
				enumsMap.put(cm.getName(), cm.toMap(adapt));
			} else
				
			if (cm instanceof InterfaceMetadata) {
				interfacesMap.put(cm.getName(), cm.toMap(adapt));
			} 
			
		}
		
		// Add root
		ret.put(TagConstant.PROJECT_NAME, 		this.getName());

		ret.put(TagConstant.CLASSES, 		classesMap);
		ret.put(TagConstant.ENTITIES, 		entitiesMap);
		ret.put(TagConstant.ENUMS, 			enumsMap);
		ret.put(TagConstant.INTERFACES, 	interfacesMap);
		
		if (this.projectNameSpace != null) {
			ret.put(TagConstant.PROJECT_PATH, 		this.projectNameSpace);
			ret.put(TagConstant.PROJECT_NAMESPACE, 	
					this.projectNameSpace.replaceAll(
							PATH_DELIMITER, 
							PACKAGE_DELIMITER));
			ret.put(TagConstant.ENTITY_NAMESPACE, 	
					this.projectNameSpace.replaceAll(
							PATH_DELIMITER, 
							PACKAGE_DELIMITER) + "." + adapt.getModel());
			ret.put(TagConstant.TEST_NAMESPACE, 	
					this.projectNameSpace.replaceAll(
							PATH_DELIMITER, 
							PACKAGE_DELIMITER) + "." + adapt.getTest());
			ret.put(TagConstant.DATA_NAMESPACE, 	
					this.projectNameSpace.replaceAll(
							PATH_DELIMITER,
							PACKAGE_DELIMITER) + "." + adapt.getData());
			ret.put(TagConstant.SERVICE_NAMESPACE, 	
					this.projectNameSpace.replaceAll(
							PATH_DELIMITER, 
							PACKAGE_DELIMITER) + "." + adapt.getService());
			ret.put(TagConstant.FIXTURE_NAMESPACE, 	
					this.projectNameSpace.replaceAll(
							PATH_DELIMITER, 
							PACKAGE_DELIMITER) + "." + adapt.getFixture());
		}
		
		ret.put(TagConstant.ANDROID_SDK_DIR,
				ApplicationMetadata.androidSdkPath);
		// SDKDIR Hack
		final HashMap<String, String> sdkDir = new HashMap<String, String>();
		sdkDir.put("dir", "${sdk.dir}");
		ret.put(TagConstant.ANT_ANDROID_SDK_DIR, sdkDir);
		ret.put(TagConstant.OUT_CLASSES_ABS_DIR, "CLASSPATHDIR/");
		ret.put(TagConstant.OUT_DEX_INPUT_ABS_DIR, "DEXINPUTDIR/");
		
		// Add Extra bundle
		final HashMap<String, Object> optionsMap =
				new HashMap<String, Object>();
		for (final Metadata bm : this.getOptions().values()) {
			optionsMap.put(bm.getName(), bm.toMap(adapt));
		}
		ret.put(TagConstant.OPTIONS, optionsMap);
		
		return ret;
	}
}
