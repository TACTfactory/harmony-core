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
 * identifier used for this class.<br/>
 *
 * You must put this annotation on the children of the inheritance hierarchy.
 * <br/>
 * By default, it will be the class name.
 *
 */
public @interface DiscriminatorIdentifier {
    /** The discriminator identifier. */
    String value();
}
