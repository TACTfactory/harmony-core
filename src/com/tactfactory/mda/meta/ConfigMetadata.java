/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;


public class ConfigMetadata {
	/** Identify configuration resource */
	public String key;
	
	/** Value of configuration resource */
	public String value;
	
	/**
	 * Insert to meta a new resource configuration
	 * 
	 * @param key
	 * @param value
	 * @return the generated ConfigMetadata
	 */
	public static ConfigMetadata addConfiguration(final String key, final String value) {		
		final ConfigMetadata configMeta = new ConfigMetadata();
		configMeta.key = key;
		configMeta.value = value;
		
		ApplicationMetadata.INSTANCE.configs.put(configMeta.key, configMeta);
		
		return configMeta;
	}
}
