package com.tactfactory.harmony.plateforme.android.updater;

import java.io.File;
import java.io.IOException;

import com.tactfactory.harmony.updater.IAddImport;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

public class AddImportAndroid implements IAddImport {

    private final String sourceFile;
    private final String fullImport;
    
    public AddImportAndroid(String sourceFile, String fullImport) {
        this.sourceFile = sourceFile;
        this.fullImport = fullImport;
    }
    
    @Override
    public void execute() {
        File file = new File(this.sourceFile);
        
        if (file != null && file.isFile()) {
            String strFile = TactFileUtils.fileToString(file);
            
            final String importToAdd = "import " + this.fullImport + ";";
            
            if (!strFile.contains(importToAdd)) {
                strFile = strFile.replaceFirst("import",
                        importToAdd + "\n\nimport");
                
                try {
                    TactFileUtils.writeStringToFile(file, strFile);
                } catch (IOException e) {
                    ConsoleUtils.displayError(e);
                }
            }
        }
    }

}
