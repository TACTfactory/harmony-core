/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Interface for generated value.
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface GeneratedValue {
	/** Auto mode. */
	String MODE_AUTO = "AUTO";
	/** No generated value. */
	String MODE_NONE = "NONE";
	/** Sequence mode. */
	String MODE_SEQUENCE = "SEQUENCE";
	/** Identity mode. */
	String MODE_IDENTITY = "IDENTITY";
	
	/** Used strategy. Defaults to Auto mode. */
	String strategy() default MODE_AUTO;
}
