package com.tactfactory.harmony.plateforme.android;

import java.io.File;

import com.tactfactory.harmony.updater.IReplaceExtends;
import com.tactfactory.harmony.utils.TactFileUtils;

public class AndroidReplaceExtends implements IReplaceExtends {
    
    private final File file;
    private final String oldInheritance;
    private final String newInheritance;
    
    public AndroidReplaceExtends(File file, 
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
