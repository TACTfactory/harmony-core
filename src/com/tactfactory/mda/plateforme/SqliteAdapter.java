/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.plateforme;

import java.lang.reflect.Field;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * SQliteAdapter.
 */
public abstract class SqliteAdapter {
	/** Prefix for column name generation. */
	private static final String PREFIX = "COL_";
	/** Suffix for column name generation. */
	private static final String SUFFIX = "_ID";

	/**
	 * Generate field's structure for database creation schema.
	 * @param fm The field
	 * @return The field's structure
	 */
	public static String generateStructure(final FieldMetadata fm) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append(' ');
		builder.append(fm.getColumnDefinition().toLowerCase());
		if (fm.isId()) {
			builder.append(" PRIMARY KEY");
			if (fm.getColumnDefinition().equals("integer")) {
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
			if (fm.isUnique() != null && fm.isUnique()) {
				builder.append(" UNIQUE");
			}
			
			// Set Nullable
			if (fm.isNullable() == null || !fm.isNullable()) {
				builder.append(" NOT NULL");
			}
		}

		
		return builder.toString();
	}

	/**
	 * Generate the column type for a given harmony type.
	 * @param fieldType The harmony type of a field.
	 * @return The columnType for SQLite
	 */
	public static String generateColumnType(final String fieldType) {
		String type = fieldType;
		if (type.equals(Column.Type.STRING.getValue()) 
			|| type.equals(Column.Type.TEXT.getValue()) 
			|| type.equals(Column.Type.LOGIN.getValue())) {
			type = "VARCHAR";
		} else
			
		if (type.equals(Column.Type.PASSWORD.getValue())) {
			type = "VARCHAR";
		} /*else
			
		if (type.equals(Column.Type.DATETIME.getValue())) {
			//type = "VARCHAR";
		}*/

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
		if (type.equals("int")) {
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
