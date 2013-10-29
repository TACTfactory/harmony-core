package com.tactfactory.harmony.annotation;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(SOURCE)
@Inherited
/**
 * An orderBy constraint.
 * Must be put inside an OrderBys array
 */
public @interface OrderBy {
	/** Column name.*/
	String columnName();
	/** Order (ASC or DESC).*/
	String order();
}
