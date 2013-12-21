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

import com.google.common.base.Strings;
import com.tactfactory.harmony.dependencies.libraries.LibraryPool;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Context class.
 */
public final class Context {
	/** Delimiter. */
	public static final String DELIMITER = "/";
	
	/** Library pool. */
	private final LibraryPool libPool = new LibraryPool();

	/** Project folder. */
	private static String projectForlder	= "app"		+ DELIMITER;

	/** Library folder. */
	private static String libraryFolder 	= "lib"		+ DELIMITER;

	/** Bundle folder. */
	private static String bundleFolder 		= "vendor"	+ DELIMITER;

	/** Template folder. */
	private static String templateFolder 	= "tpl"		+ DELIMITER;

	/** Project base folder. */
	private static String projectBaseFolder = "android" + DELIMITER;
	
	/** Current bundle folder. */
	private static String currentBundleFolder;

	/** Path of Harmony base. */
	private String basePath = new File("./").getAbsolutePath(); // /

	/** Path of harmony.jar. or Binary */
	private static String harmonyPath =
			Harmony.class
			.getProtectionDomain()
			.getCodeSource()
			.getLocation()
			.toString()
			.substring("file:".length());

	/** Path of project (app folder in Harmony root).<br/>
	 * eg. /app/
	 */
	private String projectPath = "";

	/** Project space. <br/>
	 * eg. /app/android/
	 */
	private String projectBasePath;

	/** Path of bundles.<br/>
	 * eg. /vendor/
	 */
	private String bundlePath;

	/** Path of libraries.<br/>
	 * eg. /lib/
	 */
	private String libraryPath;

	//TODO remove by bundle path

	/** Path of templates.<br/>
	 * eg. /vendor/../tpl/
	 */
	private String templatePath;

	/** Path of sub-libraries.<br/>
	 * eg. /vendor/../lib/
	 */
	//private String librarySubPath;

	// Temporizes...
	/** Symfony path. */
	public static final String SYMFONY_PATH = "D:/Site/wamp/www/Symfony";

	/**
	 * Contructor.
	 */
	public Context() {
		// For root case
		File baseDir = this.detectAppTree(new File(harmonyPath));

		// Clean binary case (for /bin and /vendor/**/bin)
		if (baseDir == null && harmonyPath.endsWith("bin/")) {
			final File predictiveBaseDir =
					new File(harmonyPath).getParentFile();

			ConsoleUtils.displayDebug("Eclipse Mode : ", harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}

		if (baseDir == null && harmonyPath.endsWith("harmony.jar")) {
			final File predictiveBaseDir = new File(harmonyPath)
					.getParentFile()
					.getParentFile()
					.getParentFile();

			ConsoleUtils.displayDebug("Console Mode : ", harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}


		// For vendor/tact-core case
		if (baseDir == null) {
			final File predictiveBaseDir =
					new File(harmonyPath)
						.getParentFile()
						.getParentFile()
						.getParentFile();

			ConsoleUtils.displayDebug("Other Mode : ", harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}

		//For Emma
		if (baseDir == null) {
			final File predictiveBaseDir =
					new File(harmonyPath)
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile();

			ConsoleUtils.displayDebug("Emma Mode : ", harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}

		if (baseDir != null) {
			// Transform PATH_BASE !!!
			this.basePath = baseDir.getPath().toString() + "/";
		} else {
			// For any other case
			ConsoleUtils.displayError(new Exception(
					"INVALID FOLDERS TREE. APP FOLDER MISSING."));
			System.exit(-1);
		}

		ConsoleUtils.displayDebug("Detect app on ", basePath);

		// Set Path
		this.projectPath 		= this.basePath + projectForlder;
		this.projectBasePath	= this.projectPath + projectBaseFolder;
		this.libraryPath 		= this.basePath + libraryFolder;

		if (Strings.isNullOrEmpty(bundlePath)) { // TODO check why..
			this.bundlePath 	= this.basePath + bundleFolder;
		}

		//TODO remove by bundle path
		this.templatePath 	= templateFolder;
				//TactFileUtils.absoluteToRelativePath(
				//PATH_BASE + templateFolder, PATH_BASE);
		
		this.libPool.parseLibraries(new File(this.bundlePath));
	}


	/** eg. /
	 * @return the executePath
	 */
	public String getBasePath() {
		return this.basePath;
	}

	/** Get project path <br/>
	 * eg. /app/
	 *
	 * @return the projectPath
	 */
	public String getProjectPath() {
		return this.projectPath;
	}

	/** eg. /app/android/
	 * @return the projectAndroidPath
	 */
	public String getProjectAndroidPath() {
		return this.projectBasePath;
	}

	/** eg. /vendor/
	 * @return the bundlePath
	 */
	public String getBundlesPath() {
		return this.bundlePath;
	}

	/** eg. /vendor/tact-core/ or /bin
	 * @return the harmonyPath
	 */
	public String getHarmonyPath() {
		return harmonyPath;
	}

	/** eg. /lib/
	 * @return the libraryPath
	 */
	public String getLibsPath() {
		return this.libraryPath;
	}

	/**
	 * @return the templatePath
	 */
	public String getTemplatesPath() {
		return this.templatePath;
	}
	
	/**
	 * @param libraryName The library to get
	 * @return The library File
	 */
	public File getLibrary(final String libraryName) {
		return this.libPool.getLibrary(libraryName);
	}
	
	/**
	 * @param libraryName The library to get
	 * @return The library license File
	 */
	public File getLibraryLicense(final String libraryName) {
		return this.libPool.findLibraryLicense(libraryName);
	}

	/**
	 * Check if the given folder contains the App folder.
	 * @param checkPath The path to check.
	 * @return The path if App was found, null if not.
	 */
	private File detectAppTree(final File checkPath) {
		File result = null;
		final File[] list = checkPath.listFiles();

		if (list != null) {
			for (File dir : list) {
				if (dir.getPath().endsWith(projectForlder.replace("/", ""))) {
					result = checkPath;
					break;
				}
			}
		}

		return result;
	}
	
	/**
	 * Sets the current bundle folder.
	 * @param folder The folder path
	 */
	public static void setCurrentBundleFolder(String folder) {
		currentBundleFolder = folder;
	}
	
	/**
	 * Sets the current bundle folder.
	 * @return the current bundle folder path
	 */
	public static String getCurrentBundleFolder() {
		return currentBundleFolder;
	}

}
