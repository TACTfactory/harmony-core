package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;

public class FileManipulatorImplement extends FileManipulator {
    private final ClassMetadata classMeta;
    private final String className;
    
    public FileManipulatorImplement(SourceFileManipulator manipulator,
            ClassMetadata classMetadata, String className) {
        super(manipulator);
        
        this.classMeta = classMetadata;
        this.className = className;
    }

    public ClassMetadata getClassMeta() {
        return classMeta;
    }

    public String getClassName() {
        return className;
    }
}
