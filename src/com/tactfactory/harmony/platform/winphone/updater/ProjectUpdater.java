/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.winphone.updater;

import java.io.File;
import java.util.Locale;

import com.tactfactory.harmony.platform.winphone.WinphoneAdapter;
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
        
        String file = new File(this.filename).getPath();
        
        switch (this.type) {
            case Compile:
                xmlProjectWinphone.addCompileFile(file, this.depends);
                break;
            
            case ApplicationDefinition:
                xmlProjectWinphone.addApplicationDefinitionFile(file);
                break;
                
            case Page:
                xmlProjectWinphone.addPageFile(file);
                break;
                
            case None:
                xmlProjectWinphone.addNoneFile(file);
                break;
                
            case Content:
                xmlProjectWinphone.addContentFile(file);
                break;
                
            case EmbeddedResource:
                xmlProjectWinphone.addEmbeddedFile(file);
                break;
                
            case Reference:
                xmlProjectWinphone.addReferenceFile(file);
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
