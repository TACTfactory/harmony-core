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

public abstract class SqliteAdapter {
	private static final String PREFIX = "COL_";
	private static final String SUFFIX = "_ID";

	public static String generateStructure(final FieldMetadata fm) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append(' ');
		builder.append(fm.type.toLowerCase());
		if (fm.id) {
			builder.append(" PRIMARY KEY");
			if (fm.type.equals("integer")) {
				builder.append(" AUTOINCREMENT");
			}
		} else {
		
			// Set Length
			final Type fieldType = Type.fromName(fm.type);
			if (fieldType != null) {
				if (fm.length != null && fm.length != fieldType.getLength()) {
					builder.append('(');
					builder.append(fm.length);
					builder.append(')');
				} else if (fm.precision != null 
						&& fm.precision != fieldType.getPrecision()) {
					builder.append('(');
					builder.append(fm.precision);
					if (fm.scale != null 
							&& fm.scale != fieldType.getScale()) {
						builder.append(',');
						builder.append(fm.scale);
					}
					builder.append(')');
				}
			}
			
			// Set Unique
			if (fm.unique != null && fm.unique) {
				builder.append(" UNIQUE");
			}
			
			// Set Nullable
			if (fm.nullable == null || !fm.nullable) {
				builder.append(" NOT NULL");
			}
		}

		
		return builder.toString();
	}

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

	public static String generateColumnName(final String fieldName) {
		return PREFIX + fieldName.toUpperCase();
	}
	
	public static String generateRelationColumnName(final String fieldName) {
		return PREFIX + fieldName.toUpperCase() + SUFFIX;
	}
	
	public static String generateColumnDefinition(final String type) {
		String ret = type;
		if (type.equals("int")) {
			ret = "integer";
		}
		return ret;
	}
	
	public static enum Keywords {
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
