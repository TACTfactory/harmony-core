/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import java.io.File;
import java.util.ArrayList;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.dependencies.android.sdk.AndroidSDKManager;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.platform.android.AndroidAdapter;
import com.tactfactory.harmony.updater.IUpdateLibrary;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.OsUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

public class UpdateLibraryAndroid implements IUpdateLibrary {
    private final AndroidAdapter adapter;
    private final String libName;
    private final String libraryProjectPath;
    private final String target;
    private final String referencePath;
    private final boolean isSupportV4Dependant;
    
    /**
     * Update an android project library.
     * @param adapter Android adapter
     * @param libName The library name (ie. demact-abs)
     * @param libraryProjectPath The library project path inside the downloaded
     *              folder
     * @param target The SDK build target
     * @param referencePath The library path to reference in your project
     * @param isSupportV4Dependant true if the library is supportv4 dependent
     */
    public UpdateLibraryAndroid(AndroidAdapter adapter,
            String libName, String libraryProjectPath,
            String target, String referencePath, boolean isSupportV4Dependant) {
        this.adapter = adapter;
        this.libName = libName;
        this.libraryProjectPath = libraryProjectPath;
        this.target = target;
        this.referencePath = referencePath;
        this.isSupportV4Dependant = isSupportV4Dependant;
    }

    @Override
    public void execute() {
        ArrayList<String> command = new ArrayList<String>();
        
        //make build sherlock
        String sdkTools = String.format("%s/%s",
                ApplicationMetadata.getAndroidSdkPath(),
                "tools/android");
        
        if (OsUtil.isWindows()) {
            sdkTools += ".bat";
        }

        command.add(new File(sdkTools).getAbsolutePath());
        command.add("update");
        command.add("project");
        command.add("--path");
        command.add(this.libraryProjectPath);
        command.add("--name");
        command.add(this.libName);
        
        if (this.target != null) {
            command.add("--target");
            command.add(this.target);
        }
        
        ConsoleUtils.launchCommand(command);
        command.clear();
        
        if (this.isSupportV4Dependant) {
            AndroidSDKManager.copySupportV4Into(
                    this.libraryProjectPath + "/libs/");
        }

        if (this.referencePath != null) {
            // Update android project to reference the new downloaded library
            String projectPath = Harmony.getProjectPath()
                    + this.adapter.getPlatform();
            
            command.add(new File(sdkTools).getAbsolutePath());
            command.add("update");
            command.add("project");
            command.add("--path");
            command.add(projectPath);
            command.add("--library");
            command.add(TactFileUtils.absoluteToRelativePath(
                    this.referencePath, projectPath));
            ConsoleUtils.launchCommand(command);
            command.clear();
        }
    }
}
