package com.tactfactory.harmony.updater.impl;

import java.io.File;

import com.tactfactory.harmony.updater.IUpdater;

public final class CopyFile implements IUpdater {
    private final String fileSource;
    private final String fileDestination;
    
    public CopyFile(String source, String destination) {
        this.fileSource = source;
        this.fileDestination = destination;
    }
    
    public CopyFile(File source, File destination) {
    	this(source.getPath(), destination.getPath());
    }

    public String getFileSource() {
        return fileSource;
    }

    public String getFileDestination() {
        return fileDestination;
    }
}
