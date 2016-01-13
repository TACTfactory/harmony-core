package com.tactfactory.harmony.completor;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;

public abstract class ResourceCompletor {

    public static void completeEntities() {
//        for (EntityMetadata entityMeta
//                : ApplicationMetadata.INSTANCE.getEntities().values()) {
//            if (entityMeta.isResource()) {
//                final FieldMetadata path = new FieldMetadata(entityMeta);
//                path.setName("path");
//                path.setColumnName("path");
//                path.setHarmonyType(Column.Type.STRING.getValue());
//                path.setColumnDefinition("VARCHAR");
//                path.setHidden(true);
//                path.setNullable(true);
//                entityMeta.getFields().put(path.getName(), path);
//            }
//        }
    }

}
