/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import java.io.File;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.InheritanceMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IAddExtends;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

public class AddExtendsAndroid implements IAddExtends {

    private final EntityMetadata entity;
    private final String extendedClassName;

    public AddExtendsAndroid(
            EntityMetadata entity,
            String extendedClassName) {
        this.entity = entity;
        this.extendedClassName = extendedClassName;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        this.addExtends(generator);
    }

    /**
     * Extends classname in the class if it doesn't already.
     * @param entity The Metadata containing the infos on the java class
     * @param extendedClassName the name of the class to implement
     */
    private final void addExtends(BaseGenerator<? extends IAdapter> generator) {

        if (entity.getInheritance() == null
                        || entity.getInheritance().getSuperclass() == null) {

            final String filepath = String.format("%s%s",
                    generator.getAdapter().getSourceEntityPath(),
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

            // After treatment on entity, write it in the original file
            TactFileUtils.stringBufferToFile(fileString, entityFile);

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
