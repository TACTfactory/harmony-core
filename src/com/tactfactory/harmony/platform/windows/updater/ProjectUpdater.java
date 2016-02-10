/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.windows.updater;

import java.io.File;
import java.util.Locale;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.windows.WindowsAdapter;
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
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        XmlProjectWindows xmlProjectWindows = new XmlProjectWindows();

        //TODO replace
        WindowsAdapter adapter = new WindowsAdapter();
        String applicationName = adapter.getApplicationMetadata()
                .getName().toLowerCase(Locale.ENGLISH);
        xmlProjectWindows.open(adapter.getSourcePath()
                + applicationName + ".csproj");

        String file = new File(this.filename).getPath();

        switch (this.type) {
            case Compile:
                xmlProjectWindows.addCompileFile(file, this.depends);
                break;

            case ApplicationDefinition:
                xmlProjectWindows.addApplicationDefinitionFile(file);
                break;

            case Page:
                xmlProjectWindows.addPageFile(file);
                break;

            case None:
                xmlProjectWindows.addNoneFile(file);
                break;

            case Content:
                xmlProjectWindows.addContentFile(file);
                break;

            case EmbeddedResource:
                xmlProjectWindows.addEmbeddedFile(file);
                break;

            case Reference:
                xmlProjectWindows.addReferenceFile(file);
                break;

            default:
                break;
        }

        xmlProjectWindows.save();
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
