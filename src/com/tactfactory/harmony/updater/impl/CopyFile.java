package com.tactfactory.harmony.updater.impl;

import java.io.File;

import com.tactfactory.harmony.updater.IUpdater;

/** 
 * Command of generator for copy file.
 * 
 * @author Erwan LeHuitouze <erwan.lehuitouze@tactfactory.com>
 * @author Mickael Gaillard <mickael.gaillard@tactfactory.com>
 */
public final class CopyFile implements IUpdater {

    private final String fileSource;
    private final String fileDestination;

    /**
     * Constructor of the command generator.
     * 
     * @param source String path of the source.
     * @param destination String path of the destination.
     */
    public CopyFile(String source, String destination) {
        this.fileSource = source;
        this.fileDestination = destination;
    }

    /**
     * Constructor of the command generator.
     * 
     * @param source File instance of the source.
     * @param destination File instance of the destination.
     */
    public CopyFile(File source, File destination) {
    	this(source.getPath(), destination.getPath());
    }

    /**
     * The path of the source to copy.
     * 
     * @return String path of the source.
     */
    public String getFileSource() {
        return fileSource;
    }

    /**
     * The path of the destination to copy.
     * 
     * @return String path of the destination.
     */
    public String getFileDestination() {
        return fileDestination;
    }
}
