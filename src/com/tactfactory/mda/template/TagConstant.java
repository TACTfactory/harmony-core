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
	public final static String PROJECT_NAMESPACE = "project_namespace";
	public final static String PROJECT_NAME = "project_name";
	public final static String LOCAL_NAMESPACE = "local_namespace";
	public final static String CONTROLLER_NAMESPACE = "controller_namespace";
	public final static String TEST_NAMESPACE = "test_namespace";
	public final static String DATA_NAMESPACE = "data_namespace";
	public final static String ENTITY_NAMESPACE = "entity_namespace";
	public final static String SERVICE_NAMESPACE = "service_namespace";
	public static final String FIXTURE_NAMESPACE = "fixture_namespace";
	public final static String ANDROID_SDK_DIR = "sdk_dir";
	
	// Ant markers
	public final static String ANT_ANDROID_SDK_DIR = "sdk";
	public final static String OUT_CLASSES_ABS_DIR = "out_classes_absolute_dir";
	public final static String OUT_DEX_INPUT_ABS_DIR = "out_dex_input_absolute_dir";
	
	//Template annotations
	public final static String ENTITIES = "entities";
	public final static String ENTITY_REF = "targetEntity";
	public final static String FIELDS = "fields";
	public final static String FIELD_NAME = "columnName";
	public final static String FIELD_DEF = "columnDefinition";
	public final static String FIELD_REF = "field_ref";
	public final static String FIELD_CUSTOM_EDIT = "customEditType";
	public final static String FIELD_CUSTOM_SHOW = "customShowType";
	public final static String RELATIONS = "relations";
	public final static String RELATION = "relation";
	public final static String RELATION_TYPE = "relation_type";
	public final static String SPACE = "namespace";
	public final static String NAME = "name";
	public final static String TYPE = "type";
	public final static String SCHEMA = "schema";
	public final static String ALIAS = "alias";
	public final static String IDS = "ids";
	public final static String HIDDEN = "hidden";
	public final static String IS_LOCALE= "is_locale";
	public final static String INTERNAL = "internal";
	public final static String CURRENT_ENTITY = "current_entity";
	public final static String OPTIONS = "options";
	public static final String LEVEL = "level";
	public static final String MODE = "mode";
	public static final String PRIORITY = "priority";
	public static final String ID = "id";
	
	
	
	
	

	public static class AndroidSDK {
		private String dir;

		public AndroidSDK(String dir){
			this.dir = dir;
		}
		
		public String getDir() {
			return dir;
		}
	}
}
