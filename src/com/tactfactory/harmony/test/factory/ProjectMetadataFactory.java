/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.factory;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;

/**
 * Base class for test projects metadata factories.
 */
public class ProjectMetadataFactory {
	protected static final String CLASS_SERIALIZABLE = "Serializable";
	protected static final String CLASS_PARCELABLE = "Parcelable";
	protected static final String CLASS_PARCEL = "Parcel";
	protected static final String CLASS_CLONEABLE = "Cloneable";
	protected static final String CLASS_DATETIME = "DateTime";
	protected static final String CLASS_ENTITY = "Entity";
	protected static final String CLASS_TYPE = "Type";
	protected static final String CLASS_ARRAYLIST = "ArrayList";
	protected static final String CLASS_LIST = "List";
	protected static final String CLASS_ISODATETIMEFORMAT = "ISODateTimeFormat";

	protected static final String CLASS_COLUMN = "Column";
	protected static final String CLASS_COLUMN_RESULT = "ColumnResult";
	protected static final String CLASS_DISCRIMINATOR_COLUMN = "DiscriminatorColumn";
	protected static final String CLASS_GENERATED_VALUE = "GeneratedValue";
	protected static final String CLASS_GENERATED_VALUE_STRATEGY = "Strategy";
	protected static final String CLASS_ID = "Id";
	protected static final String CLASS_VIEW = "View";
	protected static final String CLASS_INHERITANCE_TYPE = "InheritanceType";
	protected static final String CLASS_MANY_TO_ONE = "ManyToOne";
	protected static final String CLASS_MANY_TO_MANY = "ManyToMany";
	protected static final String CLASS_ONE_TO_MANY = "OneToMany";
	protected static final String CLASS_ONE_TO_ONE = "OneToOne";
	protected static final String CLASS_TABLE = "Table";
	protected static final String CLASS_INHERITANCE_MODE = "InheritanceMode";
	
	/**
	 * Generate a default ID field.
	 * @param owner The owner of the field
	 * @param idName The id name
	 */
	protected static void generateIdField(EntityMetadata owner, String idName) {
		FieldMetadata id = new FieldMetadata(owner);
		id.setName(idName);
		id.setNullable(false);
		id.setUnique(true);
		id.setColumnName(idName);
		owner.addId(id);
		owner.getFields().put(id.getName(), id);
	}

	/**
	 * Generate a default field.
	 * 
	 * @param owner The owner of the field
	 * @param fieldName The name
	 * @param nullable True if the field should be nullable
	 * @param unique True if field should be unique
	 */
	protected static void generateField(
			EntityMetadata owner,
			String fieldName,
			boolean nullable,
			boolean unique) {
		
		ProjectMetadataFactory.generateField(
				owner,
				fieldName,
				nullable,
				unique,
				null);
	}

	/**
	 * Generate a field.
	 * 
	 * @param owner The owner of the field
	 * @param fieldName The name
	 * @param nullable True if the field should be nullable
	 * @param unique True if field should be unique
	 * @param columnName The column name of the field
	 */
	protected static void generateField(EntityMetadata owner,
			String fieldName,
			boolean nullable,
			boolean unique,
			String columnName) {
		
		FieldMetadata field = new FieldMetadata(owner);
		field.setName(fieldName);
		field.setNullable(nullable);
		field.setUnique(unique);
		if (columnName == null) {
			field.setColumnName(fieldName);
		} else {
			field.setColumnName(columnName);
		}
		owner.getFields().put(field.getName(), field);
	}

	
	protected static EntityMetadata addEntity(String name, ApplicationMetadata app) {
		EntityMetadata result = new EntityMetadata();
		result.setName(name);
		app.getEntities().put(result.getName(), result);
		return result;
	}

	protected static void addImplements(EntityMetadata entity, String... implementTypes) {
		for (String implementType : implementTypes) {
			entity.getImplementTypes().add(implementType);
		}
	}
	
	protected static void addImports(EntityMetadata entity, String... importTypes) {
		for (String importType : importTypes) {
			entity.getImports().add(importType);
		}
	}
	
	protected static void setFieldAsId(EntityMetadata entity, String... fieldNames) {
		for (String fieldName : fieldNames) {
			entity.getIds().put(fieldName, entity.getFields().get(fieldName));
		}
	}
}
