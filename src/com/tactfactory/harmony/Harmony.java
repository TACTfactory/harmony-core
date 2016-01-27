/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.JSPFProperties;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import com.google.common.base.Strings;
import com.tactfactory.harmony.command.GeneralCommand;
import com.tactfactory.harmony.command.base.Command;
import com.tactfactory.harmony.exception.HarmonyException;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Harmony main class.<br/>
 * <br/>
 * Usage :
 * <pre>
 * Harmony harmony = Harmony.getInstance();
 * harmony.findAndExecute(action, action_arguments, harmony_option);
 * </pre>
 */
public final class Harmony {

    /** Harmony version. */
    public static final String VERSION = getVersion();

    /** Singleton of console. */
    private volatile static Harmony instance;

    /**
     * Get Harmony instance (Singleton).
     * @return The harmony instance
     */
    public synchronized static Harmony getInstance() {
        if (Harmony.instance == null) {
            try {
                Harmony.instance = new Harmony();
                Harmony.instance.initialize();
            } catch (Exception e) {
                ConsoleUtils.displayError(e);
            }
        }

        return Harmony.instance;
    }

    /** HarmonyContext of execution. */
    private final HarmonyContext harmonyContext = new HarmonyContext();
    private final ProjectContext projectContext = new ProjectContext(this);

    /** Bootstrap. */
    private final Map<Class<?>, Command> bootstrap = new ConcurrentHashMap<Class<?>, Command>();

    /** Template folders. */
    private final Map<String, File> templateFolders = new HashMap<String, File>();

    /** Command classes associated to bundle folder. */
    private final Map<Class<?>, String> commandBundleFolders = new HashMap<Class<?>, String>();

    /** Constructor.
     * @throws Exception PluginManager failure
     */
    private Harmony() throws Exception {
        this.loadPluginsAndTemplate(new File(this.harmonyContext.getBundlesPath()));

        Locale.setDefault(Locale.US);
    }

    /**
     * @param pluginBaseDirectory The plugin base directory
     */
    private void loadPluginsAndTemplate(final File pluginBaseDirectory) {
        // Cache
        final JSPFProperties props = new JSPFProperties();
        /*props.setProperty(PluginManager.class, "cache.enabled", "true");

        // Optional
        props.setProperty(PluginManager.class, "cache.mode",    "weak");
        props.setProperty(PluginManager.class, "cache.file",    "jspf.cache");*/

        // Filters
        final IOFileFilter includeFilter =
                FileFilterUtils.suffixFileFilter(".jar");

        final IOFileFilter excludeFilter = FileFilterUtils.and(
                FileFilterUtils.notFileFilter(
                        FileFilterUtils.nameFileFilter("lib")),
                FileFilterUtils.notFileFilter(
                        FileFilterUtils.nameFileFilter("libs")));

        // Check list of Bundles .jar
        final Collection<File> plugins = TactFileUtils.listFiles(
                pluginBaseDirectory,
                includeFilter,
                excludeFilter);

        // Add Bundles to Plugin Manager &  foldertemplate
        for (File plugin : plugins) {
            this.loadPlugin(plugin, props);
            this.loadTemplates(plugin);
        }
    }

    private void loadPlugin(File plugin, JSPFProperties props) {
        PluginManager pluginManager = null;

        // TODO : clean plugin manager mechanic for folders
        pluginManager = PluginManagerFactory.createPluginManager(props);

        // Load bundles
        ConsoleUtils.displayDebug("Load plugins : " + plugin.getName());
        pluginManager.addPluginsFrom(plugin.toURI());

        // Template bundles
        final File bundleFolderFile =
                plugin.getParentFile().getAbsoluteFile();

        // Process extensions commands
        final PluginManagerUtil pmu = new PluginManagerUtil(pluginManager);
        final Collection<Command> commands = pmu.getPlugins(Command.class);

        // Bootstrap all commands
        for (final Command command : commands) {
            this.bootstrap.put(command.getClass(), command);
            this.commandBundleFolders.put(command.getClass(),
                    bundleFolderFile.getAbsolutePath());
        }

        pluginManager.shutdown();
    }

    private void loadTemplates(File plugin) {
        // Template bundles
        File templateFolderFile = plugin;

        if (templateFolderFile.isFile()) {
            templateFolderFile = templateFolderFile.getParentFile();
        }

        this.templateFolders.put(
                plugin.getName(),
                templateFolderFile.getAbsoluteFile());

        ConsoleUtils.displayDebug(
                "Load templates from : " + templateFolderFile);
    }

    /**
     * Initialize Harmony.
     * @throws Exception if current working path is unavailable.
     */
    public void initialize() throws Exception {
        ConsoleUtils.display(
                "Current Working Path: ",
                new File(".").getCanonicalPath());

        this.projectContext.detectProject();
        this.projectContext.detectPlatforms();

        // Debug Log
        ConsoleUtils.display(
                "Current Project : ",
                ApplicationMetadata.INSTANCE.getName());

        ConsoleUtils.display(
                "Current NameSpace : ",
                ApplicationMetadata.INSTANCE.getProjectNameSpace());

        ConsoleUtils.display(
                "Current Android SDK Path : ",
                ApplicationMetadata.getAndroidSdkPath());

        ConsoleUtils.display(
                "Current Android SDK Revision : ",
                HarmonyContext.getAndroidSdkVersion());
        ConsoleUtils.display("");
    }

    /**
     * Check and launch command in proper command class.
     *
     * @param action Command to execute
     * @param args Commands arguments
     * @param option Console option (ANSI, Debug, ...)
     */
    public void findAndExecute(final String action,
            final String[] args,
            final String option) {
        boolean isfindAction = false;

        // Select Action and launch
        for (final Command baseCommand : this.bootstrap.values()) {
            if (baseCommand.isAvailableCommand(action)) {
                String bundleFolder = this.commandBundleFolders.get(baseCommand.getClass());
                harmonyContext.setCurrentBundleFolder(bundleFolder + "/");

                baseCommand.registerAdapters(projectContext.getAdapters());
                baseCommand.execute(action, args, option);

                isfindAction = true;
            }
        }

        // No found action
        if (!isfindAction) {
            ConsoleUtils.displayError("Command not found...");

            this.getCommand(GeneralCommand.class).execute(
                    GeneralCommand.LIST,
                    null,
                    null);
        }
    }

    /**
     * Select the proper command class.
     *
     * @param commandName Class command name
     * @return CommandBase object
     */
    public Command getCommand(final Class<?> commandName) {
        return this.bootstrap.get(commandName);
    }

    /**
     * Get all command class.
     *
     * @return The command class
     */
    public Collection<Command> getCommands() {
        return this.bootstrap.values();
    }

    public HarmonyContext getHarmonyContext() {
        return this.harmonyContext;
    }

    public ProjectContext getProjectContext() {
        return this.projectContext;
    }

    // TODO/FIXME remove..
    /**
     * Get the android SDK version.
     * @return the android SDK version
     * @deprecated Use HarmonyContext.getAndroidSdkVersion()
     */
    public static String getAndroidSDKVersion() {
        return HarmonyContext.getAndroidSdkVersion();
    }

    /**
     * Get the harmony project base path.<br/>
     * eg. /
     * @return the harmony base path
     */
    public static String getRootPath() {
        return getInstance().harmonyContext.getBasePath();
    }

    /**
     * Get the android project folder.<br/>
     * eg. /app/
     * @return the android project folder
     */
    public static String getProjectPath() {
        return getInstance().harmonyContext.getProjectPath();
    }

    /**
     * Get the android project path.<br/>
     * eg./app/android/
     * @return the android project path
     */
    public static String getProjectAndroidPath() {
        return getInstance().harmonyContext.getProjectAndroidPath();
    }

    /**
     * Get the bundles path.<br/>
     * eg. /vendor/
     * @return the bundles path
     */
    public static String getBundlePath() {
        return getInstance().harmonyContext.getBundlesPath();
    }

    /**
     * Get library identified by given name.
     * @param libraryName The library name
     * @return The library file
     * @throws HarmonyException
     */
    public static File getLibrary(final String libraryName) {
        File lib = getInstance().harmonyContext.getLibrary(libraryName);
        return lib;
    }

    /**
     * Get library license identified by given library name.
     * @param libraryName The library name
     * @return The library license file
     */
    public static File getLibraryLicense(final String libraryName) {
        return getInstance().harmonyContext.getLibraryLicense(libraryName);
    }

    /**
     * Get the libraries path.<br/>
     * eg. /lib/
     * @return the libraries path
     */
    public static String getLibsPath() {
        return getInstance().harmonyContext.getLibsPath();
    }

    /**
     * Get current bundle path.
     * @return The current bundle path
     */
    public static String getCurrentBundlePath() {
        return getInstance().harmonyContext.getCurrentBundleFolder();
    }

    /**
     * Gets the path of the given command.
     * @param command The command
     * @return The path
     */
    public static String getCommandPath(Class<? extends Command> command) {
        return getInstance().commandBundleFolders.get(command);
    }

    /**
     * Get the templates path.
     * @return the template path
     */
    public static String getTemplatesPath() {
        return getInstance().harmonyContext.getTemplatesPath();
    }

    /**
     * @return the templateFolders
     */
    public static Map<String, File> getTemplateFolders() {
        return getInstance().templateFolders;
    }

    private static String getVersion() {
        return getVersion(Harmony.class);
    }

    private static String getVersion(Class<?> clas) {
        Package objPackage = clas.getPackage();
        String version = objPackage.getImplementationVersion();

        if (Strings.isNullOrEmpty(version)) {
            version = "DEVELOPPMENT";
        }

        return version;
    }
}
