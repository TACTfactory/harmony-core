package com.tactfactory.mda.bundles.search.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * To mark a field which will be searchable
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface Searchable {

}
