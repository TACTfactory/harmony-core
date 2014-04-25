package com.tactfactory.harmony.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * To mark a property for
 * relational persistence the @Column annotation is used.
 * This annotation usually requires at least 1 attribute to be set, the type.
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
@Inherited
public @interface View {
	/**
	 * No views generated for this entity.
	 */
	boolean hidden() default false;

	/**
	 * Should create the list activity for this entity. (default true)
	 */
	boolean list() default true;
	/**
	 * Should create the show activity for this entity. (default true)
	 */
	boolean show() default true;
	/**
	 * Should create the edit activity for this entity. (default true)
	 */
	boolean edit() default true;
	/**
	 * Should create the create activity for this entity. (default true)
	 */
	boolean create() default true;
	/**
	 * Should display the delete button for this entity. (default true)
	 */
	boolean delete() default true;
}
