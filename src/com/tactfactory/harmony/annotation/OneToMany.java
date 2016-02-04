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
 * Annotation used to define a OneToMany relation (1-N).
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
@Inherited
public @interface OneToMany {
    /**
     * The Entity targeted by the relation.
     */
    String targetEntity() default "";

    /**
     * The field in the target Entity mapping
     * this relation (for bidirectionnal entities only).
     */
    String mappedBy() default "";
}
