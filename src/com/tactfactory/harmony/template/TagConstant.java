/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

/**
 * Tag Constants for Freemarker templates.
 * 
 *
 */
public abstract class TagConstant {
	//Project markers
	
	/** Constant for project path. */
	public static final String PROJECT_PATH = "project_path";
	/** Constant for project namespace. */
	public static final String PROJECT_NAMESPACE = "project_namespace";
	/** Constant for project name. */
	public static final String PROJECT_NAME = "project_name";
	/** Constant for classes local namespace. */
	public static final String LOCAL_NAMESPACE = "local_namespace";
	/** Constant for project controller namespace. */
	public static final String CONTROLLER_NAMESPACE = "controller_namespace";
	/** Constant for project tests namespace. */
	public static final String TEST_NAMESPACE = "test_namespace";
	/** Constant for project data namespace. */
	public static final String DATA_NAMESPACE = "data_namespace";
	/** Constant for project entities namespace. */
	public static final String ENTITY_NAMESPACE = "entity_namespace";
	/** Constant for project services namespace. */
	public static final String SERVICE_NAMESPACE = "service_namespace";
	/** Constant for project fixtures namespace. */
	public static final String FIXTURE_NAMESPACE = "fixture_namespace";
	/** Constant for android sdk dir. */
	public static final String ANDROID_SDK_DIR = "sdk_dir";
	
	// Ant markers
	/** Constant for ant sdk dir. */
	public static final String ANT_ANDROID_SDK_DIR = "sdk";
	/** Constant for ant out classes absolute directory. */
	public static final String OUT_CLASSES_ABS_DIR = "out_classes_absolute_dir";
	/** Constant for ant out dex input absolute directory. */
	public static final String OUT_DEX_INPUT_ABS_DIR = 
			"out_dex_input_absolute_dir";
	
	//Template annotations
	/** Constant for entities. */
	public static final String ENTITIES = "entities";
	/** Constant for relation target entity. */
	public static final String ENTITY_REF = "targetEntity";
	/** Constant for classes fields. */
	public static final String FIELDS = "fields";
	/** Constant for fields name. */
	public static final String FIELD_NAME = "columnName";
	/** Constant for fields column definition. */
	public static final String FIELD_DEF = "columnDefinition";
	/** Constant for relation field reference. */
	public static final String FIELD_REF = "field_ref";
	/** Constant for classes relations. */
	public static final String RELATIONS = "relations";
	/** Constant for field relation information. */
	public static final String RELATION = "relation";
	/** Constant for relation type. */
	public static final String RELATION_TYPE = "relation_type";
	/** Constant for namespace. */
	public static final String SPACE = "namespace";
	/** Constant for entity inheritance. */
	public static final String EXTENDS = "extends";
	/** Constant for name. */
	public static final String NAME = "name";
	/** Constant for type. */
	public static final String TYPE = "type";
	/** Constant for harmony type. */
	public static final String HARMONY_TYPE = "harmony_type";
	/** Constant for schema. */
	public static final String SCHEMA = "schema";
	/** Constant for alias. */
	public static final String ALIAS = "alias";
	/** Constant for entitiesids. */
	public static final String IDS = "ids";
	/** Constant for hidden field. */
	public static final String HIDDEN = "hidden";
	/** Constant for unique field. */
	public static final String UNIQUE = "unique";
	/** Constant for locale field. */
	public static final String IS_LOCALE = "is_locale";
	/** Constant for internal field. */
	public static final String INTERNAL = "internal";
	/** Constant for template's current entity. */
	public static final String CURRENT_ENTITY = "current_entity";
	/** Constant for options. */
	public static final String OPTIONS = "options";
	/** Constant for sync level. */
	public static final String LEVEL = "level";
	/** Constant for sync mode. */
	public static final String MODE = "mode";
	/** Constant for sync priority. */
	public static final String PRIORITY = "priority";
	/** Constant for ID. */
	public static final String ID = "id";
	/** Constant for nullable field. */
	public static final String NULLABLE = "nullable";
	/** Constant for provider id. */
	public static final String PROVIDER_ID = "provider_id";
}
