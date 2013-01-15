/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import com.tactfactory.mda.ConsoleUtils;

import static java.lang.annotation.RetentionPolicy.SOURCE;
import static java.lang.annotation.ElementType.FIELD;

/**
 * To mark a property for relational persistence the @Column annotation is used. 
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
		//Name		Internal	length	null	prec	scale	unique
		
		// BASE
		STRING(		"string", 	255, 	true, 	null, 	null, 	null),
		TEXT(		"text", 	1024, 	true, 	null, 	null, 	null),
		BOOLEAN(	"boolean", 	null, 	false, 	null, 	null, 	null),
		INTEGER(	"integer", 	null, 	true, 	null, 	null, 	null),
		INT(		"int", 		null, 	false,	null,	null,	null),
		FLOAT(		"float", 	null, 	false,	null,	null,	null),
		DATETIME(	"datetime", null,	true,	null,	null,	null),
		DATE(		"date", 	null, 	true,	null,	null,	null),
		TIME(		"time",		null,	true,	null,	null,	null),
		
		// EXTEND
		LOGIN(		"login", 	255, 	false,	null,	null,	true),
		PASSWORD(	"password", 255, 	false,	null,	null,	null),
		EMAIL(		"email", 	255,	true,	null,	null,	true),
		PHONE(		"phone", 	24,		true,	null,	null,	null),
		CITY(		"city", 	255, 	true,	null,	null,	null),
		ZIPCODE(	"zipcode", 	9999999,true,	null,	null,	null),
		COUNTRY(	"country",	255,	true,	null,	null,	null),
		BC_EAN(		"ean",		12,		true,	null,	null,	null);
		
		private String type;
		private int length = Integer.MAX_VALUE;
		private boolean nullable = false;
		private boolean unique = false;
		//columnDefinition is define by DatabaseAdapter
		private int precision = Integer.MAX_VALUE;
		private int scale = Integer.MAX_VALUE;
		
		private Type(String value, Integer length, Boolean nullable, Integer precision, Integer scale, Boolean unique){
			this.type = value;
			
			if (length != null)
				this.length = length;

			if (precision != null)
				this.precision  = precision;
			
			if (scale != null)
				this.scale = scale;
			
			if (unique != null)
				this.unique = unique;
		}
		
		public String getValue(){
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
		
		public static Type fromString(String value){
			if (value!= null) {
				for (Type type : Type.values()) {
					if (value.equalsIgnoreCase(type.type)) {
						return type;
					}    
				}
			}
			
			return null;
		}
		
		
		public static Type fromName(String name){
			if(name.lastIndexOf(".")>0)
				name = name.substring(name.lastIndexOf(".")+1); // Take only what comes after the last dot
			ConsoleUtils.displayDebug("Searching for Type : "+name);
			try{
				Field field = Type.class.getField(name.toUpperCase());	
				if(field.isEnumConstant()) {
					ConsoleUtils.displayDebug("Found type : "+name);
					return (Type)field.get(Type.class);
				}
				else 
					return null;
			}catch(Exception e){
				return null;
			}
		}
			
		public static String toTypeString(String name){
				if(name.lastIndexOf(".")>0)
					name = name.substring(name.lastIndexOf(".")+1); // Take only what comes after the last dot
				ConsoleUtils.displayDebug("Searching for Type : "+name);
				try{
					Field field = Type.class.getField(name);	
					if(field.isEnumConstant()) {
						ConsoleUtils.displayDebug("Found type : "+name);
						return ((Type)field.get(Type.class)).getValue();
					}
					else 
						return name;
				}catch(Exception e){
					return name;
				}
		}
			
		
		/** Type that maps an SQL VARCHAR to a JAVA string. */
		//public final static String STRING = "string";
		/** Type that maps an SQL VARCHAR to a JAVA string with only ASCII value.*/
		//public final static String LOGIN = "login";
		/** Type that maps an SQL VARCHAR to a JAVA string with only ASCII value but not show. */
		//public final static String PASSWORD = "password";
		/** Type that maps an SQL INT to a JAVA integer. */
		//public final static String INTEGER = "integer";
	    /*smallint: Type that maps a database SMALLINT to a JAVA integer.
	    bigint: Type that maps a database BIGINT to a JAVA string.
	    boolean: Type that maps an SQL boolean to a JAVA boolean.
	    decimal: Type that maps an SQL DECIMAL to a JAVA double.
	    date: Type that maps an SQL DATETIME to a JAVA DateTime object.
	    time: Type that maps an SQL TIME to a JAVA DateTime object.*/
	    /** Type that maps an SQL DATETIME/TIMESTAMP to a JAVA DateTime object. */
		//public final static String DATETIME = "datetime";
	    /** Type that maps an SQL CLOB to a JAVA string. */
	   // public final static String TEXT = "text";
	    /*object: Type that maps a SQL CLOB to a JAVA object using serialize() and unserialize()
	    array: Type that maps a SQL CLOB to a JAVA object using serialize() and unserialize()
	    float: Type that maps a SQL Float (Double Precision) to a JAVA double. IMPORTANT: Works only with locale settings that use decimal points as separator. */

	}
	
	/**
	 * The mapping type to use for the column.
	 * 
	 * @return (optional, defaults to "string") The mapping type to use for the column.
	 * 
	 * @see com.tactfactory.mda.annotation.Column.Type
	 */
	Type type() default Type.STRING;
			
	/** 
	 * The name of the column in the database.
	 * 
	 * @return (optional, defaults to field name) The name of the column in the database.
	 */
	String name() default "";
	
	/**
	 * The length of the column in the database.
	 * 
	 * @return (optional, default 255) The length of the column in the database. (Applies only if a string-valued column is used).
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
	 * @return (optional, default FALSE) Whether the database column is nullable.
	 */
	boolean nullable() default false;
	
	/**
	 * The precision for a decimal (exact numeric) column.
	 * 
	 * @return (optional, default 0) The precision for a decimal (exact numeric) column. (Applies only if a decimal column is used.)
	 */
	int precision() default 0;
	
	/**
	 * The scale for a decimal (exact numeric) column.
	 * 
	 * @return (optional, default 0) The scale for a decimal (exact numeric) column. (Applies only if a decimal column is used.)
	 */
	int scale() default 0;
	
	/**
	 * The database type to use for the column
	 * 
	 * @return (optional, defaults to type mapping) The database type to use for the column.
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
}
