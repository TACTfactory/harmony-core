/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme.android.updater;

import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ManifestApplicationThemeAndroid implements IUpdaterFile {

    private final AndroidAdapter adapter;
    private final String theme;
    
    public ManifestApplicationThemeAndroid(AndroidAdapter adapter, String theme) {
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
