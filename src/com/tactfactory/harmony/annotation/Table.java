/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The entity will be persisted to a table with @Table annotation.
 * By default, the entity will be persisted 
 * to a table with the same name as the class name.
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
@Inherited
public @interface Table {

	/**
	 * The table name.
	 */
	String name() default "";
}
