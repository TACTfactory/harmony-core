package com.tactfactory.harmony.plateforme.android.updater;

import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestServiceAndroid implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String name;
    private final String label;
    
    /**
     * ManifestServiceAndroid updater to add a service to the android manifest.
     * @param adapter {@link AndroidAdapter}
     * @param name Name of the service (with namespace)
     * @param label Label of the service
     */
    public ManifestServiceAndroid(AndroidAdapter adapter,
            String name, String label) {
        this.adapter = adapter;
        this.name = name;
        this.label = label;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifest = new ManifestUpdater(this.adapter);
        manifest.addService(this.name, this.label);
        manifest.save();
    }

}
