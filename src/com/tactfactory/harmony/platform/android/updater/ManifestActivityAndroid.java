/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import com.tactfactory.harmony.generator.androidxml.ManifestUpdater;
import com.tactfactory.harmony.generator.androidxml.manifest.ManifestActivity;
import com.tactfactory.harmony.platform.android.AndroidAdapter;
import com.tactfactory.harmony.updater.IManifestActivity;

public class ManifestActivityAndroid implements IManifestActivity {

    private final AndroidAdapter adapter;
    
    private String entity;
    private String entityPackage;
    private String activityName;
    
    private ManifestActivity activity;
    
    public ManifestActivityAndroid(AndroidAdapter adapter, String entity,
            String entityPackage, String activity) {
        this.adapter = adapter;
        this.entity = entity;
        this.entityPackage = entityPackage;
        this.activityName = activity;
    }
    
    public ManifestActivityAndroid(AndroidAdapter adapter,
            ManifestActivity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifestUpdater = new ManifestUpdater(this.adapter);
        
        if (this.activity == null) {
            manifestUpdater.addActivity(
                    this.adapter.getApplicationMetadata().getProjectNameSpace(),
                    this.activityName,
                    this.entity,
                    this.entityPackage);
        } else {
            manifestUpdater.addActivity(this.activity);
        }
        
        manifestUpdater.save();
    }
}
