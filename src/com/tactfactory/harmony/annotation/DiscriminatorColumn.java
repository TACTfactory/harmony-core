/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.annotation;

/**
 * (Optional) This annotation is used only in the case of
 * a SingleTable inheritance mode. It will define the discriminator 
 * column used in this inheritance.
 *
 * Defaults to type = "varchar" and name = "${TopMostClassName}Type" 
 */
public @interface DiscriminatorColumn {
	/** The name of the column. */
	String name();
	
	/** The type of the column. */
	String type();
}
