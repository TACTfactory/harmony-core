/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.google.common.base.Strings;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * SQliteAdapter.
 */
public final class SqliteAdapter {
	/** Prefix for column name generation. */
	private static final String PREFIX = "COL_";
	/** Suffix for column name generation. */
	private static final String SUFFIX = "_ID";
	
	/** Error message for type not found. */
	private static final String ERROR_TYPE_NOT_FOUND =
			"No type found for %s in entity %s (field : %s).";
	/** Proposal message for erroneous type. */
	private static final String PROPOSAL_TYPE_NOT_FOUND =
			ERROR_TYPE_NOT_FOUND +
			" You should use %s or %s to declare your relation.";

	/**
	 * Generate field's structure for database creation schema.
	 * @param fm The field
	 * @return The field's structure
	 */
	public static String generateStructure(final FieldMetadata fm) {

		final StringBuilder builder = new StringBuilder(20);
		builder.append(' ');
		builder.append(fm.getColumnDefinition());
		if (fm.isId()
				&& (!(fm.getOwner() instanceof EntityMetadata)
					|| ((EntityMetadata) fm.getOwner()).getIds().size() == 1)) {
			builder.append(" PRIMARY KEY");
			if (fm.getStrategy() != null && 
					fm.getStrategy().equals(Strategy.MODE_IDENTITY)) {
				builder.append(" AUTOINCREMENT");
			}
		} else {

			// Set Length
			final Type fieldType = Type.fromName(fm.getType());
			if (fieldType != null) {
				if (fm.getLength() != null
						&& fm.getLength() != fieldType.getLength()) {
					builder.append('(');
					builder.append(fm.getLength());
					builder.append(')');
				} else if (fm.getPrecision() != null
						&& fm.getPrecision() != fieldType.getPrecision()) {
					builder.append('(');
					builder.append(fm.getPrecision());
					if (fm.getScale() != null
							&& fm.getScale() != fieldType.getScale()) {
						builder.append(',');
						builder.append(fm.getScale());
					}
					builder.append(')');
				}
			}

			// Set Unique
			/*if (fm.isUnique() != null && fm.isUnique()) {
				builder.append(" UNIQUE");
			}*/
			
			boolean isSingleTabInherited = 
					fm.getOwner().getInheritance() != null 
					&& fm.getOwner().getInheritance().getType() 
									== InheritanceMode.SINGLE_TABLE
					&& fm.getOwner().getInheritance().getSuperclass() != null;
			
			
			// Set Nullable
			if ((fm.isNullable() == null || !fm.isNullable())
					&& !isSingleTabInherited) {
				builder.append(" NOT NULL");
			}

			if (fm.getDefaultValue() != null) {
				builder.append(" DEFAULT '" + fm.getDefaultValue() + "'");
			}
		}


		return builder.toString();
	}

	/**
	 * Generate the column type for a given harmony type.
	 * @param field The harmony type of a field.
	 * @return The columnType for SQLite
	 */
	public static String generateColumnType(final FieldMetadata field) {
		String type;
		if (!Strings.isNullOrEmpty(field.getHarmonyType())) {
			
			if (field.getHarmonyType().equals(Column.Type.ENUM.getValue())) {
				EnumMetadata enumMeta = 
						ApplicationMetadata.INSTANCE.getEnums().get(
								field.getType());
				type =	enumMeta.getType();
			} else {
				type = field.getHarmonyType();
			}
			
		} else {
			if (field.getRelation() != null) {
				EntityMetadata relatedEntity = field.getRelation().getEntityRef();
				ArrayList<FieldMetadata> ids = new ArrayList<FieldMetadata>(
						relatedEntity.getIds().values());
				if (ids.size() > 0) {
					type = SqliteAdapter.generateColumnType(ids.get(0));
				} else {
					StringBuilder warning = new StringBuilder();
					warning.append("Erroneous relation between ");
					warning.append(field.getOwner().getName());
					warning.append(".");
					warning.append(field.getName());
					warning.append(" AND ");
					warning.append(relatedEntity.getName());
					warning.append(". Entity ");
					warning.append(relatedEntity.getName());
					warning.append(" must be an entity and declare an @Id.");
					ConsoleUtils.displayWarning(warning.toString());
					
					field.getOwner().removeField(field);
					
					type = "BLOB";
				}
			} else {
				type = field.getType();
			}
		}
		
		if (type.equalsIgnoreCase(Column.Type.STRING.getValue())
			|| type.equalsIgnoreCase(Column.Type.TEXT.getValue())
			|| type.equalsIgnoreCase(Column.Type.LOGIN.getValue())
			|| type.equalsIgnoreCase(Column.Type.PHONE.getValue())
			|| type.equalsIgnoreCase(Column.Type.PASSWORD.getValue())
			|| type.equalsIgnoreCase(Column.Type.EMAIL.getValue())
			|| type.equalsIgnoreCase(Column.Type.CITY.getValue())
			|| type.equalsIgnoreCase(Column.Type.ZIPCODE.getValue())
			|| type.equalsIgnoreCase(Column.Type.COUNTRY.getValue())
			|| type.equalsIgnoreCase("VARCHAR")) {
			type = "VARCHAR";
		} else

		if (type.equalsIgnoreCase(Column.Type.DATETIME.getValue())) {
			type = "DATETIME";
		} else

		if (type.equalsIgnoreCase(Column.Type.DATE.getValue())) {
			type = "DATE";
		} else

		if (type.equalsIgnoreCase(Column.Type.TIME.getValue())) {
			type = "DATETIME";
		} else

		if (type.equalsIgnoreCase(Column.Type.BOOLEAN.getValue())) {
			type = "BOOLEAN";
		} else

		if (type.equalsIgnoreCase(Column.Type.INTEGER.getValue())
				|| type.equalsIgnoreCase(Column.Type.INT.getValue())
				|| type.equalsIgnoreCase(Column.Type.BC_EAN.getValue())) {
			type = "INTEGER";
		} else

		if (type.equalsIgnoreCase(Column.Type.FLOAT.getValue())) {
			type = "FLOAT";
		} else

		if (type.equalsIgnoreCase(Column.Type.DOUBLE.getValue())) {
			type = "DOUBLE";
		} else

		if (type.equalsIgnoreCase(Column.Type.SHORT.getValue())) {
			type = "SHORT";
		} else

		if (type.equalsIgnoreCase(Column.Type.LONG.getValue())) {
			type = "LONG";
		} else

		if (type.equalsIgnoreCase(Column.Type.CHAR.getValue())) {
			type = "STRING";
		} else

		if (type.equalsIgnoreCase(Column.Type.BYTE.getValue())) {
			type = "STRING";
		} else

		if (type.equalsIgnoreCase(Column.Type.CHARACTER.getValue())) {
			type = "STRING";
		} else {
			if (field.getOwner() != null) {

				String realType;
				String errorMessage;
				boolean isArray = false;
				if (type.contains("<")) {
					isArray = true;
					realType = type.substring(
							type.lastIndexOf('<') + 1,
							type.indexOf('>'));
				} else {
					
					realType = type;
				}
				
				if (ApplicationMetadata.INSTANCE.getEntities()
						.containsKey(realType)) {
					if (isArray) {
						errorMessage = String.format(
								PROPOSAL_TYPE_NOT_FOUND,
								type,
								field.getOwner().getName(),
								field.getName(),
								"@OneToMany",
								"@ManyToMany");
					} else {
						errorMessage = String.format(
								PROPOSAL_TYPE_NOT_FOUND,
								type,
								field.getOwner().getName(),
								field.getName(),
								"@ManyToOne",
								"@OneToOne");
						
					}
				} else {
					errorMessage = String.format(
							ERROR_TYPE_NOT_FOUND,
							type,
							field.getOwner().getName(),
							field.getName());
				}
				
				ConsoleUtils.displayWarning(errorMessage);
			}
			type = "BLOB";
		}
		
		return type;
	}

	/**
	 * Generate a column name.
	 * @param fieldName The original field's name
	 * @return the generated column name
	 */
	public static String generateColumnName(final String fieldName) {
		return PREFIX + fieldName.toUpperCase();
	}

	/**
	 * Generate a relation column name.
	 * @param fieldName The original field's name
	 * @return the generated column name
	 */
	public static String generateRelationColumnName(final String fieldName) {
		return PREFIX + fieldName.toUpperCase() + SUFFIX;
	}

	/**
	 * Generate a column definition.
	 * @param type The original field's type
	 * @return the generated column definition
	 */
	public static String generateColumnDefinition(final String type) {
		String ret = type;
		if (type.equalsIgnoreCase("int")) {
			ret = "integer";
		}
		return ret;
	}

	/**
	 * SQLite Reserved keywords.
	 */
	public static enum Keywords {
		// CHECKSTYLE:OFF
		ABORT,
		ACTION,
		ADD,
		AFTER,
		ALL,
		ALTER,
		ANALYZE,
		AND,
		AS,
		ASC,
		ATTACH,
		AUTOINCREMENT,
		BEFORE,
		BEGIN,
		BETWEEN,
		BY,
		CASCADE,
		CASE,
		CAST,
		CHECK,
		COLLATE,
		COLUMN,
		COMMIT,
		CONFLICT,
		CONSTRAINT,
		CREATE,
		CROSS,
		CURRENT_DATE,
		CURRENT_TIME,
		CURRENT_TIMESTAMP,
		DATABASE,
		DEFAULT,
		DEFERRABLE,
		DEFERRED,
		DELETE,
		DESC,
		DETACH,
		DISTINCT,
		DROP,
		EACH,
		ELSE,
		END,
		ESCAPE,
		EXCEPT,
		EXCLUSIVE,
		EXISTS,
		EXPLAIN,
		FAIL,
		FOR,
		FOREIGN,
		FROM,
		FULL,
		GLOB,
		GROUP,
		HAVING,
		IF,
		IGNORE,
		IMMEDIATE,
		IN,
		INDEX,
		INDEXED,
		INITIALLY,
		INNER,
		INSERT,
		INSTEAD,
		INTERSECT,
		INTO,
		IS,
		ISNULL,
		JOIN,
		KEY,
		LEFT,
		LIKE,
		LIMIT,
		MATCH,
		NATURAL,
		NO,
		NOT,
		NOTNULL,
		NULL,
		OF,
		OFFSET,
		ON,
		OR,
		ORDER,
		OUTER,
		PLAN,
		PRAGMA,
		PRIMARY,
		QUERY,
		RAISE,
		REFERENCES,
		REGEXP,
		REINDEX,
		RELEASE,
		RENAME,
		REPLACE,
		RESTRICT,
		RIGHT,
		ROLLBACK,
		ROW,
		SAVEPOINT,
		SELECT,
		SET,
		TABLE,
		TEMP,
		TEMPORARY,
		THEN,
		TO,
		TRANSACTION,
		TRIGGER,
		UNION,
		UNIQUE,
		UPDATE,
		USING,
		VACUUM,
		VALUES,
		VIEW,
		VIRTUAL,
		WHEN,
		WHERE;
		// CHECKSTYLE:ON
		/**
		 * Tests if the given String is a reserverd SQLite keyword.
		 * @param name The string
		 * @return True if it is
		 */
		public static boolean exists(final String name) {
			boolean exists = false;
			try {
				final Field field =
						Keywords.class.getField(name.toUpperCase());
				if (field.isEnumConstant()) {
					ConsoleUtils.displayWarning(
							name
							 + " is a reserved SQLite keyword."
							 + " You may have problems with"
							 + " your database schema.");
					exists = true;
				} else {
					exists = false;
				}
			} catch (final NoSuchFieldException e) {
				exists = false;
			}
			return exists;
		}

	}
}
