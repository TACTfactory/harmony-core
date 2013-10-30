package com.tactfactory.harmony.annotation;


import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

@Documented
@Retention(SOURCE)
@Inherited
/**
 * List of OrderBy annotations used by this entity or relation.
 * Can be put on an Entity or a OneToMany/ManyToMany relation.
 */
public @interface OrderBys {
	/** List of order by constraints. */
	OrderBy[] value();
}
