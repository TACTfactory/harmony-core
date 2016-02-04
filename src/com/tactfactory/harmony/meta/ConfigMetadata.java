/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

/**
 * Configuration Metadata.
 */
public final class ConfigMetadata {
    /** Identify configuration resource. */
    private String key;

    /** Value of configuration resource. */
    private String value;

    /**
     * Insert to meta a new resource configuration.
     *
     * @param key Configuration Key
     * @param value Configuration String
     * @return the generated ConfigMetadata
     */
    public static ConfigMetadata addConfiguration(final String key, final String value) {
        final ConfigMetadata configMeta = new ConfigMetadata();
        configMeta.key = key;
        configMeta.value = value;

        ApplicationMetadata.INSTANCE.getConfigs().put(configMeta.key, configMeta);

        return configMeta;
    }

    /**
     * @return the key
     */
    public final String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public final void setKey(final String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public final String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public final void setValue(final String value) {
        this.value = value;
    }
}
