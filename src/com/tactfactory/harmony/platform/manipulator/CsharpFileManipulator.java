/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.manipulator;

import java.io.File;
import java.util.Map;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapter;

import freemarker.template.Configuration;

public class CsharpFileManipulator extends SourceFileManipulator {

    public CsharpFileManipulator(File file, BaseAdapter adapter,
            Configuration config) {
        super(file, adapter, config);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean generateMethod(String templateName, Map<String, Object> model) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addImplement(ClassMetadata classMeta, String className) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addImport(ClassMetadata classMeta, String className,
            String classPackage) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean generateFieldAccessor(FieldMetadata f, String templateName, EntityMetadata model) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean generateField(String templateName, Map<String, Object> model) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean alreadyHasField(String fieldDeclaration) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean regenerateMethod(String templateName,
            String methodSignature, Map<String, Object> model) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected int indexOf(String content, int fromIndex, boolean allowComments) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean addField(IAdapter iAdapter, ClassMetadata classMeta, String className,
            FieldMetadata fieldMetadata) {
        // TODO Auto-generated method stub
        return false;
    }

}
