/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * To mark a property for 
 * relational persistence the @Column annotation is used. 
 * This annotation usually requires at least 1 attribute to be set, the type. 
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface Column {
	
	/**
	 * Mapping Type defines the mapping between a Java type and an SQL type.
	 */
	public enum Type {
		//Name		Internal	length	null	prec	scale	unique	isLocale
		
		// BASE
		STRING	("string", 	255, 	true, 	null, 	null, 	null,	null),
		TEXT	("text", 	1024, 	true, 	null, 	null, 	null,	null),
		BOOLEAN	("boolean", 	null, 	false, 	null, 	null, 	null,	null),
		INTEGER	("integer", 	null, 	true, 	null, 	null, 	null,	null),
		INT		("int", 		null, 	false,	null,	null,	null,	null),
		FLOAT	("float", 	null, 	false,	null,	null,	null,	null),
		DATETIME("datetime", null,	true,	null,	null,	null,	false),
		DATE	("date", 	null, 	true,	null,	null,	null,	false),
		TIME	("time",		null,	true,	null,	null,	null,	false),
		
		// EXTEND
		LOGIN	("login", 	255, 	false,	null,	null,	true,	null),
		PASSWORD("password", 255, 	false,	null,	null,	null,	null),
		EMAIL	("email", 	255,	true,	null,	null,	true,	null),
		PHONE	("phone", 	24,		true,	null,	null,	null,	null),
		CITY	("city", 	255, 	true,	null,	null,	null,	null),
		ZIPCODE	("zipcode", 255, 	true,	null,	null,	null,	null),
		COUNTRY	("country",	255,	true,	null,	null,	null,	null),
		BC_EAN	("ean",		12,		true,	null,	null,	null,	null);
		
		private String type;
		private int length = Integer.MAX_VALUE;
		private final boolean nullable = false;
		private boolean unique = false;
		private boolean isLocale = false;
		//columnDefinition is define by DatabaseAdapter
		private int precision = Integer.MAX_VALUE;
		private int scale = Integer.MAX_VALUE;
		

		private Type(final String value,
				final Integer length, 
				final Boolean nullable, 
				final Integer precision, 
				final Integer scale, 
				final Boolean unique,
				final Boolean isLocale) {
			this.type = value;
			
			if (length != null) {
				this.length = length;
			}

			if (precision != null) {
				this.precision  = precision;
			}
			
			if (scale != null) {
				this.scale = scale;
			}
			
			if (unique != null) {
				this.unique = unique;
			}
			
			if (isLocale != null) {
				this.isLocale = isLocale;
			}
		}
		
		public String getValue() {
			return this.type;
		}
		
		public int getLength() {
			return this.length;
		}
		
		public boolean isNullable() {
			return this.nullable;
		}
		
		public boolean isUnique() {
			return this.unique;
		}
		
		public int getPrecision() {
			return this.precision;
		}
		
		public int getScale() {
			return this.scale;
		}
		
		public boolean isLocale() {
			return this.isLocale;
		}
		
		public static Type fromString(final String value) {
			Type ret = null;
			if (value != null) {
				for (final Type type : Type.values()) {
					if (value.equalsIgnoreCase(type.type)) {
						ret = type;
					}    
				}
			}
			
			return ret;
		}
		
		

		public static Type fromName(final String name) {
			String realName;
			Type ret;
			if (name.lastIndexOf('.') > 0) {
				// Take only what comes after the last dot
				realName = name.substring(name.lastIndexOf('.') + 1); 
			} else {
				realName = name;
			}
			try {
				final Field field = 
						Type.class.getField(realName.toUpperCase());	
				if (field.isEnumConstant()) {
					ret = (Type) field.get(Type.class);
				} else {
					 ret = null; 
				}
			} catch (final NoSuchFieldException e) {
				ret = null;
			} catch (final IllegalAccessException e) {
				ret = null;				
			}
			return ret;
		}
			
		public static String toTypeString(final String name) {
			String ret = name;
			final Type t = fromName(name);
			if (t != null) {
				ret = t.getValue();
			}
			return ret;
		}

	}
	
	/**
	 * The mapping type to use for the column.
	 * 
	 * @return (optional, defaults to "string") 
	 * The mapping type to use for the column.
	 * 
	 * @see com.tactfactory.mda.annotation.Column.Type
	 */
	Type type() default Type.STRING;
			
	/** 
	 * The name of the column in the database.
	 * 
	 * @return (optional, defaults to field name) 
	 * The name of the column in the database.
	 */
	String name() default "";
	
	/**
	 * The length of the column in the database.
	 * 
	 * @return (optional, default 255) 
	 * The length of the column in the database. 
	 * (Applies only if a string-valued column is used).
	 */
	int length() default 255;
	
	/**
	 * Whether the column is a unique key.
	 * 
	 * @return (optional, default FALSE) Whether the column is a unique key.
	 */
	boolean unique() default false;
	
	/**
	 * Whether the database column is nullable.
	 * 
	 * @return (optional, default FALSE) 
	 * Whether the database column is nullable.
	 */
	boolean nullable() default false;
	
	/**
	 * The precision for a decimal (exact numeric) column.
	 * 
	 * @return (optional, default 0) 
	 * The precision for a decimal (exact numeric) column. 
	 * (Applies only if a decimal column is used.)
	 */
	int precision() default 0;
	
	/**
	 * The scale for a decimal (exact numeric) column.
	 * 
	 * @return (optional, default 0) 
	 * The scale for a decimal (exact numeric) column. 
	 * (Applies only if a decimal column is used.)
	 */
	int scale() default 0;
	
	/**
	 * The database type to use for the column
	 * 
	 * @return (optional, defaults to type mapping) 
	 * The database type to use for the column.
	 * 
	 * @see com.tactfactory.mda.plateforme.SqliteAdapter for mapping list
	 */
	String columnDefinition() default "";
	
	/**
	 * Hide field in view
	 * 
	 * @return (optional, defaults to false) Hide the filed in a view.
	 */
	boolean hidden() default false;
	
	/**
	 * (Date, DateTime and Time only)
	 * Use locale for date
	 * 
	 * @return (optional, defaults to true) Adjust date to locale
	 */
	boolean locale() default false;
}
