package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;
import com.tactfactory.harmony.updater.IFileManipulator;

public class FileManipulator implements IFileManipulator {
    protected final SourceFileManipulator sourceManipulator;
    
    public FileManipulator(SourceFileManipulator manipulator) {
        this.sourceManipulator = manipulator;
    }

    public SourceFileManipulator getSourceManipulator() {
        return sourceManipulator;
    }
}
