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
 * Annotation for defining OneToOne relations (1-1).
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface OneToOne {
	/**
	 * The Entity targeted by the relation.
	 */
	String targetEntity() default "";

	/**
	 * The field which map this relation.
	 */
	String mappedBy() default "";

	/**
	 * The field which inverse this relation.
	 */
	String inversedBy() default "";

	/**
	 * Is this field hidden in the views ?
	 */
	boolean hidden() default false;
}
