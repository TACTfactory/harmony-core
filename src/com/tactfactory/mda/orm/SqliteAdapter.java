/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

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
		
		return builder.toString();
	}

	public static String generateColumnName(FieldMetadata field) {
		return PREFIX + field.name.toUpperCase();
	}
	
	public static String generateRelationColumnName(String fieldName) {
		return PREFIX + fieldName.toUpperCase() + SUFFIX;
	}
}
