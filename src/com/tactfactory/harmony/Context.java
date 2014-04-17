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
import java.net.URI;
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
	private static final String projectFolder        = "app" + DELIMITER;

	/** Library folder. */
	private static final String libraryFolder        = "lib" + DELIMITER;

	/** Bundle folder. */
	private static final String bundleFolder         = "vendor" + DELIMITER;

	/** Template folder. */
	private static final String templateFolder       = "tpl" + DELIMITER;

	/** Project base folder. */
	private static final String projectBaseFolder    = "android" + DELIMITER;
	
	/** Path of harmony.jar. or Binary */
    private String harmonyPath;
    
	/** Current bundle folder. */
	private String currentBundleFolder;

	/** Path of Harmony base. */
	private String basePath = new File("./").getAbsolutePath();

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
	
	/**
	 * Contructor.
	 */
	public Context() {
	    this.harmonyPath = URI.create(Harmony.class
	            .getProtectionDomain()
	            .getCodeSource()
	            .getLocation()
	            .getPath()).getPath();
	    
		File harmonyFile = new File(this.harmonyPath);
		File baseDir = this.getBaseDir(harmonyFile);

		if (baseDir != null) {
			// Transform PATH_BASE !!!
			this.basePath = baseDir.getPath().toString() + "/";
		} else {
			// For any other case
			ConsoleUtils.displayError(new Exception(
					"INVALID FOLDERS TREE. APP FOLDER MISSING."));
			System.exit(-1);
		}

		ConsoleUtils.displayDebug("Detect app on " + basePath);

		// Set Path
		this.projectPath 		= this.basePath + projectFolder;
		this.projectBasePath	= this.projectPath + projectBaseFolder;
		this.libraryPath 		= this.basePath + libraryFolder;

		if (Strings.isNullOrEmpty(bundlePath)) { // TODO check why..
			this.bundlePath 	= this.basePath + bundleFolder;
		}

		//TODO remove by bundle path
		this.templatePath 	= templateFolder;
		
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
		return this.harmonyPath;
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
				if (dir.getPath().endsWith(projectFolder.replace("/", ""))) {
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
	public void setCurrentBundleFolder(String folder) {
		this.currentBundleFolder = folder;
	}
	
	/**
	 * Sets the current bundle folder.
	 * @return the current bundle folder path
	 */
	public String getCurrentBundleFolder() {
		return this.currentBundleFolder;
	}
	
	/**
	 * Get the base directory of harmony.
	 * @return File representing base directory.
	 */
	private File getBaseDir(File harmonyFile) {
		// For root case
		File baseDir = this.detectAppTree(harmonyFile);

		// Clean binary case (for /bin and /vendor/**/bin)
		if (baseDir == null && this.harmonyPath.endsWith("bin/")) {
			final File predictiveBaseDir =
					harmonyFile.getParentFile();
			
			baseDir = this.detectAppTree(predictiveBaseDir);
			
			if (baseDir != null) {
				ConsoleUtils.displayDebug("Eclipse Mode : " + this.harmonyPath);
			}
		}

		if (baseDir == null && this.harmonyPath.endsWith("harmony.jar")) {
			final File predictiveBaseDir = harmonyFile
					.getParentFile()
					.getParentFile()
					.getParentFile();

			baseDir = this.detectAppTree(predictiveBaseDir);
			
			if (baseDir != null) {
				ConsoleUtils.displayDebug("Console Mode : " + this.harmonyPath);
			}
		}
		
		//For Gradle
		if (baseDir == null) {
			final File predictiveBaseDir =
					harmonyFile
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile();

			baseDir = this.detectAppTree(predictiveBaseDir);
			
			if (baseDir != null) {
                ConsoleUtils.displayDebug("Gradle Mode : " + this.harmonyPath);
			}
		}
		
		//For Gradle Emma
		if (baseDir == null) {
			final File predictiveBaseDir =
					harmonyFile
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile();

			baseDir = this.detectAppTree(predictiveBaseDir);
			
			if (baseDir != null) {
                ConsoleUtils.displayDebug(
                        "Gradle Emma Mode : " + this.harmonyPath);
			}
		}
		
		return baseDir;
	}
}
