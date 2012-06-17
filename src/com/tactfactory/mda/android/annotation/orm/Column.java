package com.tactfactory.mda.android.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Inherited
public @interface Column {
	int length() default 0;

}
