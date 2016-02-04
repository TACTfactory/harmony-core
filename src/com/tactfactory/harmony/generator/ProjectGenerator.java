/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.harmony.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Generator class for the project.
 *
 */
public class ProjectGenerator extends BaseGenerator<IAdapter> {

    /**
     * Constructor.
     * @param adapter The adapter to use for the generation.
     * @throws Exception if adapter is null
     */
    public ProjectGenerator(final IAdapter adapter) throws Exception {
        super(adapter);

        this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
    }

    /**
     * Remove Platform specific Project Structure.
     * @return success to make the platform project folder
     */
    public final boolean removeProject() {
        boolean result = false;

        final File dirproj = new File(
                String.format("%s/%s/",
                        Harmony.getProjectPath(),
                        this.getAdapter().getPlatform()));

        final int removeResult = TactFileUtils.deleteRecursive(dirproj);

        if (removeResult == 0) {
            result = true;

            ConsoleUtils.display("Project " + this.getAdapter().getPlatform() + " removed!");
        } else {
            ConsoleUtils.display("An error has occured while deleting the project.");
        }

        return result;
    }

    /**
     * Make Project Structure.
     * @return success to make the platform project folder
     */
    public final boolean makeProject() {
        boolean result = false;

        this.clearProjectSources();
        this.createFolders();
        this.makeSources();
        this.initGitProject();
        this.addResources();
        this.generateStartView();

        // copy libraries
        List<IUpdater> libraries = this.getAdapter().getAdapterProject().getLibraries();
        this.processUpdater(libraries);

        //remove unused files in library.
        List<IUpdater> librariesFiles = this.getAdapter().getAdapterProject().getFilesToDelete();
        this.processUpdater(librariesFiles);

        // Make Test project
        try {
            new TestGenerator(this.getAdapter()).initTestProject();
            result = true;
        } catch (Exception e) {
            ConsoleUtils.displayError(e);
        }

        return result;
    }

    /**
     * Generate StartView File and merge it with datamodel.
     */
    public final void generateStartView() {
        ConsoleUtils.display(">> Generate Start view & Strings...");

        List<IUpdater> files = this.getAdapter().getAdapterProject().getStartViewFiles();

        this.processUpdater(files);
    }

    /**
     * Updates the local.properties SDK Path with the SDK Path stored
     * in the ApplicationMetadata.
     */
    public static final void updateSDKPath() {
        boolean sdkFound = false;

        final File fileProp = new File(
                String.format("%s/%s",
                        Harmony.getProjectAndroidPath(),
                        "local.properties"));

        if (fileProp.exists()) {
            final List<String> lines = TactFileUtils.fileToStringArray(fileProp);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("sdk.dir=")) {
                    lines.set(i, "sdk.dir=" + ApplicationMetadata.getAndroidSdkPath());
                    sdkFound = true;
                    break;
                }
            }

            if (!sdkFound) {
                lines.set(lines.size() - 1, "sdk.dir=" + ApplicationMetadata.getAndroidSdkPath());
            }

            TactFileUtils.stringArrayToFile(lines, fileProp);
        }
    }

    /**
     * Update the project dependencies.
     */
    public final void updateDependencies() {
        try {
            TactFileUtils.makeFolder(this.getAdapter().getLibsPath());

            // copy libraries
            List<IUpdater> libraries = this.getAdapter().getAdapterProject().getLibraries();
            this.processUpdater(libraries);
        } catch (Exception e) {
            ConsoleUtils.displayError(e);
        }
    }

    /**
     * Create project folders.
     */
    private void createFolders() {
        List<IUpdater> createFolders = this.getAdapter().getAdapterProject().getCreateFolders();

        this.processUpdater(createFolders);
    }

    /**
     * Make sources project File.
     */
    private void makeSources() {
        List<IUpdater> files = this.getAdapter().getAdapterProject().getProjectFiles();

        this.processUpdater(files);

        try {
            new MenuGenerator(this.getAdapter()).generateMenu();
        } catch (Exception e) {
            ConsoleUtils.displayError(e);
        }
    }

    /**
     * Add resources files.
     */
    private void addResources() {
        final String resourcePath = String.format("%s", this.getAdapter().getRessourcePath());
        final String templateResourcePath =  String.format("%s/%s/%s",
                Harmony.getBundlePath(),
                "tact-core",
                this.getAdapter().getTemplateRessourcePath());

        try {
            List<String> directories = this.getAdapter().getDirectoryForResources();

            for (String directory : directories) {
                TactFileUtils.copyDirectory(
                        new File(String.format("%s/%s/",
                                templateResourcePath,
                                directory)),
                        new File(String.format("%s/%s/",
                                resourcePath,
                                directory)));
            }
        } catch (IOException e) {
            ConsoleUtils.displayError(e);
        }
    }

    /**
     * Initialize git project.
     */
    private void initGitProject() {
        final File projectFolder = new File(Harmony.getProjectPath() + this.getAdapter().getPlatform());

        try {
            Git.init().setDirectory(projectFolder).call();
        } catch (GitAPIException e) {
            ConsoleUtils.displayError(e);
        }
    }

    /**
     * Delete files that need to be recreated.
     */
    private void clearProjectSources() {
        List<IUpdater> files = this.getAdapter().getAdapterProject().getProjectFilesToClear();

        this.processUpdater(files);
    }
}
