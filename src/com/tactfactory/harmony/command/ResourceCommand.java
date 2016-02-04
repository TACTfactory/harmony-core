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
import com.tactfactory.harmony.command.base.CommandBase;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Manipulate resource command.
 */
@PluginImplementation
public class ResourceCommand extends CommandBase {

    /** Bundle name. */
    public static final String BUNDLE = "resource";
    /** Subject. */
    public static final String SUBJECT = "generate";

    /** Image action. */
    public static final String ACTION_IMAGE = "image";
    /** Translate action. */
    public static final String ACTION_TRANSLATE = "translate";

    //commands
    /** Command : RESOURCE:GENERATE:IMAGE. */
    public static final String GENERATE_IMAGE     =
            BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_IMAGE;
    /** Command : RESOURCE:GENERATE:TRANSLATE. */
    public static final String GENERATE_TRANSLATE    =
            BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_TRANSLATE;

    @Override
    public final void summary() {
        LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
        commands.put(GENERATE_IMAGE, "Generate all resize of the HD images");
        commands.put(GENERATE_TRANSLATE, "Generate translate");

        ConsoleUtils.displaySummary(
                BUNDLE,
                commands);
    }

    @Override
    public final void execute(final String action,
            final String[] args,
            final String option) {
        ConsoleUtils.display("> Resource Generator");

        this.setCommandArgs(Console.parseCommandArgs(args));

        for(IAdapter adapter : this.getAdapters()) {
            try {
                if (action.equals(GENERATE_IMAGE)) {
                    adapter.resizeImage();
                }
            } catch (final Exception e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    @Override
    public final boolean isAvailableCommand(final String command) {
        return  command.equals(GENERATE_IMAGE)
                || command.equals(GENERATE_TRANSLATE);
    }
}
