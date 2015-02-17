/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.ios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.exception.NotImplementedException;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.platform.manipulator.JavaFileManipulator;
import com.tactfactory.harmony.platform.manipulator.SourceFileManipulator;

import freemarker.template.Configuration;

/** Apple iOS Adapter of project structure. */
public class IosAdapter extends BaseAdapter {
    /** Error message for not implemented feature. */
    private static final String NOT_IMPLEMENTED_MESSAGE =
            "IOS adapter has not been implemented yet.";

    private IAdapterProject adapterProject = new IosProjectAdapter(this);
    
    /** String type. */
    private static final String STR = "NSString";

    /** Int type. */
    private static final String INT = "int";

    /** DateTime type. */
    private static final String DATETIME = "DateTime";
    /**
     * Constructor.
     */
    public IosAdapter()  {
        super();
        
        this.setProject("project");
        this.setPlatform("ios");
        this.setResource("res");
        this.setSource("src");
    }


    @Override
    public String getNameSpace(final ClassMetadata cm, final String type) {
        return String.format("%s.%s",
                cm.getSpace(),
                type);
    }

    @Override
    public String getNameSpaceEntity(final ClassMetadata cm,
            final String type) {
        return String.format("%s.%s",
                this.getNameSpace(cm, type),
                cm.getName().toLowerCase());
    }


    @Override
    public String getNativeType(final FieldMetadata field) {
        String ret = field.getHarmonyType();
        if (ret.equals(Column.Type.ENUM.getValue())) {
            ret = field.getEnumMeta().getTargetEnum();
        } else
            
        if (ret.equals(Column.Type.RELATION.getValue())) {
            if (field.getRelation().getType().endsWith("ToMany")) {
                ret = String.format("ArrayList<%s>",
                        field.getRelation().getEntityRef().getName());
            } else {
                ret = field.getRelation().getEntityRef().getName();
            }
        } else
            
        if (ret.equals(Column.Type.STRING.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.TEXT.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.INTEGER.getValue())) {
            if (field.isPrimitive()) {
                ret = INT;
            } else {
                ret = "Integer";
            }
        } else

        if (ret.equals(Column.Type.DATETIME.getValue())) {
            ret = DATETIME;
        } else

        if (ret.equals(Column.Type.DATE.getValue())) {
            ret = DATETIME;
        } else

        if (ret.equals(Column.Type.TIME.getValue())) {
            ret = DATETIME;
        } else

        if (ret.equals(Column.Type.LOGIN.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.PASSWORD.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.EMAIL.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.PHONE.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.CITY.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.ZIPCODE.getValue())) {
            if (field.isPrimitive()) {
                ret = INT;
            } else {
                ret = "Integer";
            }
        } else

        if (ret.equals(Column.Type.COUNTRY.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.BC_EAN.getValue())) {
            if (field.isPrimitive()) {
                ret = INT;
            } else {
                ret = "Integer";
            }
        } else
        
        if (ret.equals(Column.Type.CHAR.getValue())) {

            if (field.isPrimitive()) {
                ret = "char";
            } else {
                ret = "Character";
            }
        } else
            
        if (ret.equals(Column.Type.FLOAT.getValue())) {
            if (field.isPrimitive()) {
                ret = "float";
            } else {
                ret = "Float";
            }
        }
        
        if (ret.equals(Column.Type.DOUBLE.getValue())) {
            if (field.isPrimitive()) {
                ret = "double";
            } else {
                ret = "Double";
            }
        } else
            
        if (ret.equals(Column.Type.LONG.getValue())) {
            if (field.isPrimitive()) {
                ret = "long";
            } else {
                ret = "Long";
            }
        }
        
        if (ret.equals(Column.Type.BOOLEAN.getValue())) {
            if (field.isPrimitive()) {
                ret = "boolean";
            } else {
                ret = "Boolean";
            }
        }
        return ret;
    }


    @Override
    public void resizeImage() {
        throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
    }


    @Override
    public boolean filesEqual(String oldContent, String newContent,
            String fileName, boolean ignoreHeader) {
        return oldContent.equals(newContent);
    }

    @Override
    public SourceFileManipulator getFileManipulator(
            final File file,
            final Configuration config) {
        return new JavaFileManipulator(file, this, config);
    }

    @Override
    public List<String> getDirectoryForResources() {
        List<String> result = new ArrayList<String>();
        
        
        return result;
    }

    @Override
    public IAdapterProject getAdapterProject() {
        return this.adapterProject;
    }


    @Override
    public String getSourceEntityPath() {
        return String.format("%s%s/%s/",
                this.getSourcePath(),
                this.getApplicationMetadata().getProjectNameSpace(),
                "entity");
    }
}
