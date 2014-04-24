package com.tactfactory.harmony.plateforme.android;

import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestPermission implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String permission;
    
    public ManifestPermission(AndroidAdapter adapter, String permission) {
        this.adapter = adapter;
        this.permission = permission;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifest = new ManifestUpdater(this.adapter);
        manifest.addPermission(this.permission);
        manifest.save();
    }

}
