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

import com.tactfactory.harmony.command.base.CommandBase;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Commands for router.
 *
 */
@PluginImplementation
public class RouterCommand extends CommandBase {

    /** Bundle name. */
    public static final String BUNDLE = "router";
    /** Generation subject. */
    public static final String SUBJECT_GENERATE = "generate";
    /** Debugging subject. */
    public static final String SUBJECT_DEBUG = "debug";
    /** Manifest action. */
    public static final String ACTION_MANIFEST = "manifest";

    /** Command : ROUTER:DEBUG. */
    public static final String ROUTER_DEBUG =
            BUNDLE + SEPARATOR + SUBJECT_DEBUG;
    /** Command : ROUTER:GENERATE:MANIFEST. */
    public static final String ROUTER_GENERATE_MANIFEST =
            BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_MANIFEST;

    /**
     * Debug routes.
     */
    protected void routerDebug() {
    }

    /**
     * Generate manifest.
     */
    public void generateManifest() {
    }

    @Override
    public final void summary() {
        LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
        commands.put(ROUTER_DEBUG,     "List all URI");
        commands.put(ROUTER_GENERATE_MANIFEST,     "Generate Manifest");

        ConsoleUtils.displaySummary(
                BUNDLE,
                commands);
    }

    @Override
    public final void execute(final String action,
            final String[] args,
            final String option) {
        // TODO Auto-generated method stub

    }

    @Override
    public final boolean isAvailableCommand(final String command) {
        // TODO Auto-generated method stub
        return false;
    }

}
