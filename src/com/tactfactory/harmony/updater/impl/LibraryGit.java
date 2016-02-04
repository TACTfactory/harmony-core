/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.updater.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.GitUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.GitUtils.GitException;

/**
 * Command of generator for install library from Git.
 *
 * @author Erwan LeHuitouze <erwan.lehuitouze@tactfactory.com>
 * @author Mickael Gaillard <mickael.gaillard@tactfactory.com>
 */
public class LibraryGit implements IUpdater {

    private final String url;
    private final String path;
    private final String branch;
    private final String name;
    private final List<File> filesToDelete;
    private final String libraryPath;

    /**
     * Constructor of the command generator.
     *
     * @param url Repository url.
     * @param path Local folder to install.
     * @param branch Branch to checkout.
     * @param name Name of the library.
     * @param filesToDelete Excluded files of project.
     * @param libraryPath Path of the sub-folder (eg. ActionBarSherlock is compose by sample and the real lib folder)
     */
    public LibraryGit(String url, String path, String branch, String name,
            List<File> filesToDelete, String libraryPath) {
        this.url = url;
        this.path = path;
        this.branch = branch;
        this.name = name;
        this.filesToDelete = filesToDelete;
        this.libraryPath = libraryPath;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getBranch() {
        return branch;
    }

    public String getName() {
        return name;
    }

    public List<File> getFilesToDelete() {
        return filesToDelete;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        if (!TactFileUtils.exists(this.getPath())) {
            try {
                final File projectFolder = new File(Harmony.getProjectPath()
                        + generator.getAdapter().getPlatform() + "/");

                GitUtils.cloneRepository(
                        this.getPath(),
                        this.getUrl(),
                        this.getBranch());

                GitUtils.addSubmodule(
                        projectFolder.getAbsolutePath(),
                        this.getPath(),
                        this.getUrl());

                // Delete useless files
                if (this.getFilesToDelete() != null) {
                    for (File fileToDelete : this.getFilesToDelete()) {
                        TactFileUtils.deleteRecursive(fileToDelete);
                    }
                }
            } catch (IOException e) {
                ConsoleUtils.displayError(e);
            } catch (GitException e) {
                ConsoleUtils.displayError(e);
            }
        }
    }
}