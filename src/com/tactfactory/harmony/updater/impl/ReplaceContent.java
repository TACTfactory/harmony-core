package com.tactfactory.harmony.updater.impl;

import java.io.File;
import java.io.IOException;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IReplaceContent;
import com.tactfactory.harmony.utils.TactFileUtils;

public class ReplaceContent implements IReplaceContent {

    final String sourceFile;
    final String oldContent;
    final String newContent;

    public ReplaceContent(String sourceFile,
            String oldContent, String newContent) {
        this.sourceFile = sourceFile;
        this.oldContent = oldContent;
        this.newContent = newContent;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        File file = new File(this.sourceFile);

        if (file != null && file.isFile()) {
            String strFile = TactFileUtils.fileToString(file);

            strFile = strFile.replace(oldContent, newContent);

            try {
                TactFileUtils.writeStringToFile(
                        new File(this.sourceFile),
                        strFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
