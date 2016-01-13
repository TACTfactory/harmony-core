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

public class ManifestPermissionAndroid implements IUpdaterFile {

    private final String permission;

    public ManifestPermissionAndroid(String permission) {
        this.permission = permission;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        ManifestUpdater manifest = new ManifestUpdater(generator.getAdapter());
        manifest.addPermission(this.permission);
        manifest.save();
    }

}
