/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import com.tactfactory.harmony.meta.ConfigMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.old.IConfigFileUtil;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Configuration generator.
 */
public class ConfigGenerator extends BaseGenerator<IAdapter> {
    /**
     * Constructor.
     * @param adapter The adapter to use
     * @throws Exception if adapter is null
     */
    public ConfigGenerator(final IAdapter adapter) throws Exception {
        super(adapter);
    }

    /**
     * Update XML Config.
     */
    public final void generateConfigFile() {
        ConsoleUtils.display(">> Generate config strings...");
        
        try {
            IConfigFileUtil configUtil = this.getAdapter()
                    .getAdapterProject().getConfigFileUtil();
            
            configUtil.open(this.getAdapter().getConfigsPathFile());
            
            Iterable<ConfigMetadata> configs = this.getAppMetas()
                    .getConfigs().values();
            
            for (final ConfigMetadata configMeta : configs) {
                String addedString = configUtil.addElement(
                        configMeta.getKey(),
                        configMeta.getValue());
                
                configMeta.setValue(addedString);
            }
            
            configUtil.save();
        } catch (final Exception e) {
            ConsoleUtils.displayError(e);
        }
    }
}
