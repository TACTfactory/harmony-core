package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;

public class FileManipulatorImport extends FileManipulator {
    private final ClassMetadata classMeta;
    private final String className;
    private final String classPackage;
    
    public FileManipulatorImport(SourceFileManipulator manipulator,
            ClassMetadata classMetadata, String className,
            String classPackage) {
        super(manipulator);
        
        this.classMeta = classMetadata;
        this.className = className;
        this.classPackage = classPackage;
    }

    public ClassMetadata getClassMeta() {
        return classMeta;
    }

    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }
}
