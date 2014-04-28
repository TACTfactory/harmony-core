package com.tactfactory.harmony.plateforme.winphone.updater;

import java.util.Locale;

import com.tactfactory.harmony.plateforme.winphone.WinphoneAdapter;
import com.tactfactory.harmony.plateforme.winphone.XmlProjectWinphone;
import com.tactfactory.harmony.updater.IUpdaterFile;

public class ProjectUpdater implements IUpdaterFile {

    private final String filename;
    private final String depends;
    private final FileType type;
    
    public ProjectUpdater(FileType type, String filename) {
        this(type, filename, null);
    }
    
    public ProjectUpdater(FileType type, String filename, String filenameXaml) {
        this.type = type;
        this.filename = filename;
        this.depends = filenameXaml;
    }
    
    @Override
    public void execute() {
        XmlProjectWinphone xmlProjectWinphone = new XmlProjectWinphone();
        
        //TODO replace
        WinphoneAdapter adapter = new WinphoneAdapter();
        String applicationName = adapter.getApplicationMetadata()
                .getName().toLowerCase(Locale.ENGLISH);
        xmlProjectWinphone.open(adapter.getSourcePath()
                + applicationName + ".csproj");
        
        switch (this.type) {
            case Compile:
                xmlProjectWinphone.addCompileFile(this.filename, this.depends);
                break;
            
            case ApplicationDefinition:
                xmlProjectWinphone.addApplicationDefinitionFile(this.filename);
                break;
                
            case Page:
                xmlProjectWinphone.addPageFile(this.filename);
                break;
                
            case None:
                xmlProjectWinphone.addNoneFile(this.filename);
                break;
                
            case Content:
                xmlProjectWinphone.addContentFile(this.filename);
                break;
                
            case EmbeddedResource:
                xmlProjectWinphone.addEmbeddedFile(this.filename);
                break;
                
            case Reference:
                xmlProjectWinphone.addReferenceFile(this.filename);
                break;
                
            default:
                break;
        }
        
        xmlProjectWinphone.save();
    }

    public enum FileType {
        Compile,
        ApplicationDefinition,
        Page,
        None,
        Content,
        EmbeddedResource,
        Reference
    }
}
