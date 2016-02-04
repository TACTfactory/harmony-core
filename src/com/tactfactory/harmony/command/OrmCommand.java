/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.util.LinkedHashMap;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.command.base.CommandBundleBase;
import com.tactfactory.harmony.generator.ActivityGenerator;
import com.tactfactory.harmony.generator.ApplicationGenerator;
import com.tactfactory.harmony.generator.EntityGenerator;
import com.tactfactory.harmony.generator.EnumGenerator;
import com.tactfactory.harmony.generator.ProjectGenerator;
import com.tactfactory.harmony.generator.ProviderGenerator;
import com.tactfactory.harmony.generator.SQLiteGenerator;
import com.tactfactory.harmony.generator.TestGenerator;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.TargetPlatform;
import com.tactfactory.harmony.platform.android.AndroidAdapter;
import com.tactfactory.harmony.platform.ios.IosAdapter;
import com.tactfactory.harmony.platform.winphone.WinphoneAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Project Custom Files Code Generator.
 *
 * use Entity files in name space folder
 * it can :
 * <ul>
 * <li>Generate entities</li>
 * <li>Generate form</li>
 * <li>Generate CRUD functions</li>
 * </ul>
 *
 */
@PluginImplementation
public class OrmCommand extends CommandBundleBase<BaseAdapter> {

    /** Bundle name. */
    public static final String BUNDLE = "orm";
    /** Subject. */
    public static final String SUBJECT = "generate";

    /** Action entity. */
    public static final String ACTION_ENTITY = "entity";
    /** Action entities. */
    public static final String ACTION_ENTITIES = "entities";
    /** Action form. */
    public static final String ACTION_FORM = "form";
    /** Action crud. */
    public static final String ACTION_CRUD = "crud";

    //commands
    //public static String GENERATE_FORM         =
    //        BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_FORM;
    //public static String GENERATE_ENTITY     =
    //        BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITY;

    /** Command : ORM:GENERATE:ENTITIES. */
    public static final String GENERATE_ENTITIES    =
            BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITIES;

    /** Command : ORM:GENERATE:CRUD. */
    public static final String GENERATE_CRUD         =
            BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_CRUD;

    /**
     * Generate java code files from parsed Entities.
     */
    protected final void generateEntities() {
        this.generateMetas();

        if (ApplicationMetadata.INSTANCE.getEntities() != null) {
            this.makeLayoutDatabase();
            this.makeLayoutTestDatabase();
        }

    }

    /**
     * Generate Create,Read, Upload, Delete code functions.
     */
    protected final void generateCrud() {
        this.generateMetas();
        if (ApplicationMetadata.INSTANCE.getEntities() != null) {
            this.makeLayoutUi(true);
        }
    }

    /**
     * Generate the Persistence part for the given classes.
     */
    protected final void makeLayoutDatabase() {
        for(IAdapter adapter : this.getAdapters()) {
            try {
                new EnumGenerator(adapter).generateAll();
                new EntityGenerator(adapter).generateAll();
                new ApplicationGenerator(adapter).generateApplication();
                new SQLiteGenerator(adapter).generateAll();
                new ProviderGenerator(adapter).generateProvider();

            } catch (final Exception e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    /**
     * Generate Test DB for Entities.
     */
    protected final void makeLayoutTestDatabase() {
        for(IAdapter adapter : this.getAdapters()) {
            try {
                new TestGenerator(adapter).generateAll();

            } catch (final Exception e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    /**
     * Generate the GUI part for the given classes.
     * @param generateHome True if you want the HomeActivity to be regenerated.
     */
    protected final void makeLayoutUi(final boolean generateHome) {
        for(IAdapter adapter : this.getAdapters()) {
            try {
                if (generateHome) {
                    new ProjectGenerator(adapter).generateStartView();
                }

                new ActivityGenerator(adapter).generateAll();

            } catch (final Exception e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    @Override
    public final void summary() {
        LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
//        commands.put(GENERATE_ENTITY, "Generate Entry");
        commands.put(GENERATE_ENTITIES, "Generate Entries");
//        commands.put(GENERATE_FORM, "Generate Form");
        commands.put(GENERATE_CRUD, "Generate CRUD");

        ConsoleUtils.displaySummary(
                BUNDLE,
                commands);
    }

    @Override
    public final void execute(final String action,
            final String[] args,
            final String option) {
        ConsoleUtils.display("> ORM Generator");

        this.setCommandArgs(Console.parseCommandArgs(args));

        try {
            /*if (action.equals(GENERATE_ENTITY)) {
                this.generateEntity();
            } else*/

            if (action.equals(GENERATE_ENTITIES)) {
                this.generateEntities();
            } else

            /*if (action.equals(GENERATE_FORM)) {
                this.generateForm();
            } else*/

            if (action.equals(GENERATE_CRUD)) {
                this.generateCrud();
            }
        } catch (final Exception e) {
            ConsoleUtils.displayError(e);
        }
    }

    @Override
    public final boolean isAvailableCommand(final String command) {
        return  //|| command.equals(GENERATE_ENTITY)
                command.equals(GENERATE_ENTITIES)
                //|| command.equals(GENERATE_FORM)
                || command.equals(GENERATE_CRUD);
    }

    @Override
    public void initBundleAdapter() {
        this.adapterMapping.put(
                TargetPlatform.ANDROID,
                AndroidAdapter.class);
        this.adapterMapping.put(
                TargetPlatform.WINPHONE,
                WinphoneAdapter.class);
        this.adapterMapping.put(
                TargetPlatform.IPHONE,
                IosAdapter.class);
    }
}
