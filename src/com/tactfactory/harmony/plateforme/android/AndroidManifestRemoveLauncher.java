package com.tactfactory.harmony.plateforme.android;

import java.util.List;

import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IManifestRemoveLauncher;

public class AndroidManifestRemoveLauncher implements IManifestRemoveLauncher {

    private final AndroidAdapter adapter;
    private final boolean onlyIfSingle;
    
    public AndroidManifestRemoveLauncher(AndroidAdapter adapter, boolean onlyIfSingle) {
        this.adapter = adapter;
        this.onlyIfSingle = onlyIfSingle;
    }
    
    @Override
    public void execute() {
        ManifestUpdater manifest = new ManifestUpdater(this.adapter);
        
        // Find if launcher activities already exist.
        List<String> launcherActivitiesNames = 
                manifest.getLauncherActivitiesNames();
        
        if (this.onlyIfSingle && launcherActivitiesNames.size() == 1) {
            // TODO : Activity name will certainly be useful for issue #4181
            String activityName = launcherActivitiesNames.get(0);
            manifest.removeLauncherIntentFilter(activityName);
        } else {
            for (String launcher : launcherActivitiesNames) {
                manifest.removeLauncherIntentFilter(launcher);
            }
        }
    }

}
