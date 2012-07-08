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
