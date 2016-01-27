/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base Metadata class.
 */
public abstract class BaseMetadata implements Metadata {
    /** Component name. */
    private String name;

    /** List of bundles Metadata. */
    private Map<String, Metadata> options = new LinkedHashMap<String, Metadata>();

    /**
     * Get Metadata option name.
     * @return The option name
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @return the options
     */
    public final Map<String, Metadata> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public final void setOptions(final Map<String, Metadata> options) {
        this.options = options;
    }

    /**
     * @param name the name to set
     */
    public final void setName(final String name) {
        this.name = name;
    }

}
