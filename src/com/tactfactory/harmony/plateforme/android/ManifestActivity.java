package com.tactfactory.harmony.plateforme.android;

import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestActivity implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String entity;
    private final String entityPackage;
    private final String activity;
    
    public ManifestActivity(AndroidAdapter adapter, String entity,
            String entityPackage, String activity) {
        this.adapter = adapter;
        this.entity = entity;
        this.entityPackage = entityPackage;
        this.activity = activity;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifestUpdater = new ManifestUpdater(this.adapter);
        manifestUpdater.addActivity(
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.activity,
                this.entity,
                this.entityPackage);
        manifestUpdater.save();
    }

}
