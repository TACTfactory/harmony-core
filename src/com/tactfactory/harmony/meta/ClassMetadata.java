/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.harmony.generator.TagConstant;
import com.tactfactory.harmony.platform.IAdapter;

/** Entity class metadata. */
public class ClassMetadata extends BaseMetadata {
    /** InnerClasses array. */
    private Map<String, ClassMetadata> innerClasses = new LinkedHashMap<String, ClassMetadata>();

    /** Outerclass. */
    private String outerClass;

    /** List of fields of entity class. */
    private Map<String, FieldMetadata> fields = new LinkedHashMap<String, FieldMetadata>();

    /** Implemented class list of the entity class. */
    private List<String> implementTypes = new ArrayList<String>();

    /** Namespace of entity class. */
    private String space = "";

    /** Implemented class list of the entity class. */
    private List<MethodMetadata> methods = new ArrayList<MethodMetadata>();

    /** Imports of the class. */
    private List<String> imports = new ArrayList<String>();

    /** Inheritance mode if any. */
    private InheritanceMetadata inheritanceMeta;

    /** Has this class been parsed by harmony ? */
    private boolean hasBeenParsed = false;


    /**
     * Transform the class to a map given an adapter.
     * @param adapter The adapter used to customize the fields
     * @return the map
     */
    @Override
    public Map<String, Object> toMap(final IAdapter adapter) {
        final Map<String, Object> model = new HashMap<String, Object>();

        model.put(TagConstant.SPACE,                this.space);
        model.put(TagConstant.NAME,                 this.getName());
        model.put(TagConstant.CONTROLLER_NAMESPACE, adapter.getNameSpaceEntity(this, adapter.getController()));
        model.put(TagConstant.DATA_NAMESPACE,       adapter.getNameSpace(this, adapter.getData()));
        model.put(TagConstant.TEST_NAMESPACE,       adapter.getNameSpace(this, adapter.getTest()));
        model.put(TagConstant.FIELDS,               this.toFieldArray(this.fields.values(), adapter));

        final Map<String, Object> optionsModel = new HashMap<String, Object>();

        for (final Metadata option : this.getOptions().values()) {
            optionsModel.put(option.getName(), option.toMap(adapter));
        }

        model.put(TagConstant.OPTIONS, optionsModel);

        if (this.inheritanceMeta != null) {
            model.put(TagConstant.INHERITANCE, this.inheritanceMeta.toMap(adapter));
        }

        model.put(TagConstant.OUTER_CLASS, this.outerClass);
        model.put(TagConstant.HAS_BEEN_PARSED, this.hasBeenParsed);

        return model;
    }

    /**
     * @return the fields
     */
    public final Map<String, FieldMetadata> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public final void setFields(final Map<String, FieldMetadata> fields) {
        this.fields = fields;
    }

    /**
     * @return the methods
     */
    public final List<MethodMetadata> getMethods() {
        return methods;
    }

    /**
     * @param methods the methods to set
     */
    public final void setMethods(final List<MethodMetadata> methods) {
        this.methods = methods;
    }

    /**
     * @return the imports
     */
    public final List<String> getImports() {
        return imports;
    }

    /**
     * @return the space
     */
    public final String getSpace() {
        return space;
    }

    /**
     * @param space the space to set
     */
    public final void setSpace(final String space) {
        this.space = space;
    }

    /**
     * @param imports the imports to set
     */
    public final void setImports(final List<String> imports) {
        this.imports = imports;
    }

    /**
     * @return the implementTypes
     */
    public final List<String> getImplementTypes() {
        return implementTypes;
    }

    /**
     * @param implementTypes the implementTypes to set
     */
    public final void setImplementTypes(final List<String> implementTypes) {
        this.implementTypes = implementTypes;
    }

    /**
     * Build a map from a collection of fields.
     * @param c The collection of fields
     * @param adapter The adapter to use.
     * @return The fields map.
     */
    private Map<String, Map<String, Object>> toFieldArray(
            final Collection<FieldMetadata> c,
            final IAdapter adapter) {
        final Map<String, Map<String, Object>> result = new LinkedHashMap<String, Map<String, Object>>();
        Map<String, Object> subField = null;

        for (final FieldMetadata field : c) {
            //field.customize(adapter);

            subField = field.toMap(adapter);

            // Add field translate
            if (!field.isInternal() && !field.isHidden()) {
                field.makeString("label");
            }

            result.put(field.getName(), subField);
        }

        return result;
    }

    /**
     * @return the innerClasses
     */
    public final Map<String, ClassMetadata> getInnerClasses() {
        return innerClasses;
    }

    /**
     * @param innerClasses the innerClasses to set
     */
    public final void setInnerClasses(final Map<String, ClassMetadata> innerClasses) {
        this.innerClasses = innerClasses;
    }

    public final void setInheritance(final InheritanceMetadata inheritanceMeta) {
        this.inheritanceMeta = inheritanceMeta;
    }

    public final InheritanceMetadata getInheritance() {
        return this.inheritanceMeta;
    }

    /**
     * @return the outerClass
     */
    public final String getOuterClass() {
        return outerClass;
    }

    /**
     * @param outerClass the outerClass to set
     */
    public final void setOuterClass(String outerClass) {
        this.outerClass = outerClass;
    }

    /**
     * Removes given field from this class.
     * @param field The field to remove
     */
    public void removeField(FieldMetadata field) {
        field.setOwner(null);
        this.fields.remove(field.getName());
    }

    /**
     * Sets this class parsed.
     * @param parsed True of parsed
     */
    public void setParsed(boolean parsed) {
        this.hasBeenParsed = parsed;
    }

    /**
     * Has this class been parsed ?
     * @return true if parsed
     */
    public boolean hasBeenParsed() {
        return this.hasBeenParsed;
    }
}
