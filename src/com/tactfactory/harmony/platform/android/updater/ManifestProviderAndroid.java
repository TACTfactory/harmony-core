/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.generator.ProviderGenerator;
import com.tactfactory.harmony.generator.androidxml.ManifestUpdater;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.android.AndroidProjectAdapter;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestProviderAndroid implements IUpdaterFile {

    private final String providerNamespace;
    private final String providerName;

    public ManifestProviderAndroid(String providerNamespace,
            String providerName) {
        this.providerNamespace = providerNamespace;
        this.providerName = providerName;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        ManifestUpdater manifestUpdater = new ManifestUpdater(generator.getAdapter());
        manifestUpdater.addProvider(
                String.format("%s.%s",
                        providerNamespace,
                        providerName),
                AndroidProjectAdapter.STRING_PREFIX + ProviderGenerator.PROVIDER_NAME_STRING_ID,
                providerNamespace,
                AndroidProjectAdapter.STRING_PREFIX + ProviderGenerator.PROVIDER_DESCRIPTION_STRING_ID);
        manifestUpdater.save();
    }

}
