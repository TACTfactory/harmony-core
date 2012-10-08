/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;
import static java.lang.annotation.ElementType.FIELD;

@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface Column {
	
	/**
	 * Mapping Type defines the mapping between a Java type and an SQL type.
	 */
	public static abstract class Type {
		/** Type that maps an SQL VARCHAR to a JAVA string. */
		public final static String STRING = "string"; // 
		public final static String LOGIN = "login";
		public final static String PASSWORD = "password";
		/** integer: Type that maps an SQL INT to a JAVA integer. */
		public final static String INTEGER = "integer";
	    /*smallint: Type that maps a database SMALLINT to a PHP integer.
	    bigint: Type that maps a database BIGINT to a PHP string.
	    boolean: Type that maps an SQL boolean to a PHP boolean.
	    decimal: Type that maps an SQL DECIMAL to a PHP double.
	    date: Type that maps an SQL DATETIME to a PHP DateTime object.
	    time: Type that maps an SQL TIME to a PHP DateTime object.*/
	    /** datetime: Type that maps an SQL DATETIME/TIMESTAMP to a PHP DateTime object. */
		public final static String DATETIME = "datetime";
	    /** text: Type that maps an SQL CLOB to a JAVA string. */
	    public final static String TEXT = "text";
	    /*object: Type that maps a SQL CLOB to a PHP object using serialize() and unserialize()
	    array: Type that maps a SQL CLOB to a PHP object using serialize() and unserialize()
	    float: Type that maps a SQL Float (Double Precision) to a PHP double. IMPORTANT: Works only with locale settings that use decimal points as separator. */

	}
	
	/**
	 * @return type: (optional, defaults to "string") The mapping type to use for the column.
	 */
	String type() default Type.TEXT;
			
	/** 
	 * @return name: (optional, defaults to field name) The name of the column in the database.
	 */
	String name() default "";
	
	/**
	 * @return length: (optional, default 255) The length of the column in the database. (Applies only if a string-valued column is used).
	 */
	int length() default 255;
	
	/**
	 * @return unique: (optional, default FALSE) Whether the column is a unique key.
	 */
	boolean unique() default false;
	
	/**
	 * @return nullable: (optional, default FALSE) Whether the database column is nullable.
	 */
	boolean nullable() default false;
	
	/**
	 * @return precision: (optional, default 0) The precision for a decimal (exact numeric) column. (Applies only if a decimal column is used.)
	 */
	int precision() default 0;
	
	/**
	 * @return scale: (optional, default 0) The scale for a decimal (exact numeric) column. (Applies only if a decimal column is used.)
	 */
	int scale() default 0;
}
