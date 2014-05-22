package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.updater.IUpdater;

/** 
 * Command of generator for create folder.
 * 
 * @author Erwan LeHuitouze <erwan.lehuitouze@tactfactory.com>
 * @author Mickael Gaillard <mickael.gaillard@tactfactory.com>
 */
public final class CreateFolder implements IUpdater {

    private final String path;

    /**
     * Constructor of the command generator.
     * 
     * @param path String path to create.
     */
    public CreateFolder(String path) {
        this.path = path;
    }

    /**
     * The path of the folder to create.
     * 
     * @return String path of the folder.
     */
    public String getPath() {
        return path;
    }
}
