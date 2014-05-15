package com.tactfactory.harmony.plateforme.android.updater;

import java.io.File;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.InheritanceMetadata;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.updater.IAddExtends;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

public class AddExtendsAndroid implements IAddExtends {
    
    private final AndroidAdapter adapter;
    private final EntityMetadata entity;
    private final String extendedClassName;
    
    public AddExtendsAndroid(AndroidAdapter adapter, EntityMetadata entity,
            String extendedClassName) {
        this.adapter = adapter;
        this.entity = entity;
        this.extendedClassName = extendedClassName;
    }

    @Override
    public void execute() {
        this.addExtends();
    }

    /**
     * Extends classname in the class if it doesn't already.
     * @param entity The Metadata containing the infos on the java class
     * @param extendedClassName the name of the class to implement
     */
    private final void addExtends() {
        
        if (entity.getInheritance() == null
                        || entity.getInheritance().getSuperclass() == null) {
            
            final String filepath = String.format("%s/%s",
                    this.adapter.getSourceEntityPath(),
                    String.format("%s.java", entity.getName()));
            
            ConsoleUtils.display(">>> Decorate " + entity.getName());

            final File entityFile = TactFileUtils.getFile(filepath);
            
            // Load the file once in a String buffer
            final StringBuffer fileString =
                    TactFileUtils.fileToStringBuffer(entityFile);
            
            ConsoleUtils.displayDebug("Add " + extendedClassName + " extends");
            
            final int classNamePos =
                    fileString.indexOf("class " + entity.getName())
                    + ("class " + entity.getName()).length();
            
            ConsoleUtils.display(">>> Class name POS " + classNamePos);
            fileString.insert(classNamePos, " extends " + extendedClassName);
            
            InheritanceMetadata inheritanceMeta = entity.getInheritance();
            
            if (inheritanceMeta == null) {
                inheritanceMeta = new InheritanceMetadata();
                entity.setInheritance(inheritanceMeta);
            }
            
            EntityMetadata superclass = ApplicationMetadata.INSTANCE
                    .getEntities().get(extendedClassName);
            
            if (superclass == null) {
                superclass = new EntityMetadata();
                superclass.setName(extendedClassName);
            }
            
            inheritanceMeta.setSuperclass(superclass);
        }
    }
}
