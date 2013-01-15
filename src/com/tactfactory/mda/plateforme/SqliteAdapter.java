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

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.RelationMetadata;

public class SqliteAdapter {
	private static String PREFIX = "COL_";
	private static String SUFFIX = "_ID";

	public static String generateStructure(FieldMetadata field) {
		StringBuilder builder = new StringBuilder();
		builder.append(" " + field.columnDefinition.toLowerCase());
		if(field.id){
			builder.append(" PRIMARY KEY");
			if(field.columnDefinition.equals("integer")) builder.append(" AUTOINCREMENT");
		}else{
		
			// Set Length
			Type fieldType = Type.fromName(field.columnDefinition);
			if(field.length!=null && field.length!=fieldType.getLength()){
				builder.append("("+field.length+")");
			} else if (field.precision!=null && field.precision!=fieldType.getPrecision()){
				builder.append("("+field.precision);
				if(field.scale!=null && field.scale!=fieldType.getScale()){
					builder.append(","+field.scale);
				}
				builder.append(")");
			}
			
			// Set Unique
			if(field.unique!=null && field.unique){
				builder.append(" UNIQUE");
			}
			
			// Set Nullable
			if(field.nullable!=null && !field.nullable) {
				builder.append(" NOT NULL");
			}
		}

		
		return builder.toString();
	}
	
	public static String generateRelationStructure(FieldMetadata field) {
		StringBuilder builder = new StringBuilder();
		
		RelationMetadata relation = field.relation;
		builder.append("FOREIGN KEY("+generateColumnName(field)+") REFERENCES "+relation.entity_ref+"("+relation.field_ref+")");
		
		return builder.toString();

	}

	public static String generateColumnType(FieldMetadata field) {
		String type = field.columnDefinition;
		
		if (type.equals(Column.Type.STRING) ||
			type.equals(Column.Type.TEXT)	||
			type.equals(Column.Type.LOGIN)	) {
			type = "VARCHAR";
		} else
			
		if (type.equals(Column.Type.PASSWORD)) {
			type = "VARCHAR";
		} else
			
		if (type.equals(Column.Type.DATETIME)) {
			//type = "VARCHAR";
		}

		return type;
	}

	public static String generateColumnName(FieldMetadata field) {
		return PREFIX + field.name.toUpperCase();
	}
	
	public static String generateRelationColumnName(String fieldName) {
		return PREFIX + fieldName.toUpperCase() + SUFFIX;
	}
	
	public static String generateColumnDefinition(String type){
		String ret = type;
		if(type.equals("int"))
			ret = "integer";
		return ret;
	}
	
	public static enum Keywords{
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
		
		public static boolean exists(String name){
			try{
				Field field = Keywords.class.getField(name.toUpperCase());	
				if(field.isEnumConstant()) {
					ConsoleUtils.displayWarning(name+" is a reserved SQLite keyword. You may have problems with your database schema.");
					return true;
				}
				else 
					return false;
			}catch(Exception e){
				return false;
			}
			
		}
		
	}
}
