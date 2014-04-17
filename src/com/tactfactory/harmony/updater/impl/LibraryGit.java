package com.tactfactory.harmony.updater.impl;

import java.io.File;
import java.util.List;

import com.tactfactory.harmony.updater.IUpdater;

public class LibraryGit implements IUpdater {
    private final String url;
    private final String path;
    private final String branch;
    private final String name;
    private final List<File> filesToDelete;
    private final String libraryPath;
    
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
}