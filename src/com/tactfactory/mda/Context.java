package com.tactfactory.mda;

import java.io.File;

import com.google.common.base.Strings;
import com.tactfactory.mda.utils.ConsoleUtils;

public final class Context {
	/** Delimiter. */
	public static final String DELIMITER = "/";
	
	private static String projectForlder	= "app"		+ DELIMITER;
	private static String libraryFolder 	= "lib"		+ DELIMITER;
	private static String bundleFolder 		= "vendor"	+ DELIMITER;
	private static String templateFolder 	= "tpl"		+ DELIMITER;
	private static String projectBaseFolder = "android" + DELIMITER;
	
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
	
	public Context() {
		// For root case
		File baseDir = this.detectAppTree(new File(harmonyPath));
		
		// Clean binary case (for /bin and /vendor/**/bin)
		if (baseDir == null && harmonyPath.endsWith("bin/")) {
			File predictiveBaseDir = new File(harmonyPath).getParentFile();
			
			ConsoleUtils.displayDebug("Eclipse Mode : " + harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}
		
		if (baseDir == null && harmonyPath.endsWith("harmony.jar")) {
			File predictiveBaseDir = new File(harmonyPath)
					.getParentFile()
					.getParentFile()
					.getParentFile();
			
			ConsoleUtils.displayDebug("Console Mode : " + harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}
		
		
		// For vendor/tact-core case
		if (baseDir == null) {
			File predictiveBaseDir = 
					new File(harmonyPath)
						.getParentFile()
						.getParentFile()
						.getParentFile();
			
			ConsoleUtils.displayDebug("Other Mode : " + harmonyPath);
			baseDir = this.detectAppTree(predictiveBaseDir);
		}
		
		//For Emma
		if (baseDir == null) {
			File predictiveBaseDir = 
					new File(harmonyPath)
						.getParentFile()
						.getParentFile()
						.getParentFile()
						.getParentFile();
			
			ConsoleUtils.displayDebug("Emma Mode : " + harmonyPath);
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
		
		ConsoleUtils.displayDebug("Detect app on " + basePath);
		
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
	 * Check if the given folder contains the App folder.
	 * @param checkPath The path to check.
	 * @return The path if App was found, null if not.
	 */
	private File detectAppTree(final File checkPath) {
		File result = null;
		File[] list = checkPath.listFiles();
		
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
	
}