/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import com.tactfactory.mda.orm.annotation.Column;

public class SqliteAdapter {
	private static String PREFIX = "COL_";
	private static String SUFFIX = "_ID";

	public static String generateStructure(FieldMetadata field) {
		StringBuilder builder = new StringBuilder();
		builder.append(" " + field.columnDefinition.toLowerCase());
		
		
		// Set Length
		if(field.length!=255){
			builder.append("("+field.length+")");
		} else if (field.precision!=0){
			builder.append("("+field.precision);
			if(field.scale!=0){
				builder.append(","+field.scale);
			}
			builder.append(")");
		}
		
		// Set Unique
		if(field.unique){
			builder.append(" UNIQUE");
		}
		
		// Set Nullable
		if(!field.nullable) {
			builder.append(" NOT NULL");
		}
		
		if(field.id){
			builder.append(" PRIMARY KEY");
			if(field.columnDefinition.equals("integer")) builder.append(" AUTOINCREMENT");
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
		return PREFIX + field.fieldName.toUpperCase();
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
}
