/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import java.util.List;

import com.tactfactory.harmony.generator.androidxml.ManifestUpdater;
import com.tactfactory.harmony.platform.android.AndroidAdapter;
import com.tactfactory.harmony.updater.IManifestRemoveLauncher;

public class ManifestRemoveLauncherAndroid implements IManifestRemoveLauncher {

    private final AndroidAdapter adapter;
    private final boolean onlyIfSingle;
    
    public ManifestRemoveLauncherAndroid(AndroidAdapter adapter, boolean onlyIfSingle) {
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
