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
import com.tactfactory.harmony.command.Command;
import com.tactfactory.harmony.command.GeneralCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.AndroidAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/** Harmony main class. */
public final class Harmony {

	/** Harmony version. */
	public static final String VERSION = "0.6.2-DEV";

	/** Singleton of console. */
	private static Harmony instance;

	/**
	 * Get Harmony instance (Singleton).
	 * @return The harmony instance
	 */
	public static Harmony getInstance() {
		if (Harmony.instance == null) {
			try {
				new Harmony();
			} catch (Exception e) {
				ConsoleUtils.displayError(e);
			}
		}
		return Harmony.instance;
	}

	/** Context of execution. */
	private final Context context = new Context();

	/** Bootstrap. */
	private final Map<Class<?>, Command> bootstrap =
			new ConcurrentHashMap<Class<?>, Command>();

	/** Template folders. */
	private final Map<String, File> templateFolders =
			new HashMap<String, File>();
	

	/** Command classes associated to bundle folder. */
	private final Map<Class<?>, String> commandBundleFolders =
			new HashMap<Class<?>, String>();

	/** Constructor.
	 * @throws Exception PluginManager failure
	 */
	private Harmony() throws Exception {
		this.loadPlugins(new File(this.context.getBundlesPath()));

		Locale.setDefault(Locale.US);
		Harmony.instance = this;
	}

	/**
	 * @param pluginBaseDirectory The plugin base directory
	 */
	private void loadPlugins(final File pluginBaseDirectory) {
		// Cache
		final JSPFProperties props = new JSPFProperties();
		/*props.setProperty(PluginManager.class, "cache.enabled", "true");

		//optional
		props.setProperty(PluginManager.class, "cache.mode",    "weak");
		props.setProperty(PluginManager.class, "cache.file",    "jspf.cache");*/

		/** PluginManager. */
		PluginManager pluginManager = null;
				

		// Filters
		final IOFileFilter includeFilter =
				FileFilterUtils.suffixFileFilter(".jar");
		final IOFileFilter excludeFilter =
				FileFilterUtils.notFileFilter(
							FileFilterUtils.nameFileFilter("lib"));

		// Check list of Bundles .jar
		final Collection<File> plugins = TactFileUtils.listFiles(
				pluginBaseDirectory,
				includeFilter,
				excludeFilter);

		// Add Bundles to Plugin Manager &  foldertemplate
		for (File plugin : plugins) {
			// TODO : clean plugin manager mechanic for folders
			pluginManager = PluginManagerFactory.createPluginManager(props);
			
			// Load bundles
			ConsoleUtils.displayDebug(
					"Load plugins : " + plugin.getName());
			pluginManager.addPluginsFrom(plugin.toURI());
			
			// Template bundles
			final File templateFolderFile =
					plugin.getParentFile().getAbsoluteFile();
			this.templateFolders.put(plugin.getName(), templateFolderFile);
			ConsoleUtils.displayDebug(
					"Load templates from : " + templateFolderFile);
		
			
			// Process extensions commands
			final PluginManagerUtil pmu = new PluginManagerUtil(pluginManager);
			final Collection<Command> commands = pmu.getPlugins(Command.class);

			// Bootstrap all commands
			for (final Command command : commands) {
				this.bootstrap.put(command.getClass(), command);
				this.commandBundleFolders.put(command.getClass(), 
						templateFolderFile.getAbsolutePath());
			}
			
			pluginManager.shutdown();
		}

		

	}

	/** 
	 * Initialize Harmony.
	 * @throws Exception if current working path is unavailable.
	 */
	public void initialize() throws Exception {
		ConsoleUtils.display(
				"Current Working Path: ", 
				new File(".").getCanonicalPath());

		// Check name space
		if (Strings.isNullOrEmpty(
				ApplicationMetadata.INSTANCE.getProjectNameSpace())) {

			// get project namespace from AndroidManifest.xml
			final File manifest = new File(String.format("%s/%s",
					this.context.getProjectAndroidPath(),
					"AndroidManifest.xml"));

			if (manifest.exists()) {
				ApplicationMetadata.INSTANCE.setProjectNameSpace(
						ProjectDiscover.getNameSpaceFromManifest(manifest));
			}

			// get project name from configs.xml
			/*final File config = new File(String.format("%s/%s",
					this.context.getProjectAndroidPath(),
					"/res/values/configs.xml"));		//FIXME path by adapter

			if (config.exists()) {
				ApplicationMetadata.INSTANCE.setName(
						ProjectDiscover.getProjectNameFromConfig(config));
			}*/
			// TODO MATCH : Voir avec Mickael pertinence d'utiliser le build.xml
			// pour récupérer le project name
			final File config = new File(String.format("%s/%s",
					this.context.getProjectAndroidPath(),
					"build.xml"));

			if (config.exists()) {
				ApplicationMetadata.INSTANCE.setName(
						ProjectDiscover.getProjectNameFromConfig(config));
			}

			// get SDK from local.properties
			final String localProp = String.format("%s/%s",
					this.context.getProjectAndroidPath(),
					"local.properties");
			final File localPropFile = new File(localProp);
			

			// and target from project.properties
			final File projPropFile = new File(String.format("%s/%s",
					this.context.getProjectAndroidPath(),
					"project.properties"));


			if (localPropFile.exists()) {
				ApplicationMetadata.setAndroidSdkPath(
						ProjectDiscover.getSdkDirFromPropertiesFile(
								localProp));
			}

			String target = 
					ProjectDiscover.getTargetFromPropertiesFile(
							projPropFile);
			if (target == null) {
				target = AndroidAdapter.DEFAULT_TARGET;
			}
			ApplicationMetadata.INSTANCE.setAndroidTarget(target);

		} else {
			final String[] projectNameSpaceData =
					ApplicationMetadata.INSTANCE.getProjectNameSpace()
							.split(Context.DELIMITER);

			ApplicationMetadata.INSTANCE.setName(
					projectNameSpaceData[projectNameSpaceData.length - 1]);
		}

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
				ProjectDiscover.getAndroidSdkVersion());
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
				Context.setCurrentBundleFolder(
						this.commandBundleFolders.get(baseCommand.getClass()) 
						+ "/");
				
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
	 * @return BaseCommand object
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

	/**
	 * Get the android SDK version.
	 * @return the android SDK version
	 */
	public static String getAndroidSDKVersion() {
		return ProjectDiscover.getAndroidSdkVersion();
	}

	// TODO/FIXME remove..
	/**
	 * Get the harmony project base path.<br/>
	 * eg. /
	 * @return the harmony base path
	 */
	public static String getRootPath() {
		return getInstance().context.getBasePath();
	}

	/**
	 * Get the android project folder.<br/>
	 * eg. /app/
	 * @return the android project folder
	 */
	public static String getProjectPath() {
		return getInstance().context.getProjectPath();
	}

	/**
	 * Get the android project path.<br/>
	 * eg./app/android/
	 * @return the android project path
	 */
	public static String getProjectAndroidPath() {
		return getInstance().context.getProjectAndroidPath();
	}

	/**
	 * Get the bundles path.<br/>
	 * eg. /vendor/
	 * @return the bundles path
	 */
	public static String getBundlePath() {
		return getInstance().context.getBundlesPath();
	}
	
	/**
	 * Get library identified by given name.
	 * @param libraryName The library name
	 * @return The library file
	 */
	public static File getLibrary(final String libraryName) {
		File lib = getInstance().context.getLibrary(libraryName);
		return lib;
	}
	
	/**
	 * Get library license identified by given library name.
	 * @param libraryName The library name
	 * @return The library license file
	 */
	public static File getLibraryLicense(final String libraryName) {
		return getInstance().context.getLibraryLicense(libraryName);
	}

	/**
	 * Get the harmony core path.<br/>
	 * eg. /vendor/tact-core or /bin
	 * @return the harmony path
	 */
	/*public static String getHarmonyPath() {
		return getInstance().context.getHarmonyPath();
	}*/

	/**
	 * Get the libraries path.<br/>
	 * eg. /lib/
	 * @return the libraries path
	 */
	public static String getLibsPath() {
		return getInstance().context.getLibsPath();
	}

	/**
	 * Get current bundle path.
	 * @return The current bundle path
	 */
	public static String getCurrentBundlePath() {
		return Context.getCurrentBundleFolder();
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
		return getInstance().context.getTemplatesPath();
	}

	/**
	 * @return the templateFolders
	 */
	public static Map<String, File> getTemplateFolders() {
		return getInstance().templateFolders;
	}
}
