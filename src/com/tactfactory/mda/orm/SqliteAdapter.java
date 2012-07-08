package com.tactfactory.mda.orm;

import com.tactfactory.mda.orm.annotation.Column;

public class SqliteAdapter {
	private static String PREFIX = "COL_";

	public static String generateStructure(FieldMetadata field) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(" " + field.type.toLowerCase());
		
		if (field.nullable) {
			builder.append(" not null");
		} else {
			builder.append(" null");
		}
		
		
		return builder.toString();
	}
	
	public static String generateColumnName(FieldMetadata field) {
		return PREFIX + field.name.toUpperCase();
	}
}
