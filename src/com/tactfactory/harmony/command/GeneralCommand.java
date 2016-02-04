/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.command.base.Command;
import com.tactfactory.harmony.command.base.CommandBase;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * General Harmony commands.
 *
 */
@PluginImplementation
public class GeneralCommand extends CommandBase {
    /** List command. */
    public static final String LIST = "list";
    /** Help command. */
    public static final String HELP = "help";

    /**
     * Display Help Message.
     */
    public final void help() {
        ConsoleUtils.display("Usage:\n"
                + "\tconsole [--options] command [--parameters]\n"
                + "\nUsage example:\n"
                + "\tconsole --verbose project:init:android --nam =test "
                + "--namespac =com/tact/android/test --sdkdir =/root/android\n"
                + "\nPlease use 'console list' to display available commands !"
                );
    }

    /**
     * Display list of All commands.
     */
    public final void list() {
        ConsoleUtils.display("Available Commands:");

        ArrayList<Command> thirdPartyCommands = new ArrayList<Command>(
                Harmony.getInstance().getCommands());
        ArrayList<Command> coreCommands = new ArrayList<Command>();
        ArrayList<Command> commonCommands = new ArrayList<Command>();

        this.moveCommand(
                thirdPartyCommands, coreCommands, GeneralCommand.class);

        this.moveCommand(
                thirdPartyCommands, coreCommands, ProjectCommand.class);

        this.moveCommand(
                thirdPartyCommands, coreCommands, DependenciesCommand.class);

        this.moveCommand(
                thirdPartyCommands, coreCommands, OrmCommand.class);

        this.moveCommand(
                thirdPartyCommands, coreCommands, MenuCommand.class);

        this.moveCommand(
                thirdPartyCommands, coreCommands, FosCommand.class);

        this.moveCommand(
                thirdPartyCommands, coreCommands, FixtureCommand.class);

        this.moveCommand(
                thirdPartyCommands, commonCommands, ResourceCommand.class);

        this.moveCommand(
                thirdPartyCommands, commonCommands, BundleCommand.class);

        this.moveCommand(
                thirdPartyCommands, commonCommands, RouterCommand.class);

        Comparator<Command> thirdPartyComparator = new Comparator<Command>() {

            @Override
            public int compare(Command o1, Command o2) {
                return o1.getClass().getSimpleName().compareTo(
                        o2.getClass().getSimpleName());
            }
        };

        Collections.sort(thirdPartyCommands, thirdPartyComparator);

        this.displayList(coreCommands);
        this.displayList(thirdPartyCommands);
        this.displayList(commonCommands);
    }

    /**
     * Display the summary of a list of commands.
     * @param commands The list of commands
     */
    private final void displayList(List<Command> commands) {
        for (final Command command : commands) {
            command.summary();
        }
    }

    /**
     * Move a command from a list to another.
     *
     * @param from From list
     * @param to To list
     * @param commandName The name of the command
     */
    private final void moveCommand(
            final List<Command> from,
            final List<Command> to,
            final Class<? extends Command> commandName) {
        Command command = Harmony.getInstance().getCommand(commandName);
        if (command != null) {
            from.remove(command);
            to.add(command);
        }
    }

    @Override
    public final void summary() {
        LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
        commands.put(HELP, "Display this help message");
        commands.put(LIST, "List all commands");

        ConsoleUtils.displaySummary(
                "General",
                commands);
    }

    @Override
    public final void execute(final String action,
            final String[] args,
            final String option) {
        if (action.equals(LIST)) {
            this.list();
        } else

        if (action.equals(HELP)) {
            this.help();
        }
    }

    @Override
    public final boolean isAvailableCommand(final String command) {
        return     command.equals(LIST)
                || command.equals(HELP);
    }

}
