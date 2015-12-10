/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

/**
 * Tag Constants for Freemarker templates.
 */
public abstract class TagConstant {
	//Project markers

	/** Constant for project path. */
	public static final String PROJECT_PATH = "project_path";
	/** Constant for project namespace. */
	public static final String PROJECT_NAMESPACE = "project_namespace";
	/** Constant for project name. */
	public static final String PROJECT_NAME = "project_name";
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
	public static final String CLASSES = "classes";
	/** Constant for entities. */
	public static final String ENTITIES = "entities";
    /** Constant for entities. */
    public static final String RESOURCE = "resource";
	/** Constant for enums. */
	public static final String ENUMS = "enums";
	/** Constant for enum. */
	public static final String ENUM = "enum";
	/** Constant for entities. */
	public static final String INTERFACES = "interfaces";
	/** Constant for relation target entity. */
	public static final String ENTITY_REF = "targetEntity";
	/** Constant for relation target enum. */
	public static final String ENUM_REF = "targetEnum";
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
	/** Constant for name. */
	public static final String NAME = "name";
	/** Constant for names. */
	public static final String NAMES = "names";
	/** Constant for type. */
	public static final String TYPE = "type";
	/** Constant for harmony type. */
	public static final String HARMONY_TYPE = "harmony_type";
	/** Constant for schema. */
	public static final String SCHEMA = "schema";
	/** Constant for alias. */
	public static final String ALIAS = "alias";
	/** Constant for table name. */
	public static final String TABLE_NAME = "tableName";
	/** Constant for entitiesids. */
	public static final String IDS = "ids";
	/** Constant for indexes. */
	public static final String INDEXES = "indexes";
	/** Constant for indexes. */
	public static final String ORDERS = "orders";
	/** Constant for hidden field. */
	public static final String HIDDEN = "hidden";
	/** Constant for primitive field. */
	public static final String PRIMITIVE = "primitive";
	/** Constant for entity create action. */
	public static final String CREATE_ACTION = "createAction";
	/** Constant for entity edit action. */
	public static final String EDIT_ACTION = "editAction";
	/** Constant for entity delete action. */
	public static final String DELETE_ACTION = "deleteAction";
	/** Constant for entity list action. */
	public static final String LIST_ACTION = "listAction";
	/** Constant for entity show action. */
	public static final String SHOW_ACTION = "showAction";
	/** Constant for static field. */
	public static final String STATIC = "static";
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
	/** Constant for field's default value. */
	public static final String DEFAULT_VALUE = "defaultValue";
	/** Constant for provider id. */
	public static final String PROVIDER_ID = "provider_id";
	/** Constant for superclass. */
	public static final String SUPERCLASS = "superclass";
	/** Constant for subclasses. */
	public static final String SUBCLASSES = "subclasses";
	/** Constant for mother class. */
	public static final String OWNER = "owner";
	/** Constant for files header. */
	public static final String HEADER = "header";
	/** Constant for template methods. */
	public static final String UTILITY_PATH = "utilityPath";
	/** Constant for harmony version. */
	public static final String HARMONY_VERSION = "harmony_version";
	/** Constant for services. */
	public static final String SERVICES = "services";
	/** Constant for inheritance type. */
	public static final String INHERITANCE_TYPE = "inheritanceType";
	/** Constant for discriminator column. */
	public static final String DISCRIMINATOR_COLUMN = "discriminatorColumn";
	/** Constant for discriminator identifier. */
	public static final String DISCRIMINATOR_IDENTIFIER =
			"discriminatorIdentifier";
	/** Constant for inheritance. */
	public static final String INHERITANCE = "inheritance";
	/** Constant for outer class. */
	public static final String OUTER_CLASS = "outerClass";
	/** Constant for inner classes. */
	public static final String INNER_CLASSES = "innerclasses";
	/** Constant for column results. */
	public static final String COLUMN_RESULT = "columnResult";
	/** Constant for writable. */
	public static final String WRITABLE = "writable";
	/** Constant for has been parsed. */
	public static final String HAS_BEEN_PARSED = "hasBeenParsed";
	/** Constant for strategy. */
	public static final String STRATEGY = "strategy";
	/** Constant for values. */
	public static final String VALUES = "values";

}
