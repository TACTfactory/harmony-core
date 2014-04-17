package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.updater.IUpdater;

public final class DeleteFile implements IUpdater {
    private final String path;
    
    public DeleteFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}