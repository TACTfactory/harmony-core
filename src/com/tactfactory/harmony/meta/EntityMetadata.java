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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.tactfactory.harmony.generator.TagConstant;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.platform.IAdapter;

/** Entity class metadata. */
public final class EntityMetadata extends ClassMetadata {

    /** Used for join tables (ManyToMany relations). */
    private boolean internal = false;

    /** Entity SQL table name. */
    private String tableName;

    /** List of ids of entity class. */
    private Map<String, FieldMetadata> ids = new LinkedHashMap<String, FieldMetadata>();

    /** List of relations of entity class. */
    private Map<String, FieldMetadata> relations = new LinkedHashMap<String, FieldMetadata>();

    /** List of indexes of entity. */
    private Map<String, ArrayList<String>> indexes = new LinkedHashMap<String, ArrayList<String>>();

    /** List of orderBys of entity. */
    private Map<String, String> orders = new LinkedHashMap<String, String>();

    /** Should we be able to create this entity ? */
    private boolean createAction = true;
    /** Should we be able to edit this entity ? */
    private boolean editAction = true;
    /** Should we be able to show this entity ? */
    private boolean showAction = true;
    /** Should we be able to list this entity ? */
    private boolean listAction = true;
    /** Should we be able to delete this entity ? */
    private boolean deleteAction = true;
    /** Should be resource ? */
    private boolean resource = false;

    /**
     * Add Component String of field.
     * @param componentName Component name
     */
    public final void makeString(final String componentName) {
        final String key = this.getName().toLowerCase()
                + "_" + componentName.toLowerCase(Locale.ENGLISH);
        TranslationMetadata.addDefaultTranslation(
                key, this.getName(), Group.MODEL);
    }

    /**
     * Transform the class to a map given an adapter.
     * @param adapter The adapter used to customize the fields
     * @return the map
     */
    @Override
    public final Map<String, Object> toMap(final IAdapter adapter) {
        final Map<String, Object> model = super.toMap(adapter);

        model.put(TagConstant.TABLE_NAME, this.tableName);
        model.put(TagConstant.IDS, this.toFieldArray(this.getIds().values(), adapter));
        model.put(TagConstant.RELATIONS, this.toFieldArray(this.relations.values(), adapter));
        model.put(TagConstant.INTERNAL,    this.internal);
        model.put(TagConstant.SHOW_ACTION, this.showAction);
        model.put(TagConstant.LIST_ACTION, this.listAction);
        model.put(TagConstant.CREATE_ACTION, this.createAction);
        model.put(TagConstant.EDIT_ACTION, this.editAction);
        model.put(TagConstant.DELETE_ACTION, this.deleteAction);
        model.put(TagConstant.INDEXES, this.indexes);
        model.put(TagConstant.ORDERS, this.orders);
        model.put(TagConstant.RESOURCE, this.resource);

        return model;
    }

    /**
     * @return the internal
     */
    public final boolean isInternal() {
        return internal;
    }

    /**
     * @param internal the internal to set
     */
    public final void setInternal(final boolean internal) {
        this.internal = internal;
    }

    /**
     * @return the ids
     */
    public final Map<String, FieldMetadata> getIds() {
        // TODO : check if hack is valid ?
        Map<String, FieldMetadata> result;

        if (this.ids.isEmpty() && this.getInheritance() != null && this.getInheritance().getSuperclass() != null) {
            result = this.getInheritance().getSuperclass().getIds();
        } else {
            result = ids;
        }

        return result;
    }

    /**
     * Add an id to the entity.
     */
    public final void addId(FieldMetadata id) {
        this.ids.put(id.getName(), id);
    }

    /**
     * @param ids the ids to set
     */
    public final void setIds(final Map<String, FieldMetadata> ids) {
        this.ids = ids;
    }


    /**
     * @return the relations
     */
    public final Map<String, FieldMetadata> getRelations() {
        return relations;
    }

    /**
     * @param relations the relations to set
     */
    public final void setRelations(final Map<String, FieldMetadata> relations) {
        this.relations = relations;
    }

    /**
     * Build a map from a collection of fields.
     * @param c The collection of fields
     * @param adapter The adapter to use.
     * @return The fields map.
     */
    private List<Map<String, Object>> toFieldArray(
            final Collection<FieldMetadata> c,
            final IAdapter adapter) {
        final List<Map<String, Object>> result =
                new ArrayList<Map<String, Object>>();
        Map<String, Object> subField = null;

        for (final FieldMetadata field : c) {
            //field.customize(adapter);

            subField = field.toMap(adapter);

            // Add field translate
            field.makeString("label");

            result.add(subField);
        }

        return result;
    }

    /**
     * Add an index constraint to this entity.
     * @param indexName The index name
     * @param columns The columns of this index
     */
    public void addIndex(String indexName, ArrayList<String> columns) {
        this.indexes.put(indexName, columns);
    }

    /**
     * Add an order constraint to this entity.
     * @param columnName The column name
     * @param order The order (ASC || DESC)
     */
    public void addOrder(String columnName, String order) {
        this.orders.put(columnName, order);
    }

    /**
     * Tells if this entitymetadata has fields. (either by itself or by its
     * superclasses)
     * @return True if has fields
     */
    public final boolean hasFields() {
        boolean result = false;

        if (!this.getFields().isEmpty()) {
            result = true;
        } else {
            InheritanceMetadata inheritance = this.getInheritance();

            if (inheritance != null && inheritance.getSuperclass() != null) {
                result = inheritance.getSuperclass().hasFields();
            }
        }

        return result;
    }

    @Override
    public final void removeField(FieldMetadata field) {
        super.removeField(field);
        this.relations.remove(field.getName());
        this.ids.remove(field.getName());
    }

    /**
     * @return the createAction
     */
    public final boolean isCreateAction() {
        return this.createAction;
    }

    /**
     * @return the editAction
     */
    public final boolean isEditAction() {
        return this.editAction;
    }

    /**
     * @return the showAction
     */
    public final boolean isShowAction() {
        return this.showAction;
    }

    /**
     * @return the deleteAction
     */
    public final boolean isDeleteAction() {
        return this.deleteAction;
    }

    /**
     * @return the listAction
     */
    public final boolean isListAction() {
        return listAction;
    }

    /**
     * @param createAction the createAction to set
     */
    public final void setCreateAction(boolean createAction) {
        this.createAction = createAction;
    }

    /**
     * @param createAction the createAction to set
     */
    public final void setDeleteAction(boolean deleteAction) {
        this.deleteAction = deleteAction;
    }

    /**
     * @param editAction the editAction to set
     */
    public final void setEditAction(boolean editAction) {
        this.editAction = editAction;
    }

    /**
     * @param showAction the showAction to set
     */
    public final void setShowAction(boolean showAction) {
        this.showAction = showAction;
    }

    /**
     * @param listAction the listAction to set
     */
    public final void setListAction(boolean listAction) {
        this.listAction = listAction;
    }

    /**
     * @return Get the entity SQL name
     */
    public final String getTableName() {
        return this.tableName;
    }

    /**
     * @param name The entity SQL table name
     */
    public final void setTableName(String name) {
        this.tableName = name;
    }

       /**
     * @return the resource
     */
    public final boolean isResource() {
        return this.resource;
    }

    /**
     * @param resource the resource to set
     */
    public final void setResource(boolean resource) {
        this.resource = resource;
    }
}
