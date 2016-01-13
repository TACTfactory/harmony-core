/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command.base;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.parser.BaseParser;
import com.tactfactory.harmony.parser.ClassCompletor;
import com.tactfactory.harmony.parser.HeaderParser;
import com.tactfactory.harmony.parser.java.JavaModelParser;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Common Command structure.
 */
public abstract class CommandBase implements Command {

    /** Command separator. */
    protected static final String SEPARATOR = ":";

    /** Registered parsers for the global parser. */
    private static HashMap<String, BaseParser> registeredParsers
            = new HashMap<String, BaseParser>();

    /** Adapter to apply generator */
    private ArrayList<IAdapter> adapters = new ArrayList<IAdapter>();

    /** Command arguments. */
    private HashMap<String, String> commandArgs;

    /** Parser. */
    private JavaModelParser javaModelParser;

    /**
     * Gets the Metadatas of all the entities actually in the package entity.
     * You can register your own bundle parsers
     * with the method this.javaModelParser.registerParser()
     */
    public void generateMetas() {
        ApplicationMetadata.INSTANCE.getClasses().clear();
        ApplicationMetadata.INSTANCE.getEntities().clear();
        ApplicationMetadata.INSTANCE.getEnums().clear();

        HeaderParser.parseHeaderFile();

        ConsoleUtils.display(">> Analyse Models...");
        this.javaModelParser = new JavaModelParser();

        for (final BaseParser parser : registeredParsers.values()) {
            this.javaModelParser.registerParser(parser);
        }

        // Parse models and load entities into CompilationUnits
        try {
            this.javaModelParser.loadEntities();
        } catch (final Exception e) {
            ConsoleUtils.displayError(e);
        }

        // Convert CompilationUnits entities to ClassMetaData
        if (this.javaModelParser.getEntities().size() > 0) {
            for (final CompilationUnit mclass
                    : this.javaModelParser.getEntities()) {
                this.javaModelParser.parse(
                        mclass,
                        ApplicationMetadata.INSTANCE);
            }

            // TODO : Refactor ClassCompletor
            new ClassCompletor(
                    ApplicationMetadata.INSTANCE.getEntities(),
                    ApplicationMetadata.INSTANCE.getEnums()).execute();

            for (final BaseParser parser : registeredParsers.values()) {
                parser.callFinalCompletor();
            }

            this.validateMetadata();
        } else {
            ConsoleUtils.displayWarning("No entities found in entity package!");
        }
    }

    @Override
    public final synchronized void registerParser(final BaseParser parser) {
        registeredParsers.put(parser.getClass().getName(),parser);
    }

    @Override
    public void registerAdapters(final ArrayList<IAdapter> adapters) {
        this.adapters = adapters;
    }

    public ArrayList<IAdapter> getAdapters() {
        return this.adapters;
    }

    /**
     * Set the command arguments.
     * @param args The arguments.
     */
    protected final void setCommandArgs(final HashMap<String, String> args) {
        this.commandArgs = args;
    }

    /**
     * Get the command arguments.
     * @return The arguments.
     */
    protected final HashMap<String, String> getCommandArgs() {
        return this.commandArgs;
    }

    /**
     * Validate all metadata tree.
     */
    private final void validateMetadata() {
        //TODO Check all validity of metadata (Classes, enums, etc...).

        List<String> entityToRemove = new ArrayList<>();
        List<FieldMetadata> relationToRemove = new ArrayList<>();

        for (EntityMetadata entityMetadata : ApplicationMetadata.INSTANCE.getEntities().values()) {
            for (FieldMetadata fieldMetadata : entityMetadata.getRelations().values()) {
                if (fieldMetadata.getColumnDefinition().equalsIgnoreCase("BLOB")
                        || (fieldMetadata.getRelation().getType().equals("OneToMany")
                                && fieldMetadata.getRelation().getMappedBy() != null
                                && !Strings.isNullOrEmpty(fieldMetadata.getRelation().getMappedBy().getName())
                                && !fieldMetadata.getRelation().getEntityRef().getFields().containsKey(
                                        fieldMetadata.getRelation().getMappedBy().getName()))) {
                    ConsoleUtils.displayWarning(String.format("Field %s of entity %s isn't valid."
                            + " Check your relation annotation.",
                            fieldMetadata.getName(), entityMetadata.getName()));
                    ConsoleUtils.displayWarning(String.format("Field %s.%s will be ignored.",
                            entityMetadata.getName(), fieldMetadata.getName()));

                    fieldMetadata.setHarmonyType("STRING");
                    entityToRemove.add(String.format("%sto%s",
                            entityMetadata.getName(),
                            fieldMetadata.getRelation().getEntityRef().getName()));
                    fieldMetadata.setRelation(null);

                    relationToRemove.add(fieldMetadata);
                }
            }

            for (FieldMetadata fieldMetadata : relationToRemove) {
                entityMetadata.getRelations().remove(fieldMetadata.getName());
                entityMetadata.removeField(fieldMetadata);
            }

            relationToRemove.clear();
        }

        for (String entityKey : entityToRemove) {
            ApplicationMetadata.INSTANCE.getEntities().remove(entityKey);
        }

        // Validate the entities.
        for (EntityMetadata entityMetadata : ApplicationMetadata.INSTANCE.getEntities().values()) {
            for (FieldMetadata fieldMetadata : entityMetadata.getFields().values()) {
                if (fieldMetadata.getColumnDefinition().equalsIgnoreCase("BLOB")) {
                    ConsoleUtils.displayWarning(String.format("Field %s of entity %s isn't valid.",
                            fieldMetadata.getName(), entityMetadata.getName()));
                    ConsoleUtils.displayWarning(String.format("Field %s.%s will be considered as a String.",
                            entityMetadata.getName(), fieldMetadata.getName()));

                    fieldMetadata.setHarmonyType("STRING");
                }
            }
        }
    }
}
