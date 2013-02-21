/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

public abstract class TagConstant {
	//Project markers
	public static final String PROJECT_PATH = "project_path";
	public static final String PROJECT_NAMESPACE = "project_namespace";
	public static final String PROJECT_NAME = "project_name";
	public static final String LOCAL_NAMESPACE = "local_namespace";
	public static final String CONTROLLER_NAMESPACE = "controller_namespace";
	public static final String TEST_NAMESPACE = "test_namespace";
	public static final String DATA_NAMESPACE = "data_namespace";
	public static final String ENTITY_NAMESPACE = "entity_namespace";
	public static final String SERVICE_NAMESPACE = "service_namespace";
	public static final String FIXTURE_NAMESPACE = "fixture_namespace";
	public static final String ANDROID_SDK_DIR = "sdk_dir";
	
	// Ant markers
	public static final String ANT_ANDROID_SDK_DIR = "sdk";
	public static final String OUT_CLASSES_ABS_DIR = "out_classes_absolute_dir";
	public static final String OUT_DEX_INPUT_ABS_DIR = 
			"out_dex_input_absolute_dir";
	
	//Template annotations
	public static final String ENTITIES = "entities";
	public static final String ENTITY_REF = "targetEntity";
	public static final String FIELDS = "fields";
	public static final String FIELD_NAME = "columnName";
	public static final String FIELD_DEF = "columnDefinition";
	public static final String FIELD_REF = "field_ref";
	public static final String FIELD_CUSTOM_EDIT = "customEditType";
	public static final String FIELD_CUSTOM_SHOW = "customShowType";
	public static final String RELATIONS = "relations";
	public static final String RELATION = "relation";
	public static final String RELATION_TYPE = "relation_type";
	public static final String SPACE = "namespace";
	public static final String EXTENDS = "extends";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String SCHEMA = "schema";
	public static final String ALIAS = "alias";
	public static final String IDS = "ids";
	public static final String HIDDEN = "hidden";
	public static final String IS_LOCALE = "is_locale";
	public static final String INTERNAL = "internal";
	public static final String CURRENT_ENTITY = "current_entity";
	public static final String OPTIONS = "options";
	public static final String LEVEL = "level";
	public static final String MODE = "mode";
	public static final String PRIORITY = "priority";
	public static final String ID = "id";
	public static final String NULLABLE = "nullable";
}
