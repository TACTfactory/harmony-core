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
 * (Optional) Annotation used to precise Inheritance type. <br />
 * <br />
 * You must put this annotation on the topmost class of the entity hierarchy.<br />
 * <br />
 * The inheritance type can be one of two modes :<br />
 *         - Joined inheritance: one table for the parent, one table for each
 *             children. A OneToOne relation on the primary keys will do the
 *             junction.<br />
 *         - SingleTab inheritance: one table for the mother and all its children.
 *             A discriminator column will be added to the table to distinguish
 *             the type of the entity. This column can be customized with the
 *             "@DiscriminatorColumn" and "@DiscriminatorIdentifier" annotations.
 * <br />
 * <br />
 * By default, if this annotation is not present, the Joined mode will be used.
 */
public @interface InheritanceType {
    /**
     * Enum for the different annotation types.
     */
    public enum InheritanceMode {
        /**
         * Joined inheritance : One table for every entity. OneToOne relation
         * to bind them. (Default)
         */
        JOINED("Joined"),
        /**
         * SingleTab inheritance : One table for each entity. Discriminator
         * column to distinguish type.
         */
        SINGLE_TABLE("SingleTable");

        private String value;

        private InheritanceMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
    /**
     * The inheritance mode.
     */
    InheritanceMode value();
}
