/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.annotation;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

/**
 * List of OrderBy annotations used by this entity or relation.
 * Can be put on an Entity or a OneToMany/ManyToMany relation.
 */
@Documented
@Retention(SOURCE)
@Inherited
public @interface OrderBys {
	/** List of order by constraints. */
	OrderBy[] value();
}
