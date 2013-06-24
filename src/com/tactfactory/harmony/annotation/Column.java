/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.annotation;

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
	/** Default field length. */
	int DEFAULT_LENGTH = 255;
	
	/**
	 * Mapping Type defines the mapping between a Java type and an SQL type.
	 */
	public enum Type {
		//Name		Internal	length	null	prec	scale	unique	isLocale
		
		// BASE
		/** String type. */
		STRING	("string", 	255, 	true, 	null, 	null, 	null,	null),
		/** Text type. */
		TEXT	("text", 	1024, 	true, 	null, 	null, 	null,	null),
		/** boolean type. */
		BOOLEAN	("boolean", 	null, 	false, 	null, 	null, 	null,	null),
		/** Integer type. */
		INTEGER	("integer", 	null, 	true, 	null, 	null, 	null,	null),
		/** short type. */
		SHORT	("short", 		null, 	false,	null,	null,	null,	null),
		/** byte type. */
		BYTE	("byte", 		null, 	false,	null,	null,	null,	null),
		/** char type. */
		CHAR	("char", 		null, 	false,	null,	null,	null,	null),
		/** char type. */
		CHARACTER	("character", null, false,	null,	null,	null,	null),
		/** int type. */
		INT		("int", 		null, 	false,	null,	null,	null,	null),
		/** long type. */
		LONG 	("long", 	null, 	false,	null,	null,	null,	null),
		/** float type. */
		FLOAT	("float", 	null, 	false,	null,	null,	null,	null),
		/** double type. */
		DOUBLE  ("double", 	null, 	false,	null,	null,	null,	null),
		/** DateTime type. */
		DATETIME("datetime", null,	true,	null,	null,	null,	false),
		/** Date type. */
		DATE	("date", 	null, 	true,	null,	null,	null,	false),
		/** Time type. */
		TIME	("time",		null,	true,	null,	null,	null,	false),
		/** Enum type. */
		ENUM	("enum",		null,	false,	null,	null,	null,	null),
		
		// EXTEND
		/** Login type. */
		LOGIN	("login", 	255, 	false,	null,	null,	true,	null),
		/** Password type. */
		PASSWORD("password", 255, 	false,	null,	null,	null,	null),
		/** Email type. */
		EMAIL	("email", 	255,	true,	null,	null,	true,	null),
		/** Phone type. */
		PHONE	("phone", 	24,		true,	null,	null,	null,	null),
		/** City type. */
		CITY	("city", 	255, 	true,	null,	null,	null,	null),
		/** Zipcode type. */
		ZIPCODE	("zipcode", 255, 	true,	null,	null,	null,	null),
		/** Country type. */
		COUNTRY	("country",	255,	true,	null,	null,	null,	null),
		/** EAN type. */
		BC_EAN	("ean",		12,		true,	null,	null,	null,	null);
		
		/** Type type. */
		private String type;
		
		/** Default length. */
		private int length = Integer.MAX_VALUE;
		
		/** Default nullability. */
		private final boolean nullable = false;
		
		/** Default unicity. */
		private boolean unique = false;
		
		/** Is locale by default ? */
		private boolean isLocale = false;
		
		//columnDefinition is define by DatabaseAdapter
		/** Default precision. */
		private int precision = Integer.MAX_VALUE;
		
		/** Default scale. */
		private int scale = Integer.MAX_VALUE;
		

		/**
		 * Constructor.
		 * @param value name
		 * @param length default length
		 * @param nullable default nullability
		 * @param precision default precision
		 * @param scale default scale
		 * @param unique default unicity
		 * @param isLocale default locality
		 */
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
		
		/**
		 * Get the type name.
		 * @return The type name
		 */
		public String getValue() {
			return this.type;
		}
		
		/**
		 * Get the type default length.
		 * @return The type default length
		 */
		public int getLength() {
			return this.length;
		}
		
		/**
		 * Get the type default nullability.
		 * @return The type default nullability.
		 */
		public boolean isNullable() {
			return this.nullable;
		}
		
		/**
		 * Get the type default unicity.
		 * @return The type default unicity
		 */
		public boolean isUnique() {
			return this.unique;
		}
		
		/**
		 * Get the type default precision.
		 * @return The type default precision
		 */
		public int getPrecision() {
			return this.precision;
		}
		
		/**
		 * Get the type default scale.
		 * @return The type default scale
		 */
		public int getScale() {
			return this.scale;
		}
		
		/**
		 * Get the type default isLocale.
		 * @return The type default isLocale
		 */
		public boolean isLocale() {
			return this.isLocale;
		}
		
		/**
		 * Get the type by its name if it exists.
		 * @param value The type name
		 * @return the corresponding type. null if nothing is found.
		 */
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
		
		/**
		 * Get the type by its enum name if it exists.
		 * @param name The type enum name
		 * @return the corresponding type. null if nothing is found.
		 */
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
			
		/**
		 * Format the type name.
		 * @param name The type name
		 * @return The formatted type name
		 */
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
	 * @see com.tactfactory.harmony.annotation.Column.Type
	 */
	Type type() default Type.STRING;
			
	/** 
	 * The name of the column in the database.
	 */
	String name() default "";
	
	/**
	 * The length of the column in the database.
	 */
	int length() default DEFAULT_LENGTH;
	
	/**
	 * Whether the column is a unique key.
	 */
	boolean unique() default false;
	
	/**
	 * Whether the database column is nullable.
	 */
	boolean nullable() default false;
	
	/**
	 * The precision for a decimal (exact numeric) column.
	 */
	int precision() default 0;
	
	/**
	 * The scale for a decimal (exact numeric) column.
	 */
	int scale() default 0;
	
	/**
	 * The database type to use for the column.
	 */
	String columnDefinition() default "";
	
	/**
	 * Hide field in view.
	 */
	boolean hidden() default false;
	
	/**
	 * (Date, DateTime and Time only)
	 * Use locale for date.
	 */
	boolean locale() default false;
}
