/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import java.util.List;

import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Generate the CRUD activities.
 */
public class ActivityGenerator extends BaseGenerator<IAdapter> {

    /** Are the entities writable ? */
    private boolean isWritable;

    /** Has the project a date ? */
    private boolean isDate;

    /** Has the project a time ? */
    private boolean isTime;

    /**
     * Constructor.
     * @param adapter The adapter to use
     * @throws Exception if adapter is null
     */
    public ActivityGenerator(final IAdapter adapter) throws Exception {
        this(adapter, true);
    }

    /**
     * Constructor.
     * @param adapter The adapter to use.
     * @param writable Are the entities writable ? (default to true)
     * @throws Exception if adapter is null
     */
    public ActivityGenerator(final IAdapter adapter,
            final Boolean writable) throws Exception {
        super(adapter);

        this.isWritable = writable;
        this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
        this.intializeDateAndTime();
    }

    /**
     * Generate all activities for every entity.
     */
    public final void generateAll() {
        ConsoleUtils.display(">> Generate CRUD view...");

        Iterable<EntityMetadata> entities =
                this.getAppMetas().getEntities().values();

        for (final EntityMetadata cm : entities) {
            if (!cm.isInternal() && cm.hasFields()) {
                this.getDatamodel().put(
                        TagConstant.CURRENT_ENTITY,
                        cm.getName());
                this.generateAllAction(cm);
            }
        }

        List<IUpdater> updaters = this.getAdapter().getAdapterProject()
                .getViews(this.isDate, this.isTime);
        this.processUpdater(updaters);

        updaters = this.getAdapter().getAdapterProject().getActivityLibraries();
        this.processUpdater(updaters);

        MenuGenerator menuGenerator = new MenuGenerator(this.getAdapter());
        menuGenerator.generateMenu("CrudCreate");
        menuGenerator.generateMenu("CrudEdit");
        menuGenerator.generateMenu("CrudDelete");
        menuGenerator.generateMenu("Save");
        menuGenerator.updateMenu();

        TranslationMetadata.addDefaultTranslation(
                "menu_item_create",
                "Add",
                Group.COMMON);

        TranslationMetadata.addDefaultTranslation(
                "menu_item_edit",
                "Edit",
                Group.COMMON);

        TranslationMetadata.addDefaultTranslation(
                "menu_item_delete",
                "Delete",
                Group.COMMON);

        TranslationMetadata.addDefaultTranslation(
                "menu_item_save",
                "Save",
                Group.COMMON);

        TranslationMetadata.addDefaultTranslation(
                "dialog_delete_title",
                "Delete",
                Group.COMMON);

        TranslationMetadata.addDefaultTranslation(
                "dialog_delete_message",
                "Are you sure you want to delete this item ?",
                Group.COMMON);

        try {
            new TranslationGenerator(this.getAdapter()).generateStringsXml();
        } catch (Exception e) {
            ConsoleUtils.displayError(e);
        }
    }

    /**
     * Generate all actions (List, Show, Edit, Create).
     * @param entityName The entity for which to generate the crud.
     */
    public final void generateAllAction(final EntityMetadata entity) {
        final String entityName = entity.getName();
        ConsoleUtils.display(">>> Generate CRUD view for " +  entityName);

        List<IUpdater> updaters;

        EntityMetadata entityMetadata =
              this.getAppMetas().getEntities().get(entityName);

        if (this.isWritable) {
            ConsoleUtils.display("   with write actions");

            if (entity.isCreateAction()) {
                updaters = this.getAdapter().getAdapterProject()
                        .getCreateView(entityMetadata);
                this.processUpdater(updaters);
            }

            if (entity.isEditAction()) {
                updaters = this.getAdapter().getAdapterProject()
                        .getEditView(entityMetadata);
                this.processUpdater(updaters);
            }

            TranslationMetadata.addDefaultTranslation(
                    entityMetadata.getName() + "_error_edit",
                    entityMetadata.getName() + " edition error…",
                    Group.MODEL);

            TranslationMetadata.addDefaultTranslation(
                    "common_create",
                    "Create",
                    Group.COMMON);

            TranslationMetadata.addDefaultTranslation(
                    "common_edit",
                    "Edit",
                    Group.COMMON);

            TranslationMetadata.addDefaultTranslation(
                    "common_delete",
                    "Del",
                    Group.COMMON);

            TranslationMetadata.addDefaultTranslation(
                    entityMetadata.getName() + "_progress_save_title",
                    entityMetadata.getName() + " save progress",
                    Group.MODEL);

            TranslationMetadata.addDefaultTranslation(
                    entityMetadata.getName() + "_progress_save_message",
                    entityMetadata.getName() + " is saving to database…",
                    Group.MODEL);

            if (!entityMetadata.getRelations().isEmpty()
                    || entityMetadata.getInheritance() != null) {
                TranslationMetadata.addDefaultTranslation(
                        entityMetadata.getName() + "_progress_load_relations_title",
                        entityMetadata.getName() + " related entities loading",
                        Group.MODEL);

                TranslationMetadata.addDefaultTranslation(
                        entityMetadata.getName()
                                + "_progress_load_relations_message",
                        entityMetadata.getName()
                                + " related entities are loading...",
                        Group.MODEL);
            }

            for (final FieldMetadata fm : entityMetadata.getFields().values()) {
                if (!fm.isInternal() && !fm.isHidden()
                        && fm.getRelation() != null) {

                    TranslationMetadata.addDefaultTranslation(
                            String.format("%s_%s_dialog_title",
                                    entityMetadata.getName(),
                                    fm.getName()),
                            String.format("Select %s", fm.getName()),
                            Group.MODEL);
                }
            }
        }

        if (entity.isShowAction()) {
            updaters = this.getAdapter().getAdapterProject()
                    .getShowView(entityMetadata);
            this.processUpdater(updaters);
        }

        if (entity.isListAction()) {
            updaters = this.getAdapter().getAdapterProject()
                    .getListView(entityMetadata);
            this.processUpdater(updaters);
        }

        updaters = this.getAdapter().getAdapterProject()
                .getCommonView(entityMetadata, this.isWritable);
        this.processUpdater(updaters);

        TranslationMetadata.addDefaultTranslation(
                entityMetadata.getName() + "_error_create",
                entityMetadata.getName() + " creation error…",
                Group.MODEL);

        TranslationMetadata.addDefaultTranslation(
                entityMetadata.getName() + "_progress_load_title",
                entityMetadata.getName() + " Loading progress",
                Group.MODEL);

        TranslationMetadata.addDefaultTranslation(
                entityMetadata.getName() + "_progress_load_message",
                entityMetadata.getName() + " is loading…",
                Group.MODEL);

        TranslationMetadata.addDefaultTranslation(
                entityMetadata.getName() + "_not_found",
                "No " + entityMetadata.getName() + " found to display…",
                Group.MODEL);

        TranslationMetadata.addDefaultTranslation(
                entityMetadata.getName() + "_empty_list",
                entityMetadata.getName() + " list is empty !",
                Group.MODEL);
    }

    /**
     * Check if project has a date and or a time
     *     and initialize the isDate and isTime class variable.
     */
    private void intializeDateAndTime() {
        Iterable<EntityMetadata> entities =
                this.getAppMetas().getEntities().values();

        // Make entities
        for (final EntityMetadata meta : entities) {
            if (meta.hasFields() && !meta.isInternal()) {
                // copy Widget
                if (this.isDate && this.isTime) {
                    break;
                }

                for (final FieldMetadata field : meta.getFields().values()) {
                    final String type = field.getHarmonyType();
                    if (!this.isDate &&
                            (type.equals(Type.DATE.getValue())
                                    || type.equals(Type.DATETIME.getValue()))) {
                        this.isDate = true;
                    }

                    if (!this.isTime &&
                            (type.equals(Type.TIME.getValue())
                                    || type.equals(Type.DATETIME.getValue()))) {
                        this.isTime = true;
                    }
                }
            }
        }
    }
}
