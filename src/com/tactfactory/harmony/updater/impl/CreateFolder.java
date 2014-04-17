package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.updater.IUpdater;

public final class CreateFolder implements IUpdater {
    private final String path;
    
    public CreateFolder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
