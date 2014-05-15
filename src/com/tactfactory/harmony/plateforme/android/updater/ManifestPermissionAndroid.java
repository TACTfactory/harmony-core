package com.tactfactory.harmony.plateforme.android.updater;

import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestPermissionAndroid implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String permission;
    
    public ManifestPermissionAndroid(AndroidAdapter adapter, String permission) {
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
