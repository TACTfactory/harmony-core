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
import com.tactfactory.harmony.generator.androidxml.manifest.ManifestActivity;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IManifestActivity;

public class ManifestActivityAndroid implements IManifestActivity {

    private String entity;
    private String entityPackage;
    private String activityName;

    private ManifestActivity activity;

    public ManifestActivityAndroid(String entity, String entityPackage, String activity) {
        this.entity = entity;
        this.entityPackage = entityPackage;
        this.activityName = activity;
    }

    public ManifestActivityAndroid(ManifestActivity activity) {
        this.activity = activity;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        ManifestUpdater manifestUpdater = new ManifestUpdater(generator.getAdapter());

        if (this.activity == null) {
            manifestUpdater.addActivity(
                    generator.getAdapter().getApplicationMetadata().getProjectNameSpace(),
                    this.activityName,
                    this.entity,
                    this.entityPackage);
        } else {
            manifestUpdater.addActivity(this.activity);
        }

        manifestUpdater.save();
    }
}
