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

/**
 * Annotation defining the JoinColumn.
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface JoinColumn {

	/**
	 * The JoinColumn name.
	 */
	String name() default "";

	/**
	 * The referenced Column name.
	 */
	String referencedColumnName() default Id.COLUMN_ID;
}
