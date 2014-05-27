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

import com.tactfactory.harmony.updater.IReplaceExtends;
import com.tactfactory.harmony.utils.TactFileUtils;

public class ReplaceExtendsAndroid implements IReplaceExtends {
    
    private final File file;
    private final String oldInheritance;
    private final String newInheritance;
    
    public ReplaceExtendsAndroid(File file, 
            String oldInheritance, 
            String newInheritance) {
        this.file = file;
        this.oldInheritance = oldInheritance;
        this.newInheritance = newInheritance;
    }

    @Override
    public void execute() {
        this.replaceInheritance(
                this.file, this.oldInheritance, this.newInheritance);
    }

    /**
     * Replace the inheritance in all the files of the given folder
     * 
     * @param file The folder to parse. 
     * @param oldInheritance The old inheritance name
     * @param newInheritance The new inheritance name
     */
    private final void replaceInheritance(File file, 
            String oldInheritance, 
            String newInheritance) {
        
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                this.replaceInheritance(f, oldInheritance, newInheritance);
            }
        } else if (!file.getName().equals(oldInheritance + ".java") 
                && !file.getName().equals(newInheritance + ".java")) {
            StringBuffer buffer = TactFileUtils.fileToStringBuffer(file);
            buffer = new StringBuffer(buffer.toString().replace(
                    oldInheritance, 
                    newInheritance));
            TactFileUtils.stringBufferToFile(buffer, file);
        }
    }
}
