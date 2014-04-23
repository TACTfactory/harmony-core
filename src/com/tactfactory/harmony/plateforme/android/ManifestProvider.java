package com.tactfactory.harmony.plateforme.android;

import com.tactfactory.harmony.template.ProviderGenerator;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestProvider implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String providerNamespace;
    private final String providerName;
    
    public ManifestProvider(AndroidAdapter adapter, String providerNamespace,
            String providerName) {
        this.adapter = adapter;
        this.providerNamespace = providerNamespace;
        this.providerName = providerName;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifestUpdater = new ManifestUpdater(this.adapter);
        manifestUpdater.addProvider(
                String.format("%s.%s",
                        providerNamespace,
                        providerName),
                AndroidProjectAdapter.STRING_PREFIX + ProviderGenerator.PROVIDER_NAME_STRING_ID,
                providerNamespace,
                AndroidProjectAdapter.STRING_PREFIX + ProviderGenerator.PROVIDER_DESCRIPTION_STRING_ID);
        manifestUpdater.save();
    }

}
