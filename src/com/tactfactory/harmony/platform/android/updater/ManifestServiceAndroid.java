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
import com.tactfactory.harmony.generator.androidxml.ManifestUpdater;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestServiceAndroid implements IUpdaterFile {

    private final String name;
    private final String label;

    /**
     * ManifestServiceAndroid updater to add a service to the android manifest.
     * @param name Name of the service (with namespace)
     * @param label Label of the service
     */
    public ManifestServiceAndroid(String name, String label) {
        this.name = name;
        this.label = label;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        ManifestUpdater manifest = new ManifestUpdater(generator.getAdapter());
        manifest.addService(this.name, this.label);
        manifest.save();
    }

}
