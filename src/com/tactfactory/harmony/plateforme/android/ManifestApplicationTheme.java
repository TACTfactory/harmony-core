package com.tactfactory.harmony.plateforme.android;

import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestApplicationTheme implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String theme;
    
    public ManifestApplicationTheme(AndroidAdapter adapter, String theme) {
        this.adapter = adapter;
        this.theme = theme;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifestUpdater = new ManifestUpdater(this.adapter);
        manifestUpdater.setApplicationTheme(this.theme);
        manifestUpdater.save();
    }

}
