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
 * Interface for generated value.
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface GeneratedValue {
		public enum Strategy {
		/** Auto mode. */
		MODE_AUTO("AUTO"),
		/** No generated value. */
		MODE_NONE("NONE"),
		/** Sequence mode. */
		MODE_SEQUENCE("SEQUENCE"),
		/** Identity mode. */
		MODE_IDENTITY("IDENTITY");
		
		private String value;
		
		private Strategy(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}

	/** Used strategy. Defaults to Auto mode. */
	Strategy strategy() default Strategy.MODE_AUTO;
}
