package com.tactfactory.harmony.plateforme.android;

import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.template.androidxml.manifest.ManifestActivity;
import com.tactfactory.harmony.updater.IManifestActivity;

public class AndroidManifestActivity implements IManifestActivity {

    private final AndroidAdapter adapter;
    
    private String entity;
    private String entityPackage;
    private String activityName;
    
    private ManifestActivity activity;
    
    public AndroidManifestActivity(AndroidAdapter adapter, String entity,
            String entityPackage, String activity) {
        this.adapter = adapter;
        this.entity = entity;
        this.entityPackage = entityPackage;
        this.activityName = activity;
    }
    
    public AndroidManifestActivity(AndroidAdapter adapter,
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
